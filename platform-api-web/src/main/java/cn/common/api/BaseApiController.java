package cn.common.api;


import cn.common.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pro.skywalking.configuration.redis.RedisRepository;
import pro.skywalking.exception.ApiException;
import pro.skywalking.exception.BusinessException;
import pro.skywalking.resp.base.ApiResponse;
import pro.skywalking.utils.BaseUtil;
import pro.skywalking.utils.MdcUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证器 基本的控制器
 * @description 验证器 基本的控制器
 */
@RestController("baseApiController")
@RestControllerAdvice
@Slf4j
public class BaseApiController {

    /**
     * 注册当前的HttpServletRequest
     */
    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private HttpServletResponse httpServletResponse;

    @Resource
    private RedisRepository redisRepository;

    /**
     * 拿到用户key
     * @return
     */
    public String currentRequestUserKey(){
        return "1";
    }

    /**
     * 生成APIResponse
     * @return
     */
    public ApiResponse apiResponse(){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(ErrorCode.SUCCESS.getMessage());
        apiResponse.setCode(ErrorCode.SUCCESS.getCode());
        apiResponse.setRequestSeqNo(MdcUtils.getOrGenMsgId(BaseUtil.getIpAddress(httpServletRequest)));
        apiResponse.setData(new Object());
        apiResponse.setSuccess(Boolean.TRUE);
        return apiResponse;
    }

    /**
     * GET请求的返回封装
     * @param object
     * @return
     */
    public ApiResponse apiResponse(Object object){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(ErrorCode.SUCCESS.getMessage());
        apiResponse.setCode(ErrorCode.SUCCESS.getCode());
        apiResponse.setRequestSeqNo(MdcUtils.getOrGenMsgId(BaseUtil.getIpAddress(httpServletRequest)));
        apiResponse.setData(object);
        apiResponse.setSuccess(Boolean.TRUE);
        return apiResponse;
    }

    /**
     * 处理基本的异常信息
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<Map<String,Object>> handleBaseException(HttpServletRequest request, Exception e){
        log.error("发生系统异常: "+e.getMessage(),e);
        ApiResponse apiResponse = apiResponse();
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>请求地址: {} <<<<<<<<<<<<<<<<<<<<<<<<<<<"
                ,request.getRequestURI());
        if(e instanceof MethodArgumentNotValidException) {
            return methodArgumentNotValidExceptionHandler(apiResponse,e);
        }
        if (e instanceof ApiException) {
            return apiExceptionHandlerMethod(apiResponse,e);
        }
        if (e instanceof BusinessException) {
            return businessExceptionHandlerMethod(apiResponse,e);
        }
        if (e instanceof ServletRequestBindingException) {
            return servletRequestBindingExceptionHandler(apiResponse,e);
        }
        if(e instanceof BindException) {
            return paramBindExceptionHandler(apiResponse,e);
        }
        return otherExceptionHandler(apiResponse,e);
    }

    /**
     * 权限不足报错拦截
     */
    public ApiResponse<Map<String, Object>> authorizationExceptionHandler(ApiResponse<Map<String, Object>> resp,
                                                                          Exception e) {
        BindException be = (BindException) e;
        StringBuilder errStr = new StringBuilder();
        if(be.getBindingResult().getErrorCount()>0){
            for (ObjectError oe : be.getBindingResult().getAllErrors()) {
                errStr.append(oe.getDefaultMessage()).append(";");
            }
        }
        resp.setCode(ErrorCode.E_502.getCode());
        resp.setMessage(errStr.length()>0?errStr.toString(): ErrorCode.E_502.getMessage());
        resp.setSuccess(Boolean.FALSE);
        log.error("发生系统异常:"+e.getMessage(),e);
        return resp;
    }

    /**
     * 权限不足报错拦截
     */
    public ApiResponse<Map<String, Object>> unauthorizedExceptionHandler(ApiResponse<Map<String, Object>> resp, Exception e) {
        BindException be = (BindException) e;
        StringBuilder errStr = new StringBuilder();
        if(be.getBindingResult().getErrorCount()>0){
            for (ObjectError oe : be.getBindingResult().getAllErrors()) {
                errStr.append(oe.getDefaultMessage()).append(";");
            }
        }
        resp.setCode(ErrorCode.E_502.getCode());
        resp.setMessage(errStr.length()>0?errStr.toString(): ErrorCode.E_502.getMessage());
        resp.setSuccess(Boolean.FALSE);
        log.error("发生系统异常:"+e.getMessage(),e);
        return resp;
    }


