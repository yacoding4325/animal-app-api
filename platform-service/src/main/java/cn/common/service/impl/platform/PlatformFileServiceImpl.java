package cn.common.service.impl.platform;

import cn.common.service.platform.PlatformFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.skywalking.configuration.oss.AliOssService;
import pro.skywalking.configuration.oss.meta.MultipartFileUrlMeta;

import javax.annotation.Resource;

/**
 * @packageName cn.common.service.impl
 * @Description: 平台文件上传相关服务方法实现
 */
@Service("platformFileService")
@Slf4j
public class PlatformFileServiceImpl implements PlatformFileService {

    @Resource
    private AliOssService aliOssService;

    /**
     * 上传文件
     * @param file
     * @return pro.skywalking.req.platform.upload.MultipartFileUrlResp
     */
    @Override
    public MultipartFileUrlMeta uploadFile(MultipartFile file){
        MultipartFileUrlMeta multipartFileUrlMeta = aliOssService.multipartFileToOss(file, false);
        return multipartFileUrlMeta;
    }

    /**
     * 上传文件
     * @param file
     * @return pro.skywalking.req.platform.upload.MultipartFileUrlResp
     */
    @Override
    public MultipartFileUrlMeta uploadFileMixed(MultipartFile file){
        MultipartFileUrlMeta multipartFileUrlMeta = aliOssService.mixFileToOss(file, false);
        return multipartFileUrlMeta;
    }

}
