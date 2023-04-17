package cn.common.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 登录返回Resp01
 */
@Data
public class AppLoginResp implements Serializable {

	private static final long serialVersionUID = -6135855997336996720L;

	/**
	  * token
	  */
	private String token;


	/**
	 * 用户信息
	 */
	private LoginResp userData;


}
