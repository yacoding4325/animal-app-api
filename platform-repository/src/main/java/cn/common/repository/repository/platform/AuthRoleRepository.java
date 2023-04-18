package cn.common.repository.repository.platform;

import pro.skywalking.repository.BaseRepository;
import cn.common.repository.entity.platform.AuthRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**

* @Description: 系统角色->Repository
*/
@Mapper
public interface AuthRoleRepository extends BaseRepository<AuthRole> {

    /**
    * 根据业务主键批量删除
    * @param mainIdList 业务主键ID集合
    * @return List
     */
    @Delete(
        "<script>" +
        " DELETE FROM auth_role WHERE 1=1 AND  " +
        " auth_role_id IN " +
        " <foreach collection='mainIdList' item= 'item' index= 'index' open='(' separator=',' close=')' >" +
        "          #{item} " +
        " </foreach>" +
        "</script>"
        )
    void batchDeleteItem(@Param("mainIdList") List<String> mainIdList);
}
