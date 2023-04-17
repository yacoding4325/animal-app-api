package cn.common.req;

import lombok.Data;
import pro.skywalking.req.base.BasePageReq;

import java.io.*;

/**
* 领养信息实体
*/
@Data
public class AdoptionApplyReq extends BasePageReq implements Serializable {

    private static final long serialVersionUID = -5436304126556357899L;

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

}
