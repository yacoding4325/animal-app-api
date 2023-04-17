package cn.common.resp;

import pro.skywalking.req.platform.base.BaseResp;
import lombok.Data;

import java.io.*;

/**
* 攻略实体

*/
@Data
public class FeedingStrategyResp extends BaseResp implements Serializable {

    private static final long serialVersionUID = -8073718508895105768L;

    /**
     * 业务主键ID->"feedingStrategyId"
     */
    private String feedingStrategyId;

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
