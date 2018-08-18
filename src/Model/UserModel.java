package Model;

/**
 * 用户对象
 * @author Administrator
 *
 */
public class UserModel {

	/**
	 * 主键
	 */
	int AutoID;
	/**
	 * 用户名	
	 */
	String UserName;
	/**
	 * 密码
	 */
	String UserPassWord;
	/**
	 * 权限级别
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
