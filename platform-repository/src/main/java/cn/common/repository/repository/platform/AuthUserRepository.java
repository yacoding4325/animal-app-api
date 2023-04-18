package cn.common.repository.repository.platform;

import pro.skywalking.repository.BaseRepository;
import cn.common.repository.entity.platform.AuthUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @packageName cn.singer.repository.entity
* @Description: 系统用户->Repository
*/
@Mapper
public interface AuthUserRepository extends BaseRepository<AuthUser> {

    /**
    * 根据业务主键批量删除
    * @title: AuthRoleRepository.java
    * @param mainIdList 业务主键ID集合
    * @return List
     */
    @Delete(
        "<script>" +
        " DELETE FROM auth_user WHERE 1=1 AND  " +
        " auth_user_id IN " +
        " <foreach collection='mainIdList' item= 'item' index= 'index' open='(' separator=',' close=')' >" +
        "          #{item} " +
        " </foreach>"+
        "</script>"
        )
    void batchDeleteItem(@Param("mainIdList") List<String> mainIdList);
}
