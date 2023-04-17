package cn.common.resp;

import pro.skywalking.req.platform.base.BaseResp;
import lombok.Data;

import java.io.Serializable;

/**
* APP首页轮播图信息返回数据封装类
*/
@Data
public class MainSwiperResp extends BaseResp implements Serializable {

    private static final long serialVersionUID = 6656805994631923881L;

    /**
     * 业务主键ID->"mainSwiperId"
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
