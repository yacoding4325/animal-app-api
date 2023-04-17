package cn.common.service.impl.platform;

import cn.common.enums.ErrorCode;
import cn.common.enums.GenderEnum;
import cn.common.internal.AuthUserLoginReq;
import cn.common.repository.entity.biz.AppUser;
import cn.common.repository.repository.biz.AppUserRepository;
import cn.common.req.AppUpdatePwdReq;
import cn.common.req.AppUserRegReq;
import cn.common.resp.AppLoginResp;
import cn.common.resp.LoginResp;
import cn.common.resp.TokenVerifyResp;
import cn.common.service.platform.AuthUserService;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pro.skywalking.configuration.redis.RedisRepository;
import pro.skywalking.constants.AuthConstants;
import pro.skywalking.constants.BaseConstant;
import pro.skywalking.enums.AuthStatusEnum;
import pro.skywalking.exception.BusinessException;
import pro.skywalking.utils.BaseUtil;
import pro.skywalking.utils.CheckParam;
import pro.skywalking.utils.MD5;
import pro.skywalking.utils.SnowflakeIdWorker;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
* @Description: 首页分类相关服务方法实现*/
@Service("authUserService")
@Slf4j
public class AuthUserServiceImpl implements AuthUserService {

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private BaseConstant baseConstant;

    @Resource
    private HttpServletRequest request;

    @Resource
    private AppUserRepository appUserRepository;

    @Resource
    private AuthConstants authConstants;

    @Resource
    private RedisRepository redisRepository;

    /**
     * 验证Token
     */
    @Override
    public TokenVerifyResp verifyToken(){
        String token = request.getHeader(AuthConstants.TOKEN_KEY);
        String result = redisRepository.get(AuthConstants.APP_AUTH_TOKEN_PREFIX + token);

        TokenVerifyResp resp = new TokenVerifyResp();
        if(CheckParam.isNull(result)){
            resp.setVerifyStatus(Boolean.FALSE);
        }else{
            resp.setVerifyStatus(Boolean.TRUE);
        }
        return resp;
    }

    /**
     * 拿到用户ID
     */
    @Override
    public String currentAppUserId(){
        String token = request.getHeader(AuthConstants.TOKEN_KEY);
        String result = redisRepository.get(AuthConstants.APP_AUTH_USER_SKETCH_PREFIX + token);
        if(CheckParam.isNull(result)){
            throw new BusinessException(ErrorCode.LOGIN_ERROR_CODE.getCode(),
                    ErrorCode.LOGIN_ERROR_CODE.getMessage());
        }
        AppLoginResp platformLoginResp = JSON.parseObject(result, AppLoginResp.class);
        if(CheckParam.isNull(platformLoginResp)){
            throw new BusinessException(ErrorCode.LOGIN_ERROR_CODE.getCode(),
                    ErrorCode.LOGIN_ERROR_CODE.getMessage());
        }
        LoginResp userData = platformLoginResp.getUserData();
        if(CheckParam.isNull(userData)){
            throw new BusinessException(ErrorCode.LOGIN_ERROR_CODE.getCode(),
                    ErrorCode.LOGIN_ERROR_CODE.getMessage());
        }
        return userData.getAppUserId();
    }

    /**
     * 拿到登录后的用户信息 不抛出异常
     */
    @Override
    public AppLoginResp queryLoginUserMetaNoThrow(){
        String token = request.getHeader(AuthConstants.TOKEN_KEY);
        String result = redisRepository.get(AuthConstants.APP_AUTH_USER_SKETCH_PREFIX + token);
        if(CheckParam.isNull(result)){
            return new AppLoginResp ();
        }
        AppLoginResp  platformLoginResp = JSON.parseObject(result, AppLoginResp .class);
        return platformLoginResp;
    }


    /**
     * 用户注册
     * @param req 请求参数
     */
    @Override
    public AppLoginResp userRegister(AppUserRegReq req){
        log.info(">>>>>>用户注册参数:{}<<<<<<",JSON.toJSONString(req));
        verifyRegister(req);
        AppUser entity = mapperFacade.map(req, AppUser.class);
        try {
            BaseUtil.setFieldValueNotNull(entity);
            entity.setGender(GenderEnum.UNKNOWN.getCode());
            entity.setDecryptionPassword(req.getPassword());
            entity.setPassword(MD5.md5(req.getPassword()));
            entity.setAuthStatus(AuthStatusEnum.EFFECTIVE.getCode());
            entity.setUserNumber(SnowflakeIdWorker.uniqueMainId());
        } catch (Exception e) {
            log.error("新增->设置为空的属性失败 {} , {} ",e.getMessage(),e);
            throw new BusinessException(ErrorCode.SET_EMPTY_FIELD_ERROR.getCode()
                    ,ErrorCode.SET_EMPTY_FIELD_ERROR.getMessage());
        }
        appUserRepository.insert(entity);
        return handleLoginSuccess(entity);
    }


