package cn.common.repository.repository.biz;

import pro.skywalking.repository.BaseRepository;
import cn.common.repository.entity.biz.AppUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description: APP用户->Repository
 */
@Mapper
public interface AppUserRepository extends BaseRepository<AppUser> {


    /**
     * 根据业务主键批量删除
     * @param mainIdList 业务主键ID集合
     * @return List
     */
    @Delete(
            " DELETE FROM app_user WHERE 1=1 AND  " +
                    " app_user_id IN " +
                    " <foreach collection='mainIdList' item= 'item' index= 'index' open='(' separator=',' close=')' >" +
                    "          #{item} " +
                    " </foreach>"
    )
    void batchDeleteItem(@Param("mainIdList") List<String> mainIdList);

    @Select("SELECT * FROM app_user WHERE unique_no = #{inviteCode}")
    AppUser findByInviteCode(@Param("inviteCode") String inviteCode);

    @Select("SELECT * FROM app_user WHERE app_user_id = #{appUserId}")
    AppUser findByAppUserId(@Param("appUserId") String appUserId);
}
