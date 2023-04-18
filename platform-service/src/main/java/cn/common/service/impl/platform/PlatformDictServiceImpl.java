package cn.common.service.impl.platform;

import cn.common.repository.entity.platform.PlatformDict;
import cn.common.repository.repository.platform.PlatformDictRepository;
import cn.common.service.platform.PlatformDictService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pro.skywalking.configuration.redis.RedisRepository;
import pro.skywalking.utils.CheckParam;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author create by singer - Singer email:singer-coder@qq.com
 * @packageName cn.common.service.impl
 * @Description: 平台数据字典服务方法实现
 * @date 2022-01-28
 */
@Service("platformDictService")
@Slf4j
public class PlatformDictServiceImpl implements PlatformDictService {

    @Resource
    private PlatformDictRepository platformDictRepository;

    @Resource
    private RedisRepository redisRepository;

    /**
     * <p>插入字典数据 写入缓存以及数据库</p>
     * @author: create by singer - Singer email:singer-coder@qq.com
     * @date 2022/2/9
     * @param platformDict 字典内容
     * @return cn.common.repository.entity.platform.PlatformDict
     */
    @Override
    public PlatformDict insertDict(PlatformDict platformDict){
        String dictType = platformDict.getDictType();
        Assert.notNull(dictType, "dictType is null");
        String dictKey = platformDict.getDictKey();
        Assert.notNull(dictKey, "dictKey is null");
        String dictValue = platformDict.getDictValue();
        Assert.notNull(dictValue, "dictValue is null");
        redisRepository.delete(dictType);
        platformDictRepository.insert(platformDict);
        redisRepository.set(dictType,JSON.toJSONString(platformDict));
        return platformDict;
    }


    /**
     * <p>更新字典 更新缓存里面的数据以及数据库里面的字典数据</p>
     * @author: create by singer - Singer email:singer-coder@qq.com
     * @date 2022/2/9
     * @param platformDict 字典内容
     * @return cn.common.repository.entity.platform.PlatformDict
     */
    @Override
    public PlatformDict updateDict(PlatformDict platformDict){
        String dictType = platformDict.getDictType();
        Assert.notNull(dictType, "dictType is null");
        String dictKey = platformDict.getDictKey();
        Assert.notNull(dictKey, "dictKey is null");
        String dictValue = platformDict.getDictValue();
        Assert.notNull(dictValue, "dictValue is null");
        redisRepository.delete(dictType);
        PlatformDict dict = platformDictRepository.selectOne(new LambdaQueryWrapper<PlatformDict>()
                .eq(PlatformDict::getDictType, dictType));
        if(CheckParam.isNull(dict)){
            return null;
        }
        dict.setDictKey(dictKey);
        dict.setDictValue(dictValue);
        dict.setDictType(dictType);
        platformDictRepository.updateById(dict);
        redisRepository.set(dictType,JSON.toJSONString(dict));
        return dict;
    }

    /**
      * <p>从缓存和数据库里面拿到字典的值，拿不到则返回空</p>
      * <p>能拿到则设置缓存</p>
      * @author: create by singer - Singer email:singer-coder@qq.com
      * @date 2022/2/9
      * @param dictType
      * @return cn.common.repository.entity.platform.PlatformDict
      */
    @Override
    public PlatformDict getAndSetDict(String dictType){
        String cache = redisRepository.get(dictType);
        if(!CheckParam.isNull(cache)){
            return JSON.parseObject(cache,PlatformDict.class);
        }
        PlatformDict platformDict = platformDictRepository.selectOne(new LambdaQueryWrapper<PlatformDict>()
                .eq(PlatformDict::getDictType, dictType));
        if(CheckParam.isNull(platformDict)){
            return null;
        }else{
            redisRepository.set(dictType,JSON.toJSONString(platformDict),10L, TimeUnit.SECONDS);
            return platformDict;
        }
    }



}
