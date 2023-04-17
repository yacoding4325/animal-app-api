package cn.common.req;

import lombok.Data;

import java.io.*;

/**
* 救助站实体
*/
@Data
public class RescueStationAddReq implements Serializable {

    private static final long serialVersionUID = 187843102457255097L;

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
