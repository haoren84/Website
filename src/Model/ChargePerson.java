package Model;

/**
 * ��������Ϣ
 * @author Administrator
 *
 */
public class ChargePerson {

	/**
	 * ����ID
	 */
	int AutoID = -1;
	/**
	 * ����������
	 */
	String personName = "";
	/**
	 * �����˹�˾
	 */
	String personCompany = "";
	/**
	 * �����˲���
	 */
	String personDepartName = "";
	/**
	 * �������Ա�
	 */
	String personSex = "";
	/**
	 * �����˵绰
	 */
	String personTel = "";
	/**
	 * ����������
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
