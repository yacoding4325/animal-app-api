package cn.common.api.biz;

import cn.common.api.BaseApiController;
import cn.common.req.FeedingStrategyReq;
import cn.common.resp.FeedingStrategyResp;
import cn.common.service.biz.FeedingStrategyService;
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
* @Description: 养宠攻略前端控制器
*/
@RestController
@RequestMapping(value = "api/v1/feedingStrategy")
@Slf4j
public class FeedingStrategyController extends BaseApiController {

    @Resource
    private FeedingStrategyService feedingStrategyService;

    @Resource
    private HttpServletResponse response;

    /**
     * 主键ID集合批量删除养宠攻略2
     * @param req 需要被删除的信息
     */
    @PostMapping(value = "/batchDeleteItem")
    @ApiLog(value = "根据主键ID集合批量删除")
    @NeedLogin(value = true)
    public ApiResponse batchDeleteItem(@RequestBody BaseDeleteReq req){
        feedingStrategyService.batchDeleteItem(req);
        return apiResponse();
    }

    /**
     * 查询所有养宠攻略信息
     * @param
     * @return java.util.List
     */
    @GetMapping(value = "/queryAllFeedingStrategy")
    @ApiLog(value = "查询所有信息")
    @NeedLogin(value = false)
    public ApiResponse<List<FeedingStrategyResp>> queryAllFeedingStrategy(){
        return apiResponse(feedingStrategyService.queryAllFeedingStrategy());
    }

    /**
     * 分页查询养宠攻略
     * @param  pageReq 分页查询Req
     * @return Pagination
     */
    @PostMapping(value = "/queryByPage")
    @ApiLog(value = "分页查询信息")
    @NeedLogin(value = false)
    public ApiResponse<Pagination<FeedingStrategyResp>> queryByPage(
        @RequestBody @Valid FeedingStrategyReq pageReq){
        return apiResponse(feedingStrategyService.queryByPage(pageReq));
    }
}
