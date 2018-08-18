package TstWebRequest;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Business.UserModelAction;
import Model.UserModel;

/**
 * Servlet implementation class UserCtrlServlet
 */
@WebServlet("/UserCtrlServlet")
public class UserCtrlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserCtrlServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		response.getWriter().append(ReturnResquest(request, response));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private String ReturnResquest(HttpServletRequest request, HttpServletResponse response) {

		if (request.getParameter("action") == null) {
			return "";
		}

		String strActionName = new String(request.getParameter("action"));

		StringBuffer sb = new StringBuffer();

		if (strActionName == null || strActionName.isEmpty()) {
			return sb.toString();
		}

		switch (strActionName) {
		case "GetUserPageData":// ��ȡ�û���ҳ����
		{
			sb = GetUserPageData(request);
		}
			break;
		case "AddUser"://�����û�
		{
			
			sb=AddUser(request);
		}
		break;
		
		case "UpdateUser"://�޸��û�
		{
			sb=UpdateUser(request);
		}
		break;
		
		case "DeleteUser"://ɾ���û�
		{
			sb=DeleteUser(request);
		}
		break;
		
		case "UserLogin"://�û���¼
		{
			sb=UserLogin(request);
		}
		break;
		
		case "UpdateUserByUserName"://�û��޸�����
		{
			sb=UpdateUserPwd(request);
		}
		
		}

