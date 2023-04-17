package cn.common.req;

import pro.skywalking.req.base.BasePageReq;
import lombok.Data;

import java.io.Serializable;

/**
* APP首页轮播图信息分页查询请求封装类
*/
@Data
public class MainSwiperReq extends BasePageReq implements Serializable {

    private static final long serialVersionUID = -6316530432218310436L;

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
