package cn.common.api.biz;

import cn.common.api.BaseApiController;
import cn.common.req.MainSwiperReq;
import cn.common.resp.MainSwiperResp;
import cn.common.service.biz.MainSwiperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pro.skywalking.anon.ApiLog;
import pro.skywalking.req.base.BaseDeleteReq;
import pro.skywalking.resp.base.ApiResponse;
import pro.skywalking.resp.page.Pagination;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/*** @packageName cn.common.api.controller
* @Description: APP首页轮播图信息前端控制器
*/
@RestController
@RequestMapping(value = "api/v1/mainSwiper")
@Slf4j
public class MainSwiperController extends BaseApiController {

    @Resource
    private MainSwiperService mainSwiperService;

    @Resource
    private HttpServletResponse response;

    /**
     * 主键ID集合批量删除APP首页轮播图信息
     * @param req 需要被删除的信息
     */
    @PostMapping(value = "/batchDeleteItem")
    @ApiLog(value = "根据主键ID集合批量删除")
    public ApiResponse batchDeleteItem(@RequestBody BaseDeleteReq req){
        mainSwiperService.batchDeleteItem(req);
        return apiResponse();
    }

    /**
     * 查询所有APP首页轮播图信息信息
     * @param
     * @return java.util.List
     */
    @GetMapping(value = "/queryAllMainSwiper")
    @ApiLog(value = "查询所有信息")
    public ApiResponse<List<MainSwiperResp>> queryAllMainSwiper(){
        return apiResponse(mainSwiperService.queryAllMainSwiper());
    }

    /**
     * 分页查询APP首页轮播图信息
     * @param  pageReq 分页查询Req
     * @return Pagination
     */
    @PostMapping(value = "/queryByPage")
    @ApiLog(value = "分页查询信息")
    public ApiResponse<Pagination<MainSwiperResp>> queryByPage(
        @RequestBody @Valid MainSwiperReq pageReq){
        return apiResponse(mainSwiperService.queryByPage(pageReq));
    }
}
