package cn.common.repository.entity.platform;

import pro.skywalking.entity.BaseEntity;
import pro.skywalking.utils.SnowflakeIdWorker;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
* 用户角色实体
3
*/
@Data
@TableName("auth_user_role")
public class AuthUserRole extends BaseEntity implements Serializable {


    private static final long serialVersionUID = 2749458880903906040L;

    /**
     * 业务主键ID
     */
    private String authUserRoleId = SnowflakeIdWorker.uniqueMainId();


    /**
     * 系统角色ID
     */
    private String authRoleId;

    /**
     * 系统用户ID
     */
    private String authUserId;

}
