package cn.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 领养状态的枚举
 */
@Getter
@AllArgsConstructor
public enum AdoptStatusEnum {

    /**
     * 待领养
     */
    WAIT_ADOPT(1,"待领养"),

    /**
     * 已领养
     */
    ADOPTED(2,"已领养"),

    /**
     * 领养审核中
     */
    AUDITING(3,"领养审核中");


    /**
     * 状态码
     */
    private Integer code;

    /**
     * 描述
     */
    private String description;


    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
