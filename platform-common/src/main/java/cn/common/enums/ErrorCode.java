package cn.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 系统错误码枚举
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {


    /**
      * 成功
      */
    SUCCESS("200","成功"),

    /**
     * 参数格式错误
     */
    PARAM_ERROR("000004","参数格式错误"),

    /**
     * 业务流程异常,拒绝处理
     */
    BUSINESS_REFUSE("403", "业务流程异常,拒绝处理"),

    /**
     * 业务处理异常
     */
    ERROR("500","网络异常"),


    /**
     * 用户名或密码错误
     */
    PASSWORD_OR_USER_ERROR("1080","用户名或密码错误"),

    /**
     * 登录失效,需要验证登录
     */
    LOGIN_ERROR_CODE("501","登录失效,需要验证登录"),

    /**
     * 参数缺失
     */
    NO_REQUIRED_PARAM_ERROR("0000005","参数缺失"),

    /**
     * 用户认证信息丢失
     */
    NO_AUTH_INFO_ERROR("0000006","用户认证信息丢失"),

    /**
     * 认证错误
     */
    AUTH__ERROR("0000007","认证错误"),

    /**
     * 没有权限
     */
    E_502("0000008","没有权限"),

    /**
     * 旧密码输入错误
     */
    ERROR_OLD_PASSWORD("0000009","旧密码输入错误"),

    /**
     * 修改密码失败
     */
    ERROR_UPDATE_PASSWORD("0000010","修改密码失败"),

    /**
     * token过期,需要重新登陆
     */
    ERROR_NEED_RE_LOGIN("0000011","token过期,需要重新登陆"),

    /**
     * 需要token
     */
    ERROR_NO_TOKEN("0000012","需要token"),

    /**
     * token无效,需要重新登陆
     */
    ERROR_TOKEN_INVALID("0000013","token无效,需要重新登陆"),

    /**
     * 图形验证码错误
     */
    ERROR_KAPTCHA_CODE_INVALID("0000014","图形验证码错误"),

    /**
     * 成功
     */
    ERROR_USER_EXISTED("0000015","用户已经存在"),

    /**
     * 短信验证码错误
     */
    ERROR_MESSAGE_INVALID("0000016","短信验证码错误"),

    /**
     * 该用户不存在
     */
    ERROR_APP_USER_NOT_EXISTED("0000017","该用户不存在"),

    /**
     * 重复密码不相等
     */
    ERROR_APP_REPEAT_PWD_NOT_EQUAL_ERROR("0000018","重复密码不相等"),

    /**
     * 超级管理员无法删除
     */
    AN_NOT_DELETE_SUPER("0000019","超级管理员无法删除"),

    /**
     * 验证码无效
     */
    VERIFY_CODE_ERROR("0000020","验证码无效"),

    /**
     * 用户被封禁，请联系管理员
     */
    APP_USER_LOCKED("0000021","用户被封禁，请联系管理员"),

    /**
     * 数学计算错误
     */
    MATHEMATICAL_CAL_ERROR("0000024","数学计算错误"),

    /**
     * 宠物不存在
     */
    ADOPTION_DATA_NOT_EXIST("0000020","宠物不存在"),

    /**
     * 已经领养过该宠物
     */
    ALREADY_ADOPTED_ERROR("0000021","已经领养过该宠物"),

    /**
     * 设置为空的属性为空
     */
    SET_EMPTY_FIELD_ERROR("0000026","设置为空的属性为出现异常"),



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
