package cn.common.repository.repository.platform;

import pro.skywalking.repository.BaseRepository;
import cn.common.repository.entity.platform.AuthPermission;
import cn.common.repository.repository.result.AuthPermissionResult;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @Description: 系统权限->Repository

*/
@Mapper
public interface AuthPermissionRepository extends BaseRepository<AuthPermission> {

    /**
    * 根据业务主键批量删除
    * @title: AuthRoleRepository.java05
    * @param mainIdList 业务主键ID集合
    * @return List
     */
    @Delete(
        "<script>" +
        " DELETE FROM auth_permission WHERE 1=1 AND  " +
        " auth_permission_id IN " +
        " <foreach collection='mainIdList' item= 'item' index= 'index' open='(' separator=',' close=')' >" +
        "          #{item} " +
        " </foreach>" +
        "</script>"
        )
    void batchDeleteItem(@Param("mainIdList") List<String> mainIdList);

    /**
     * 查询角色具备的权限
     * @param authRoleId 角色ID
     * @return List
     */
    @Select("<script>" +
            " SELECT " +
            " authPermission.id AS id, " +
            " authPermission.auth_permission_id AS authPermissionId, " +
            " authPermission.parent_id AS parentId, " +
            " authPermission.permission_type AS permissionType, " +
            " authPermission.sort_index AS sortIndex, " +
            " authPermission.permission_code AS permissionCode, " +
            " authPermission.permission_icon AS permissionIcon, " +
            " authPermission.permission_path AS permissionPath, " +
            " authPermission.permission_name AS permissionName, " +
            " authPermission.permission_remark AS permissionRemark, " +
            " authPermission.create_time AS createTime, " +
            " authPermission.update_time AS updateTime " +
            "FROM " +
            " auth_permission authPermission " +
            " LEFT JOIN auth_role_permission rolePermission  " +
            " ON rolePermission.auth_permission_id = authPermission.auth_permission_id " +
            " WHERE 1=1 AND rolePermission.auth_role_id = #{authRoleId} " +
            "</script>")
    List<AuthPermissionResult> queryAuthRolePermission(@Param("authRoleId") String authRoleId);

   /**
    * 查询用户具备的权限
    * @param authUserId 用户ID
    * @return List
    */
   @Select("<script>" +
           " SELECT " +
           " authPermission.id AS id, " +
           " authPermission.auth_permission_id AS authPermissionId, " +
           " authPermission.parent_id AS parentId, " +
           " authPermission.permission_type AS permissionType, " +
           " authPermission.sort_index AS sortIndex, " +
           " authPermission.permission_code AS permissionCode, " +
           " authPermission.permission_icon AS permissionIcon, " +
           " authPermission.permission_path AS permissionPath, " +
           " authPermission.permission_name AS permissionName, " +
           " authPermission.permission_remark AS permissionRemark, " +
           " authPermission.create_time AS createTime, " +
           " authPermission.update_time AS updateTime " +
           "FROM " +
           " auth_permission authPermission " +
           " LEFT JOIN auth_role_permission rolePermission  " +
           " ON rolePermission.auth_permission_id = authPermission.auth_permission_id " +
           " RIGHT JOIN    " +
           " ( " +
           " SELECT  " +
           "  userRole.auth_role_id AS authRoleId, " +
           "  userRole.auth_user_id AS authUserId " +
           "  FROM auth_user authUser LEFT JOIN auth_user_role userRole ON userRole.auth_user_id = authUser.auth_user_id  " +
           "  WHERE 1=1 AND authUser.auth_user_id = #{authUserId}  " +
           " ) authUserRole " +
           " ON rolePermission.auth_role_id = authUserRole.authRoleId " +
           "</script>")
   List<AuthPermissionResult> queryAuthUserPermission(@Param("authUserId") String authUserId);


   /**
    * 查询用户具备的权限
    * @param authUserIdList 用户ID
    * @return List
    */
   @Select("<script>" +
           " SELECT " +
           " authPermission.auth_permission_id AS authPermissionId, " +
           " authPermission.parent_id AS parentId, " +
           " authPermission.permission_type AS permissionType, " +
           " authPermission.sort_index AS sortIndex, " +
           " authPermission.permission_code AS permissionCode, " +
           " authPermission.permission_icon AS permissionIcon, " +
           " authPermission.permission_path AS permissionPath, " +
           " authPermission.permission_name AS permissionName, " +
           " authPermission.permission_remark AS permissionRemark, " +
           " authPermission.create_time AS createTime, " +
           " authPermission.update_time AS updateTime " +
           "FROM " +
           " auth_permission authPermission " +
           " LEFT JOIN auth_role_permission rolePermission  " +
           " ON rolePermission.auth_permission_id = authPermission.auth_permission_id " +
           " RIGHT JOIN    " +
           " ( " +
           " SELECT  " +
           "  userRole.auth_role_id AS authRoleId, " +
           "  userRole.auth_user_id AS authUserId " +
           "  FROM auth_user authUser LEFT JOIN auth_user_role userRole ON userRole.auth_user_id = authUser.auth_user_id  " +
           "  WHERE 1=1 AND authUser.auth_user_id IN  " +
           "  <foreach collection='authUserIdList' item= 'item' index= 'index' open='(' separator=',' close=')' > " +
           "   #{item}" +
           "  </foreach> " +
           " ) authUserRole " +
           " ON rolePermission.auth_role_id = authUserRole.authRoleId " +
           "</script>")
   List<AuthPermissionResult> queryPermissionByAuthUserList(@Param("authUserIdList") List<String> authUserIdList);

}
