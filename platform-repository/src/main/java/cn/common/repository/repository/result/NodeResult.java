package cn.common.repository.repository.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**

 * @Description: 节点查询返回参数封装
 */
@Data
public class NodeResult implements Serializable {


	private static final long serialVersionUID = -735815717696444704L;


	private Long id;//当前节点的ID

	private String name;

	private Boolean leaf;

	private Integer level;

	private String parentId;

	private Date expireTime;

	private Integer maxPeopleNum;

	private Integer jxMobileProject;

	private Integer jxMobileCode;

	/**
	 * 只有校区级别才有的字段  是否开启 支付 默认为 0需要支付 1 不需要支付
	 */
	private Integer openCheckPay;


	private Integer onlyCheckDeposit;

	/**
	 * 是否驱逐套餐
	 */
	private  Boolean payPackageEvicted;

	private Long projectId;

	//维度
	private String	latitude;

	//经度
	private String longitude;

	//卡片权限
	private Integer openCardPermission;

	private String code = "";

	private Long storeyId = 0L;

	private Long buildingId = 0L;

	private Long campusId = 0L;

	private Long roomId = 0L;

	private String nodeKey;

	private Long userId;

	private Long userType;

	private String macId;

	public String getNodeKey(){
		String res=projectId+""+campusId+""+buildingId+""+storeyId+""+roomId+""+id;
		return res.replaceAll("HOUQINBAO","").replaceAll("-","");
	}
}
