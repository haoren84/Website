package Model;

/**
 * ���⹤����Ϣ
 * 
 * @author Administrator
 *
 */
public class MainProjectInfo {
	/**
	 * ����ID
	 */
	int AutoID = -1;
	/**
	 * �������ƣ����ظ�
	 */
	String prjName = "";
	/**
	 * ��������
	 */
	int prjType = 0;
	/**
	 * ���̵�ַ
	 */
	String PrjAddress = "";
	/**
	 * ���̸�����
	 */
	String prjManager = "";
	/**
	 * ����
	 */
	float prjLng = 0.f;
	/**
	 * γ��
	 */
	float PrjLat = 0.f;
	/**
	 * ���̱�ע
	 */
	String Remark = "";
	/**
	 * �������ݿ�����
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
