package cn.common.service.biz;


import cn.common.req.MessageWordAddReq;
import cn.common.req.MessageWordReq;
import cn.common.resp.MessageWordResp;
import pro.skywalking.req.base.BaseDeleteReq;
import pro.skywalking.resp.page.Pagination;

/**

* @Description: 交流消息相关服务
*/
public interface MessageWordService {

    /**
     * 新增
     * @param addReq 新增Req
     */
    void addItem(MessageWordAddReq addReq);


    /**
     * 批量删除信息
     * @param req 需要被删除的信息
     */
    void batchDeleteItem(BaseDeleteReq req);

    /**
     * 分页查询
     * @param  pageReq 分页查询Req
     * @return Pagination
     */
    Pagination<MessageWordResp> queryByPage(
        MessageWordReq pageReq);


}
