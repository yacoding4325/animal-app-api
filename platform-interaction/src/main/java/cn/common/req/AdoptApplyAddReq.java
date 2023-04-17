package cn.common.req;

import pro.skywalking.validation.NotEmpty;
import lombok.Data;

import java.io.*;

/**
* 领养信息实体
*/
@Data
public class AdoptApplyAddReq implements Serializable {


    private static final long serialVersionUID = 338744437534904163L;

    /**
     * 宠物ID
     */
    @NotEmpty(message = "宠物ID->不可为空")
    private String adoptionDataId;

    /**
     * 领养备注
     */
    private String adoptionRemark;
}
