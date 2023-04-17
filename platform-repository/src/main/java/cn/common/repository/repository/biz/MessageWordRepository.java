package cn.common.repository.repository.biz;

import pro.skywalking.repository.BaseRepository;
import cn.common.repository.entity.biz.MessageWord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Singer create by singer email:singer-coder@qq.com
* @packageName cn.singer.repository.entity
* @Description: 交流消息->Repository
* @date 2019-08-12
*/
@Mapper
public interface MessageWordRepository extends BaseRepository<MessageWord> {

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
        "  DELETE FROM interaction_msg WHERE 1=1 AND  " +
        "  interaction_msg_id IN " +
        " <foreach collection='mainIdList' item= 'item' index= 'index' open='(' separator=',' close=')' >" +
        "          #{item} " +
        " </foreach>" +
        "</script>"
        )
    void batchDeleteItem(@Param("mainIdList") List<String> mainIdList);

}
