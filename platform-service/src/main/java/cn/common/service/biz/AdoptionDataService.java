package cn.common.service.biz;


import cn.common.internal.ViewRecordMeta;
import cn.common.req.AdoptPetLimitReq;
import cn.common.req.AdoptionDataAddReq;
import cn.common.req.AdoptionDataReq;
import cn.common.resp.AdoptionDataResp;
import pro.skywalking.resp.page.Pagination;

import java.util.List;

/**
* @Description: 待领养信息相关服务
*/
public interface AdoptionDataService {

    /**
     * 新增
     * @param addReq 新增Req
     */
    void addItem(AdoptionDataAddReq addReq);

    /**
     * 所有待领养信息
     */
    List<AdoptionDataResp> allAdoptionData();

    /**
     * 提交宠物查看记录
     * @param mainId 宠物ID
     * @return
     */
    void setViewPetData(String mainId);

    /**
     * 查询当前用户的查看记录
     * @param
     * @return java.util.List
     */
    List<ViewRecordMeta> queryViewRecord();

    /**
     * 查询指定数量的动物
     * @param pageReq
     * @return java.util.List
     */
    List<AdoptionDataResp> queryTopLimit(AdoptPetLimitReq pageReq);

    /**
     * 查询宠物详情
     * @param mainId 宠物信息ID
     * @return
     */
    AdoptionDataResp queryParticulars(String mainId);

    /**
     * 分页查询
     * @param  pageReq 分页查询Req
     * @return Pagination
     */
    Pagination<AdoptionDataResp> queryByPage(
        AdoptionDataReq pageReq);

}
