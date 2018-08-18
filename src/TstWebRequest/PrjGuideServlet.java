package TstWebRequest;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Business.MonitorPrjAction;
import Model.Monitor;
import Model.MonitorPoint;

/**
 * Servlet implementation class PrjGuideServlet 处理向导页面的请求
 */
@WebServlet("/PrjGuideServlet")
public class PrjGuideServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrjGuideServlet() {
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

		response.getWriter().append(ReturnResquest(request, response));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		case "GetAllMonitorData"://获取所有的监测项目数据
		{
			sb=GetAllMonitorData(request);
		}
		break;
		case "GetAllMonitorPointData"://
		{
			sb=GetAllMonitorPointData(request);
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
	 * 获取所有的监测项目数据
	 * @param request
	 * @return
	 */
	private StringBuffer GetAllMonitorData(HttpServletRequest request) {
		
		String strCurPrjName = GetCurPrjName(request);
		
		MonitorPrjAction action=new MonitorPrjAction(strCurPrjName); 
		
		List<Monitor> listMonitor=action.GetAllMonitorData();
		
		StringBuffer sb=new StringBuffer();
		
		if(listMonitor==null||listMonitor.size()<=0) {
			sb.append("{\"total\":0,\"rows\":[]}");
			
			return sb;
		}
		
		int nLength=listMonitor.size();
		
		sb.append("{\"total\":"+nLength+",\"rows\":[");
		
		for(Monitor item : listMonitor) {
			sb.append("{");
			sb.append(String.format("\"id\":\"%s\",", item.getAutoID()));
			sb.append(String.format("\"monitorName\":\"%s\",", item.getMonitorName()));
			sb.append(String.format("\"monitorMethord\":\"%s\",", item.getMonitorMethord()));
			sb.append(String.format("\"monitorPointCount\":\"%d\",", item.getMonitorPointCount()));
			sb.append(String.format("\"monitorPointPrefix\":\"%s\",", item.getMonitorPointPrefix()));
			sb.append(String.format("\"monitorPointStartID\":\"%s\",", item.getMonitorPointStartID()));
			sb.append(String.format("\"monitorPointEndID\":\"%s\"", item.getMonitorPointEndID()));
			sb.append("},");
		}
		
		sb.deleteCharAt(sb.length() - 1);
		
		sb.append("]}");
		
		return sb;
	}

	/**
	 * 获取对应的所有测点
	 * @param request
	 * @return
	 */
	private StringBuffer GetAllMonitorPointData(HttpServletRequest request) {
		
		StringBuffer sb=new StringBuffer();
		
		String strCurPrjName = GetCurPrjName(request);
		
		MonitorPrjAction action=new MonitorPrjAction(strCurPrjName); 
		
		String strMonitorName=null;
		
		if(request.getParameter("monitorName")!=null) {
			strMonitorName=request.getParameter("monitorName");
		}
		
		if(strMonitorName.isEmpty()) {
			
			sb.append("{\"total\":0,\"rows\":[]}");
			
			return sb;
		}
		
		List<MonitorPoint> listMPoint=action.GetAllMonitorPointData(strMonitorName);
		
		if(listMPoint==null||listMPoint.size()<=0) {
			
			sb.append("{\"total\":0,\"rows\":[]}");
			
			return sb;
		}
		
		int nLength=listMPoint.size();
		
		sb.append("{\"total\":"+nLength+",\"rows\":[");
		
		for(MonitorPoint item : listMPoint) {
			sb.append("{");
			sb.append(String.format("\"id\":\"%s\",", item.getAutoID()));
			sb.append(String.format("\"monitorName\":\"%s\",", item.getMonitorName()));
			sb.append(String.format("\"monitorPtName\":\"%s\"", item.getMonitorPtName()));
			sb.append("},");
		}
		
		sb.deleteCharAt(sb.length() - 1);
		
		sb.append("]}");
		
		return sb;
	}
	
	

}
