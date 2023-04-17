package cn.common.api.biz;

import cn.common.api.BaseApiController;
import cn.common.req.AdoptApplyAddReq;
import cn.common.req.AdoptionApplyReq;
import cn.common.resp.AdoptApplyResp;
import cn.common.service.biz.AdoptApplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.skywalking.anon.ApiLog;
import pro.skywalking.interceptor.NeedLogin;
import pro.skywalking.resp.base.ApiResponse;
import pro.skywalking.resp.page.Pagination;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
* @Description: 前端控制器
*/
@RestController
@RequestMapping(value = "api/v1/adoptionApply")
@Slf4j
public class AdoptApplyController extends BaseApiController {

    @Resource
    private AdoptApplyService adoptApplyService;

    @Resource
    private HttpServletResponse response;

    /**
     * 新增领养信息
     * @param addReq 新增Req
     */
    @PostMapping(value = "/addItem")
    @ApiLog(value = "新增领养信息信息")
    @NeedLogin
    public ApiResponse addItem(@RequestBody @Valid AdoptApplyAddReq addReq){
        adoptApplyService.addItem(addReq);
        return apiResponse();
    }

    /**
     * 分页查询领养申请信息
     * @param  pageReq 分页查询Req
     * @return Pagination
     */
    @PostMapping(value = "/queryByPage")
    @ApiLog(value = "分页查询领养申请信息")
    @NeedLogin(value = false)
    public ApiResponse<Pagination<AdoptApplyResp>> queryByPage(
        @RequestBody @Valid AdoptionApplyReq pageReq){
        return apiResponse(adoptApplyService.queryByPage(pageReq));
    }
}
