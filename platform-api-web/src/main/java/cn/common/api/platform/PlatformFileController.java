package cn.common.api.platform;

import cn.common.api.BaseApiController;
import cn.common.service.platform.PlatformFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.skywalking.anon.ApiLog;
import pro.skywalking.configuration.oss.AliOssService;
import pro.skywalking.configuration.oss.meta.AppFileUrlMeta;
import pro.skywalking.configuration.oss.meta.MultipartFileUrlMeta;
import pro.skywalking.configuration.oss.meta.MultipleFileAccessUrlMeta;
import pro.skywalking.interceptor.NeedLogin;
import pro.skywalking.resp.base.ApiResponse;


import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**

 * @Description: 系统文件相关前端控制器
 */
@RestController
@RequestMapping("api/platformFile")
@Slf4j
public class PlatformFileController extends BaseApiController {


    @Resource
    private AliOssService aliOssService;


    @Resource
    private PlatformFileService platformFileService;

    /**
     * 拿到文件直接访问路径
     */
    @GetMapping("/appFileAccessUrl")
    @ApiLog(value = "拿到文件直接访问路径")
    public ApiResponse<List<AppFileUrlMeta>> appFileAccessUrl(@RequestParam(name = "fileNames") String fileNames){
        return apiResponse(aliOssService.getObjectAccessUrlList(Arrays.asList(fileNames.split(","))));
    }


    /**
     * 拿到单个文件名称的地址
     */
    @GetMapping("/singleFileAccessUrl")
    @ApiLog(value = "拿到单个文件名称的地址")
    public ApiResponse<String> singleFileAccessUrl(@RequestParam(name = "fileName")String fileName){
        return apiResponse(aliOssService.getObjectAccessUrl(fileName));
    }

    /**
     * 拿到多个文件名称的地址
     * @param meta 文件名称集合
     * @return String
     */
    @PostMapping("/multipleFileAccessUrl")
    @ApiLog(value = "拿到多个文件名称的地址")
    public ApiResponse<List<String>> multipleFileAccessUrl(@RequestBody @Valid MultipleFileAccessUrlMeta meta){
        return apiResponse(aliOssService.getObjectAccessUrlList(meta.getFileNameList()));
    }

    /**
     * 拿到多个文件名称的地址
     * @param meta 文件名称集合
     * @return List
     */
    @PostMapping("/queryMultipleFileAccessUrl")
    @ApiLog(value = "拿到多个文件名称的地址")
    public ApiResponse<List<String>> queryMultipleFileAccessUrl(@RequestBody @Valid MultipleFileAccessUrlMeta meta){
        return apiResponse(aliOssService.getObjectAccessUrlList(meta.getFileNameList()));
    }

    /**
     * 删除文件
     * @param name 删除OSS的文件
     */
    @DeleteMapping("/deleteFileByFileName")
    @ApiLog(value = "删除文件")
    @NeedLogin(value = false)
    public ApiResponse deleteFileByFileName(@RequestParam(name = "name") String name){
        aliOssService.deleteObject(name);
        return apiResponse();
    }

    /**
     * 上传文件
     * @param file
     * @return String
     */
    @PostMapping(value = "/uploadFile")
    @ApiLog(value = "上传文件")
    @NeedLogin(value = false)
    public ApiResponse<MultipartFileUrlMeta> uploadFile(@RequestParam(name = "file") MultipartFile file){
        return apiResponse(platformFileService.uploadFile(file));
    }

    /**
     * 上传文件(混淆)
     * @param file
     * @return String
     */
    @PostMapping(value = "/uploadFileMixed")
    @ApiLog(value = "上传文件(混淆)")
    public ApiResponse<MultipartFileUrlMeta> uploadFileMixed(@RequestParam(name = "file") MultipartFile file){
        return apiResponse(platformFileService.uploadFileMixed(file));
    }

}
