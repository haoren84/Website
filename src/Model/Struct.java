package Model;

/**
 * 工程结构
 */
public class Struct {
	/**
	 * 主键ID
	 */
	int AutoID;
	/**
	 * 工程结构名称
	 */
	String structName;
	/**
	 * 工程结构编号
	 */
	String structCode;
	/**
	 * 类型
	 */
	String structType;
	/**
	 * 所属父结构
	 */
	String structParent;
	/**
	 * 备注
	 */
	String structRemark;
	/**
	 * 地址
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
