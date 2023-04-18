package cn.common.api.platform;

import cn.common.api.BaseApiController;
import cn.common.service.platform.PlatformService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.skywalking.anon.ApiLog;
import pro.skywalking.interceptor.NeedLogin;
import pro.skywalking.resp.base.ApiResponse;
import pro.skywalking.resp.platform.dict.PlatformDictResp;
import pro.skywalking.resp.platform.other.AuthUserSketchResp;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 平台前端控制器
 */
@RestController
@RequestMapping(value = "api/v1/platform")
@Slf4j
public class PlatformController extends BaseApiController {

    @Resource
    private PlatformService platformService;

    /**
     * PlatformController
     * @param dictType 字典类型
     * @return java.util.List
     */
    @GetMapping(value = "/queryDictByType")
    @NeedLogin(value = false)
    public ApiResponse<List<PlatformDictResp>> queryDictByType(@RequestParam(name = "dictType")
                                                                           String dictType){
        return apiResponse(platformService.queryDictByType(dictType));
    }

    /**
     * 查询所有用户信息
     * @return java.util.List
     */
    @GetMapping("/queryAllAuthUser")
    @ApiLog(value = "查询所有用户信息")
    public ApiResponse<List<AuthUserSketchResp>> queryAllAuthUser(){
        return apiResponse(platformService.queryAllAuthUser());
    }

}
