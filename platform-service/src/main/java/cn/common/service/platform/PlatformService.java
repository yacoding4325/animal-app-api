package cn.common.service.platform;


import pro.skywalking.resp.platform.dict.PlatformDictResp;
import pro.skywalking.resp.platform.other.AuthUserSketchResp;

import java.util.List;

/**
 * @projectName animal-app-api
 * @Description: 平台相关服务方法
 */
public interface PlatformService {


    /**
     * 根据字典类型查询字典
     * @param dictType 字典类型
     * @return java.util.List
     */
    List<PlatformDictResp> queryDictByType(String dictType);


    /**
     * 查询所有用户信息
     * @return java.util.List
     */
    List<AuthUserSketchResp> queryAllAuthUser();
}
