package cn.common.repository.repository.platform;

import pro.skywalking.repository.BaseRepository;
import cn.common.repository.entity.platform.AuthUserRole;
import cn.common.repository.repository.result.AuthUserRoleResult;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @Description: 用户角色->Repository

*/
@Mapper
public interface AuthUserRoleRepository extends BaseRepository<AuthUserRole> {

    /**
    * 根据业务主键批量删除
    * @param mainIdList 业务主键ID集合
    * @return List
     */
    @Delete(
        "<script>" +
        " DELETE FROM auth_user_role WHERE 1=1 AND  " +
        " auth_user_role_id IN " +
        " <foreach collection='mainIdList' item='item' index='index' open='(' separator=',' close=')' >" +
        "          #{item} " +
        " </foreach>" +
        "</script>"
        )
    void batchDeleteItem(@Param("mainIdList") List<String> mainIdList);

    /**
     * 根据删除指定用户所有角色

     * @param authUserIdList 用户ID集合
     */
    @Delete(
         "<script>" +
         " DELETE FROM auth_user_role WHERE 1=1 AND  " +
         " auth_user_id IN " +
         " <foreach collection='authUserIdList' item='item' index='index' open='(' separator=',' close=')' >" +
         "          #{item} " +
         " </foreach>" +
         "</script>"
    )
    void batchDeleteByAuthUserId(@Param("authUserIdList") List<String> authUserIdList);

    /**
     * 根据删除指定用户所有角色
     * @param authUserId 用户ID
     */
    @Delete("<script>" +
            "	DELETE FROM auth_user_role WHERE auth_user_id = #{authUserId}	" +
            "</script>")
    void deleteByAuthUserId(@Param("authUserId") String authUserId);


   /**
    * 查询用户具备的角色
    * @title: AuthMenuRepository.java
    * @param authUserIdList 用户ID
    * @return List
    */
   @Select("<script>" +
           " SELECT " +
           " authRole.`id` AS id, " +
           " authRole.`role_name` AS roleName, " +
           " authRole.`role_code` AS roleCode, " +
           " authRole.`auth_role_id` AS authRoleId, " +
           " userRole.`auth_user_id` AS authUserId,  " +
           " userRole.create_time AS createTime, " +
           " userRole.update_time AS updateTime " +
           " FROM " +
           " auth_user_role userRole " +
           " LEFT JOIN auth_role authRole ON userRole.auth_role_id = authRole.auth_role_id  " +
           " WHERE " +
           " 1 = 1" +
           " AND userRole.auth_user_id IN" +
           " <foreach collection='authUserIdList' item= 'item' index= 'index' open='(' separator=',' close=')' > " +
           "          #{item} " +
           " </foreach>" +
           "</script>")
   List<AuthUserRoleResult> queryRoleListByUserIdList(@Param("authUserIdList") List<String> authUserIdList);

   /**
    * 查询用户具备的角色
    * @param authUserId 用户ID
    * @return List
    */
   @Select("<script>" +
           " SELECT " +
           " authRole.`id` AS id, " +
           " authRole.`role_name` AS roleName, " +
           " authRole.`role_code` AS roleCode, " +
           " authRole.`auth_role_id` AS authRoleId, " +
           " userRole.`auth_user_id` AS authUserId,  " +
           " userRole.create_time AS createTime, " +
           " userRole.update_time AS updateTime " +
           " FROM " +
           " auth_user_role userRole " +
           " LEFT JOIN auth_role authRole ON userRole.auth_role_id = authRole.auth_role_id  " +
           " WHERE " +
           " 1 = 1" +
           " AND userRole.auth_user_id = #{authUserId} " +
           "</script>")
   List<AuthUserRoleResult> queryUserRoleList(@Param("authUserId") String authUserId);

}
