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
		case "GetUserPageData":// 获取用户分页数据
		{
			sb = GetUserPageData(request);
		}
			break;
		case "AddUser"://新增用户
		{
			
			sb=AddUser(request);
		}
		break;
		
		case "UpdateUser"://修改用户
		{
			sb=UpdateUser(request);
		}
		break;
		
		case "DeleteUser"://删除用户
		{
			sb=DeleteUser(request);
		}
		break;
		
		case "UserLogin"://用户登录
		{
			sb=UserLogin(request);
		}
		break;
		
		case "UpdateUserByUserName"://用户修改密码
		{
			sb=UpdateUserPwd(request);
		}
		
		}

		return sb.toString();
	}

	/**
	 * 用户分页数据
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
	 * 新建用户
	 * @param request
	 * @return
	 */
	private StringBuffer AddUser(HttpServletRequest request) {
		
		String strUserName="",strUserPwd="";
		
		StringBuffer sb=new StringBuffer();
		
		if(request.getParameter("UserName")!=null) {
			strUserName=request.getParameter("UserName").toString();
		}else {
			
			sb.append("{\"result\":\"false\",\"errorMsg\":\"缺少用户名\"}");
			
			return sb;
		}
		
		if(request.getParameter("UserPwd")!=null) {
			strUserPwd=request.getParameter("UserPwd").toString();
		}else {
			
			sb.append("{\"result\":\"false\",\"errorMsg\":\"缺少用户密码\"}");
			
			return sb;
		}
		
		//用户名查重
		
		UserModelAction action=new UserModelAction();
		
		List<UserModel> list=action.SelectModelByUserName(strUserName);
		
		if(list!=null&&list.size()>0) {
			
			sb.append("{\"result\":\"false\",\"errorMsg\":\"用户名重复\"}");
			
			return sb;
			
		}
		
		UserModel model=new UserModel();
		
		model.setUserName(strUserName);
		model.setUserPassWord(strUserPwd);
		model.setUserRightLeve("user");
		
		boolean bRes=action.CreateUser(model);
		
		if(bRes) {
			
			sb.append("{\"result\":\"true\",\"errorMsg\":\"用户创建成功\"}");
			
		}else {
			
			sb.append("{\"result\":\"false\",\"errorMsg\":\"用户创建失败\"}");
		}
		
		return sb;
	}

	/**
	 * 修改用户
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
			sb.append("{\"result\":\"false\",\"errorMsg\":\"缺少参数\"}");
			
			return sb;
		}
		
		if(request.getParameter("UserName")!=null) {
			strUserName=request.getParameter("UserName").toString();
		}else {
			
			sb.append("{\"result\":\"false\",\"errorMsg\":\"缺少用户名\"}");
			
			return sb;
		}
		
		if(request.getParameter("UserPwd")!=null) {
			
			strUserPwd=request.getParameter("UserPwd").toString();
			
		}else {
			
			sb.append("{\"result\":\"false\",\"errorMsg\":\"缺少用户密码\"}");
			
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
			
			sb.append("{\"result\":\"true\",\"errorMsg\":\"用户修改成功\"}");
			
		}else {
			
			sb.append("{\"result\":\"false\",\"errorMsg\":\"用户修改失败\"}");
		}
		
		return sb;
	}
	
	/**
	 * 删除用户
	 * @param request
	 * @return
	 */
	private StringBuffer DeleteUser(HttpServletRequest request) {
		
		int nAutoID=0;
		
		StringBuffer sb=new StringBuffer();
		
		if(request.getParameter("autoid")!=null) {
			nAutoID=Integer.parseInt(request.getParameter("autoid").toString());
		}else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"缺少参数\"}");
			
			return sb;
		}
		
		UserModelAction action=new UserModelAction();
		
		UserModel model=new UserModel();
		model.setAutoID(nAutoID);
		
		boolean bRes=action.DeleteUser(model);
		
		if(bRes) {
			
			sb.append("{\"result\":\"true\",\"errorMsg\":\"用户删除成功\"}");
			
		}else {
			
			sb.append("{\"result\":\"false\",\"errorMsg\":\"用户删除失败\"}");
		}
		
		return sb;
	}
	
	/**
	 * 用户登录
	 * @param request
	 * @return
	 */
	private StringBuffer UserLogin(HttpServletRequest request) {
		
		String strUserName="",strUserPwd="";
		
		StringBuffer sb=new StringBuffer();
		
		if(request.getParameter("UserName")!=null) {
			strUserName=request.getParameter("UserName").toString();
		}else {
			
			sb.append("{\"result\":\"false\",\"errorMsg\":\"缺少用户名\"}");
			
			return sb;
		}
		
		if(request.getParameter("UserPwd")!=null) {
			strUserPwd=request.getParameter("UserPwd").toString();
		}else {
			
			sb.append("{\"result\":\"false\",\"errorMsg\":\"缺少用户密码\"}");
			
			return sb;
		}
		
		UserModelAction action=new UserModelAction();
		
		boolean bRes=action.CheckUserInfoByNameAndPwd(strUserName, strUserPwd);
		
		if(bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"验证成功\"}");
		}else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"验证失败\"}");
		}
		
		return sb;
	}

	/**
	 * 修改密码
	 * @param request
	 * @return
	 */
	private StringBuffer UpdateUserPwd(HttpServletRequest request) {
		
		String strUserName="",strUserPwd="";
		
		StringBuffer sb=new StringBuffer();
		
		if(request.getParameter("UserName")!=null) {
			strUserName=request.getParameter("UserName").toString();
		}else {
			
			sb.append("{\"result\":\"false\",\"errorMsg\":\"缺少用户名\"}");
			
			return sb;
		}
		
		if(request.getParameter("UserPwd")!=null) {
			strUserPwd=request.getParameter("UserPwd").toString();
		}else {
			
			sb.append("{\"result\":\"false\",\"errorMsg\":\"缺少用户密码\"}");
			
			return sb;
		}
		
		UserModelAction action=new UserModelAction();
		
		boolean bRes=action.UpdateUserPwdByUserName(strUserName, strUserPwd);
		
		if(bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"修改成功\"}");
		}else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"修改失败\"}");
		}
		
		return sb;
	}


}
