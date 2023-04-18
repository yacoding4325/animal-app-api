package cn.common.service.impl.platform;

import cn.common.repository.entity.platform.AuthUser;
import cn.common.repository.entity.platform.PlatformDict;
import cn.common.repository.repository.platform.AuthUserRepository;
import cn.common.repository.repository.platform.PlatformDictRepository;
import cn.common.service.platform.PlatformService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import pro.skywalking.collection.CollectionUtils;
import pro.skywalking.resp.platform.dict.PlatformDictResp;
import pro.skywalking.resp.platform.other.AuthUserSketchResp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @projectName animal-app-api
 * @Description: 平台相关服务方法实现3
 */
@Service("platformService")
@Slf4j
public class PlatformServiceImpl implements PlatformService {

    /**
      * 注册当前的ServletRequest
      */
    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private PlatformDictRepository platformDictRepository;

    @Resource
    private AuthUserRepository authUserRepository;

    /**
      * 根据字典类型查询字典
      * @param dictType 字典类型
      * @return java.util.List
      */
    @Override
    public List<PlatformDictResp> queryDictByType(String dictType){
        List<PlatformDict> platformDictList = platformDictRepository
                .selectList(new LambdaQueryWrapper<PlatformDict>().eq(PlatformDict::getDictType, dictType));
        if(CollectionUtils.isEmpty(platformDictList)){
            return Lists.newArrayList();
        }
        return mapperFacade.mapAsList(platformDictList, PlatformDictResp.class);
    }

    /**
     * 查询所有用户信息
     * @return java.util.List
     */
    @Override
    public List<AuthUserSketchResp> queryAllAuthUser(){
        List<AuthUser> authUserList =
                authUserRepository.selectList(new LambdaQueryWrapper<>());
        if(CollectionUtils.isEmpty(authUserList)){
            return Lists.newArrayList();
        }
        List<AuthUserSketchResp> respList = mapperFacade.mapAsList(authUserList, AuthUserSketchResp.class);
        return respList;
    }

}
