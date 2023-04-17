package cn.common.service.impl.biz;

import pro.skywalking.collection.CollectionUtils;
import pro.skywalking.helper.PageBuilder;
import pro.skywalking.req.base.BaseDeleteReq;
import pro.skywalking.resp.page.Pagination;
import pro.skywalking.utils.BaseUtil;
import pro.skywalking.utils.CheckParam;
import pro.skywalking.utils.SnowflakeIdWorker;
import cn.common.repository.entity.biz.RescueStation;
import cn.common.repository.repository.biz.RescueStationRepository;
import cn.common.req.RescueStationAddReq;
import cn.common.req.RescueStationReq;
import cn.common.resp.RescueStationResp;
import cn.common.service.platform.AuthUserService;
import cn.common.service.biz.RescueStationService;
import cn.common.service.data.ItemCriteriaBuilder;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
* @Description: 首页分类相关服务方法实现
*/

@Service("rescueStationService")
@Slf4j
public class RescueStationServiceImpl implements RescueStationService {

    @Resource
    private RescueStationRepository rescueStationRepository;

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private AuthUserService authUserService;

    @Resource
    private ItemCriteriaBuilder itemCriteriaBuilder;

    /**
     * 新增
     * @author: create by singer - Singer email:singer-coder@qq.com
     * @date 2021/2/15
     * @param addReq 新增Req
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
        public void addItem(RescueStationAddReq addReq){
        log.info(">>>>>>>>>>>>>>>>>新增Req {} <<<<<<<<<<<<<<<<", JSON.toJSONString(addReq));
        RescueStation entity = mapperFacade.map(addReq, RescueStation.class);
        try {
            String appUserId = authUserService.currentAppUserId();
            BaseUtil.setFieldValueNotNull(entity);
            entity.setRescueStationId(SnowflakeIdWorker.uniqueMainId());
            entity.setOperatorId(appUserId);
        } catch (Exception e) {
            log.error("新增->设置为空的属性失败 {} , {} ",e.getMessage(),e);
        }
        rescueStationRepository.insert(entity);
    }

    /**
     * 批量删除信息
     * @author: create by singer - Singer email:singer-coder@qq.com
     * @date 2021/2/2
     * @param req 需要被删除的信息
     */
    @Override
    public void batchDeleteItem(BaseDeleteReq req){
        List<String> mainIdList = req.getMainIdList();
        if(CollectionUtils.isEmpty(mainIdList)) {
            return;
        }
        rescueStationRepository.batchDeleteItem(mainIdList);
    }


    /**
      * 查询所有救助站信息
      * @author: create by singer - Singer email:singer-coder@qq.com
      * @date 2021/12/18
      * @return java.util.List
      */
    @Override
    public List<RescueStationResp> queryAllStation(){
        List<RescueStation> rescueStationList = rescueStationRepository.selectList(new LambdaQueryWrapper<>());
        if(CollectionUtils.isEmpty(rescueStationList)){
            return Lists.newArrayList();
        }
        return mapperFacade.mapAsList(rescueStationList,RescueStationResp.class);
    }

    /**
     * 分页查询
     * @author: create by singer - Singer email:singer-coder@qq.com
     * @date 2021/2/15
     * @param  pageReq 分页查询Req
     * @return Pagination
     */
    @Override
    public Pagination<RescueStationResp> queryByPage(
        RescueStationReq pageReq){
        log.info(">>>>>>>>>>>>>>>>>分页查询Req {} <<<<<<<<<<<<<<<<", JSON.toJSONString(pageReq));
        //构建查询条件
        LambdaQueryWrapper<RescueStation> pageWrapper = new LambdaQueryWrapper<>();
        itemCriteriaBuilder.rigidCriteria(pageWrapper,true);
        setPageCriteria(pageWrapper,pageReq);
        pageWrapper.orderBy(true,false, RescueStation::getCreateTime);
        //开始分页
        Page<Object> page = PageHelper.startPage(pageReq.getCurrentPage(), pageReq.getItemsPerPage());
        List<RescueStation> pageList = rescueStationRepository.selectList(pageWrapper);
        if (CollectionUtils.isEmpty(pageList)) {
            return PageBuilder.buildPageResult(page,new ArrayList<>());
        }
        List<RescueStationResp> respList =
            mapperFacade.mapAsList(pageList, RescueStationResp.class);
        Integer startIndex = (pageReq.getItemsPerPage() * pageReq.getCurrentPage()) - pageReq.getItemsPerPage() + 1;
        AtomicInteger idBeginIndex = new AtomicInteger(startIndex);
            respList.stream().forEach(item -> {
                item.setId(Integer.valueOf(idBeginIndex.getAndIncrement()).longValue());
        });
        return PageBuilder.buildPageResult(page,respList);
    }

    /**
     * 设置分页条件
     * @author: create by singer - Singer email:singer-coder@qq.com
     * @date 2021/5/31
     * @param pageWrapper 查询条件
     * @param pageReq 分页插件
     * @return
     */
    private void setPageCriteria(LambdaQueryWrapper<RescueStation> pageWrapper, RescueStationReq pageReq){

         if(!CheckParam.isNull(pageReq.getStationName())){
             pageWrapper.like(RescueStation::getStationName,"%"+pageReq.getStationName());
         }

         if(!CheckParam.isNull(pageReq.getCapacity())){
             pageWrapper.like(RescueStation::getCapacity,"%"+pageReq.getCapacity());
         }

         if(!CheckParam.isNull(pageReq.getStationAddress())){
             pageWrapper.like(RescueStation::getStationAddress,"%"+pageReq.getStationAddress());
         }

        if(!CheckParam.isNull(pageReq.getRescueRegion())){
            pageWrapper.like(RescueStation::getRescueRegion,"%"+pageReq.getRescueRegion());
        }

        if(!CheckParam.isNull(pageReq.getPhoneNumber())){
            pageWrapper.like(RescueStation::getPhoneNumber,"%"+pageReq.getPhoneNumber());
        }
    }

}
