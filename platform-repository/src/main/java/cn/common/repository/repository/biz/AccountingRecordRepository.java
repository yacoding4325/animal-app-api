package cn.common.repository.repository.biz;

import cn.common.repository.entity.biz.AccountingRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pro.skywalking.repository.BaseRepository;

import java.util.List;

/**
* @Description: 账务->Repository
*/
@Mapper
public interface AccountingRecordRepository extends BaseRepository<AccountingRecord> {


    /**
    * 根据业务主键批量删除
    * @param mainIdList 业务主键ID集合
    * @return List
     */
    @Delete(
        " <script> "+
        " DELETE FROM accounting_record WHERE 1=1 AND  " +
        " accounting_record_id IN " +
        " <foreach collection='mainIdList' item= 'item' index= 'index' open='(' separator=',' close=')' >" +
        "          #{item} " +
        " </foreach>" +
        " </script> "
        )
    void batchDeleteItem(@Param("mainIdList") List<String> mainIdList);

}
