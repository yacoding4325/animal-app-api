package cn.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 审核状态的枚举
 */
@Getter
@AllArgsConstructor
public enum AuditStatusEnum {

    /**
     * 通过
     */
    APPROVED ("APPROVED","通过"),

    /**
     * 驳回
     */
    REJECT("REJECT","驳回"),

    /**
     * 待处理
     */
    WAIT_AUDIT("WAIT_AUDIT","待审核");


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
