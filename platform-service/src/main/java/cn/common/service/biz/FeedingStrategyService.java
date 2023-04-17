package cn.common.service.biz;


import cn.common.req.FeedingStrategyReq;
import cn.common.resp.FeedingStrategyResp;
import pro.skywalking.req.base.BaseDeleteReq;
import pro.skywalking.resp.page.Pagination;

import java.util.List;

/**
* @Description: 养宠攻略相关服务
*/
public interface FeedingStrategyService {

    /**
     * 批量删除信息
     * @param req 需要被删除的信息
     */
    void batchDeleteItem(BaseDeleteReq req);

    /**
     * 查询所有信息
     * @param
     * @return java.util.List
     */
    List<FeedingStrategyResp> queryAllFeedingStrategy();

    /**
     * 分页查询
     * @param  pageReq 分页查询Req
     * @return Pagination
     */
    Pagination<FeedingStrategyResp> queryByPage(
        FeedingStrategyReq pageReq);

}
