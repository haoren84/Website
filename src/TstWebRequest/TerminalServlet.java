package TstWebRequest;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Business.TerminalAction;
import Model.InstrumentTerminalModel;

/**
 * Servlet implementation class TerminalServlet
 */
@WebServlet("/TerminalServlet")
public class TerminalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TerminalServlet() {
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

		response.getWriter().append(this.ReturnResquest(request, response));
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

	/**
	 * 返回请求信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
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
		case "GetTerminalPageData":// 获取终端的分页数据
		{
			sb = GetTerminalPageData(request);
		}
			break;
		case "GetTerminalFactoryComboData":// 获取终端的下拉分页数据
		{
			sb = GetTerminalFactoryComboData(request);
		}
			break;
		case "AddTerminal":// 新增终端
		{
			sb = AddTerminal(request);
		}
			break;
		case "UpdateTerminal":// 修改终端
		{
			sb = UpdateTerminal(request);
		}
			break;
		case "DeleteTerminal":// 删除终端
		{
			sb = DeleteTerminal(request);
		}
			break;
		case "CheckTerminalMap"://检查终端是否匹配
		{
			sb=CheckTerminalMap(request);
		}
		break;
		case "CheckTerminalisExist":
		{
			sb=CheckTerminalisExist(request);
		}
		break;
		}

		return sb.toString();
	}

	/**
	 * 获取当前的工程名称
	 * 
	 * @param request
	 * @return
	 */
	private String GetCurPrjName(HttpServletRequest request) {

		CommonRequestCtrl comCtrl = new CommonRequestCtrl();

		String strCurPrjName = comCtrl.GetCurPrjNameByCookie(request);

		return strCurPrjName;
	}

	/**
	 * 获取终端的分页数据
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer GetTerminalPageData(HttpServletRequest request) {

		String strTerminalName = "", strTerminalType = "";

		if (request.getParameter("terminalName") != null) {
			strTerminalName = request.getParameter("terminalName").toString();
		}
		if (request.getParameter("terminalType") != null) {
			strTerminalType = request.getParameter("terminalType").toString();
		}

		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));

		String strCurPrjName = GetCurPrjName(request);

		TerminalAction action = new TerminalAction(strCurPrjName);

		StringBuffer sb = action.GetTerminalPage(strTerminalName, strTerminalType, page, rows);

		return sb;
	}

	/**
	 * 获取终端的下拉分页数据
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer GetTerminalFactoryComboData(HttpServletRequest request) {

		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));

		String strCurPrjName = GetCurPrjName(request);

		TerminalAction action = new TerminalAction(strCurPrjName);

		StringBuffer sb = action.GetTerminalFactoryComboData(page, rows);

		return sb;
	}

	/**
	 * 新增终端请求
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer AddTerminal(HttpServletRequest request) {

		InstrumentTerminalModel model = new InstrumentTerminalModel();

		// 1. 校验前台输入的参数
		if (request.getParameter("insFactoryID") != null) {
			model.setinsFactoryID(request.getParameter("insFactoryID").toString());
		}

		if (request.getParameter("insID") != null) {
			String strinsID = request.getParameter("insID").toString();
			model.setinsID(Integer.parseInt(strinsID));
		}

		if (request.getParameter("insType") != null) {
			String strinsType = request.getParameter("insType").toString();
			model.setinsType(Integer.parseInt(strinsType));
		}

		if (request.getParameter("insChnCount") != null) {
			String strinsChnCount = request.getParameter("insChnCount").toString();
			model.setinsChnCount(Integer.parseInt(strinsChnCount));
		}

		if (request.getParameter("terminalName") != null) {
			model.setTerminalName(request.getParameter("terminalName").toString());
		}

		if (request.getParameter("terminalType") != null) {
			model.setTerminalType(request.getParameter("terminalType").toString());
		}

		if (request.getParameter("terminalDesc") != null) {
			model.setTerminalDesc(request.getParameter("terminalDesc").toString());
		}

		if (request.getParameter("netID") != null) {
			model.setNetID(request.getParameter("netID").toString());
		}

		String strCurPrjName = GetCurPrjName(request);
		
		TerminalAction action = new TerminalAction(strCurPrjName);
		
		StringBuffer sb = new StringBuffer();

		boolean bRes = action.CreateTerminal(model);

		if (bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"新增成功\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"新增失败\"}");
		}
		
		return sb;
	}

	/**
	 * 修改终端
	 * @param request
	 * @return
	 */
	private StringBuffer UpdateTerminal(HttpServletRequest request) {
		
		InstrumentTerminalModel model = new InstrumentTerminalModel();
		
		String strOldFactoryID="";
		
		if(request.getParameter("oldFactoryID") != null) {
			strOldFactoryID=request.getParameter("oldFactoryID").toString();
		}
		
		if(request.getParameter("autoid") != null) {
			String strAutoID=request.getParameter("autoid").toString();
			model.setAutoID(Integer.parseInt(strAutoID));
		}

		// 1. 校验前台输入的参数
		if (request.getParameter("insFactoryID") != null) {
			model.setinsFactoryID(request.getParameter("insFactoryID").toString());
		}

		if (request.getParameter("insID") != null) {
			String strinsID = request.getParameter("insID").toString();
			model.setinsID(Integer.parseInt(strinsID));
		}

		if (request.getParameter("insType") != null) {
			String strinsType = request.getParameter("insType").toString();
			model.setinsType(Integer.parseInt(strinsType));
		}

		if (request.getParameter("insChnCount") != null) {
			String strinsChnCount = request.getParameter("insChnCount").toString();
			model.setinsChnCount(Integer.parseInt(strinsChnCount));
		}

		if (request.getParameter("terminalName") != null) {
			model.setTerminalName(request.getParameter("terminalName").toString());
		}

		if (request.getParameter("terminalType") != null) {
			model.setTerminalType(request.getParameter("terminalType").toString());
		}

		if (request.getParameter("terminalDesc") != null) {
			model.setTerminalDesc(request.getParameter("terminalDesc").toString());
		}

		if (request.getParameter("netID") != null) {
			model.setNetID(request.getParameter("netID").toString());
		}

		String strCurPrjName = GetCurPrjName(request);
		
		TerminalAction action = new TerminalAction(strCurPrjName);
		
		StringBuffer sb = new StringBuffer();
		
		boolean bRes = action.UpdateTerminal(model, strOldFactoryID);
		
		if (bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"修改成功\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"修改失败\"}");
		}
		
