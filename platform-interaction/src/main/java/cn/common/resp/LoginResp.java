package cn.common.resp;

import lombok.Data;
import pro.skywalking.req.platform.base.BaseResp;

import java.io.Serializable;

/**
* 系统用户实体

*/
@Data
public class LoginResp extends BaseResp implements Serializable {

    private static final long serialVersionUID = -678298128571145347L;

    /**
     * 业务主键ID
     */
    private String appUserId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户编号
     */
    private String userNumber;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 明文密码
     */
    private String decryptionPassword;

    /**
     * 密码(MD5)
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别
     */
    private String gender;

    /**
     * 用户状态 跟随枚举
     */
    private Integer authStatus;

}
