package cn.common.service.platform;


import cn.common.internal.AuthUserLoginReq;
import cn.common.req.AppUpdatePwdReq;
import cn.common.req.AppUserRegReq;
import cn.common.resp.AppLoginResp;
import cn.common.resp.TokenVerifyResp;

/**
* @Description: 系统用户相关服务
*/

public interface AuthUserService {

    /**
     * 验证Token
     */
    TokenVerifyResp verifyToken();

    /**
     * 拿到用户ID
     */
    String currentAppUserId();

    /**
     * 拿到登录后的用户信息 不抛出异常
     */
    AppLoginResp queryLoginUserMetaNoThrow();

    /**
     * 用户注册
     * @param req 请求参数
     */
    AppLoginResp userRegister(AppUserRegReq req);

    /**
     * 拿到登录后的用户信息
     */
    AppLoginResp queryLoginUserMeta();

    /**
     * 更新密码
     * @param req 请求参数
     */
    void updatePassword(AppUpdatePwdReq req);

    /**
     * 系统退出登录
     */
    void logOut();

    /**
     * 系统登录
     * @param req 用户登录请求参数
     */
    AppLoginResp authUserLogin(AuthUserLoginReq req);

}