		return sb.toString();
	}

	/**
	 * �û���ҳ����
	 * @param request
	 * @return
	 */
	private StringBuffer GetUserPageData(HttpServletRequest request) {

		StringBuffer sb = new StringBuffer();

		if (request.getParameter("page") == null || request.getParameter("rows") == null) {

			sb.append("{\"total\":0,\"rows\":[]}");

			return sb;
		}

		int page = Integer.parseInt(request.getParameter("page").toString());

		int rows = Integer.parseInt(request.getParameter("rows").toString());

		UserModelAction action = new UserModelAction();

		List<UserModel> list = action.GetUserPageData(page, rows);

		int nDataCount = action.GetUserDataCount();

		if (nDataCount == 0 || list == null || list.size() <= 0) {

			sb.append("{\"total\":0,\"rows\":[]}");

			return sb;

		} else {
			sb.append("{");
			sb.append("\"rows\":[");
			for (UserModel item : list) {
				sb.append("{");
				sb.append(String.format("\"id\":\"%s\",", item.getAutoID()));
				sb.append(String.format("\"username\":\"%s\"", item.getUserName()));
				sb.append("},");
			}
			sb.deleteCharAt(sb.length() - 1);

			sb.append("],\"total\":").append(nDataCount).append("}");

		}

		return sb;
	}

	/**
	 * �½��û�
	 * @param request
	 * @return
	 */
	private StringBuffer AddUser(HttpServletRequest request) {
		
		String strUserName="",strUserPwd="";
		
		StringBuffer sb=new StringBuffer();
		
		if(request.getParameter("UserName")!=null) {
			strUserName=request.getParameter("UserName").toString();
		}else {
			
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ȱ���û���\"}");
			
			return sb;
		}
		
		if(request.getParameter("UserPwd")!=null) {
			strUserPwd=request.getParameter("UserPwd").toString();
		}else {
			
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ȱ���û�����\"}");
			
			return sb;
		}
		
		//�û�������
		
		UserModelAction action=new UserModelAction();
		
		List<UserModel> list=action.SelectModelByUserName(strUserName);
		
		if(list!=null&&list.size()>0) {
			
			sb.append("{\"result\":\"false\",\"errorMsg\":\"�û����ظ�\"}");
			
			return sb;
			
		}
		
		UserModel model=new UserModel();
		
		model.setUserName(strUserName);
		model.setUserPassWord(strUserPwd);
		model.setUserRightLeve("user");
		
		boolean bRes=action.CreateUser(model);
		
		if(bRes) {
			
			sb.append("{\"result\":\"true\",\"errorMsg\":\"�û������ɹ�\"}");
			
		}else {
			
			sb.append("{\"result\":\"false\",\"errorMsg\":\"�û�����ʧ��\"}");
		}
		
		return sb;
	}

	/**
	 * �޸��û�
	 * @param request
	 * @return
	 */
	private StringBuffer UpdateUser(HttpServletRequest request) {
		
		String strUserName="",strUserPwd="",strUserRightLeve="";
		
		int nAutoID=0;
		
		StringBuffer sb=new StringBuffer();
		
		if(request.getParameter("autoid")!=null) {
			nAutoID=Integer.parseInt(request.getParameter("autoid").toString());
		}else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ȱ�ٲ���\"}");
			
			return sb;
		}
		
		if(request.getParameter("UserName")!=null) {
			strUserName=request.getParameter("UserName").toString();
		}else {
			
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ȱ���û���\"}");
			
			return sb;
		}
		
		if(request.getParameter("UserPwd")!=null) {
			
			strUserPwd=request.getParameter("UserPwd").toString();
			
		}else {
			
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ȱ���û�����\"}");
			
			return sb;
			
		}
		
		if(request.getParameter("UserRightLeve")!=null) {
			
			strUserRightLeve=request.getParameter("UserRightLeve").toString();
		}else {
			strUserRightLeve="user";
		}
		
		UserModel model=new UserModel();
		model.setAutoID(nAutoID);
		model.setUserName(strUserName);
		model.setUserPassWord(strUserPwd);
		model.setUserRightLeve(strUserRightLeve);
		
		UserModelAction action=new UserModelAction();
		
		boolean bRes=action.UpdateUser(model);
		
		if(bRes) {
			
			sb.append("{\"result\":\"true\",\"errorMsg\":\"�û��޸ĳɹ�\"}");
			
		}else {
			
			sb.append("{\"result\":\"false\",\"errorMsg\":\"�û��޸�ʧ��\"}");
		}
		
		return sb;
	}
	
	/**
	 * ɾ���û�
	 * @param request
	 * @return
	 */
	private StringBuffer DeleteUser(HttpServletRequest request) {
		
		int nAutoID=0;
		
		StringBuffer sb=new StringBuffer();
		
		if(request.getParameter("autoid")!=null) {
			nAutoID=Integer.parseInt(request.getParameter("autoid").toString());
		}else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ȱ�ٲ���\"}");
			
			return sb;
		}
		
		UserModelAction action=new UserModelAction();
		
		UserModel model=new UserModel();
		model.setAutoID(nAutoID);
		
		boolean bRes=action.DeleteUser(model);
		
		if(bRes) {
			
			sb.append("{\"result\":\"true\",\"errorMsg\":\"�û�ɾ���ɹ�\"}");
			
		}else {
			
			sb.append("{\"result\":\"false\",\"errorMsg\":\"�û�ɾ��ʧ��\"}");
		}
		
		return sb;
	}
	
	/**
	 * �û���¼
	 * @param request
	 * @return
	 */
	private StringBuffer UserLogin(HttpServletRequest request) {
		
		String strUserName="",strUserPwd="";
		
		StringBuffer sb=new StringBuffer();
		
		if(request.getParameter("UserName")!=null) {
			strUserName=request.getParameter("UserName").toString();
		}else {
			
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ȱ���û���\"}");
			
			return sb;
		}
		
		if(request.getParameter("UserPwd")!=null) {
			strUserPwd=request.getParameter("UserPwd").toString();
		}else {
			
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ȱ���û�����\"}");
			
			return sb;
		}
		
		UserModelAction action=new UserModelAction();
		
		boolean bRes=action.CheckUserInfoByNameAndPwd(strUserName, strUserPwd);
		
		if(bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"��֤�ɹ�\"}");
		}else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"��֤ʧ��\"}");
		}
		
		return sb;
	}

	/**
	 * �޸�����
	 * @param request
	 * @return
	 */
	private StringBuffer UpdateUserPwd(HttpServletRequest request) {
		
		String strUserName="",strUserPwd="";
		
		StringBuffer sb=new StringBuffer();
		
		if(request.getParameter("UserName")!=null) {
			strUserName=request.getParameter("UserName").toString();
		}else {
			
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ȱ���û���\"}");
			
			return sb;
		}
		
		if(request.getParameter("UserPwd")!=null) {
			strUserPwd=request.getParameter("UserPwd").toString();
		}else {
			
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ȱ���û�����\"}");
			
			return sb;
		}
		
		UserModelAction action=new UserModelAction();
		
		boolean bRes=action.UpdateUserPwdByUserName(strUserName, strUserPwd);
		
		if(bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"�޸ĳɹ�\"}");
		}else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"�޸�ʧ��\"}");
		}
		
		return sb;
	}


}
