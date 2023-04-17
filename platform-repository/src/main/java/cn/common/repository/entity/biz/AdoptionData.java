package cn.common.repository.entity.biz;

import pro.skywalking.entity.BaseEntity;
import pro.skywalking.utils.SnowflakeIdWorker;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
* 待领养信息实体
*/
@Data
@TableName("adoption_data")
public class AdoptionData extends BaseEntity implements Serializable {


    private static final long serialVersionUID = 3007272426493110451L;

    /**
     * 业务主键ID
     */
    private String adoptionDataId = SnowflakeIdWorker.uniqueMainId();

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
     * 领养状态 1 待领养 2 已领养 3 领养审核中 3 领养审核中
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
    private Boolean neuterStatus = Boolean.FALSE;

    /**
     * 介绍
     */
    private String presentation;

    /**
     * 是否驱虫
     */
    private Boolean repellentStatus = Boolean.FALSE;

    /**
     * 是否接种
     */
    private Boolean immuneStatus = Boolean.FALSE;

}
