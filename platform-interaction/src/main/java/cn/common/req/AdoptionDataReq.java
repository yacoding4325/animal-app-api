package cn.common.req;

import lombok.Data;
import pro.skywalking.req.base.BasePageReq;

import java.io.*;
import java.math.BigInteger;

/**
* 待领养信息实体
*/
@Data
public class AdoptionDataReq extends BasePageReq implements Serializable {


    private static final long serialVersionUID = 1709142379870557658L;

    /**
     * 业务主键ID->"adoptionDataId"
     */
    private String adoptionDataId;

    /**
     * 宠物类型
     */
    private String adoptionType;

    /**
     * 宠物名称
     */
    private String adoptionName;

    /**
     * 宠物图片
     */
    private String adoptionUrl;

    /**
     * 领养状态 1 待领养 2 已领养 3 领养审核中
     */
    private Integer adoptionStatus;

    /**
     * 领养人
     */
    private String adopter;

    /**
     * 宠物性别
     */
    private String gender;

    /**
     * 宠物年龄
     */
    private Integer age;

    /**
     * 所属救助站ID
     */
    private String rescueStationId;

    /**
     * 物种
     */
    private String species;

    /**
     * 品种
     */
    private String breed;

    /**
     * 是否绝育
     */
    private Boolean neuterStatus;

    /**
     * 介绍
     */
    private String presentation;

    /**
     * 是否驱虫
     */
    private Boolean repellentStatus;

    /**
     * 是否接种
     */
    private Boolean immuneStatus;

    /**
     * 查询限制的数量
     */
    private Integer topLimitCount = BigInteger.ZERO.intValue();

}
