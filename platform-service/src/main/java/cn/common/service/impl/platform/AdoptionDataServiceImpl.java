package cn.common.service.impl.platform;

import cn.common.constants.BizConstants;
import cn.common.enums.AdoptStatusEnum;
import pro.skywalking.collection.CollectionUtils;
import pro.skywalking.configuration.oss.AliOssService;
import pro.skywalking.configuration.redis.RedisRepository;
import pro.skywalking.constants.BaseConstant;
import pro.skywalking.helper.PageBuilder;
import pro.skywalking.resp.page.Pagination;
import pro.skywalking.utils.BaseUtil;
import pro.skywalking.utils.CheckParam;
import pro.skywalking.utils.SnowflakeIdWorker;
import cn.common.internal.ViewRecordMeta;
import cn.common.repository.entity.biz.AdoptionData;
import cn.common.repository.repository.biz.AdoptionDataRepository;
import cn.common.req.AdoptPetLimitReq;
import cn.common.req.AdoptionDataAddReq;
import cn.common.req.AdoptionDataReq;
import cn.common.resp.AdoptionDataResp;
import cn.common.service.biz.AdoptionDataService;
import cn.common.service.platform.AuthUserService;
import cn.common.service.data.ItemCriteriaBuilder;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
* @Description: 首页分类相关服务方法实现
*/
@Service("adoptionDataService")
@Slf4j
public class AdoptionDataServiceImpl implements AdoptionDataService {


    @Resource
    private RedisRepository redisRepository;

    @Resource
    private AdoptionDataRepository adoptionDataRepository;

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private BaseConstant baseConstant;

    @Resource
    private AuthUserService authUserService;

    @Resource
    private ItemCriteriaBuilder itemCriteriaBuilder;

    @Resource
    private AliOssService aliOssService;


    /**
     * 新增
     * @param addReq 新增Req
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void addItem(AdoptionDataAddReq addReq){
        log.info(">>>>>>>>>>>>>>>>>新增Req {} <<<<<<<<<<<<<<<<", JSON.toJSONString(addReq));
        AdoptionData entity = mapperFacade.map(addReq, AdoptionData.class);
        try {
            String appUserId = authUserService.currentAppUserId();
            BaseUtil.setFieldValueNotNull(entity);
            //领养状态 1 待领养 2 已领养 3 领养审核中 3 领养审核中
            entity.setAdoptionStatus(AdoptStatusEnum.WAIT_ADOPT.getCode());
            entity.setAdoptionDataId(SnowflakeIdWorker.uniqueMainId());
            entity.setOperatorId(appUserId);
        } catch (Exception e) {
            log.error("新增->设置为空的属性失败 {} , {} ",e.getMessage(),e);
        }
        adoptionDataRepository.insert(entity);
    }

    /**
      * 所有待领养信息
      * @return java.util.List
      */
    @Override
    public List<AdoptionDataResp> allAdoptionData(){
        List<AdoptionData> adoptionDataList = adoptionDataRepository.selectList(
                new LambdaQueryWrapper<>());
        if(CollectionUtils.isEmpty(adoptionDataList)){
            return Lists.newArrayList();
        }
        List<AdoptionDataResp> respList = mapperFacade.mapAsList(adoptionDataList, AdoptionDataResp.class);
        return respList;
    }

    /**
      * 查询当前用户的查看记录
      * @param
      * @return java.util.List
      */
    @Override
    public List<ViewRecordMeta> queryViewRecord(){
        String currentAppUserId = authUserService.currentAppUserId();
        String viewRecordCache = redisRepository.get(BizConstants.VIEW_RECORD_CACHE_PREFIX + currentAppUserId);
        if(!CheckParam.isNull(viewRecordCache)){
            List<ViewRecordMeta> viewRecordMetaList = JSON.parseArray(viewRecordCache, ViewRecordMeta.class);
            return viewRecordMetaList;
        }
        return Lists.newArrayList();
    }

