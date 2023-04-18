package cn.common.service.platform;

import cn.common.repository.entity.platform.PlatformDict;

/**

 * @Description: 平台数据字典服务

 */
public interface PlatformDictService {

    /**
     * <p>插入字典数据 写入缓存以及数据库</p>
     * @param platformDict 字典内容
     * @return cn.common.repository.entity.platform.PlatformDict
     */
    PlatformDict insertDict(PlatformDict platformDict);

    /**
     * <p>从缓存和数据库里面拿到字典的值，拿不到则返回空</p>
     * <p>能拿到则设置缓存</p>
     * @param dictType
     * @return cn.common.repository.entity.platform.PlatformDict
     */
    PlatformDict getAndSetDict(String dictType);

    /**
     * <p>更新字典 更新缓存里面的数据以及数据库里面的字典数据</p>
     * @param platformDict 字典内容
     * @return cn.common.repository.entity.platform.PlatformDict
     */
    PlatformDict updateDict(PlatformDict platformDict);

}
