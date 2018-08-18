package TstWebRequest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Business.DataManageAction;
import Business.MonitorPrjAction;
import Model.Insdata_Realtime;
import Model.MonitorPoint;
import Model.StructMPointMap;

/**
 * Servlet implementation class DataManageServlet  处理基础数据页面的请求
 */
@WebServlet("/DataManageServlet")
public class DataManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataManageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		
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
		
		case "DataManageStructTree"://数据管理中的测点树
		{
			sb=GetDataManageStructMPointTree(request);
		}
		break;
		
		case "DataManagerRealTime"://数据管理中实时数据
		{
			sb=GetDataManageRealtimeData(request);
		}
		break;
		
		case "GetDataManageRealtimeDataByMonitorName"://根据监测项目信息数据管理中实时数据
		{
			sb=GetDataManageRealtimeDataByMonitorName(request);
		}
		break;
		
		case "GetMPointComboDataByStructIDAndMonitorID"://获取测点下拉数据
		{
			sb=GetMPointComboDataByStructIDAndMonitorID(request);
		}
		break;
		
		case "GetMPointComboDataByMonitorIDAndMonitorName"://获取测点下拉数据-根据检测项目信息
		{
			sb=GetMPointComboDataByMonitorIDAndMonitorName(request);
		}
		break;
		
		case "GetSensorValueNameComboData"://测量量下拉数据
		{
			sb=GetSensorValueNameComboData(request);
		}
		break;
		
		case "GetHistoryChartData"://获取历史数据的图表数据
		{
			sb=GetHistoryChartData(request);
		}
		break;
		
		case "GetDataManageMonitorTreeData"://获取监测项目的树数据
		{
			sb=GetDataManageMonitorTreeData(request);
		}
		break;
		
		case "GetDataManageMonitorTreeDataWithRelation"://获取监测项目的数据-测点信息必须建立了关联测点
		{
			sb=GetDataManageMonitorTreeDataWithRelation(request);
		}
		break;
		
		case "GetHistorySamplePageData"://获取历史数据的分页数据
		{
			sb=GetHistorySamplePageData(request);
		}
		break;
		case "GetSensorMeasureType"://GetSensorMeasureType 获取传感器测量类型
		{
			sb=GetSensorMeasureType(request);
		}
		break;
		case "GetJLSZMPointName"://GetJLSZMPointName 获取静力水准仪的测点名称
		{
			sb=GetJLSZMPointName(request);
		}
		break;
		case "GetHistoryJLSZSamplePageData"://GetHistoryJLSZSamplePageData 获取静力水准仪的分页数据 
		{
			sb=GetHistoryJLSZSamplePageData(request);
		}
		break;
		case "GetHistoryJLSZChartData"://获取静力水准仪的chart数据
		{
			sb=GetHistoryJLSZChartData(request);
		}
		break;
		case "GetRealTimeDynamicChartData"://获取实时动态的chart数据
		{
			sb=GetRealTimeDynamicChartData(request);
		}
		break;
		case "GetValueDataType"://获取测点数据的存储类型
		{
			sb=GetValueDataType(request);
		}
		break;
		case "GetHistoryFileTableData"://获取文件类型数据的表格记录
		{
			sb=GetHistoryFileTableData(request);
		}
		break;
		case "GetHistoryFilePath"://获取历史数据文件的解压地址
		{
			sb=GetHistoryFilePath(request);
		}
		break;
		case "GetHistoryFileChartData"://获取历史数据文件的chart数据
		{
			sb=GetHistoryFileChartData(request);
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
	 * 获取数据管理界面的测点树信息
	 * @param request
	 * @return
	 */
	private StringBuffer GetDataManageStructMPointTree(HttpServletRequest request) {
		
		//只有ID的时候，请求的是结构上下级
		
		//除了ID还有MonitorID的是测点的请求
		
		String strCurPrjName = GetCurPrjName(request);
		
		DataManageAction action=new DataManageAction(strCurPrjName);
		
		if(request.getParameter("monitorID")==null) {
			
			int nStructID=Integer.parseInt(request.getParameter("structID"));
			
			StringBuffer sb=action.GetDataManageStructTree(nStructID);
			
			return sb;
			
		}else {
			
			int nStructID=Integer.parseInt(request.getParameter("structID"));
			
			int nMonitorID =Integer.parseInt(request.getParameter("monitorID"));
			
			StringBuffer sb=action.GetDataManageMPointTree(nStructID, nMonitorID);
			
			return sb;
		}
		
	}
	
	/**
	 * 获取数据管理界面的实时数据
	 * @return
	 */
	private StringBuffer GetDataManageRealtimeData(HttpServletRequest request) {
		
		StringBuffer sb=new StringBuffer();
		
		int nStructID=Integer.parseInt(request.getParameter("structID"));
		
		int nMonitorID =Integer.parseInt(request.getParameter("monitorID"));
		
		String strCurPrjName = GetCurPrjName(request);
		
		MonitorPrjAction monitorPrjAction=new MonitorPrjAction(strCurPrjName);
		
		DataManageAction dataManageAction=new DataManageAction(strCurPrjName);
		
		//根据结构id，监测项目id获取
		List<StructMPointMap> listStructMPointMap=monitorPrjAction.GetStructMPointMapInfoByStructIDAndMonitorID(nStructID, nMonitorID);
		
		if(listStructMPointMap==null||listStructMPointMap.size()<=0) {
			sb.append("[]");
			return sb;
		}
		
		sb.append("[");
		
		for(StructMPointMap mapItem:listStructMPointMap) {
			sb.append("{");
			sb.append(String.format("\"id\":\"%d\",", mapItem.getAutoID()));
			sb.append(String.format("\"structID\":\"%d\",", mapItem.getStructID()));
			sb.append(String.format("\"structName\":\"%s\",", mapItem.getStructName()));
			sb.append(String.format("\"monitorID\":\"%d\",", mapItem.getMonitorID()));
			sb.append(String.format("\"monitorName\":\"%s\",", mapItem.getMonitorName()));
			sb.append(String.format("\"mpointID\":\"%d\",", mapItem.getMpointID()));
			sb.append(String.format("\"mpointName\":\"%s\",", mapItem.getMpointName()));
			
			sb.append("\"realData\":[");
			
			List<Insdata_Realtime> listRealTime=dataManageAction.GetRealTimeDataByMonitorNameAndMPointName(mapItem.getMonitorName(), mapItem.getMpointName());
			
			if(listRealTime==null||listRealTime.size()<=0) {
				sb.append("]");
			}else {
				
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				for(Insdata_Realtime realItem:listRealTime) {
					sb.append("{");
					sb.append(String.format("\"dataid\":\"%d\",", realItem.getAutoID()));
					sb.append(String.format("\"monitorName\":\"%s\",", realItem.getMonitorName()));
					sb.append(String.format("\"monitorPtName\":\"%s\",", realItem.getMonitorPtName()));
					sb.append(String.format("\"insFactoryID\":\"%s\",", realItem.getInsFactoryID()));
					sb.append(String.format("\"chnID\":\"%d\",", realItem.getChnID()));
					sb.append(String.format("\"SensorID\":\"%s\",", realItem.getSensorID()));
					sb.append(String.format("\"SensorValueName\":\"%s\",", realItem.getSensorValueName()));
					sb.append(String.format("\"SensorEUValue\":\"%f\",", realItem.getSensorEUValue()));
					sb.append(String.format("\"SampleTime\":\"%s\",", formatter.format(realItem.getSampleTime())));
					sb.append(String.format("\"Batch\":\"%d\",", realItem.getBatch()));
					sb.append(String.format("\"monitorPointZeroAutoID\":\"%d\"", realItem.getMonitorPointZeroAutoID()));
					sb.append("},");
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.append("]");
			}
			
			sb.append("},");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		
		return sb;
	}

	/**
	 * 根据检测项目名称获取实时数据
	 * @param request
	 * @return
	 */
	private StringBuffer GetDataManageRealtimeDataByMonitorName(HttpServletRequest request) {
		
		StringBuffer sb=new StringBuffer();
		
		int nMonitorID =Integer.parseInt(request.getParameter("monitorID"));
		
		String strMonitorName=request.getParameter("monitorName");
		
		String strCurPrjName = GetCurPrjName(request);
		
		DataManageAction dataManageAction=new DataManageAction(strCurPrjName);
		
		MonitorPrjAction monitorAction=new MonitorPrjAction(strCurPrjName);
		
		List<MonitorPoint> listMonitorPoint=monitorAction.GetAllMonitorPointData(strMonitorName);
		
		if(listMonitorPoint==null||listMonitorPoint.size()<=0) {
			sb.append("[]");
			return sb;
		}
		
		sb.append("[");
		
		for(MonitorPoint item:listMonitorPoint) {
			
			sb.append("{");
			sb.append(String.format("\"id\":\"%d\",", item.getAutoID()));
			sb.append(String.format("\"monitorID\":\"%d\",", nMonitorID));
			sb.append(String.format("\"monitorName\":\"%s\",", strMonitorName));
			sb.append(String.format("\"mpointID\":\"%d\",", item.getAutoID()));
			sb.append(String.format("\"mpointName\":\"%s\",", item.getMonitorPtName()));
			
			sb.append("\"realData\":[");
			
			List<Insdata_Realtime> listRealTime=dataManageAction.GetRealTimeDataByMonitorNameAndMPointName(item.getMonitorName(), item.getMonitorPtName());
			
			if(listRealTime==null||listRealTime.size()<=0) {
				sb.append("]");
			}else {
				
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				for(Insdata_Realtime realItem:listRealTime) {
					sb.append("{");
					sb.append(String.format("\"dataid\":\"%d\",", realItem.getAutoID()));
					sb.append(String.format("\"monitorName\":\"%s\",", realItem.getMonitorName()));
					sb.append(String.format("\"monitorPtName\":\"%s\",", realItem.getMonitorPtName()));
					sb.append(String.format("\"insFactoryID\":\"%s\",", realItem.getInsFactoryID()));
					sb.append(String.format("\"chnID\":\"%d\",", realItem.getChnID()));
					sb.append(String.format("\"SensorID\":\"%s\",", realItem.getSensorID()));
					sb.append(String.format("\"SensorValueName\":\"%s\",", realItem.getSensorValueName()));
					sb.append(String.format("\"SensorEUValue\":\"%s\",", String.format("%.2f", realItem.getSensorEUValue())));
					sb.append(String.format("\"SampleTime\":\"%s\",", formatter.format(realItem.getSampleTime())));
					sb.append(String.format("\"Batch\":\"%d\",", realItem.getBatch()));
					sb.append(String.format("\"monitorPointZeroAutoID\":\"%d\"", realItem.getMonitorPointZeroAutoID()));
					sb.append("},");
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.append("]");
			}
			
			sb.append("},");
			
		}
		
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		
		return sb;
	}
	
	
	/**
	 * 根据结构ID，监测项目ID获取测点下拉数据
	 * @param request
	 * @return
	 */
	private StringBuffer GetMPointComboDataByStructIDAndMonitorID(HttpServletRequest request) {
		
		StringBuffer sb=new StringBuffer();
		
		int nStructID=Integer.parseInt(request.getParameter("structID"));
		
		int nMonitorID =Integer.parseInt(request.getParameter("monitorID"));
		
		String strCurPrjName = GetCurPrjName(request);
		
		DataManageAction dataManageAction=new DataManageAction(strCurPrjName);
		
		sb=dataManageAction.GetMPointComboDataByStructIDAndMonitorID(nStructID, nMonitorID);
		
		return sb;
	}
	
	/**
	 * 根据监测项目信息获取测点下拉数据
	 * @param request
	 * @return
	 */
	private StringBuffer GetMPointComboDataByMonitorIDAndMonitorName(HttpServletRequest request) {
		
		StringBuffer sb=new StringBuffer();
		
		int nMonitorID =Integer.parseInt(request.getParameter("monitorID"));
		
		String strMonitorName=request.getParameter("monitorName").toString();
		
		String strCurPrjName = GetCurPrjName(request);
		
		MonitorPrjAction monitorAction=new MonitorPrjAction(strCurPrjName);
		
		List<MonitorPoint> listMonitorPoint=monitorAction.GetAllMonitorPointData(strMonitorName);
		
		if(listMonitorPoint == null || listMonitorPoint.size() <= 0) {

			sb.append("[]");

			return sb;
		}
		
		sb.append("[");
		
		for(MonitorPoint item:listMonitorPoint) {
			sb.append("{");
			sb.append(String.format("\"id\":\"%d\",",item.getAutoID()));
			sb.append(String.format("\"text\":\"%s\"",item.getMonitorPtName()));
			sb.append("},");
		}
		
		sb.deleteCharAt(sb.length() - 1);

		sb.append("]");

		return sb;
	}
	
	/**
	 * 根据监测项目名称和测点名称获取测量量的下拉数据
	 * @param request
	 * @return
	 */
	private StringBuffer GetSensorValueNameComboData(HttpServletRequest request) {
		
		StringBuffer sb=new StringBuffer();
		
		String strMonitorName=request.getParameter("monitorName");
		
		String strMPointName=request.getParameter("mpointName");
		
		String strCurPrjName = GetCurPrjName(request);
		
		DataManageAction dataManageAction=new DataManageAction(strCurPrjName);
		
		List<String> listString=dataManageAction.GetSeneorValueName(strMonitorName, strMPointName);
		
		if(listString==null||listString.size()<=0) {
			sb.append("[]");
			return sb;
		}
		
		sb.append("[");
		
		for(String item : listString ) {
			sb.append("{");
			sb.append(String.format("\"id\":\"%d\",",listString.indexOf(item)));
			sb.append(String.format("\"text\":\"%s\"",item));
			sb.append("},");
		}
		
		sb.deleteCharAt(sb.length() - 1);

		sb.append("]");

		return sb;
	}
	
	/**
	 * 获取历史数据的图表数据
	 * @param request
	 * @return
	 */
	private StringBuffer GetHistoryChartData(HttpServletRequest request) {
		
		StringBuffer sb=new StringBuffer();
		
		String strMonitorName=request.getParameter("monitorName");
		String strMPointName=request.getParameter("mpointName");
		String strSeneorValueName=request.getParameter("sensorValueName");
		String strStartTime=request.getParameter("startTime");
		String strEndTime=request.getParameter("endTime");
		
		String strCurPrjName = GetCurPrjName(request);
		
		DataManageAction dataManageAction=new DataManageAction(strCurPrjName);
		
		sb=dataManageAction.GetHistorySampleData(strMonitorName, strMPointName, strSeneorValueName, strStartTime, strEndTime);
		
		return sb;
	}

	/**
	 * 获取监测项目的树结构数据
	 * @param request
	 * @return
	 */
	private StringBuffer GetDataManageMonitorTreeData(HttpServletRequest request) {
		
		String strCurPrjName = GetCurPrjName(request);
		
		DataManageAction action=new DataManageAction(strCurPrjName);
		
		if(request.getParameter("monitorID")!=null) {
			
			int nMonitorID=Integer.parseInt(request.getParameter("monitorID"));
			
			if(request.getParameter("monitorName")==null) {
				
				return action.GetMonitorTreeData();
				
			}else {
				
				String strMonitorName=request.getParameter("monitorName").toString();
				
				return action.GetMonitorPointTreeData(nMonitorID, strMonitorName);
				
			}
				
			
		}else {
			
			StringBuffer sb=new StringBuffer();
			
			return sb.append("[]");
			
		}
		
		
	}
	
	/**
	 * 获取监测项目的树结构数据--关联信息已经建立
	 * @param request
	 * @return
	 */
	private StringBuffer GetDataManageMonitorTreeDataWithRelation(HttpServletRequest request) {
		
		String strCurPrjName = GetCurPrjName(request);
		
		DataManageAction action=new DataManageAction(strCurPrjName);
		
		if(request.getParameter("monitorID")!=null) {
			
			int nMonitorID=Integer.parseInt(request.getParameter("monitorID"));
			
			if(request.getParameter("monitorName")==null) {
				
				return action.GetMonitorTreeData();
				
			}else {
				
				String strMonitorName=request.getParameter("monitorName").toString();
				
				return action.GetMonitorPointTreeDataWithRelation(nMonitorID, strMonitorName);
				
			}
			
		}else {
			
			StringBuffer sb=new StringBuffer();
			
			return sb.append("[]");
			
		}
		
	}
	
	/**
	 * 获取历史数据的表格分页数据
	 * @param request
	 * @return
	 */
	private StringBuffer GetHistorySamplePageData(HttpServletRequest request) {
		
		StringBuffer sb=new StringBuffer();
		
		String strMonitorName=request.getParameter("monitorName");
		String strMPointName=request.getParameter("mpointName");
		String strSeneorValueName=request.getParameter("sensorValueName");
		String strStartTime=request.getParameter("startTime");
		String strEndTime=request.getParameter("endTime");
		int page=Integer.parseInt(request.getParameter("page"));
		int rows=Integer.parseInt(request.getParameter("rows"));
		
		String strCurPrjName = GetCurPrjName(request);
		
		DataManageAction dataManageAction=new DataManageAction(strCurPrjName);
		
		sb=dataManageAction.GetHistorySamplePageData(strMonitorName, strMPointName, strSeneorValueName, strStartTime, strEndTime, page, rows);
		
		return sb;
		
	}
	

	/**
	 * 根据监测项目的名称获取传感器的测量类型
	 * @param request
	 * @return
	 */
	private StringBuffer GetSensorMeasureType(HttpServletRequest request) {
		
		String strCurPrjName = GetCurPrjName(request);
		
		String strMonitorName=request.getParameter("monitorName");
		
		DataManageAction dataManageAction=new DataManageAction(strCurPrjName);
		
		String strSensorMeasureType=dataManageAction.GetSeneorMeasureType(strMonitorName);
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("{\"result\":\""+strSensorMeasureType+"\"}");
		
		return sb;
	}
	
	/**
	 * 静力水准仪水准仪的测点名称
	 * @param request
	 * @return
	 */
	private StringBuffer GetJLSZMPointName(HttpServletRequest request) {
		
		String strCurPrjName = GetCurPrjName(request);
		
		String strMonitorName=request.getParameter("monitorName");
		
		DataManageAction dataManageAction=new DataManageAction(strCurPrjName);
		
		StringBuffer sb=dataManageAction.GetJLSZMPointName(strMonitorName);
		
		return sb;
	}

	/**
	 * 静力水准仪的分页数据
	 * @param request
	 * @return
	 */
	private StringBuffer GetHistoryJLSZSamplePageData(HttpServletRequest request) {
		
		StringBuffer sb=new StringBuffer();
		String strMonitorName=request.getParameter("monitorName");
		String strStartTime=request.getParameter("startTime");
		String strEndTime=request.getParameter("endTime");
		int page=Integer.parseInt(request.getParameter("page"));
		int rows=Integer.parseInt(request.getParameter("rows"));
		
		String strCurPrjName = GetCurPrjName(request);
		
		DataManageAction dataManageAction=new DataManageAction(strCurPrjName);
		
		sb=dataManageAction.GetHistoryJLSZSamplePageData(strMonitorName,strStartTime, strEndTime, page, rows);
		
		return sb;
	}
	
	/**
	 * 静力水准仪的chart数据
	 * @param request
	 * @return
	 */
	private StringBuffer GetHistoryJLSZChartData(HttpServletRequest request) {
		
		StringBuffer sb=new StringBuffer();
		
		String strMonitorName=request.getParameter("monitorName");
		String strStartTime=request.getParameter("startTime");
		String strEndTime=request.getParameter("endTime");
		
		String strCurPrjName = GetCurPrjName(request);
		
		DataManageAction dataManageAction=new DataManageAction(strCurPrjName);
		
		sb=dataManageAction.GetHistoryJLSZChartData(strMonitorName,strStartTime, strEndTime);
		
		return sb;
	}
	
	/**
	 * 获取动态的实时chart数据
	 * @param request
	 * @return
	 */
	private StringBuffer GetRealTimeDynamicChartData(HttpServletRequest request) {
		
		String strMonitorName=request.getParameter("monitorName");
		String strMPointName=request.getParameter("mpointName");
		String strSeneorValueName=request.getParameter("sensorValueName");
		String strStartTime=request.getParameter("startTime");
		String strEndTime=request.getParameter("endTime");
		String strCurCount=request.getParameter("curCount");
		
		int nCurCount=Integer.parseInt(strCurCount);
		
		String strCurPrjName = GetCurPrjName(request);
		
		DataManageAction dataManageAction=new DataManageAction(strCurPrjName);
		
		StringBuffer sb=new StringBuffer();
		
		sb=dataManageAction.GetDynamicRealTimeChartData(strMonitorName, strMPointName, strSeneorValueName, strStartTime, strEndTime,nCurCount);
		
		return sb;
	}
	
	/**
	 * 获取数据的存储格式
	 * @param request
	 * @return
	 */
	private StringBuffer GetValueDataType(HttpServletRequest request) {
		
		String strCurPrjName = GetCurPrjName(request);
		
		String strMonitorName=request.getParameter("monitorName");
		
		DataManageAction dataManageAction=new DataManageAction(strCurPrjName);
		
		int nDataType=dataManageAction.GetValueDataType(strMonitorName);
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("{\"result\":\""+nDataType+"\"}");
		
		return sb;
	}
	
	/**
	 * 获取历史数据文件分页信息
	 * @param request
	 * @return
	 */
	private StringBuffer GetHistoryFileTableData(HttpServletRequest request) {
		
		String strMonitorName=request.getParameter("monitorName");
		String strMPointName=request.getParameter("mpointName");
		String strSeneorValueName=request.getParameter("sensorValueName");
		String strStartTime=request.getParameter("startTime");
		String strEndTime=request.getParameter("endTime");
		int page=Integer.parseInt(request.getParameter("page"));
		int rows=Integer.parseInt(request.getParameter("rows"));
		
		String strCurPrjName = GetCurPrjName(request);
		
		DataManageAction dataManageAction=new DataManageAction(strCurPrjName);
		
		StringBuffer sb=dataManageAction.GetHistoryFilePageData(strMonitorName, strMPointName, strSeneorValueName, strStartTime, strEndTime, page, rows);
		
		return sb;
	}
	
	/**
	 * 获取历史数据文件的解压地址
	 * @param request
	 * @return
	 */
	private StringBuffer GetHistoryFilePath(HttpServletRequest request) {
		
		int nChnID=Integer.parseInt(request.getParameter("chnID"));
		String strRarFile=request.getParameter("SeneorEUValueFilePath").toString();
		
		String strCurPrjName = GetCurPrjName(request);
		
		DataManageAction dataManageAction=new DataManageAction(strCurPrjName);
		
		StringBuffer sb=dataManageAction.GetHistoryUnRarFilePath(strRarFile, nChnID);
		
		return sb;
	}
	
	/**
	 * 获取历史数据文件的chart数据
	 * @param request
	 * @return
	 */
	private StringBuffer GetHistoryFileChartData(HttpServletRequest request) {
		
		String strFile=request.getParameter("ChartFilePath").toString();
		
		String strCurPrjName = GetCurPrjName(request);
		
		DataManageAction dataManageAction=new DataManageAction(strCurPrjName);
		
		StringBuffer sb=dataManageAction.GetHistoryFileChartData(strFile);
		
		return sb;
		
	}
	
	
}
