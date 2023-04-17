package cn.common.repository.entity.biz;

import pro.skywalking.entity.BaseEntity;
import pro.skywalking.utils.SnowflakeIdWorker;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
* APP用户实体
* @title: AppUser.java
*/
@Data
@TableName("app_user")
public class AppUser extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 413941069216448527L;

    /**
     * 业务主键ID
     */
    private String appUserId = SnowflakeIdWorker.uniqueMainId();

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
