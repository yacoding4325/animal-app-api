package cn.common.service.impl.platform;

import cn.common.service.platform.AsyncHandlerService;
import cn.common.service.platform.PlatformBizService;
import cn.common.service.platform.PlatformDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.skywalking.configuration.redis.RedisRepository;
import pro.skywalking.constants.BaseConstant;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**

 * @Description: 异步处理服务方法实现
 */
@Service(value = "asyncHandlerService")
@Slf4j
public class AsyncHandlerServiceImpl implements AsyncHandlerService {

    @Resource
    private HttpServletRequest request;

    @Resource
    private RedisRepository redisRepository;

    @Resource
    private PlatformBizService platformBizService;

    @Resource
    private BaseConstant baseConstant;

    @Resource
    private PlatformDictService platformDictService;


}