    /**
     * 参数绑定处理器
     * @param resp
     * @param e
     * @return
     */
    private ApiResponse<Map<String, Object>> paramBindExceptionHandler(ApiResponse<Map<String, Object>> resp, Exception e){
        BindException be = (BindException) e;
        StringBuilder errStr = new StringBuilder();
        if(be.getBindingResult().getErrorCount()>0){
            for (ObjectError oe : be.getBindingResult().getAllErrors()) {
                errStr.append(oe.getDefaultMessage()).append(";");
            }
        }
        resp.setCode(ErrorCode.PARAM_ERROR.getCode());
        resp.setMessage(errStr.length()>0?errStr.toString(): ErrorCode.PARAM_ERROR.getMessage());
        resp.setSuccess(Boolean.FALSE);
        log.error("发生系统异常:"+e.getMessage(),e);
        return resp;
    }

    /**
     * 其他异常处理器
     * @param resp
     * @param e
     * @return
     */
    private ApiResponse<Map<String, Object>> otherExceptionHandler(ApiResponse<Map<String, Object>> resp, Exception e){
        HashMap<String, Object> errorMap = new HashMap<>(16);
        errorMap.put("errorLocation", e.getMessage() + "    错误位置:" + e);
        resp.setCode(ErrorCode.ERROR.getCode());
        resp.setMessage(ErrorCode.ERROR.getMessage());
        resp.setData(errorMap);
        resp.setSuccess(Boolean.FALSE);
        log.error("发生系统异常:"+e.getMessage(),e);
        return resp;
    }

    /**
     * token参数绑定异常处理器
     * @param resp
     * @param e
     * @return
     */
    private ApiResponse<Map<String, Object>> servletRequestBindingExceptionHandler(ApiResponse<Map<String, Object>> resp, Exception e){
        resp.setCode(ErrorCode.NO_REQUIRED_PARAM_ERROR.getCode());
        resp.setMessage(ErrorCode.NO_REQUIRED_PARAM_ERROR.getMessage());
        resp.setSuccess(Boolean.FALSE);
        log.error("发生系统异常:"+e.getMessage(),e);
        return resp;
    }

    /**
     * 业务异常处理器
     * @param resp
     * @param e
     * @return
     */
    private ApiResponse<Object> businessExceptionHandlerMethod(ApiResponse<Object> resp, Exception e) {
        BusinessException se = (BusinessException) e;
        resp.setCode(se.getCode());
        resp.setMessage(se.getMessage());
        resp.setData(se.getData());
        resp.setSuccess(Boolean.FALSE);
        return resp;
    }

    /**
     * API异常处理器
     * @param resp
     * @param e
     * @author Singer
     */
    private ApiResponse<Map<String, Object>> apiExceptionHandlerMethod(ApiResponse<Map<String, Object>> resp, Exception e){
        ApiException se = (ApiException) e;
        resp.setCode(se.getErrorCode());
        resp.setMessage(se.getErrorMessage());
        resp.setSuccess(Boolean.FALSE);
        return resp;
    }

    /**
     * 处理参数格式验证不通过的时候的处理器
     * @param resp
     * @param e
     */
    public ApiResponse methodArgumentNotValidExceptionHandler(ApiResponse resp, Exception e){
        MethodArgumentNotValidException be = (MethodArgumentNotValidException) e;
        StringBuilder errStr = new StringBuilder();
        if(be.getBindingResult().getErrorCount()>0){
            for (ObjectError oe : be.getBindingResult().getAllErrors()) {
                errStr.append(oe.getDefaultMessage()).append(";");
            }
        }
        resp.setCode(ErrorCode.PARAM_ERROR.getCode());
        resp.setMessage(errStr.length()>0?errStr.toString(): ErrorCode.PARAM_ERROR.getMessage());
        resp.setSuccess(Boolean.FALSE);
        return resp;
    }
}
