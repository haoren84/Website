package Model;

/**
 * �û�����
 * @author Administrator
 *
 */
public class UserModel {

	/**
	 * ����
	 */
	int AutoID;
	/**
	 * �û���	
	 */
	String UserName;
	/**
	 * ����
	 */
	String UserPassWord;
	/**
	 * Ȩ�޼���
	 */
	String UserRightLeve;
	
	public int getAutoID() {
		return AutoID;
	}
	public void setAutoID(int autoID) {
		AutoID = autoID;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getUserPassWord() {
		return UserPassWord;
	}
	public void setUserPassWord(String userPassWord) {
		UserPassWord = userPassWord;
	}
	public String getUserRightLeve() {
		return UserRightLeve;
	}
	public void setUserRightLeve(String userRightLeve) {
		UserRightLeve = userRightLeve;
	}
}
