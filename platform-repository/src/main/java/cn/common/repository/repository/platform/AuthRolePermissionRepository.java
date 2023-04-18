package cn.common.repository.repository.platform;

import pro.skywalking.repository.BaseRepository;
import cn.common.repository.entity.platform.AuthRolePermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**

* @Description: 角色权限->Repository
*/
@Mapper
public interface AuthRolePermissionRepository extends BaseRepository<AuthRolePermission> {

    /**
    * 根据业务主键批量删除
    * @param mainIdList 业务主键ID集合
    * @return List
     */
    @Delete(
        "<script>" +
        " DELETE FROM auth_role_permission WHERE 1=1 AND  " +
        " auth_role_permission_id IN " +
        " <foreach collection='mainIdList' item= 'item' index= 'index' open='(' separator=',' close=')' >" +
        "          #{item} " +
        " </foreach>" +
        "</script>"
        )
    void batchDeleteItem(@Param("mainIdList") List<String> mainIdList);

    /**
     * 根据权限ID批量删除指定权限
     * @param authPermissionIdList 权限ID集合
     */
    @Delete(
            "<script>" +
                    " DELETE FROM auth_role_permission WHERE 1=1 AND  " +
                    " auth_permission_id IN " +
                    " <foreach collection='authPermissionIdList' item= 'item' index= 'index' open='(' separator=',' close=')' >" +
                    "    #{item} " +
                    " </foreach>" +
                    "</script>"
    )
    void batchDeleteByAuthPermissionIdList(@Param("authPermissionIdList") List<String> authPermissionIdList);

    /**
     * 根据角色ID批量删除指定权限
     * @param authRoleIdList 角色ID集合
     */
    @Delete(
        "<script>" +
        " DELETE FROM auth_role_permission WHERE 1=1 AND  " +
        " auth_role_id IN " +
        " <foreach collection='authRoleIdList' item= 'item' index= 'index' open='(' separator=',' close=')' >" +
        "    #{item} " +
        " </foreach>" +
        "</script>"
    )
    void batchDeleteByRoleIdList(@Param("authRoleIdList") List<String> authRoleIdList);

    /**
     * 根据角色ID删除指定权限
     * @param authRoleId 角色ID
     */
    @Delete("<script>" +
            " DELETE FROM auth_role_permission WHERE auth_role_id = #{authRoleId}	" +
            "</script>")
    void deleteByRoleId(@Param("authRoleId") String authRoleId);

}
