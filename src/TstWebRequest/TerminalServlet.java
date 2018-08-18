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
	 * ����������Ϣ
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
		case "GetTerminalPageData":// ��ȡ�ն˵ķ�ҳ����
		{
			sb = GetTerminalPageData(request);
		}
			break;
		case "GetTerminalFactoryComboData":// ��ȡ�ն˵�������ҳ����
		{
			sb = GetTerminalFactoryComboData(request);
		}
			break;
		case "AddTerminal":// �����ն�
		{
			sb = AddTerminal(request);
		}
			break;
		case "UpdateTerminal":// �޸��ն�
		{
			sb = UpdateTerminal(request);
		}
			break;
		case "DeleteTerminal":// ɾ���ն�
		{
			sb = DeleteTerminal(request);
		}
			break;
		case "CheckTerminalMap"://����ն��Ƿ�ƥ��
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
	 * ��ȡ��ǰ�Ĺ�������
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
	 * ��ȡ�ն˵ķ�ҳ����
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
	 * ��ȡ�ն˵�������ҳ����
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
	 * �����ն�����
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer AddTerminal(HttpServletRequest request) {

		InstrumentTerminalModel model = new InstrumentTerminalModel();

		// 1. У��ǰ̨����Ĳ���
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
			sb.append("{\"result\":\"true\",\"errorMsg\":\"�����ɹ�\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"����ʧ��\"}");
		}
		
		return sb;
	}

	/**
	 * �޸��ն�
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

		// 1. У��ǰ̨����Ĳ���
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
			sb.append("{\"result\":\"true\",\"errorMsg\":\"�޸ĳɹ�\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"�޸�ʧ��\"}");
		}
		
		return sb;
	}

	/**
	 * ɾ���ն�
	 * @param request
	 * @return
	 */
	private StringBuffer DeleteTerminal(HttpServletRequest request) {
		
		InstrumentTerminalModel model = new InstrumentTerminalModel();
		
		if(request.getParameter("autoid") != null) {
			String strAutoID=request.getParameter("autoid").toString();
			model.setAutoID(Integer.parseInt(strAutoID));
		}

		// 1. У��ǰ̨����Ĳ���
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
			sb.append("{\"result\":\"true\",\"errorMsg\":\"ɾ���ɹ�\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ɾ��ʧ��\"}");
		}
		
		return sb;
	}

	/**
	 * ����ն�ƥ��
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
			sb.append("{\"result\":\"true\",\"errorMsg\":\"����ƥ��\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"������ƥ��\"}");
		}
		
		return sb;
	}
	
	/**
	 * ����ն˳�������Ƿ����
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
			sb.append("{\"result\":\"true\",\"errorMsg\":\"�Ѵ���\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"������\"}");
		}
		
		return sb;
	}
}
