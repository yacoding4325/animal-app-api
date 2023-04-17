package cn.common.resp;

import pro.skywalking.req.platform.base.BaseResp;
import lombok.Data;

import java.io.*;

/**
* 救助站实体
*/
@Data
public class RescueStationResp extends BaseResp implements Serializable {

    private static final long serialVersionUID = -5713369697353162441L;

    /**
     * 业务主键ID->"rescueStationId"
     */
    private String rescueStationId;

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
