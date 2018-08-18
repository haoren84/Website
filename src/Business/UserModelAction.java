package Business;

import java.util.ArrayList;
import java.util.List;

import Model.UserModel;
import SqlHelper.MySqlHelper;
import TstDataTable.DataRow;
import TstDataTable.DataTable;

/**
 * �û�ģ�����
 * @author Administrator
 *
 */
public class UserModelAction {

	String strMainDBName = null;

	MySqlHelper mysql = null;

	public UserModelAction() {

		mysql = new MySqlHelper();

		strMainDBName = mysql.MainDBName();
	}
	
	/**
	 * �½��û�
	 * @param model
	 * @return
	 */
	public boolean CreateUser(UserModel model) {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("INSERT INTO `mainuser` " + 
				"(`UserName`, `UserPassWord`, `UserRightLeve`) " + 
				"VALUES ('"+model.getUserName()+"', '"+model.getUserPassWord()+"', '"+model.getUserRightLeve()+"');");
		
		int nRes=mysql.ExecuteQueryNoneDB(strMainDBName, sb.toString());
		
		return nRes==1;
	}
	
	/**
	 * �޸��û�
	 * @param model
	 * @return
	 */
	public boolean UpdateUser(UserModel model) {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("UPDATE `mainuser` SET `UserName`='"+model.getUserName()+"', `UserPassWord`='"+model.getUserPassWord()+"', "
				+ "`UserRightLeve`='"+model.getUserRightLeve()+"' WHERE (`AutoID`='"+model.getAutoID()+"');");
		
		int nRes=mysql.ExecuteQueryNoneDB(strMainDBName, sb.toString());
		
		return nRes==1;
	}
	
	/**
	 * �����û����޸��û�����
	 * @param strUserName
	 * @param strUserPwd
	 * @return
	 */
	public boolean UpdateUserPwdByUserName(String strUserName,String strUserPwd) {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("UPDATE `mainuser` SET `UserPassWord`='"+strUserPwd+"' WHERE (`UserName`='"+strUserName+"');");
		
		int nRes=mysql.ExecuteQueryNoneDB(strMainDBName, sb.toString());
		
		return nRes==1;
	}
	
	/**
	 * ɾ���û�
	 * @param model
	 * @return
	 */
	public boolean DeleteUser(UserModel model) {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("DELETE FROM `mainuser` WHERE `AutoID` = '"+model.getAutoID()+"';");
		
		int nRes=mysql.ExecuteQueryNoneDB(strMainDBName, sb.toString());
		
		return nRes==1;
	}
	
	/**
	 * �����û������Ҷ�Ӧ���û�
	 * @param strUserName
	 * @return
	 */
	public List<UserModel> SelectModelByUserName(String strUserName) {
		
		List<UserModel> list=new ArrayList<>();
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select * from `mainuser` WHERE `UserName`= '"+strUserName+"';");
		
		DataTable dt = mysql.ExecuteQueryDB(strMainDBName, sb.toString());
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {
					
					UserModel model=new UserModel();
					
					model.setAutoID(dr.getIntColumn("AutoID"));
					model.setUserName(dr.getStringColumn("UserName"));
					model.setUserPassWord(dr.getStringColumn("UserPassWord"));
					model.setUserRightLeve(dr.getStringColumn("UserRightLeve"));
					
					list.add(model);
				}
			}

		}
		
		return list; 
	}
	
	/**
	 * ��ȡ�û���Ϣ�ķ�ҳ����
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<UserModel> GetUserPageData(int page, int rows){
		
		List<UserModel> list=new ArrayList<>();
		
		StringBuffer sb=new StringBuffer();
		
		int nComputcout = (page - 1) * rows;
		
		sb.append("select * from `mainuser` limit ").append(nComputcout).append(",").append(rows).append(";");
		
		DataTable dt = mysql.ExecuteQueryDB(strMainDBName, sb.toString());
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {
					
					UserModel model=new UserModel();
					
					model.setAutoID(dr.getIntColumn("AutoID"));
					model.setUserName(dr.getStringColumn("UserName"));
					model.setUserPassWord(dr.getStringColumn("UserPassWord"));
					model.setUserRightLeve(dr.getStringColumn("UserRightLeve"));
					
					list.add(model);
				}
			}

		}
		
		return list;
	}
	
	/**
	 * ��ȡ�û����ܸ���
	 * @return
	 */
	public int GetUserDataCount() {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select count(*) from `mainuser`;");

		DataTable dt = mysql.ExecuteQueryDB(strMainDBName, sb.toString());
		
		int nRes = 0;

		if (dt == null || dt.getRow() == null || dt.getRow().size() <= 0) {
			nRes = 0;
		} else {
			nRes = Integer.parseInt(dt.getRow().get(0).getCol().get(0).getValue().toString());
		}

		return nRes;
	}
	
	/**
	 * ��֤�û���Ϣ
	 * @param strUserName
	 * @param strUserPwd
	 * @return
	 */
	public boolean CheckUserInfoByNameAndPwd(String strUserName,String strUserPwd) {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select * from `mainuser` where `UserName`= '"+strUserName+"' and `UserPassWord`='"+strUserPwd+"';");

		DataTable dt = mysql.ExecuteQueryDB(strMainDBName, sb.toString());
		
		if(dt == null || dt.getRow() == null || dt.getRow().size() <= 0) {
			return false;
		}else {
			return true;
		}
	}
	
}
