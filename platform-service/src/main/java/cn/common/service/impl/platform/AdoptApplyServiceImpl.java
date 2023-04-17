package cn.common.service.impl.platform;

import cn.common.enums.AdoptStatusEnum;
import cn.common.enums.AuditStatusEnum;
import cn.common.enums.ErrorCode;
import pro.skywalking.collection.CollectionUtils;
import pro.skywalking.exception.BusinessException;
import pro.skywalking.helper.PageBuilder;
import pro.skywalking.resp.page.Pagination;
import pro.skywalking.utils.BaseUtil;
import pro.skywalking.utils.CheckParam;
import pro.skywalking.utils.SnowflakeIdWorker;
import cn.common.repository.entity.biz.AdoptApply;
import cn.common.repository.entity.biz.AdoptionData;
import cn.common.repository.repository.biz.AdoptionApplyRepository;
import cn.common.repository.repository.biz.AdoptionDataRepository;
import cn.common.req.AdoptApplyAddReq;
import cn.common.req.AdoptionApplyReq;
import cn.common.resp.AdoptApplyResp;
import cn.common.resp.AdoptionDataResp;
import cn.common.service.biz.AdoptApplyService;
import cn.common.service.platform.AuthUserService;
import cn.common.service.data.ItemCriteriaBuilder;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
* @Description: 首页分类相关服务方法实现
*/
@Service("adoptApplyService")
@Slf4j
public class AdoptApplyServiceImpl implements AdoptApplyService {

    @Resource
    private AdoptionApplyRepository adoptionApplyRepository;

    //待领养信息 数据
    @Resource
    private AdoptionDataRepository adoptionDataRepository;

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private AuthUserService authUserService;

    @Resource
    private ItemCriteriaBuilder itemCriteriaBuilder;

