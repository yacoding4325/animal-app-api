package cn.common.repository.entity.platform;

import pro.skywalking.entity.BaseEntity;
import pro.skywalking.utils.SnowflakeIdWorker;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
* 系统日志实体
*/
@Data
@TableName("platform_api_log")
public class PlatformApiLog extends BaseEntity implements Serializable {


    private static final long serialVersionUID = -4006243363645794849L;

    /**
     * 业务主键ID
     */
    private String platformApiLogId = SnowflakeIdWorker.uniqueMainId();


    /**
     * 操作者用户名
     */
    private String userName;

    /**
     * 操作内容
     */
    private String operation;

    /**
     * 耗费时间
     */
    private String operationTime;

    /**
     * 操作方法
     */
    private String method;

    /**
     * 方法参数
     */
    private String params;

    /**
     * 操作地点
     */
    private String location;

    /**
     * IP地址
     */
    private String requestIp;

}