    /**
      * 提交宠物查看记录
      * @param mainId 宠物ID
      * @return
      */
    @Override
    public void setViewPetData(String mainId){
        AdoptionData adoptionData = adoptionDataRepository
                .selectOne(new LambdaQueryWrapper<AdoptionData>().eq(AdoptionData::getAdoptionDataId, mainId));
        if(CheckParam.isNull(adoptionData)){
            return;
        }
        ViewRecordMeta meta = new ViewRecordMeta();
        AdoptionDataResp data = mapperFacade.map(adoptionData, AdoptionDataResp.class);
        meta.setData(data);
        String currentAppUserId = authUserService.currentAppUserId();
        String viewRecordCache = redisRepository.get(BizConstants.VIEW_RECORD_CACHE_PREFIX + currentAppUserId);
        //如果之前存在则进行新增
        if(CheckParam.isNull(viewRecordCache)){
            List<ViewRecordMeta> viewRecordMetaList = Lists.newArrayList();
            viewRecordMetaList.add(meta);
            redisRepository.set(BizConstants.VIEW_RECORD_CACHE_PREFIX + currentAppUserId,JSON.toJSONString(viewRecordMetaList));
        }
        List<ViewRecordMeta> viewRecordMetaList = JSON.parseArray(viewRecordCache, ViewRecordMeta.class);
        Optional<ViewRecordMeta> optional =
                viewRecordMetaList.stream().filter(item -> !CheckParam.isNull(item.getData()))
                        .filter(item -> StrUtil.equalsIgnoreCase(item.getData().getAdoptionDataId(), mainId))
                        .findFirst();
        if(optional.isPresent()){
            return;
        }else{
            viewRecordMetaList.add(meta);
        }
        redisRepository.set(BizConstants.VIEW_RECORD_CACHE_PREFIX + currentAppUserId,JSON.toJSONString(viewRecordMetaList));
    }

    /**
      * 查询宠物详情
      * @param mainId 宠物信息ID
      * @return
      */
    @Override
    public AdoptionDataResp queryParticulars(String mainId){
        AdoptionData adoptionData = adoptionDataRepository
                .selectOne(new LambdaQueryWrapper<AdoptionData>().eq(AdoptionData::getAdoptionDataId, mainId));
        if(CheckParam.isNull(adoptionData)){
            return new AdoptionDataResp();
        }
        return mapperFacade.map(adoptionData,AdoptionDataResp.class);
    }


    /**
      * 查询指定数量的动物
      * @param req
      * @return java.util.List
      */
    @Override
    public List<AdoptionDataResp> queryTopLimit(AdoptPetLimitReq req){
        //构建查询条件
        LambdaQueryWrapper<AdoptionData> pageWrapper = new LambdaQueryWrapper<>();
        itemCriteriaBuilder.rigidCriteria(pageWrapper,true);
        pageWrapper.orderBy(true,false,AdoptionData::getCreateTime);
        //开始分页
        PageHelper.startPage(BigInteger.ONE.intValue(), req.getTopLimitCount());
        List<AdoptionData> pageList = adoptionDataRepository.selectList(pageWrapper);
        if (CollectionUtils.isEmpty(pageList)) {
            return Lists.newArrayList();
        }
        List<AdoptionDataResp> respList =
                mapperFacade.mapAsList(pageList, AdoptionDataResp.class);
        return respList;
    }

