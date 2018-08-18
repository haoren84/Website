package Model;

/**
 * 主库工程信息
 * 
 * @author Administrator
 *
 */
public class MainProjectInfo {
	/**
	 * 自增ID
	 */
	int AutoID = -1;
	/**
	 * 工程名称，不重复
	 */
	String prjName = "";
	/**
	 * 工程类型
	 */
	int prjType = 0;
	/**
	 * 工程地址
	 */
	String PrjAddress = "";
	/**
	 * 工程负责人
	 */
	String prjManager = "";
	/**
	 * 经度
	 */
	float prjLng = 0.f;
	/**
	 * 纬度
	 */
	float PrjLat = 0.f;
	/**
	 * 工程备注
	 */
	String Remark = "";
	/**
	 * 工程数据库名称
	 */
	String PrjDBName = "";

	public int getAutoID() {
		return AutoID;
	}

	public void setAutoID(int autoID) {
		AutoID = autoID;
	}

	public String getPrjName() {
		return prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	public int getPrjType() {
		return prjType;
	}

	public void setPrjType(int prjType) {
		this.prjType = prjType;
	}

	public String getPrjAddress() {
		return PrjAddress;
	}

	public void setPrjAddress(String prjAddress) {
		PrjAddress = prjAddress;
	}

	public String getPrjManager() {
		return prjManager;
	}

	public void setPrjManager(String prjManager) {
		this.prjManager = prjManager;
	}

	public float getPrjLng() {
		return prjLng;
	}

	public void setPrjLng(float prjLng) {
		this.prjLng = prjLng;
	}

	public float getPrjLat() {
		return PrjLat;
	}

	public void setPrjLat(float prjLat) {
		PrjLat = prjLat;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public String getPrjDBName() {
		return PrjDBName;
	}

	public void setPrjDBName(String prjDBName) {
		PrjDBName = prjDBName;
	}

}
