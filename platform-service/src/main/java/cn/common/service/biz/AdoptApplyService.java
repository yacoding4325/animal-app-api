package cn.common.service.biz;


import cn.common.req.AdoptApplyAddReq;
import cn.common.req.AdoptionApplyReq;
import cn.common.resp.AdoptApplyResp;
import pro.skywalking.resp.page.Pagination;

/**
*领养信息相关服务
*/
public interface AdoptApplyService {

    /**
     * 新增
     * @param addReq 新增Req
     */
    void addItem(AdoptApplyAddReq addReq);


    /**
     * 分页查询领养申请信息
     * @param  pageReq 分页查询Req
     * @return Pagination
     */
    Pagination<AdoptApplyResp> queryByPage(
        AdoptionApplyReq pageReq);

}
