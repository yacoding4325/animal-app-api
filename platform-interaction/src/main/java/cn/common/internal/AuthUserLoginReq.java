package cn.common.internal;

import pro.skywalking.validation.NotEmpty;
import lombok.Data;

import java.io.*;

/**
 * 系统用户交互参数封装
 */
@Data
public class AuthUserLoginReq implements Serializable {

	private static final long serialVersionUID = 4445428827414406099L;

	/**
	 * 用户名
	 */
	@NotEmpty(message = "用户名->不可为空")
	private String userName;

	/**
	 * 手机号
	 */
	//@NotEmpty(message = "手机号->不可为空")
	private String mobileNumber;

	/**
	 * 密码
	 */
	@NotEmpty(message = "用户名->密码")
	private String password;


}
