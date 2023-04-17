package cn.common.service.biz;


import cn.common.req.RescueStationAddReq;
import cn.common.req.RescueStationReq;
import cn.common.resp.RescueStationResp;
import pro.skywalking.req.base.BaseDeleteReq;
import pro.skywalking.resp.page.Pagination;

import java.util.List;

/**
* @Description: 救助站相关服务
*/
public interface RescueStationService {

    /**
     * 新增
     * @param addReq 新增Req
     */
    void addItem(RescueStationAddReq addReq);

    /**
     * 批量删除信息
     * @param req 需要被删除的信息
     */
    void batchDeleteItem(BaseDeleteReq req);

    /**
     * 查询所有救助站信息
     * @return java.util.List
     */
    List<RescueStationResp> queryAllStation();

    /**
     * 分页查询
     * @param  pageReq 分页查询Req
     * @return Pagination
     */
    Pagination<RescueStationResp> queryByPage(
        RescueStationReq pageReq);

}