    /**
     * 分页查询
     * @param  pageReq 分页查询Req
     * @return Pagination
     */
    @Override
    public Pagination<AdoptionDataResp> queryByPage(
        AdoptionDataReq pageReq){
        log.info(">>>>>>>>>>>>>>>>>分页查询Req {} <<<<<<<<<<<<<<<<", JSON.toJSONString(pageReq));
        //构建查询条件
        LambdaQueryWrapper<AdoptionData> pageWrapper = new LambdaQueryWrapper<>();
        itemCriteriaBuilder.rigidCriteria(pageWrapper,true);
        setPageCriteria(pageWrapper,pageReq);
        pageWrapper.orderBy(true,false,AdoptionData::getCreateTime);
        //开始分页
        Page<Object> page = PageHelper.startPage(pageReq.getCurrentPage(), pageReq.getItemsPerPage());
        List<AdoptionData> pageList = adoptionDataRepository.selectList(pageWrapper);
        if (CollectionUtils.isEmpty(pageList)) {
            return PageBuilder.buildPageResult(page,new ArrayList<>());
        }
        List<AdoptionDataResp> respList =
            mapperFacade.mapAsList(pageList, AdoptionDataResp.class);
        Integer startIndex = (pageReq.getItemsPerPage() * pageReq.getCurrentPage()) - pageReq.getItemsPerPage() + 1;
        AtomicInteger idBeginIndex = new AtomicInteger(startIndex);
            respList.stream().forEach(item -> {
                item.setId(Integer.valueOf(idBeginIndex.getAndIncrement()).longValue());
        });
        return PageBuilder.buildPageResult(page,respList);
    }

    /**
     * 设置分页条件
     * @param pageWrapper 查询条件
     * @param pageReq 分页插件
     * @return
     */
    private void setPageCriteria(LambdaQueryWrapper<AdoptionData> pageWrapper, AdoptionDataReq pageReq){

        if(!CheckParam.isNull(pageReq.getAdoptionType())){
            pageWrapper.like(AdoptionData::getAdoptionType,"%"+pageReq.getAdoptionType());
        }

        if(!CheckParam.isNull(pageReq.getAdoptionName())){
            pageWrapper.like(AdoptionData::getAdoptionName,"%"+pageReq.getAdoptionName());
        }

        if(!CheckParam.isNull(pageReq.getAdoptionUrl())){
            pageWrapper.like(AdoptionData::getAdoptionUrl,"%"+pageReq.getAdoptionUrl());
        }

        if(!CheckParam.isNull(pageReq.getAdoptionStatus())){
            pageWrapper.like(AdoptionData::getAdoptionStatus,"%"+pageReq.getAdoptionStatus());
        }

        if(!CheckParam.isNull(pageReq.getAdopter())){
            pageWrapper.like(AdoptionData::getAdopter,"%"+pageReq.getAdopter());
        }

        if(!CheckParam.isNull(pageReq.getGender())){
            pageWrapper.eq(AdoptionData::getGender,pageReq.getGender());
        }

        if(!CheckParam.isNull(pageReq.getRescueStationId())){
            pageWrapper.eq(AdoptionData::getRescueStationId,pageReq.getRescueStationId());
        }

        if(!CheckParam.isNull(pageReq.getAge())){
            pageWrapper.eq(AdoptionData::getAge,pageReq.getAge());
        }

        if(!CheckParam.isNull(pageReq.getSpecies())){
            pageWrapper.eq(AdoptionData::getSpecies,pageReq.getSpecies());
        }

        if(!CheckParam.isNull(pageReq.getBreed())){
            pageWrapper.eq(AdoptionData::getBreed,pageReq.getBreed());
        }

        if(!CheckParam.isNull(pageReq.getNeuterStatus())){
            pageWrapper.eq(AdoptionData::getNeuterStatus,pageReq.getNeuterStatus());
        }

        if(!CheckParam.isNull(pageReq.getRepellentStatus())){
            pageWrapper.eq(AdoptionData::getRepellentStatus,pageReq.getRepellentStatus());
        }

        if(!CheckParam.isNull(pageReq.getImmuneStatus())){
            pageWrapper.eq(AdoptionData::getImmuneStatus,pageReq.getImmuneStatus());
        }

        if(!CheckParam.isNull(pageReq.getPresentation())){
            pageWrapper.like(AdoptionData::getPresentation,"%"+pageReq.getPresentation());
        }
    }

}
