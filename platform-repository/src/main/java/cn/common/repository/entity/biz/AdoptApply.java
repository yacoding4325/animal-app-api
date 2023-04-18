package cn.common.repository.entity.biz;

import pro.skywalking.entity.BaseEntity;
import pro.skywalking.utils.SnowflakeIdWorker;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
* 领养信息实体
* @title: AdoptApply.java

*/
@Data
@TableName("adopt_apply")
public class AdoptApply extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 4409006398576119549L;

    /**
     * 业务主键ID
     */
    private String adoptApplyId = SnowflakeIdWorker.uniqueMainId();

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
     * 审核状态 通过 APPROVED 驳回 REJECT
     */
    private String auditStatus;

}
