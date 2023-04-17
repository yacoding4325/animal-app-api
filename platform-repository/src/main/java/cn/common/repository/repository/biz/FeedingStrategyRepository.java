package cn.common.repository.repository.biz;

import pro.skywalking.repository.BaseRepository;
import cn.common.repository.entity.biz.FeedingStrategy;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Singer create by singer email:singer-coder@qq.com
* @packageName cn.singer.repository.entity
* @Description: 攻略->Repository
* @date 2019-08-12
*/
@Mapper
public interface FeedingStrategyRepository extends BaseRepository<FeedingStrategy> {

    /**
    * 根据业务主键批量删除
    * @title: AuthRoleRepository.java
    * @author create by Singer - Singer email:singer-coder@qq.com
    * @date 2019/4/28 11:05
    * @param mainIdList 业务主键ID集合
    * @return List
     */
    @Delete(
        "<script>" +
        " DELETE FROM feeding_strategy WHERE 1=1 AND  " +
        " feeding_strategy_id IN " +
        " <foreach collection='mainIdList' item= 'item' index= 'index' open='(' separator=',' close=')' >" +
        "          #{item} " +
        " </foreach>" +
        "</script>"
        )
    void batchDeleteItem(@Param("mainIdList") List<String> mainIdList);

}
