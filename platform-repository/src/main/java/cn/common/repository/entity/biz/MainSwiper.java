package cn.common.repository.entity.biz;

import pro.skywalking.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
* APP首页轮播图信息实体
*/
@Data
@TableName("main_swiper")
public class MainSwiper extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -3430758838943184083L;

    /**
     * 业务主键ID
     */
    private String mainSwiperId;

    /**
     * 图片地址
     */
    private String mainUrl;

    /**
     * 标题
     */
    private String mainTitle;

    /**
     * 跳转地址
     */
    private String routerUrl;

}
