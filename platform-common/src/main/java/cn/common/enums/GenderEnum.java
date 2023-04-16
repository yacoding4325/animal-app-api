package cn.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 性别
 */
@Getter
@AllArgsConstructor
public enum GenderEnum {

    /**
     * 男性
     */
    MALE ("male","男性"),

    /**
     * 女生
     */
    FEMALE("female","女生"),

    /**
     * 未知
     */
    UNKNOWN("unknown","未知")

    ;

    /**
     * 状态码
     */
    private String code;

    /**
     * 描述
     */
    private String description;


    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
