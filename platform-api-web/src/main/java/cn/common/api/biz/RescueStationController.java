package cn.common.api.biz;

import cn.common.api.BaseApiController;
import cn.common.req.RescueStationAddReq;
import cn.common.req.RescueStationReq;
import cn.common.resp.RescueStationResp;
import cn.common.service.biz.RescueStationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.skywalking.anon.ApiLog;
import pro.skywalking.interceptor.NeedLogin;
import pro.skywalking.req.base.BaseDeleteReq;
import pro.skywalking.resp.base.ApiResponse;
import pro.skywalking.resp.page.Pagination;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
* @Description: 前端控制器*/
@RestController
@RequestMapping(value = "api/v1/rescueStation")
@Slf4j
public class RescueStationController extends BaseApiController {

    @Resource
    private RescueStationService rescueStationService;

    @Resource
    private HttpServletResponse response;

    /**
     * 新增救助站
     * @param addReq 新增救助站Req
     */
    @PostMapping(value = "/addItem")
    @ApiLog(value = "新增救助站信息")
    @NeedLogin
    public ApiResponse addItem(@RequestBody @Valid RescueStationAddReq addReq){
        rescueStationService.addItem(addReq);
        return apiResponse();
    }

    /**
     * 主键ID集合批量救助站
     * @param req 需要被删除的救助站信息
     */
    @PostMapping(value = "/batchDeleteItem")
    @ApiLog(value = "根据主键ID集合批量删除救助站")
    @NeedLogin
    public ApiResponse batchDeleteItem(@RequestBody BaseDeleteReq req){
        rescueStationService.batchDeleteItem(req);
        return apiResponse();
    }

    /**
     * 分页查询救助站
     * @param  pageReq 分页查询Req
     * @return Pagination
     */
    @PostMapping(value = "/queryByPage")
    @ApiLog(value = "分页查询救助站信息")
    @NeedLogin(value = false)
    public ApiResponse<Pagination<RescueStationResp>> queryByPage(
        @RequestBody @Valid RescueStationReq pageReq){
        return apiResponse(rescueStationService.queryByPage(pageReq));
    }

    /**
     * 查询所有救助站信息
     */
    @GetMapping(value = "/queryAllStation")
    @ApiLog(value = "查询所有救助站信息")
    @NeedLogin(value = false)
    public ApiResponse<List<RescueStationResp>> queryAllStation(){
        return apiResponse(rescueStationService.queryAllStation());
    }
}
