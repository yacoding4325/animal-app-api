package cn.common.service.impl.biz;

import pro.skywalking.collection.CollectionUtils;
import pro.skywalking.helper.PageBuilder;
import pro.skywalking.req.base.BaseDeleteReq;
import pro.skywalking.resp.page.Pagination;
import pro.skywalking.utils.BaseUtil;
import pro.skywalking.utils.CheckParam;
import pro.skywalking.utils.SnowflakeIdWorker;
import cn.common.repository.entity.biz.MessageWord;
import cn.common.repository.repository.biz.MessageWordRepository;
import cn.common.req.MessageWordReq;
import cn.common.req.MessageWordAddReq;
import cn.common.resp.MessageWordResp;
import cn.common.service.platform.AuthUserService;
import cn.common.service.biz.MessageWordService;
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
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
* @Description: 留言信息相关服务方法实现
*/
@Service("messageWordService")
@Slf4j
public class MessageWordServiceImpl implements MessageWordService {

    @Resource
    private MessageWordRepository messageWordRepository;

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
        public void addItem(MessageWordAddReq addReq){
        log.info(">>>>>>>>>>>>>>>>>新增Req {} <<<<<<<<<<<<<<<<", JSON.toJSONString(addReq));
        MessageWord entity = mapperFacade.map(addReq, MessageWord.class);
        try {
            String currentAppUserId = authUserService.currentAppUserId();
            BaseUtil.setFieldValueNotNull(entity);
            entity.setMessageWordId(SnowflakeIdWorker.uniqueMainId());
            entity.setSenderId(currentAppUserId);
            entity.setOperatorId(currentAppUserId);
        } catch (Exception e) {
            log.error("新增->设置为空的属性失败 {} , {} ",e.getMessage(),e);
        }
        messageWordRepository.insert(entity);
    }

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
        List<MessageWord> messageWordList = messageWordRepository.selectList(
                new LambdaQueryWrapper<MessageWord>().in(MessageWord::getMessageWordId, mainIdList));
        if(CollectionUtils.isEmpty(messageWordList)){
            return;
        }
        messageWordList.stream().forEach(item -> {
            messageWordRepository.deleteById(item);
        });
    }

    /**
     * 分页查询
     * @param  pageReq 分页查询Req
     * @return Pagination
     */
    @Override
    public Pagination<MessageWordResp> queryByPage(
        MessageWordReq pageReq){
        log.info(">>>>>>>>>>>>>>>>>分页查询Req {} <<<<<<<<<<<<<<<<", JSON.toJSONString(pageReq));
        //构建查询条件
        LambdaQueryWrapper<MessageWord> pageWrapper = new LambdaQueryWrapper<>();
        itemCriteriaBuilder.rigidCriteria(pageWrapper,true);
        setPageCriteria(pageWrapper,pageReq);
        pageWrapper.orderBy(true,false, MessageWord::getCreateTime);
        //开始分页
        Page<Object> page = PageHelper.startPage(pageReq.getCurrentPage(), pageReq.getItemsPerPage());
        List<MessageWord> pageList = messageWordRepository.selectList(pageWrapper);
        if (CollectionUtils.isEmpty(pageList)) {
            return PageBuilder.buildPageResult(page,new ArrayList<>());
        }
        List<MessageWordResp> respList =
            mapperFacade.mapAsList(pageList, MessageWordResp.class);
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
    private void setPageCriteria(LambdaQueryWrapper<MessageWord> pageWrapper, MessageWordReq pageReq){

         if(!CheckParam.isNull(pageReq.getSenderId())){
             pageWrapper.eq(MessageWord::getSenderId,pageReq.getSenderId());
         }

         if(!CheckParam.isNull(pageReq.getRecipientId())){
             pageWrapper.eq(MessageWord::getRecipientId,pageReq.getRecipientId());
         }

         if(!CheckParam.isNull(pageReq.getMessageContent())){
             pageWrapper.like(MessageWord::getMessageContent,pageReq.getMessageContent());
         }
    }

}
