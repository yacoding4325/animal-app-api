package cn.common.api.platform;

import cn.common.api.BaseApiController;
import cn.common.service.platform.PlatformDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description: 系统字典前端控制器
 */
@RestController
@RequestMapping(value = "api/v1/platformDict")
@Slf4j
public class PlatformDictController extends BaseApiController {

    @Resource
    private PlatformDictService platformDictService;

}
