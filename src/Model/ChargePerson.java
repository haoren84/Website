package Model;

/**
 * 负责人信息
 * @author Administrator
 *
 */
public class ChargePerson {

	/**
	 * 主键ID
	 */
	int AutoID = -1;
	/**
	 * 负责人名称
	 */
	String personName = "";
	/**
	 * 负责人公司
	 */
	String personCompany = "";
	/**
	 * 负责人部门
	 */
	String personDepartName = "";
	/**
	 * 负责人性别
	 */
	String personSex = "";
	/**
	 * 负责人电话
	 */
	String personTel = "";
	/**
	 * 负责人邮箱
	 */
	String personEmail = "";
	
	public int getAutoID() {
		return AutoID;
	}
	public void setAutoID(int autoID) {
		AutoID = autoID;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getPersonCompany() {
		return personCompany;
	}
	public void setPersonCompany(String personCompany) {
		this.personCompany = personCompany;
	}
	public String getPersonDepartName() {
		return personDepartName;
	}
	public void setPersonDepartName(String personDepartName) {
		this.personDepartName = personDepartName;
	}
	public String getPersonSex() {
		return personSex;
	}
	public void setPersonSex(String personSex) {
		this.personSex = personSex;
	}
	public String getPersonTel() {
		return personTel;
	}
	public void setPersonTel(String personTel) {
		this.personTel = personTel;
	}
	public String getPersonEmail() {
		return personEmail;
	}
	public void setPersonEmail(String personEmail) {
		this.personEmail = personEmail;
	}
	
}
