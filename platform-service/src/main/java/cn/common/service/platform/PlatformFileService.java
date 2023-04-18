package cn.common.service.platform;

import org.springframework.web.multipart.MultipartFile;
import pro.skywalking.configuration.oss.meta.MultipartFileUrlMeta;

/**
 * @packageName cn.common.service
 * @Description: 平台文件上传Service
 */
public interface PlatformFileService {

    /**
     * 上传文件
     * @param file
     * @return pro.skywalking.req.platform.upload.MultipartFileUrlResp
     */
    MultipartFileUrlMeta uploadFile(MultipartFile file);

    /**
     * 上传文件
     * @param file
     * @return pro.skywalking.req.platform.upload.MultipartFileUrlResp
     */
    MultipartFileUrlMeta uploadFileMixed(MultipartFile file);
}
