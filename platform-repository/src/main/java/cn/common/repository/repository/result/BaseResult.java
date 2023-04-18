package cn.common.repository.repository.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * 此类是基础的实体类
 * MappedSuperclass 此标签会把父类的字段映射到子类上面
 */
//@MappedSuperclass
@Data
public class BaseResult implements Serializable {

    private static final long serialVersionUID = -279378218553793536L;

    /**
      * 主键ID
      */
    private Long id;

    /**
     * 创建时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime = LocalDateTime.ofInstant(Instant.now(), Clock.systemDefaultZone().getZone());

    /**
     * 修改时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime = LocalDateTime.ofInstant(Instant.now(), Clock.systemDefaultZone().getZone());

    /**
     * 创建人
     */
    private String  operatorId;
}
