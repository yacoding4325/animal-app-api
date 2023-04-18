package cn.common.service.impl.platform;

import cn.common.repository.repository.platform.PlatformApiLogRepository;
import cn.common.service.platform.AuthUserService;
import cn.common.service.platform.PlatformApiLogService;
import cn.common.service.data.ItemCriteriaBuilder;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**

* @Description: 首页分类相关服务方法实现
*/
@Service("platformApiLogService")
@Slf4j
public class PlatformApiLogServiceImpl implements PlatformApiLogService {

    @Resource
    private PlatformApiLogRepository platformApiLogRepository;

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private AuthUserService authUserService;

    @Resource
    private ItemCriteriaBuilder itemCriteriaBuilder;


}