		return sb;
	}

	/**
	 * 删除终端
	 * @param request
	 * @return
	 */
	private StringBuffer DeleteTerminal(HttpServletRequest request) {
		
		InstrumentTerminalModel model = new InstrumentTerminalModel();
		
		if(request.getParameter("autoid") != null) {
			String strAutoID=request.getParameter("autoid").toString();
			model.setAutoID(Integer.parseInt(strAutoID));
		}

		// 1. 校验前台输入的参数
		if (request.getParameter("insFactoryID") != null) {
			model.setinsFactoryID(request.getParameter("insFactoryID").toString());
		}

		if (request.getParameter("insID") != null) {
			String strinsID = request.getParameter("insID").toString();
			model.setinsID(Integer.parseInt(strinsID));
		}

		if (request.getParameter("insType") != null) {
			String strinsType = request.getParameter("insType").toString();
			model.setinsType(Integer.parseInt(strinsType));
		}

		if (request.getParameter("insChnCount") != null) {
			String strinsChnCount = request.getParameter("insChnCount").toString();
			model.setinsChnCount(Integer.parseInt(strinsChnCount));
		}

		if (request.getParameter("terminalName") != null) {
			model.setTerminalName(request.getParameter("terminalName").toString());
		}

		if (request.getParameter("terminalType") != null) {
			model.setTerminalType(request.getParameter("terminalType").toString());
		}

		if (request.getParameter("terminalDesc") != null) {
			model.setTerminalDesc(request.getParameter("terminalDesc").toString());
		}

		if (request.getParameter("netID") != null) {
			model.setNetID(request.getParameter("netID").toString());
		}
		
		String strCurPrjName = GetCurPrjName(request);
		
		TerminalAction action = new TerminalAction(strCurPrjName);
		
		StringBuffer sb = new StringBuffer();
		
		boolean bRes = action.DeleteTerminal(model);
		
		if (bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"删除成功\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"删除失败\"}");
		}
		
		return sb;
	}

	/**
	 * 检查终端匹配
	 * @param request
	 * @return
	 */
	private StringBuffer CheckTerminalMap(HttpServletRequest request) {
		
		String strinsFactoryID="";
		
		if (request.getParameter("insFactoryID") != null) {
			strinsFactoryID=request.getParameter("insFactoryID").toString();
		}
		
		String strCurPrjName = GetCurPrjName(request);
		
		TerminalAction action = new TerminalAction(strCurPrjName);
		
		StringBuffer sb = new StringBuffer();
		
		boolean bRes = action.CheckTerminalMap(strinsFactoryID);

		if (bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"存在匹配\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"不存在匹配\"}");
		}
		
		return sb;
	}
	
	/**
	 * 检查终端出厂编号是否存在
	 * @param request
	 * @return
	 */
	private StringBuffer CheckTerminalisExist(HttpServletRequest request) {
		
		String strinsFactoryID="";
		
		if (request.getParameter("insFactoryID") != null) {
			strinsFactoryID=request.getParameter("insFactoryID").toString();
		}
		
		String strCurPrjName = GetCurPrjName(request);
		
		TerminalAction action = new TerminalAction(strCurPrjName);
		
		StringBuffer sb = new StringBuffer();
		
		boolean bRes = action.CheckTerminalisExist(strinsFactoryID);

		if (bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"已存在\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"不存在\"}");
		}
		
		return sb;
	}
}
