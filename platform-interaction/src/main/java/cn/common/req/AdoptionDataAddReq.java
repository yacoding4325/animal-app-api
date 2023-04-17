package cn.common.req;

import pro.skywalking.validation.NotEmpty;
import lombok.Data;

import java.io.*;

/**
* 待领养信息实体
*/
@Data
public class AdoptionDataAddReq implements Serializable {

    private static final long serialVersionUID = 2667927684123638714L;

    /**
     * 宠物类型
     */
    @NotEmpty(message = "宠物类型->不可为空")
    private String adoptionType;

    /**
     * 宠物名称
     */
    @NotEmpty(message = "宠物名称->不可为空")
    private String adoptionName;

    /**
     * 宠物图片
     */
    @NotEmpty(message = "宠物图片->不可为空")
    private String adoptionUrl;

    /**
     * 宠物性别
     */
    @NotEmpty(message = "宠物性别->不可为空")
    private String gender;

    /**
     * 宠物年龄
     */
    @NotEmpty(message = "宠物年龄->不可为空")
    private Integer age;

    /**
     * 所属救助站
     */
    @NotEmpty(message = "所属救助站->不可为空")
    private String rescueStationId;

    /**
     * 物种
     */
    @NotEmpty(message = "物种->不可为空")
    private String species;

    /**
     * 品种
     */
    @NotEmpty(message = "品种->不可为空")
    private String breed;

    /**
     * 是否绝育
     */
    @NotEmpty(message = "是否绝育->不可为空")
    private Boolean neuterStatus = Boolean.FALSE;

    /**
     * 介绍
     */
    @NotEmpty(message = "介绍->不可为空")
    private String presentation;

    /**
     * 是否驱虫
     */
    @NotEmpty(message = "是否驱虫->不可为空")
    private Boolean repellentStatus = Boolean.FALSE;

    /**
     * 是否接种
     */
    @NotEmpty(message = "是否接种->不可为空")
    private Boolean immuneStatus = Boolean.FALSE;

}
