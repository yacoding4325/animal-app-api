package cn.common.repository.entity.platform;

import pro.skywalking.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
* 字典信息实体
* @title: PlatformDict.java
*/
@Data
@TableName("platform_dict")
public class PlatformDict extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 2513084942873169004L;

    /**
     * 业务主键ID
     */
    private String platformDictId;


    /**
     * 业务字典类型
     */
    private String dictType;

    /**
     * 键
     */
    private String dictKey;

    /**
     * 值
     */
    private String dictValue;

    /**
     * 排序字段
     */
    private Integer sortIndex;

    /**
     * 字典备注
     */
    private String dictRemark;

}
