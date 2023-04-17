package cn.common.req;


import lombok.Data;
import pro.skywalking.validation.NotEmpty;
import java.io.*;

/**
* APP用户注册Req
*/
@Data
public class AppUserRegReq implements Serializable {

    private static final long serialVersionUID = -2487801540835616975L;

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名->不可为空")
    private String userName;

    /**
     * 手机号码

     @NotEmpty(message = "手机号码->不可为空")
     private String phoneNumber;*/

    /**
     * 密码
     */
    @NotEmpty(message = "密码->不可为空")
    private String password;

}
