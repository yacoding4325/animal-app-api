package cn.common.repository.entity.platform;

import pro.skywalking.entity.BaseEntity;
import pro.skywalking.utils.SnowflakeIdWorker;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
* 系统用户实体
*/
@Data
@TableName("auth_user")
public class AuthUser extends BaseEntity implements Serializable {


    private static final long serialVersionUID = -4161211307007064956L;

    /**
     * 业务主键ID
     */
    private String authUserId = SnowflakeIdWorker.uniqueMainId();

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
     * 真实姓名
     */
    private String realName;

    /**
     * 性别
     */
    private String gender;

    /**
     * 用户状态
     */
    private Integer authStatus;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 昵称
     */
    private String nickName;

}
