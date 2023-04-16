package cn.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 业务系统错误码枚举
 */
@Getter
@AllArgsConstructor
public enum BizErrorCode {

    /**
     * 领养信息不存在
     */
    ADOPTION_DATA_NOT_EXIST("200001","领养信息不存在"),

    /**
     * 已经领养过该宠物
     */
    ALREADY_ADOPTED_ERROR("0000021","已经领养过该宠物"),


    ;

    /**
     * 错误码code
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
