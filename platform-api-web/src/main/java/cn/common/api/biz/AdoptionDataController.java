package cn.common.api.biz;

import cn.common.api.BaseApiController;
import cn.common.internal.ViewRecordMeta;
import cn.common.req.AdoptPetLimitReq;
import cn.common.req.AdoptionDataAddReq;
import cn.common.req.AdoptionDataReq;
import cn.common.resp.AdoptionDataResp;
import cn.common.service.biz.AdoptionDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.skywalking.anon.ApiLog;
import pro.skywalking.interceptor.NeedLogin;
import pro.skywalking.resp.base.ApiResponse;
import pro.skywalking.resp.page.Pagination;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
* @Description: 前端控制器
*/
@RestController
@RequestMapping(value = "api/v1/adoptionData")
@Slf4j
//采用数据控制器
public class AdoptionDataController extends BaseApiController {

    @Resource
    private AdoptionDataService adoptionDataService;

    @Resource
    private HttpServletResponse response;

    /**
     * 新增待领养
     * @param addReq 新增Req
     */
    @PostMapping(value = "/addItem")
    @ApiLog(value = "新增待领养信息")
    public ApiResponse addItem(@RequestBody @Valid AdoptionDataAddReq addReq){
        adoptionDataService.addItem(addReq);
        return apiResponse();
    }

    /**
     * 分页查询待领养
     * @param  pageReq 分页查询Req
     * @return Pagination
     */
    @PostMapping(value = "/queryByPage")
    @ApiLog(value = "分页查询待领养信息")
    @NeedLogin(value = false)
    public ApiResponse<Pagination<AdoptionDataResp>> queryByPage(
        @RequestBody @Valid AdoptionDataReq pageReq){
        return apiResponse(adoptionDataService.queryByPage(pageReq));
    }

    /**
     * 查询指定数量的动物
     * @param req 查询请求参数
     * @return java.util.List
     */
    @PostMapping(value = "/queryTopLimit")
    @ApiLog(value = "查询指定数量的动物")
    @NeedLogin(value = false)
    public ApiResponse<List<AdoptionDataResp>> queryTopLimit(@RequestBody @Valid AdoptPetLimitReq req){
        return apiResponse(adoptionDataService.queryTopLimit(req));
    }
    /**
     * 查询宠物详情
     * @param mainId 宠物信息ID
     * @return
     */
    @GetMapping(value = "/queryParticulars")
    @ApiLog(value = "查询宠物详情")
    @NeedLogin
    public ApiResponse<AdoptionDataResp> queryParticulars(@RequestParam(name = "mainId")
                                                                      String mainId){
        return apiResponse(adoptionDataService.queryParticulars(mainId));
    }

    /**
     * 查询所有待领养信息
     * @return java.util.List
     */
    @GetMapping(value = "/allAdoptionData")
    @ApiLog(value = "查询所有待领养信息")
    @NeedLogin(value = false)
    public ApiResponse<List<AdoptionDataResp>> allAdoptionData(){
        return apiResponse(adoptionDataService.allAdoptionData());
    }

    /**
     * 设置宠物查看记录
     * @param mainId 宠物ID
     * @return
     */
    @GetMapping(value = "/setViewPetData")
    @ApiLog(value = "设置宠物查看记录")
    @NeedLogin
    public ApiResponse setViewPetData(@RequestParam(name = "mainId")
            String mainId){
        adoptionDataService.setViewPetData(mainId);
        return apiResponse();
    }

    /**
     * 查询当前用户的查看记录
     * @param
     * @return java.util.List
     */
    @GetMapping(value = "/queryViewRecord")
    @ApiLog(value = "设置宠物查看记录")
    @NeedLogin
    public ApiResponse<List<ViewRecordMeta>> queryViewRecord(){
        return apiResponse(adoptionDataService.queryViewRecord());
    }
}
