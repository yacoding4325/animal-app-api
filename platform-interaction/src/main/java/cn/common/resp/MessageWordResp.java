package cn.common.resp;

import pro.skywalking.req.platform.base.BaseResp;
import lombok.Data;

import java.io.*;

/**
* 交流消息实体
*/
@Data
public class MessageWordResp extends BaseResp implements Serializable {

    private static final long serialVersionUID = 6041024411929296910L;

    /**
     * 业务主键ID->"messageWordId"
     */
    private String messageWordId;

    /**
     * 发送人ID
     */
    private String senderId;

    /**
     * 接收人ID
     */
    private String recipientId;

    /**
     * 消息内容
     */
    private String messageContent;

}
