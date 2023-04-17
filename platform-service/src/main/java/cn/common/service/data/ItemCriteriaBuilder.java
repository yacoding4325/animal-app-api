package cn.common.service.data;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Component;

/**
 * @Title: CriteriaBuilder
 * @ProjectName animal-app-api
 * @Description: 数据权限查询条件构建器
 */
@Component("itemCriteriaBuilder")
public class ItemCriteriaBuilder {

    /**
     * 设置基础数据查询条件
     * @param wrapper 查询条件
     * @param needSetDataAuth 是否需要设置数据权限
     */
    public void rigidCriteria(LambdaQueryWrapper wrapper,Boolean needSetDataAuth){
        if(!needSetDataAuth){
            return;
        }
        //setAuthDataCriteria(wrapper,authUserId);
    }

    /**
     * 设置数据隔离查询条件
     * @param wrapper 查询条件
     * @param authUserId 用户缓存信息
     */
    public void setAuthDataCriteria(LambdaQueryWrapper wrapper,
                                    String authUserId){

    }

}
