package TstWebRequest;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Business.MPointManagerAction;
import Model.PointChnSensorRelationModel;

/**
 * Servlet implementation class MPointManagerServlet
 */
@WebServlet("/MPointManagerServlet")
public class MPointManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MPointManagerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		response.getWriter().append(this.ReturnResquest(request, response));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	/**
	 * ����������Ϣ
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
		case "GetMonitorNameTree"://�����Ŀ������������
		{
			sb=GetMonitorNameTree(request);
		}
		break;
		case "GetMonitorComboData"://�����Ŀ��������������
		{
			sb=GetMonitorComboData(request);
		}
		break;
		case "GetMPPageData"://�����Ŀ�Ĳ���ҳ����
		{
			sb=GetMPPageData(request);
		}
		break;
		case "GetTerminalComboPageData"://��ȡ�ն˵���������
		{
			sb=GetTerminalComboPageData(request);
		}
		break;
		case "GetTerminalChnComboData"://�ն˵�ͨ����������
		{
			sb=GetTerminalChnComboData(request);
		}
		break;
		case "GetSeneorComboPageData"://��ȡ����������������
		{
			sb=GetSeneorComboPageData(request);
		}
		break;
		case "AddMapInfo"://����ƥ����Ϣ
		{
			sb=AddMapInfo(request);
		}
		break;
		case "UpdateMapInfo"://�޸�ƥ����Ϣ
		{
			sb=UpdateMapInfo(request);
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
	 * �����Ŀ������������
	 * @param request
	 * @return
	 */
	private StringBuffer GetMonitorNameTree(HttpServletRequest request) {
		
		String strCurPrjName = GetCurPrjName(request);
		
		MPointManagerAction action=new MPointManagerAction(strCurPrjName);
		
		StringBuffer sb=action.GetPrjNameTree();
		
		return sb;
	}
	
	/**
	 * �����Ŀ��������������
	 * @param request
	 * @return
	 */
	private StringBuffer GetMonitorComboData(HttpServletRequest request) {
		
		String strCurPrjName = GetCurPrjName(request);
		
		MPointManagerAction action=new MPointManagerAction(strCurPrjName);
		
		StringBuffer sb=action.GetPrjNameComboData();
		
		return sb;
	}
	
	/**
	 * �����Ŀ�Ĳ���ҳ����
	 * @param request
	 * @return
	 */
	private StringBuffer GetMPPageData(HttpServletRequest request) {
		
		String strMPNameKey=null, strInsNameKey=null, strMonitorName=null;
		
		if (request.getParameter("MPNameKey") != null) {
			strMPNameKey=request.getParameter("MPNameKey").toString();
		}
		
		if (request.getParameter("InsNameKey") != null) {
			strInsNameKey=request.getParameter("InsNameKey").toString();
		}
		
		if (request.getParameter("MonitorName") != null) {
			strMonitorName=request.getParameter("MonitorName").toString();
		}
		
		int page=Integer.parseInt(request.getParameter("page"));
		int rows=Integer.parseInt(request.getParameter("rows"));
		
		String strCurPrjName = GetCurPrjName(request);
		
		MPointManagerAction action=new MPointManagerAction(strCurPrjName);
		
		StringBuffer sb=action.GetMPointPageData(strMPNameKey, strInsNameKey, strMonitorName, page, rows);
		
		return sb;
	}

	/**
	 * ��ȡ�ն˵���������
	 * @param request
	 * @return
	 */
	private StringBuffer GetTerminalComboPageData(HttpServletRequest request) {
		
		int page=Integer.parseInt(request.getParameter("page"));
		int rows=Integer.parseInt(request.getParameter("rows"));
		
		String strCurPrjName = GetCurPrjName(request);
		
		MPointManagerAction action=new MPointManagerAction(strCurPrjName);
		
		StringBuffer sb=action.GetTerminalComboPageData(page, rows);
		
		return sb;
	}
	
	/**
	 * �ն˵�ͨ����������
	 * @param request
	 * @return
	 */
	private StringBuffer GetTerminalChnComboData(HttpServletRequest request) {
		
		String strinsFactoryID="";
		
		if(request.getParameter("insFactoryID")!=null) {
			strinsFactoryID=request.getParameter("insFactoryID");
		}
		
		String strCurPrjName = GetCurPrjName(request);
		
		MPointManagerAction action=new MPointManagerAction(strCurPrjName);
		
		StringBuffer sb=action.GetTerminalChnComboData(strinsFactoryID);
		
		return sb;
	}
	
	/**
	 * ��ȡ����������������
	 * @param request
	 * @return
	 */
	private StringBuffer GetSeneorComboPageData(HttpServletRequest request) {
		
		int page=Integer.parseInt(request.getParameter("page"));
		int rows=Integer.parseInt(request.getParameter("rows"));
		
		String strCurPrjName = GetCurPrjName(request);
		
		MPointManagerAction action=new MPointManagerAction(strCurPrjName);
		
		StringBuffer sb=action.GetSeneorComboPageData(page, rows);
		
		return sb;
	}
	
	/**
	 * ����ƥ����Ϣ
	 * @param request
	 * @return
	 */
	private StringBuffer AddMapInfo(HttpServletRequest request) {
		
		PointChnSensorRelationModel model = new PointChnSensorRelationModel();
		
		if (request.getParameter("insFactoryID") != null) {
			model.setInsFactoryID(request.getParameter("insFactoryID").toString());
		}

		if (request.getParameter("monitorName") != null) {
			model.setMonitorName(request.getParameter("monitorName").toString());
		}
		if (request.getParameter("monitorPtName") != null) {
			model.setMonitorPtName(request.getParameter("monitorPtName").toString());
		}
		if (request.getParameter("SensorID") != null) {
			model.setSensorID(request.getParameter("SensorID").toString());
		}
		if (request.getParameter("chnID") != null) {
			model.setChnID(Integer.parseInt(request.getParameter("chnID").toString()));
		}

		String strCurPrjName = GetCurPrjName(request);
		
		MPointManagerAction action=new MPointManagerAction(strCurPrjName);
		
		boolean bRes=action.AddMapInfo(model);
		
		StringBuffer sb = new StringBuffer();

		if (bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"ƥ��ɹ�\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ƥ��ʧ��\"}");
		}
		return sb;
	}

	/**
	 * �޸�ƥ����Ϣ
	 * @return
	 */
	private StringBuffer UpdateMapInfo(HttpServletRequest request) {
		PointChnSensorRelationModel model = new PointChnSensorRelationModel();
		
		if (request.getParameter("insFactoryID") != null) {
			model.setInsFactoryID(request.getParameter("insFactoryID").toString());
		}

		if (request.getParameter("monitorName") != null) {
			model.setMonitorName(request.getParameter("monitorName").toString());
		}
		if (request.getParameter("monitorPtName") != null) {
			model.setMonitorPtName(request.getParameter("monitorPtName").toString());
		}
		if (request.getParameter("SensorID") != null) {
			model.setSensorID(request.getParameter("SensorID").toString());
		}
		if (request.getParameter("chnID") != null) {
			model.setChnID(Integer.parseInt(request.getParameter("chnID").toString()));
		}

		String strCurPrjName = GetCurPrjName(request);
		
		MPointManagerAction action=new MPointManagerAction(strCurPrjName);
		
		boolean bRes=action.UpdateMapInfo(model);
		
		StringBuffer sb = new StringBuffer();

		if (bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"ƥ���޸ĳɹ�\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ƥ���޸�ʧ��\"}");
		}
		return sb;
	}
}
