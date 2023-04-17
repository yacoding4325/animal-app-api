package cn.common.service.biz;


import cn.common.req.MainSwiperReq;
import cn.common.resp.MainSwiperResp;
import pro.skywalking.req.base.BaseDeleteReq;
import pro.skywalking.resp.page.Pagination;

import java.util.List;

/**

* @Description: APP首页轮播图信息相关服务
*/
public interface MainSwiperService {

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
    List<MainSwiperResp> queryAllMainSwiper();

    /**
     * 分页查询
     * @param  pageReq 分页查询Req
     * @return Pagination
     */
    Pagination<MainSwiperResp> queryByPage(
        MainSwiperReq pageReq);


}
