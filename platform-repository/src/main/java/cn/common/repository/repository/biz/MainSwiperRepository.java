package cn.common.repository.repository.biz;

import pro.skywalking.repository.BaseRepository;
import cn.common.repository.entity.biz.MainSwiper;
import org.apache.ibatis.annotations.Mapper;

/**
* @Description: APP首页轮播图信息->Repository
*/
@Mapper
public interface MainSwiperRepository extends BaseRepository<MainSwiper> {

        /**
        * 根据业务主键批量删除
        * @param mainIdList 业务主键ID集合
        * @return List

        @Delete(
            "<script>" +
            " DELETE FROM main_swiper WHERE 1=1 AND  " +
            " main_swiper_id IN " +
            " <foreach collection='mainIdList' item= 'item' index= 'index' open='(' separator=',' close=')' >" +
            "   #{item} " +
            " </foreach>" +
            "</script>"
            )
        void batchDeleteItem(@Param("mainIdList") List<String> mainIdList);*/

}