    /**
     * 新增
     * @param addReq 新增Req
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
        public void addItem(AdoptApplyAddReq addReq){
        log.info(">>>>>>>>>>>>>>>>>新增Req {} <<<<<<<<<<<<<<<<", JSON.toJSONString(addReq));
        AdoptApply entity = mapperFacade.map(addReq, AdoptApply.class);
        String appUserId = authUserService.currentAppUserId();
        String adoptionDataId = addReq.getAdoptionDataId();
        AdoptionData adoptionData = adoptionDataRepository.selectOne(new LambdaQueryWrapper<AdoptionData>()
                .eq(AdoptionData::getAdoptionDataId, adoptionDataId));
        if(CheckParam.isNull(adoptionData)){
            throw new BusinessException(ErrorCode.ADOPTION_DATA_NOT_EXIST.getCode(),
                    ErrorCode.ADOPTION_DATA_NOT_EXIST.getMessage());
        }
        //查询该宠物是否被领养过
        Long adoptedCount = adoptionApplyRepository.selectCount(new LambdaQueryWrapper<AdoptApply>()
                .eq(AdoptApply::getAdoptionDataId, adoptionDataId)
                .eq(AdoptApply::getPetitioner, appUserId)
                .ne(AdoptApply::getAuditStatus, AuditStatusEnum.REJECT.getCode()));
        if(adoptedCount > 0){
            throw new BusinessException(ErrorCode.ALREADY_ADOPTED_ERROR.getCode(),
                    ErrorCode.ALREADY_ADOPTED_ERROR.getMessage());
        }
        try {
            BaseUtil.setFieldValueNotNull(entity);
            entity.setAdoptionDataId(adoptionDataId);
            entity.setAdoptionRemark(addReq.getAdoptionRemark());
            entity.setAdoptApplyId(SnowflakeIdWorker.uniqueMainId());
            entity.setPetitioner(appUserId);
            entity.setOperatorId(appUserId);
            //审核状态 通过 APPROVED 驳回 REJECT 待审核 WAIT_AUDIT
            entity.setAuditStatus(AuditStatusEnum.WAIT_AUDIT.getCode());
            //领养状态 1 待领养 2 已领养 3 领养审核中
            if(!CheckParam.isNull(adoptionData)){
                adoptionData.setAdoptionStatus(AdoptStatusEnum.AUDITING.getCode());
                adoptionData.setAdopter(appUserId);
                adoptionDataRepository.updateById(adoptionData);
            }
        } catch (Exception e) {
            log.error("新增->设置为空的属性失败 {} , {} ",e.getMessage(),e);
        }
        adoptionApplyRepository.insert(entity);
    }

    /**
     * 分页查询领养申请信息
     * @param  pageReq 分页查询Req
     */
    @Override
    public Pagination<AdoptApplyResp> queryByPage(
        AdoptionApplyReq pageReq){
        log.info(">>>>>>>>>>>>>>>>>分页查询Req {} <<<<<<<<<<<<<<<<", JSON.toJSONString(pageReq));
        //构建查询条件
        LambdaQueryWrapper<AdoptApply> pageWrapper = new LambdaQueryWrapper<>();
        itemCriteriaBuilder.rigidCriteria(pageWrapper,true);
        setPageCriteria(pageWrapper,pageReq);
        pageWrapper.orderBy(true,false, AdoptApply::getCreateTime);
        //开始分页
        Page<Object> page = PageHelper.startPage(pageReq.getCurrentPage(), pageReq.getItemsPerPage());
        List<AdoptApply> pageList = adoptionApplyRepository.selectList(pageWrapper);
        if (CollectionUtils.isEmpty(pageList)) {
            return PageBuilder.buildPageResult(page,new ArrayList<>());
        }
        List<AdoptApplyResp> respList =
            mapperFacade.mapAsList(pageList, AdoptApplyResp.class);
        List<String> adoptionDataIdList =
                respList.stream().map(item -> item.getAdoptionDataId()).distinct().collect(Collectors.toList());
        List<AdoptionData> adoptionDataList = adoptionDataRepository.selectList(
                new LambdaQueryWrapper<AdoptionData>().in(AdoptionData::getAdoptionDataId, adoptionDataIdList));
        HashMap<String, AdoptionData> adoptionDataHashMap = adoptionDataList.stream()
                .collect(Collectors.toMap(AdoptionData::getAdoptionDataId, a -> a, (k1, k2) -> k1, HashMap::new));
        Integer startIndex = (pageReq.getItemsPerPage() * pageReq.getCurrentPage()) - pageReq.getItemsPerPage() + 1;
        AtomicInteger idBeginIndex = new AtomicInteger(startIndex);
            respList.stream().forEach(item -> {
                AdoptionData adoptionData = adoptionDataHashMap.get(item.getAdoptionDataId());
                if(!CheckParam.isNull(adoptionData)){
                    AdoptionDataResp petData = mapperFacade.map(adoptionData, AdoptionDataResp.class);
                    item.setPetData(petData);
                }else{
                    item.setPetData(new AdoptionDataResp());
                }
                item.setId(Integer.valueOf(idBeginIndex.getAndIncrement()).longValue());
        });
        return PageBuilder.buildPageResult(page,respList);
    }

    /**
     * 设置分页条件
     * @param pageWrapper 查询条件
     * @param pageReq 分页插件
     *
     */
    private void setPageCriteria(LambdaQueryWrapper<AdoptApply> pageWrapper, AdoptionApplyReq pageReq){

         if(!CheckParam.isNull(pageReq.getAdoptionDataId())){
             pageWrapper.eq(AdoptApply::getAdoptionDataId,pageReq.getAdoptionDataId());
         }

         if(!CheckParam.isNull(pageReq.getAdoptionRemark())){
             pageWrapper.like(AdoptApply::getAdoptionRemark,"%"+pageReq.getAdoptionRemark());
         }

         if(!CheckParam.isNull(pageReq.getAuditStatus())){
             pageWrapper.eq(AdoptApply::getAuditStatus,pageReq.getAuditStatus());
         }

        if(!CheckParam.isNull(pageReq.getPetitioner())){
            pageWrapper.eq(AdoptApply::getPetitioner,pageReq.getPetitioner());
        }

    }
}
