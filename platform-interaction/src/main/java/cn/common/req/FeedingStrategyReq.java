package cn.common.req;


import lombok.Data;
import pro.skywalking.req.base.BasePageReq;

import java.io.*;

/**
* 攻略实体
*/
@Data
public class FeedingStrategyReq extends BasePageReq implements Serializable {

    private static final long serialVersionUID = 5190506131540089590L;

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
