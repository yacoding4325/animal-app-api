package cn.common.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: Token校验结果
 */
@Data
public class TokenVerifyResp implements Serializable {

    private static final long serialVersionUID = -2911654423895568625L;


    /**
      * 是否校验成功
      */
    private Boolean verifyStatus = Boolean.FALSE;


}
