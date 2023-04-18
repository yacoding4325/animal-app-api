package cn.common.repository.repository.platform;

import pro.skywalking.repository.BaseRepository;
import cn.common.repository.entity.platform.PlatformDict;
import org.apache.ibatis.annotations.Mapper;

/**
* @packageName cn.singer.repository.entity
* @Description: 字典信息->Repository
*/
@Mapper
public interface PlatformDictRepository extends BaseRepository<PlatformDict> {



    /**
    * 根据业务主键批量删除
    * @title: AuthRoleRepository.java
    * @param mainIdList 业务主键ID集合
    * @return List

    @Delete(
        " DELETE FROM platform_dict WHERE 1=1 AND  " +
        " platform_dict_id IN " +
        " <foreach collection='mainIdList' item= 'item' index= 'index' open='(' separator=',' close=')' >" +
        "          #{item} " +
        " </foreach>"
        )
    void batchDeleteItem(@Param("mainIdList") List<String> mainIdList);*/

}
