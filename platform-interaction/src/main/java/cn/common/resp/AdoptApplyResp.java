package cn.common.resp;


import lombok.Data;
import pro.skywalking.req.platform.base.BaseResp;

import java.io.*;

/**
* 领养信息实体
*/
@Data
public class AdoptApplyResp extends BaseResp implements Serializable {

    private static final long serialVersionUID = -4610181594365823581L;

    /**
     * 业务主键ID->"adoptApplyId"
     */
    private String adoptApplyId;

    /**
     * 宠物ID
     */
    private String adoptionDataId;

    /**
     * 申请人
     */
    private String petitioner;

    /**
     * 领养备注
     */
    private String adoptionRemark;

    /**
     * 审核状态 审核状态 通过 APPROVED 驳回 REJECT
     */
    private String auditStatus;

    /**
     * 宠物信息
     */
    private AdoptionDataResp petData;

}
