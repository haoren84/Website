package Model;

/**
 * ���̽ṹ
 */
public class Struct {
	/**
	 * ����ID
	 */
	int AutoID;
	/**
	 * ���̽ṹ����
	 */
	String structName;
	/**
	 * ���̽ṹ���
	 */
	String structCode;
	/**
	 * ����
	 */
	String structType;
	/**
	 * �������ṹ
	 */
	String structParent;
	/**
	 * ��ע
	 */
	String structRemark;
	/**
	 * ��ַ
	 */
	String structAddress;
	
	public int getAutoID() {
		return AutoID;
	}
	public void setAutoID(int autoID) {
		AutoID = autoID;
	}
	public String getStructName() {
		return structName;
	}
	public void setStructName(String structName) {
		this.structName = structName;
	}
	public String getStructCode() {
		return structCode;
	}
	public void setStructCode(String structCode) {
		this.structCode = structCode;
	}
	public String getStructType() {
		return structType;
	}
	public void setStructType(String structType) {
		this.structType = structType;
	}
	public String getStructParent() {
		return structParent;
	}
	public void setStructParent(String structParent) {
		this.structParent = structParent;
	}
	public String getStructRemark() {
		return structRemark;
	}
	public void setStructRemark(String structRemark) {
		this.structRemark = structRemark;
	}
	public String getStructAddress() {
		return structAddress;
	}
	public void setStructAddress(String structAddress) {
		this.structAddress = structAddress;
	}
	
	
}
