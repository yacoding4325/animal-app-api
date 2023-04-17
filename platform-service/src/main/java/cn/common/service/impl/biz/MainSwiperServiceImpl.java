package cn.common.service.impl.biz;

import cn.common.repository.entity.biz.MainSwiper;
import cn.common.repository.repository.biz.MainSwiperRepository;
import cn.common.req.MainSwiperReq;
import cn.common.resp.MainSwiperResp;
import cn.common.service.biz.MainSwiperService;
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
import pro.skywalking.helper.PageBuilder;
import pro.skywalking.req.base.BaseDeleteReq;
import pro.skywalking.resp.page.Pagination;
import pro.skywalking.utils.CheckParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**

* @Description: APP首页轮播图信息相关服务方法实现
*/
@Service("mainSwiperService")
@Slf4j
public class MainSwiperServiceImpl implements MainSwiperService {

    @Resource
    private MainSwiperRepository mainSwiperRepository;

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private ItemCriteriaBuilder itemCriteriaBuilder;


    /**
     * 批量删除信息
     * @param req 需要被删除的信息
     */
    @Override
    public void batchDeleteItem(BaseDeleteReq req){
        List<String> mainIdList = req.getMainIdList();
        if(CollectionUtils.isEmpty(mainIdList)) {
            return;
        }
        List<MainSwiper> entityList = mainSwiperRepository.selectList(
            new LambdaQueryWrapper<MainSwiper>().in(MainSwiper::getMainSwiperId,mainIdList));
        entityList.stream().forEach(item -> {
            mainSwiperRepository.deleteById(item);
        });
    }

    /**
     * 查询所有信息
     * @param
     * @return java.util.List
     */
    @Override
    public List<MainSwiperResp> queryAllMainSwiper(){
        List<MainSwiper> entityList = mainSwiperRepository.selectList(new LambdaQueryWrapper<>());
            if(CollectionUtils.isEmpty(entityList)){
            return Lists.newArrayList();
        }
        return mapperFacade.mapAsList(entityList,MainSwiperResp.class);
    }

    /**
     * 分页查询
     * @param  pageReq 分页查询Req
     * @return Pagination
     */
    @Override
    public Pagination<MainSwiperResp> queryByPage(
        MainSwiperReq pageReq){
        log.info(">>>>>>>>>>>>>>>>>分页查询Req {} <<<<<<<<<<<<<<<<", JSON.toJSONString(pageReq));
        //构建查询条件
        LambdaQueryWrapper<MainSwiper> pageWrapper = new LambdaQueryWrapper<>();
        itemCriteriaBuilder.rigidCriteria(pageWrapper,true);
        setPageCriteria(pageWrapper,pageReq);
        pageWrapper.orderBy(true,false,MainSwiper::getCreateTime);
        //开始分页
        Page<Object> page = PageHelper.startPage(pageReq.getCurrentPage(), pageReq.getItemsPerPage());
        List<MainSwiper> pageList = mainSwiperRepository.selectList(pageWrapper);
        if (CollectionUtils.isEmpty(pageList)) {
            return PageBuilder.buildPageResult(page,new ArrayList<>());
        }
        List<MainSwiperResp> respList =
            mapperFacade.mapAsList(pageList, MainSwiperResp.class);
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
    private void setPageCriteria(LambdaQueryWrapper<MainSwiper> pageWrapper, MainSwiperReq pageReq){

         if(!CheckParam.isNull(pageReq.getMainUrl())){
             pageWrapper.like(MainSwiper::getMainUrl,"%"+pageReq.getMainUrl());
         }

         if(!CheckParam.isNull(pageReq.getMainTitle())){
             pageWrapper.like(MainSwiper::getMainTitle,"%"+pageReq.getMainTitle());
         }

         if(!CheckParam.isNull(pageReq.getRouterUrl())){
             pageWrapper.like(MainSwiper::getRouterUrl,"%"+pageReq.getRouterUrl());
         }
    }
}
