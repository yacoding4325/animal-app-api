package cn.common.repository.repository.result;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 用户角色关联查询结果
 */
@Data
public class AuthUserRoleResult extends BaseResult implements Serializable {

    private static final long serialVersionUID = -264643901500292816L;

    /**
     * 用户ID
     */
    private String authUserId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 描述信息
     */
    private String roleRemark;

    /**
      * 角色ID
      */
    private String authRoleId;

}
