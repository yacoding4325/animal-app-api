package cn.common.service.aspect;


import cn.common.repository.entity.platform.PlatformApiLog;
import cn.common.resp.AppLoginResp;
import cn.common.resp.LoginResp;
import cn.common.service.platform.AuthUserService;
import pro.skywalking.anon.ApiLog;
import pro.skywalking.utils.BaseUtil;
import pro.skywalking.utils.CheckParam;
import cn.common.repository.repository.platform.PlatformApiLogRepository;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @packageName cn.common.service.aspect;
 * @Description: 日志记录切面
 */
@Slf4j
@Aspect
@Component
public class LogRecordAspect {

    @Resource
    private PlatformApiLogRepository platformApiLogRepository;

    @Resource
    private AuthUserService authUserService;

    /**
     * 定义切点
     */
    @Pointcut("@annotation(pro.skywalking.anon.ApiLog)")
    public void pointcut() {
    }

    /**
     * 环绕通知，处理切点里面的数据
     * @param point 环绕通知对象主要用于处理切点里面的数据
     * @return Object
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        long beginTime = System.currentTimeMillis();
        // 执行方法
        result = point.proceed();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 设置 IP 地址
        String ipAddress = BaseUtil.getIpAddress(request);
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        AppLoginResp authUserMeta = authUserService.queryLoginUserMetaNoThrow();
        PlatformApiLog log = new PlatformApiLog();
        if(!CheckParam.isNull(authUserMeta)){
            LoginResp userData = authUserMeta.getUserData();
            if(!CheckParam.isNull(userData)){
                log.setUserName(userData.getUserName());
            }
        }else{
            log.setUserName("");
        }
        log.setRequestIp(ipAddress);
        log.setOperationTime(String.valueOf(time));
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        ApiLog logAnnotation = method.getAnnotation(ApiLog.class);
        if (logAnnotation != null) {
            // 注解上的描述
            log.setOperation(logAnnotation.value());
        }
        // 请求的类名
        String className = point.getTarget().getClass().getName();
        // 请求的方法名
        String methodName = signature.getName();
        log.setMethod(className + "." + methodName + "()");
        // 请求的方法参数值
        Object[] args = point.getArgs();
        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            StringBuilder params = new StringBuilder();
            params = handleParams(params, args, Arrays.asList(paramNames));
            log.setParams(params.toString());
        }
        log.setLocation(ipAddress);
        platformApiLogRepository.insert(log);
        return result;
    }

    /**
     * 拿到请求参数
     * @param params 要返回的参数
     * @param args 请求参数列表
     * @param paramNames 请求参数名称列表
     * @return StringBuilder
     */
    private StringBuilder handleParams(StringBuilder params, Object[] args, List paramNames) throws JsonProcessingException {
        for (int n1 = 0; n1 < args.length; n1++) {
            if (args[n1] instanceof Map) {
                Set set = ((Map) args[n1]).keySet();
                List<Object> list = new ArrayList<>();
                List<Object> paramList = new ArrayList<>();
                for (Object key : set) {
                    list.add(((Map) args[n1]).get(key));
                    paramList.add(key);
                }
                return handleParams(params, list.toArray(), paramList);
            } else {
                if (args[n1] instanceof MultipartFile) {
                    MultipartFile file = (MultipartFile) args[n1];
                    params.append(" ").append(paramNames.get(n1)).append(": ").append(file.getName());
                } else if (args[n1] instanceof Serializable) {
                    Class<?> aClass = args[n1].getClass();
                    try {
                        aClass.getDeclaredMethod("toString", new Class[]{null});
                        // 如果不抛出 NoSuchMethodException 异常则存在 toString 方法 ，安全的 writeValueAsString ，否则 走 Object的 toString方法

                        params.append(" ").append(paramNames.get(n1)).append(": ").append(JSON.toJSONString(args[n1]));
                    } catch (NoSuchMethodException e) {
                        params.append(" ").append(paramNames.get(n1)).append(": ").append(JSON.toJSONString(args[n1]));
                    }
                } else {
                    params.append(" ").append(paramNames.get(n1)).append(": ").append(args[n1]);
                }
            }
        }
        return params;
    }
}
