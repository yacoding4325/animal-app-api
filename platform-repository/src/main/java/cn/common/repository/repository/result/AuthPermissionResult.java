package cn.common.repository.repository.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;


/**
* 系统权限实体

*/
@Data
public class AuthPermissionResult implements Serializable {


    private static final long serialVersionUID = 1091286725554756565L;

    /**
      * 主键ID
      */
    private String id;

    /**
     * 业务主键ID
     */
    private String authPermissionId;

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

    /**
     * 创建时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime = LocalDateTime.ofInstant(Instant.now(), Clock.systemDefaultZone().getZone());

    /**
     * 修改时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime = LocalDateTime.ofInstant(Instant.now(), Clock.systemDefaultZone().getZone());

    /**
     * 创建人
     */
    private String  operatorId;
}
