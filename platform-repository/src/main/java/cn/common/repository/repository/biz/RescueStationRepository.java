package cn.common.repository.repository.biz;

import pro.skywalking.repository.BaseRepository;
import cn.common.repository.entity.biz.RescueStation;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @Description: 救助站->Repository
*/
@Mapper
public interface RescueStationRepository extends BaseRepository<RescueStation> {



    /**
    * 根据业务主键批量删除
    * @param mainIdList 业务主键ID集合
    * @return List
     */
    @Delete(
        "<script>" +
        " DELETE FROM rescue_station WHERE 1=1 AND  " +
        " rescue_station_id IN " +
        " <foreach collection='mainIdList' item= 'item' index= 'index' open='(' separator=',' close=')' >" +
        "          #{item} " +
        " </foreach>"+
        "</script>"
        )
    void batchDeleteItem(@Param("mainIdList") List<String> mainIdList);

}
