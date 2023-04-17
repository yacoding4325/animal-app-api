package cn.common.api.biz;

import cn.common.api.BaseApiController;
import cn.common.internal.AuthUserLoginReq;
import cn.common.req.AppUpdatePwdReq;
import cn.common.req.AppUserRegReq;
import cn.common.resp.AppLoginResp;
import cn.common.resp.TokenVerifyResp;
import cn.common.service.platform.AuthUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pro.skywalking.anon.ApiLog;
import pro.skywalking.interceptor.NeedLogin;
import pro.skywalking.resp.base.ApiResponse;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
* @Description: 前端控制器
*/
@RestController
@RequestMapping(value = "api/v1/authUser")
@Slf4j
public class AuthUserController extends BaseApiController {

    @Resource
    private AuthUserService authUserService;

    /**
     * 系统登录
     * 登录后返回该用户角色
     * @param req
     */
    @PostMapping("/authUserLogin")
    @ApiLog(value = "APP用户登录")
    @NeedLogin(value = false)
    public ApiResponse<AppLoginResp> authUserLogin(@RequestBody AuthUserLoginReq req){
        return apiResponse(authUserService.authUserLogin(req));
    }

    /**
     * 用户注册
     * @description:
     * @param req 请求参数
     * */
    @PostMapping("/userRegister")
    @ApiLog(value = "用户注册")
    @NeedLogin(value = false)
    public ApiResponse<AppLoginResp> userRegister(@RequestBody @Valid AppUserRegReq req){
        return apiResponse(authUserService.userRegister(req));
    }

    /**
     * 退出登录
     */
    @PostMapping("/logOut")
    @ApiLog(value = "退出登录")
    @NeedLogin
    public ApiResponse logOut(){
        authUserService.logOut();
        return apiResponse();
    }

    /**
     * 验证Token
     */
    @GetMapping("/verifyToken")
    @ApiLog(value = "验证Token")
    @NeedLogin(value = false)
    public ApiResponse<TokenVerifyResp> verifyToken(){
        return apiResponse(authUserService.verifyToken());
    }

    /**
     * 拿到登录后的用户信息
     */
    @GetMapping("/queryLoginUserMeta")
    @ApiLog(value = "拿到登录后的用户信息")
    @NeedLogin
    public ApiResponse<AppLoginResp> queryLoginUserMeta(){
        return apiResponse(authUserService.queryLoginUserMeta());
    }

    /**
     * 更新密码
     * @param req 请求参数
     */
    @PostMapping("/updatePassword")
    @ApiLog(value = "更新密码")
    @NeedLogin
    public ApiResponse updatePassword(@RequestBody AppUpdatePwdReq req){
        authUserService.updatePassword(req);
        return apiResponse();
    }

}