    /**
     * @description: 处理注册验证业务
     * @param req 请求参数
     * @return
     */
    public void verifyRegister(AppUserRegReq req){
        String userName = req.getUserName();
        Long count = appUserRepository.selectCount(new LambdaQueryWrapper<AppUser>().eq(AppUser::getUserName, userName));
        if(count > 0){
            throw new BusinessException(ErrorCode.BUSINESS_REFUSE.getCode()
                    ,ErrorCode.BUSINESS_REFUSE.getMessage()+":用户已经存在");
        }
    }

    /**
     * 拿到登录后的用户信息
     */
    @Override
    public AppLoginResp queryLoginUserMeta(){
        String token = request.getHeader(AuthConstants.TOKEN_KEY);
        String result = redisRepository.get(AuthConstants.APP_AUTH_USER_SKETCH_PREFIX + token);
        if(CheckParam.isNull(result)){
            throw new BusinessException(ErrorCode.LOGIN_ERROR_CODE.getCode(),
                    ErrorCode.LOGIN_ERROR_CODE.getMessage());
        }
        AppLoginResp  platformLoginResp = JSON.parseObject(result, AppLoginResp.class);
        if(CheckParam.isNull(platformLoginResp)){
            throw new BusinessException(ErrorCode.LOGIN_ERROR_CODE.getCode(),
                    ErrorCode.LOGIN_ERROR_CODE.getMessage());
        }
        return platformLoginResp;
    }

    /**
     * 系统退出登录
     */
    @Override
    public void logOut(){
        String token = request.getHeader("token");
        if(CheckParam.isNull(token)){
            return;
        }
        redisRepository.delete(AuthConstants.APP_AUTH_TOKEN_PREFIX +token);
        redisRepository.delete(AuthConstants.APP_AUTH_USER_SKETCH_PREFIX +token);
    }

    /**
     * 系统登录
     * @param req 用户登录请求参数
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public AppLoginResp authUserLogin(AuthUserLoginReq req){
        log.info(">>>>>>>>>>>登录请求参数:<<<<<<<<<<<",JSON.toJSONString(req));
        if(StringUtils.isEmpty(req.getPassword())){
            throw new BusinessException(ErrorCode.ERROR.getCode(),
                    "密码不能为空");
        }
        String loginPassWord = MD5.md5(req.getPassword());
        req.setPassword(loginPassWord);
        AppUser resUser = appUserRepository.selectOne(new LambdaQueryWrapper<AppUser>()
                .eq(AppUser::getUserName,req.getUserName())
                .eq(AppUser::getPassword,req.getPassword()));
        if(CheckParam.isNull(resUser)){
            throw new BusinessException(ErrorCode.ERROR.getCode(),
                    "用户名密码错误");
        }
        return handleLoginSuccess(resUser);
    }

    /**
     * 登录成功的逻辑处理
     * @param resUser 系统用户
     */
    public AppLoginResp handleLoginSuccess(AppUser resUser){
        AppLoginResp appLoginResp = new AppLoginResp();
        if(!CheckParam.isNull(resUser)){
            resUser.setPassword(StrUtil.EMPTY);
            LoginResp loginResp = mapperFacade.map(resUser, LoginResp.class);
            appLoginResp.setUserData(loginResp);
        }
        String token = SnowflakeIdWorker.uniqueMainId();
        try {
            appLoginResp.setToken(token);
        }catch (Exception e){
            throw new BusinessException(ErrorCode.ERROR.getCode(),
                    "登录失败");
        }
        redisRepository.set(AuthConstants.APP_AUTH_TOKEN_PREFIX +token,token,authConstants.getTokenValidTime(),TimeUnit.HOURS);
        redisRepository.set(AuthConstants.APP_AUTH_USER_SKETCH_PREFIX +token,JSON.toJSONString(appLoginResp),authConstants.getTokenValidTime(),TimeUnit.HOURS);
        log.info(">>>>>>>>>>>>>APP登录返回:{}<<<<<<<<<<<<<<",JSON.toJSONString(appLoginResp));
        return appLoginResp;
    }

    /**
     * 更新密码
     * @param req 请求参数
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updatePassword(AppUpdatePwdReq req){
        String newPassword = req.getNewPassword();
        String originalPassword = req.getOriginalPassword();
        String currentAppUserId = currentAppUserId();
        AppUser authUser = appUserRepository
                .selectOne(new LambdaQueryWrapper<AppUser>().eq(AppUser::getAppUserId, currentAppUserId)
                        .eq(AppUser::getPassword, MD5.md5(originalPassword)));
        if(CheckParam.isNull(authUser)){
            throw new BusinessException(ErrorCode.ERROR_OLD_PASSWORD.getCode(),
                    ErrorCode.ERROR_OLD_PASSWORD.getMessage());
        }
        //密码加盐加密
        String encryptPassword = MD5.md5(newPassword);
        authUser.setPassword(encryptPassword);
        authUser.setDecryptionPassword(newPassword);
        appUserRepository.updateById(authUser);
    }


}
