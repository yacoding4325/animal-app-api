package cn.common.api.platform;

import cn.common.api.BaseApiController;
import cn.common.service.platform.PlatformApiLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
* @Description: 前端控制器
*/
@RestController
@RequestMapping(value = "api/v1/platformApiLog")
@Slf4j
public class PlatformApiLogController extends BaseApiController {

    @Resource
    private PlatformApiLogService platformApiLogService;

}
