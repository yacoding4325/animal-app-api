package cn.common.repository.entity.biz;

import pro.skywalking.entity.BaseEntity;
import pro.skywalking.utils.SnowflakeIdWorker;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
* 攻略实体
*/
@Data
@TableName("feeding_strategy")
public class FeedingStrategy extends BaseEntity implements Serializable {


    private static final long serialVersionUID = -2476536266016868498L;

    /**
     * 业务主键ID
     */
    private String feedingStrategyId = SnowflakeIdWorker.uniqueMainId();

    /**
     * 发布人ID
     */
    private String publisherId;

    /**
     * 发布人
     */
    private String publisherName;

    /**
     * 攻略标题
     */
    private String strategyTitle;

    /**
     * 攻略内容
     */
    private String strategyContent;

}
