package cn.common.repository.entity.platform;

import pro.skywalking.entity.BaseEntity;
import pro.skywalking.utils.SnowflakeIdWorker;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
* 系统权限实体
* @title: AuthPermission.java
*/
@Data
@TableName("auth_permission")
public class AuthPermission extends BaseEntity implements Serializable {


    private static final long serialVersionUID = -4949515392142373130L;

    /**
     * 业务主键ID
     */
    private String authPermissionId = SnowflakeIdWorker.uniqueMainId();

    /**
     * 上级权限id
     */
    private String parentId;

    /**
     * 1 页面 2 按钮 3 其他资源
     */
    private Integer permissionType;

    /**
     * 排序
     */
    private Integer sortIndex;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 前端路径
     */
    private String permissionPath;

    /**
     * 权限编码
     */
    private String permissionCode;

    /**
     * 权限图标
     */
    private String permissionIcon;

    /**
     * 备注
     */
    private String permissionRemark;


}
