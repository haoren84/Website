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
 * Servlet implementation class DataManageServlet  �����������ҳ�������
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
		
		case "DataManageStructTree"://���ݹ����еĲ����
		{
			sb=GetDataManageStructMPointTree(request);
		}
		break;
		
		case "DataManagerRealTime"://���ݹ�����ʵʱ����
		{
			sb=GetDataManageRealtimeData(request);
		}
		break;
		
		case "GetDataManageRealtimeDataByMonitorName"://���ݼ����Ŀ��Ϣ���ݹ�����ʵʱ����
		{
			sb=GetDataManageRealtimeDataByMonitorName(request);
		}
		break;
		
		case "GetMPointComboDataByStructIDAndMonitorID"://��ȡ�����������
		{
			sb=GetMPointComboDataByStructIDAndMonitorID(request);
		}
		break;
		
		case "GetMPointComboDataByMonitorIDAndMonitorName"://��ȡ�����������-���ݼ����Ŀ��Ϣ
		{
			sb=GetMPointComboDataByMonitorIDAndMonitorName(request);
		}
		break;
		
		case "GetSensorValueNameComboData"://��������������
		{
			sb=GetSensorValueNameComboData(request);
		}
		break;
		
		case "GetHistoryChartData"://��ȡ��ʷ���ݵ�ͼ������
		{
			sb=GetHistoryChartData(request);
		}
		break;
		
		case "GetDataManageMonitorTreeData"://��ȡ�����Ŀ��������
		{
			sb=GetDataManageMonitorTreeData(request);
		}
		break;
		
		case "GetDataManageMonitorTreeDataWithRelation"://��ȡ�����Ŀ������-�����Ϣ���뽨���˹������
		{
			sb=GetDataManageMonitorTreeDataWithRelation(request);
		}
		break;
		
		case "GetHistorySamplePageData"://��ȡ��ʷ���ݵķ�ҳ����
		{
			sb=GetHistorySamplePageData(request);
		}
		break;
		case "GetSensorMeasureType"://GetSensorMeasureType ��ȡ��������������
		{
			sb=GetSensorMeasureType(request);
		}
		break;
		case "GetJLSZMPointName"://GetJLSZMPointName ��ȡ����ˮ׼�ǵĲ������
		{
			sb=GetJLSZMPointName(request);
		}
		break;
		case "GetHistoryJLSZSamplePageData"://GetHistoryJLSZSamplePageData ��ȡ����ˮ׼�ǵķ�ҳ���� 
		{
			sb=GetHistoryJLSZSamplePageData(request);
		}
		break;
		case "GetHistoryJLSZChartData"://��ȡ����ˮ׼�ǵ�chart����
		{
			sb=GetHistoryJLSZChartData(request);
		}
		break;
		case "GetRealTimeDynamicChartData"://��ȡʵʱ��̬��chart����
		{
			sb=GetRealTimeDynamicChartData(request);
		}
		break;
		case "GetValueDataType"://��ȡ������ݵĴ洢����
		{
			sb=GetValueDataType(request);
		}
		break;
		case "GetHistoryFileTableData"://��ȡ�ļ��������ݵı���¼
		{
			sb=GetHistoryFileTableData(request);
		}
		break;
		case "GetHistoryFilePath"://��ȡ��ʷ�����ļ��Ľ�ѹ��ַ
		{
			sb=GetHistoryFilePath(request);
		}
		break;
		case "GetHistoryFileChartData"://��ȡ��ʷ�����ļ���chart����
		{
			sb=GetHistoryFileChartData(request);
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
	 * ��ȡ���ݹ������Ĳ������Ϣ
	 * @param request
	 * @return
	 */
	private StringBuffer GetDataManageStructMPointTree(HttpServletRequest request) {
		
		//ֻ��ID��ʱ��������ǽṹ���¼�
		
		//����ID����MonitorID���ǲ�������
		
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
	 * ��ȡ���ݹ�������ʵʱ����
	 * @return
	 */
	private StringBuffer GetDataManageRealtimeData(HttpServletRequest request) {
		
		StringBuffer sb=new StringBuffer();
		
		int nStructID=Integer.parseInt(request.getParameter("structID"));
		
		int nMonitorID =Integer.parseInt(request.getParameter("monitorID"));
		
		String strCurPrjName = GetCurPrjName(request);
		
		MonitorPrjAction monitorPrjAction=new MonitorPrjAction(strCurPrjName);
		
		DataManageAction dataManageAction=new DataManageAction(strCurPrjName);
		
		//���ݽṹid�������Ŀid��ȡ
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
	 * ���ݼ����Ŀ���ƻ�ȡʵʱ����
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
	 * ���ݽṹID�������ĿID��ȡ�����������
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
	 * ���ݼ����Ŀ��Ϣ��ȡ�����������
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
	 * ���ݼ����Ŀ���ƺͲ�����ƻ�ȡ����������������
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
	 * ��ȡ��ʷ���ݵ�ͼ������
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
	 * ��ȡ�����Ŀ�����ṹ����
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
	 * ��ȡ�����Ŀ�����ṹ����--������Ϣ�Ѿ�����
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
	 * ��ȡ��ʷ���ݵı���ҳ����
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
	 * ���ݼ����Ŀ�����ƻ�ȡ�������Ĳ�������
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
	 * ����ˮ׼��ˮ׼�ǵĲ������
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
	 * ����ˮ׼�ǵķ�ҳ����
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
	 * ����ˮ׼�ǵ�chart����
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
	 * ��ȡ��̬��ʵʱchart����
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
	 * ��ȡ���ݵĴ洢��ʽ
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
	 * ��ȡ��ʷ�����ļ���ҳ��Ϣ
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
	 * ��ȡ��ʷ�����ļ��Ľ�ѹ��ַ
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
	 * ��ȡ��ʷ�����ļ���chart����
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
