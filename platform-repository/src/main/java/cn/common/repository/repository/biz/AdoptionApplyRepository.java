package cn.common.repository.repository.biz;

import pro.skywalking.repository.BaseRepository;
import cn.common.repository.entity.biz.AdoptApply;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @Description: 领养信息->Repository2
*/
@Mapper
public interface AdoptionApplyRepository extends BaseRepository<AdoptApply> {

    /**
    * 根据业务主键批量删除
    * @param mainIdList 业务主键ID集合
    * @return List
     */
    @Delete(
        "<script>" +
        " DELETE FROM adoption_apply WHERE 1=1 AND  " +
        " adoption_apply_id IN " +
        " <foreach collection='mainIdList' item= 'item' index= 'index' open='(' separator=',' close=')' >" +
        "          #{item} " +
        " </foreach>" +
        "</script>"
        )
    void batchDeleteItem(@Param("mainIdList") List<String> mainIdList);

}
