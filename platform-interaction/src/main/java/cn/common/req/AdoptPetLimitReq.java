package cn.common.req;

import lombok.Data;

import java.io.*;
import java.math.BigInteger;

/**
* 待领养信息实体
* @title: AdoptionData.java
*/
@Data
public class AdoptPetLimitReq implements Serializable {

    private static final long serialVersionUID = 1709142379870557658L;

    /**
     * 业务主键ID->"adoptionDataId"
     */
    private String adoptionDataId;

    /**
     * 宠物名称
     */
    private String adoptionName;


    /**
     * 领养状态 1 待领养 2 已领养 3 领养审核中
     */
    private Integer adoptionStatus;

    /**
     * 查询限制的数量
     */
    private Integer topLimitCount = BigInteger.ZERO.intValue();
}
