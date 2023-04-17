package cn.common.api.biz;

import cn.common.api.BaseApiController;
import cn.common.req.MessageWordAddReq;
import cn.common.req.MessageWordReq;
import cn.common.resp.MessageWordResp;
import cn.common.service.biz.MessageWordService;
import lombok.extern.slf4j.Slf4j;
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

/**
* @Description: 前端控制器
*/
@RestController
@RequestMapping(value = "api/v1/messageWord")
@Slf4j
public class MessageWordController extends BaseApiController {

    @Resource
    private MessageWordService messageWordService;

    @Resource
    private HttpServletResponse response;

    /**
     * 新增交流消息
     * @param addReq 新增交流消息Req
     */
    @PostMapping(value = "/addItem")
    @ApiLog(value = "新增交流消息信息")
    @NeedLogin
    public ApiResponse addItem(@RequestBody @Valid MessageWordAddReq addReq){
        messageWordService.addItem(addReq);
        return apiResponse();
    }

    /**
     * 主键ID集合批量交流消息
     * @param req 需要被删除的交流消息信息
     */
    @PostMapping(value = "/batchDeleteItem")
    @ApiLog(value = "根据主键ID集合批量删除交流消息")
    @NeedLogin
    public ApiResponse batchDeleteItem(@RequestBody BaseDeleteReq req){
        messageWordService.batchDeleteItem(req);
        return apiResponse();
    }

    /**
     * 分页查询交流消息
     * @param  pageReq 分页查询交流消息Req
     * @return Pagination
     */
    @PostMapping(value = "/queryByPage")
    @ApiLog(value = "分页查询交流消息信息")
    @NeedLogin
    public ApiResponse<Pagination<MessageWordResp>> queryByPage(
        @RequestBody @Valid MessageWordReq pageReq){
        return apiResponse(messageWordService.queryByPage(pageReq));
    }
}
