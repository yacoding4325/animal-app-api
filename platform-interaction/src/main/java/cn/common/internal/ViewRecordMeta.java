package cn.common.internal;

import cn.common.resp.AdoptionDataResp;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.*;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * @Description: 查看记录
 */
@Data
public class ViewRecordMeta implements Serializable {

    private static final long serialVersionUID = -619385333547777597L;

    /**
     * 查看记录
     */
    private AdoptionDataResp data;

    /**
     * 创建时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime = LocalDateTime.ofInstant(Instant.now(), Clock.systemDefaultZone().getZone());

}
