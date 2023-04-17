package cn.common.req;

import lombok.Data;
import pro.skywalking.validation.NotEmpty;

import java.io.Serializable;

/**
 * @Description: App更新密码请求参数封装
 */
@Data
public class AppUpdatePwdReq implements Serializable {

    private static final long serialVersionUID = 8946943706219089648L;

    /**
      * 新密码
      */
    @NotEmpty(message = "新密码->不可为空")
    String newPassword;

    /**
     * 原始密码
     */
    @NotEmpty(message = "原始密码->不可为空")
    String originalPassword;
}
