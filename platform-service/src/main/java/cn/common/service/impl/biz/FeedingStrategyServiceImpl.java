package cn.common.service.impl.biz;

import cn.common.repository.entity.biz.FeedingStrategy;
import cn.common.repository.repository.biz.FeedingStrategyRepository;
import cn.common.req.FeedingStrategyReq;
import cn.common.resp.FeedingStrategyResp;
import cn.common.service.platform.AuthUserService;
import cn.common.service.biz.FeedingStrategyService;
import cn.common.service.data.ItemCriteriaBuilder;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import pro.skywalking.collection.CollectionUtils;
import pro.skywalking.constants.BaseConstant;
import pro.skywalking.helper.PageBuilder;
import pro.skywalking.req.base.BaseDeleteReq;
import pro.skywalking.resp.page.Pagination;
import pro.skywalking.utils.CheckParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
* @Description: 养宠攻略相关服务方法实现
*/
@Service("feedingStrategyService")
@Slf4j
public class FeedingStrategyServiceImpl implements FeedingStrategyService {

    @Resource
    private FeedingStrategyRepository feedingStrategyRepository;

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private BaseConstant baseConstant;

    @Resource
    private AuthUserService authUserService;

    @Resource
    private ItemCriteriaBuilder itemCriteriaBuilder;

    @Resource
    private HttpServletResponse response;

    @Resource
    private HttpServletRequest request;


    /**
     * 批量删除信息
     * @author: create by singer - Singer email:singer-coder@qq.com
     * @param req 需要被删除的信息
     */
    @Override
    public void batchDeleteItem(BaseDeleteReq req){
        List<String> mainIdList = req.getMainIdList();
        if(CollectionUtils.isEmpty(mainIdList)) {
            return;
        }
        List<FeedingStrategy> entityList = feedingStrategyRepository.selectList(
            new LambdaQueryWrapper<FeedingStrategy>().in(FeedingStrategy::getFeedingStrategyId,mainIdList));
        entityList.stream().forEach(item -> {
            feedingStrategyRepository.deleteById(item);
        });
    }

    /**
    * 查询所有信息
    * @param
    * @return java.util.List
    */
    @Override
    public List<FeedingStrategyResp> queryAllFeedingStrategy(){
        List<FeedingStrategy> entityList = feedingStrategyRepository.selectList(new LambdaQueryWrapper<>());
            if(CollectionUtils.isEmpty(entityList)){
            return Lists.newArrayList();
        }
        return mapperFacade.mapAsList(entityList,FeedingStrategyResp.class);
    }

    /**
     * 分页查询
     * @param  pageReq 分页查询Req
     * @return Pagination
     */
    @Override
    public Pagination<FeedingStrategyResp> queryByPage(
        FeedingStrategyReq pageReq){
        log.info(">>>>>>>>>>>>>>>>>分页查询Req {} <<<<<<<<<<<<<<<<", JSON.toJSONString(pageReq));
        //构建查询条件
        LambdaQueryWrapper<FeedingStrategy> pageWrapper = new LambdaQueryWrapper<>();
        itemCriteriaBuilder.rigidCriteria(pageWrapper,true);
        setPageCriteria(pageWrapper,pageReq);
        pageWrapper.orderBy(true,false,FeedingStrategy::getCreateTime);
        //开始分页
        Page<Object> page = PageHelper.startPage(pageReq.getCurrentPage(), pageReq.getItemsPerPage());
        List<FeedingStrategy> pageList = feedingStrategyRepository.selectList(pageWrapper);
        if (CollectionUtils.isEmpty(pageList)) {
            return PageBuilder.buildPageResult(page,new ArrayList<>());
        }
        List<FeedingStrategyResp> respList =
            mapperFacade.mapAsList(pageList, FeedingStrategyResp.class);
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
    private void setPageCriteria(LambdaQueryWrapper<FeedingStrategy> pageWrapper, FeedingStrategyReq pageReq){

         if(!CheckParam.isNull(pageReq.getPublisherId())){
             pageWrapper.like(FeedingStrategy::getPublisherId,"%"+pageReq.getPublisherId());
         }

         if(!CheckParam.isNull(pageReq.getPublisherName())){
             pageWrapper.like(FeedingStrategy::getPublisherName,"%"+pageReq.getPublisherName());
         }

         if(!CheckParam.isNull(pageReq.getStrategyTitle())){
             pageWrapper.like(FeedingStrategy::getStrategyTitle,"%"+pageReq.getStrategyTitle());
         }

         if(!CheckParam.isNull(pageReq.getStrategyContent())){
             pageWrapper.like(FeedingStrategy::getStrategyContent,"%"+pageReq.getStrategyContent());
         }
    }

}
