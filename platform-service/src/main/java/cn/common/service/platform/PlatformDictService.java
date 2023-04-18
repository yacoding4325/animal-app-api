package cn.common.service.platform;

import cn.common.repository.entity.platform.PlatformDict;

/**
 * @author create by singer - Singer email:singer-coder@qq.com
 * @packageName cn.common.service
 * @Description: 平台数据字典服务
 * @date 2022-01-28
 */
public interface PlatformDictService {

    /**
     * <p>插入字典数据 写入缓存以及数据库</p>
     * @author: create by singer - Singer email:singer-coder@qq.com
     * @date 2022/2/9
     * @param platformDict 字典内容
     * @return cn.common.repository.entity.platform.PlatformDict
     */
    PlatformDict insertDict(PlatformDict platformDict);

    /**
     * <p>从缓存和数据库里面拿到字典的值，拿不到则返回空</p>
     * <p>能拿到则设置缓存</p>
     * @author: create by singer - Singer email:singer-coder@qq.com
     * @date 2022/2/9
     * @param dictType
     * @return cn.common.repository.entity.platform.PlatformDict
     */
    PlatformDict getAndSetDict(String dictType);

    /**
     * <p>更新字典 更新缓存里面的数据以及数据库里面的字典数据</p>
     * @author: create by singer - Singer email:singer-coder@qq.com
     * @date 2022/2/9
     * @param platformDict 字典内容
     * @return cn.common.repository.entity.platform.PlatformDict
     */
    PlatformDict updateDict(PlatformDict platformDict);

}
