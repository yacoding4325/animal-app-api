package cn.common.req;

import pro.skywalking.req.base.BasePageReq;
import lombok.Data;

import java.io.*;

/**
* 交流消息实体
*/
@Data
public class MessageWordReq extends BasePageReq implements Serializable {

    private static final long serialVersionUID = -8091019161504989910L;

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
