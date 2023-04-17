package cn.common.repository.entity.biz;

import pro.skywalking.entity.BaseEntity;
import pro.skywalking.utils.SnowflakeIdWorker;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
* 交流消息实体
* @title: MessageWord.java
* @author create by Singer - Singer email:singer-coder@qq.com
* @date 2019/4/24 11:13
*/
@Data
@TableName("message_word")
public class MessageWord extends BaseEntity implements Serializable {


    private static final long serialVersionUID = -4968865262400251624L;

    /**
     * 业务主键ID
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
