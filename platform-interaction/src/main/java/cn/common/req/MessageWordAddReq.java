package cn.common.req;

import pro.skywalking.validation.NotEmpty;
import lombok.Data;

import java.io.*;

/**
* 交流消息实体

*/
@Data
public class MessageWordAddReq implements Serializable {

    private static final long serialVersionUID = -5140572779659372610L;

    /**
     * 接收人ID
     */
    @NotEmpty(message = "接收人ID->不可为空")
    private String recipientId;

    /**
     * 消息内容
     */
    @NotEmpty(message = "消息内容->不可为空")
    private String messageContent;

}
