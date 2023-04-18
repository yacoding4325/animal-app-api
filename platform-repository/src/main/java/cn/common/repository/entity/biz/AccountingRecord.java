package cn.common.repository.entity.biz;

import pro.skywalking.entity.BaseEntity;
import pro.skywalking.utils.SnowflakeIdWorker;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
* 账务实体
*/
@Data
@TableName("accounting_record")
public class AccountingRecord extends BaseEntity implements Serializable {


    private static final long serialVersionUID = -2347050356295435856L;

    /**
     * 业务主键ID
     */
    private String accountingRecordId = SnowflakeIdWorker.uniqueMainId();

    /**
     * 救助站ID
     */
    private String rescueStationId;

    /**
     * 救助站名称
     */
    private String rescueStationName;

    /**
     * 账务金额
     */
    private BigDecimal accountingAmount;

    /**
     * 行为类型
     */
    private String recordType;

    /**
     * 备注信息
     */
    private String recordRemark;

}
