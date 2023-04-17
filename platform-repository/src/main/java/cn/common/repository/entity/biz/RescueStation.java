package cn.common.repository.entity.biz;

import pro.skywalking.entity.BaseEntity;
import pro.skywalking.utils.SnowflakeIdWorker;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
* 救助站实体
*/
@Data
@TableName("rescue_station")
public class RescueStation extends BaseEntity implements Serializable {


    private static final long serialVersionUID = 4959491863882149656L;

    /**
     * 业务主键ID
     */
    private String RescueStationId = SnowflakeIdWorker.uniqueMainId();

    /**
     * 救助站名称
     */
    private String stationName;

    /**
     * 救助站容量
     */
    private String capacity;

    /**
     * 救助站联系方式
     */
    private String phoneNumber;

    /**
     * 救助站地区
     */
    private String rescueRegion;

    /**
     * 救助站地址
     */
    private String stationAddress;

}
