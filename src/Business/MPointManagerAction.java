package Business;

import java.util.List;


import Model.Monitor;
import Model.PointChnSensorRelationModel;
import PropertyInfo.ConfigInfo;
import SqlHelper.MySqlHelper;
import TstDataTable.DataRow;
import TstDataTable.DataTable;


/**
 * 测点管理页面的业务处理
 * @author Administrator
 *
 */
public class MPointManagerAction {
	
	String strMainDBName = null;
	
	String strPrjName = null;

	MySqlHelper mysql = null;
	
	public MPointManagerAction(String strPrjName) {

		mysql = new MySqlHelper();
		
		strMainDBName = mysql.MainDBName();

		this.strPrjName = strPrjName;

	}
	
	/**
	 * 获取监测项目名称的树结构信息
	 * @return
	 */
	public StringBuffer GetPrjNameTree() {
		
		MonitorPrjAction monitorAction=new MonitorPrjAction(this.strPrjName);
		
		List<Monitor> listMonitor=monitorAction.GetAllMonitorData();
		
		StringBuffer sb=new StringBuffer();
		
		if (listMonitor == null || listMonitor.size() <= 0) {

			sb.append("[]");

			return sb;
		}
		
		sb.append("[{\"id\":\"0\",\"text\":\"工程\",\"children\":");
		
		sb.append("[");
		
		for(Monitor item:listMonitor) {
			
			sb.append("{");

			int nID = item.getAutoID();

			sb.append(String.format("\"id\":\"%d\",", nID));

			sb.append(String.format("\"text\":\"%s\",", item.getMonitorName()));

			sb.append(String.format("\"state\":\"%s\",", "open"));

			sb.append("\"attributes\":{");
			sb.append(String.format("\"monitorID\":\"%d\",", item.getAutoID()));
			sb.append(String.format("\"monitorName\":\"%s\"", item.getMonitorName()));
			sb.append("}},");
		}
		
		sb.deleteCharAt(sb.length() - 1);
		
		sb.append("]");
		
		sb.append("}]");
		
		return sb;
	}
	
	/**
	 * 获取监测项目的下拉数据
	 * @return
	 */
	public StringBuffer GetPrjNameComboData() {
		
		MonitorPrjAction monitorAction=new MonitorPrjAction(this.strPrjName);
		
		List<Monitor> listMonitor=monitorAction.GetAllMonitorData();
		
		StringBuffer sb=new StringBuffer();
		
		if (listMonitor == null || listMonitor.size() <= 0) {

			sb.append("[]");

			return sb;
		}
		
		sb.append("[");
		
		for(Monitor item:listMonitor) {
			
			sb.append("{");

			int nID = item.getAutoID();

			sb.append(String.format("\"id\":\"%d\",", nID));

			sb.append(String.format("\"text\":\"%s\"", item.getMonitorName()));

			/*sb.append(String.format("\"state\":\"%s\",", "open"));*/

			/*sb.append("\"attributes\":{");
			sb.append(String.format("\"monitorID\":\"%d\",", item.getAutoID()));
			sb.append(String.format("\"monitorName\":\"%s\"", item.getMonitorName()));
			sb.append("}},");*/
			
			sb.append("},");
		}
		
		sb.deleteCharAt(sb.length() - 1);
		
		sb.append("]");
		
		return sb;
	}
	
	
	/**
	 *  获取监测项目的分页测点数据总数
	 * @param strMPNameKey
	 * @param strInsNameKey
	 * @return
	 */
	public int GetMPointPageDataAllCount(String strMPNameKey,String strInsNameKey,String strMonitorName) {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select count(*) from `monitor_point` as a "
				+ "LEFT JOIN `point_chn_sensor_relation` as b "
				+ "on a.monitorName=b.monitorName and a.monitorPtName=b.monitorPtName "
				+ "LEFT JOIN `instrument_sensor` as c "
				+ "ON b.SensorID=c.SensorID "
				+ "LEFT JOIN `instrument_terminal` AS d "
				+ "on b.insFactoryID=d.insFactoryID " + 
				 " where 1=1 ");
		
		if(!strMPNameKey.isEmpty()) {
			
			sb.append(" and a.monitorPtName like '%"+strMPNameKey+"%' ");
			
		}
		
		if(!strInsNameKey.isEmpty()) {
			
			sb.append(" and b.insFactoryID like '%"+strInsNameKey+"%' ");
			
		}
		
		sb.append(" and a.monitorName='"+strMonitorName+"' ");
		
		sb.append(";");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {
			
			int nRes=Integer.parseInt(dt.getRow().get(0).getCol().get(0).getValue().toString());
			
			return nRes;
			
		}
		
		
		return 0;
	}
	
	/**
	 * 获取监测项目的分页测点数据
	 * @return
	 */
	public StringBuffer GetMPointPageData(String strMPNameKey,String strInsNameKey,String strMonitorName,int page, int rows) {
		
		StringBuffer sbRes=new StringBuffer();
		
		int nDataCount=GetMPointPageDataAllCount(strMPNameKey,strInsNameKey,strMonitorName);
		
		if(nDataCount<=0) {
			sbRes.append("{\"total\":0,\"rows\":[]}");
	
			return sbRes;
		}
		
		int nComputcout = (page - 1) * rows;
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select * from `monitor_point` as a "
				+ "LEFT JOIN `point_chn_sensor_relation` as b "
				+ "on a.monitorName=b.monitorName and a.monitorPtName=b.monitorPtName "
				+ "LEFT JOIN `instrument_sensor` as c "
				+ "ON b.SensorID=c.SensorID "
				+ "LEFT JOIN `instrument_terminal` AS d "
				+ "on b.insFactoryID=d.insFactoryID " 
				+ "where 1=1 ");
		
		if(!strMPNameKey.isEmpty()) {
			
			sb.append(" and a.monitorPtName like '%"+strMPNameKey+"%' ");
			
		}
		
		if(!strInsNameKey.isEmpty()) {
			
			sb.append(" and b.insFactoryID like '%"+strInsNameKey+"%' ");
			
		}
		
		sb.append(" and a.monitorName='"+strMonitorName+"' ");
		
		sb.append(" limit ").append(nComputcout).append(",").append(rows).append(";");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		sbRes.append("{");
		sbRes.append("\"rows\":[");
		
		if(dt!=null&&dt.getRow()!=null&&dt.getRow().size()>0) 
		{
			for(DataRow dr:dt.getRow()) 
			{
				if(dr!=null&&dr.getCol().size()>0)
				{
					sbRes.append("{");
					
					sbRes.append(String.format("\"isused\":\"%s\",", "是"));
					sbRes.append(String.format("\"monitorName\":\"%s\",", dr.getStringColumn("monitorName").toString()));
					sbRes.append(String.format("\"monitorPtName\":\"%s\",", dr.getStringColumn("monitorPtName").toString()));
					sbRes.append(String.format("\"terminalName\":\"%s\",", dr.getStringColumn("terminalName").toString()));
					sbRes.append(String.format("\"terminalType\":\"%s\",", dr.getStringColumn("terminalType").toString()));
					sbRes.append(String.format("\"chnID\":\"%d\",", dr.getIntColumn("chnID")));
					sbRes.append(String.format("\"SensorName\":\"%s\",", dr.getStringColumn("SensorName").toString()));
					sbRes.append(String.format("\"SensorID\":\"%s\",", dr.getStringColumn("SensorID").toString()));
					sbRes.append(String.format("\"insFactoryID\":\"%s\"", dr.getStringColumn("insFactoryID").toString()));
					
					sbRes.append("},");
				}
			}
		}
		
		sbRes.deleteCharAt(sbRes.length() - 1);

		sbRes.append("],\"total\":").append(nDataCount).append("}");
		
	   return sbRes;
	}
	
	
	/**
	 * 终端下拉的分页数据个数
	 * @return
	 */
	public int GetTerminalComboAllCount() {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select count(*) from `instrument_terminal` ;");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {
			
			int nRes=Integer.parseInt(dt.getRow().get(0).getCol().get(0).getValue().toString());
			
			return nRes;
			
		}
		
		return 0;
	}
	
	/**
	 * 终端下拉的分页数据
	 * @param page
	 * @param rows
	 * @return
	 */
	public StringBuffer GetTerminalComboPageData(int page, int rows) {

		StringBuffer sbRes=new StringBuffer();
		
		int nDataCount=GetTerminalComboAllCount();
		
		if(nDataCount<=0) {
			sbRes.append("{\"total\":0,\"rows\":[]}");
	
			return sbRes;
		}
		
		int nComputcout = (page - 1) * rows;
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select * from `instrument_terminal` ");
		
		sb.append(" limit ").append(nComputcout).append(",").append(rows).append(";");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		sbRes.append("{");
		sbRes.append("\"rows\":[");
		
		if(dt!=null&&dt.getRow()!=null&&dt.getRow().size()>0) 
		{
			for(DataRow dr:dt.getRow()) 
			{
				if(dr!=null&&dr.getCol().size()>0)
				{
					sbRes.append("{");
					
					sbRes.append(String.format("\"autoid\":\"%s\",", dr.getIntColumn("AutoID")));
					sbRes.append(String.format("\"insFactoryID\":\"%s\",", dr.getStringColumn("insFactoryID")));
					sbRes.append(String.format("\"insID\":\"%s\",", dr.getIntColumn("caseID")));
					sbRes.append(String.format("\"insType\":\"%s\",", dr.getIntColumn("insType")));
					sbRes.append(String.format("\"insChnCount\":\"%s\",", dr.getIntColumn("chnCount")));
					sbRes.append(String.format("\"terminalName\":\"%s\",", dr.getStringColumn("terminalName")));
					sbRes.append(String.format("\"terminalType\":\"%s\",", dr.getStringColumn("terminalType")));
					sbRes.append(String.format("\"terminalDesc\":\"%s\",", dr.getStringColumn("terminalDesc")));
					sbRes.append(String.format("\"netID\":\"%s\"", dr.getStringColumn("netID")));
					
					sbRes.append("},");
				}
			}
			sbRes.deleteCharAt(sbRes.length() - 1);
		}
		
		

		sbRes.append("],\"total\":").append(nDataCount).append("}");
		
		return sbRes;
	}

	/**
	 * 终端通道的下拉数据
	 * @param strinsFactoryID
	 * @return
	 */
	public StringBuffer GetTerminalChnComboData(String strinsFactoryID) {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select * from `instrument_terminal_chn` where insFactoryID='"+strinsFactoryID+"';");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		sb=new StringBuffer();
		
		sb.append("[");
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {
			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {
					sb.append("{");
					sb.append(String.format("\"id\":\"%s\",", dr.getIntColumn("autoid")));
					sb.append(String.format("\"insFactoryID\":\"%s\",", dr.getStringColumn("insFactoryID")));
					sb.append(String.format("\"chnID\":\"%s\",", dr.getIntColumn("chnID")));
					sb.append(String.format("\"chnIDName\":\"%s\"", String.format("通道%s", dr.getIntColumn("chnID"))));
					sb.append("},");
				}
			}
			
			sb.deleteCharAt(sb.length() - 1);
		}
		
		sb.append("]");

		return sb;
	}

	/**
	 * 传感器的下拉分页数据个数
	 * @return
	 */
	public int GetSeneorComboAllCount() {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select count(*) from `instrument_sensor` ;");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {
			
			int nRes=Integer.parseInt(dt.getRow().get(0).getCol().get(0).getValue().toString());
			
			return nRes;
			
		}
		
		return 0;
	}

	/**
	 * 传感器的下拉分页数据
	 * @param page
	 * @param rows
	 * @return
	 */
	public StringBuffer GetSeneorComboPageData(int page, int rows) {
		
		StringBuffer sbRes=new StringBuffer();
		
		int nDataCount=GetSeneorComboAllCount();
		
		if(nDataCount<=0) {
			sbRes.append("{\"total\":0,\"rows\":[]}");
	
			return sbRes;
		}
		
		int nComputcout = (page - 1) * rows;
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select * from `instrument_sensor` ");
		
		sb.append(" limit ").append(nComputcout).append(",").append(rows).append(";");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		sbRes.append("{");
		sbRes.append("\"rows\":[");
		
		if(dt!=null&&dt.getRow()!=null&&dt.getRow().size()>0) 
		{
			for(DataRow dr:dt.getRow()) 
			{
				if(dr!=null&&dr.getCol().size()>0)
				{
					sbRes.append("{");
					
					sbRes.append(String.format("\"autoid\":\"%s\",", dr.getIntColumn("AutoID")));
					sbRes.append(String.format("\"SensorID\":\"%s\",", dr.getStringColumn("SensorID")));
					sbRes.append(String.format("\"SensorMeasureType\":\"%s\",", dr.getStringColumn("SensorMeasureType")));
					sbRes.append(String.format("\"SensorSpec\":\"%s\",", dr.getStringColumn("SensorSpec")));
					sbRes.append(String.format("\"SensorFactory\":\"%s\",", dr.getStringColumn("SensorFactory")));
					sbRes.append(String.format("\"SensorName\":\"%s\",", dr.getStringColumn("SensorName")));
					sbRes.append(String.format("\"SensorDesc\":\"%s\",", dr.getStringColumn("SensorDesc")));
					sbRes.append(String.format("\"Param1\":\"%s\",", dr.getStringColumn("Param1")));
					sbRes.append(String.format("\"Param2\":\"%s\",", dr.getStringColumn("Param2")));
					sbRes.append(String.format("\"Param3\":\"%s\",", dr.getStringColumn("Param3")));
					sbRes.append(String.format("\"Param4\":\"%s\",", dr.getStringColumn("Param4")));
					sbRes.append(String.format("\"Param5\":\"%s\",", dr.getStringColumn("Param5")));
					sbRes.append(String.format("\"Param6\":\"%s\",", dr.getStringColumn("Param6")));
					sbRes.append(String.format("\"Param7\":\"%s\",", dr.getStringColumn("Param7")));
					sbRes.append(String.format("\"Param8\":\"%s\",", dr.getStringColumn("Param8")));
					
					String strSensorMeasureType=dr.getStringColumn("SensorMeasureType");
					
					switch(strSensorMeasureType) {
					
					case "振弦":
					{
						sbRes.append(String.format("\"SensorPara\":\"k:%s,b:%s\"", dr.getStringColumn("Param1"),dr.getStringColumn("Param2")));
					}
					break;
					case "应变":
					{
						sbRes.append(String.format("\"SensorPara\":\"k:%s,b:%s\"", dr.getStringColumn("Param1"),dr.getStringColumn("Param2")));
					}
					break;
					case "温度":
					{
						sbRes.append(String.format("\"SensorPara\":\"k:%s\"", dr.getStringColumn("Param1")));
					}
					break;
					default:
					{
						sbRes.append(String.format("\"SensorPara\":\"b:%s\"", dr.getStringColumn("Param1")));
					}
					break;
					}
					sbRes.append("},");
				}
			}
			sbRes.deleteCharAt(sbRes.length() - 1);
		}
		
		

		sbRes.append("],\"total\":").append(nDataCount).append("}");
		
		return sbRes;
	}
	
	/**
	 * 添加匹配信息
	 * @return
	 */
	public boolean AddMapInfo(PointChnSensorRelationModel model) {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("insert into `point_chn_sensor_relation` (`monitorName`, `monitorPtName`, `insFactoryID`, `chnID`, `SensorID`) values "
				+ "('"+model.getMonitorName()+"', '"+model.getMonitorPtName()+"', "
				+ "'"+model.getInsFactoryID()+"', '"+model.getChnID()+"', '"+model.getSensorID()+"');");
		
		int nRes=mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		
		if(nRes>0) {
			
			this.CreateHistoryDataTable(model.getMonitorPtName());
			
			this.Update_TZTIOT_StationDBInstrument(model.getInsFactoryID());
			
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 修改匹配信息
	 * @param model
	 * @return
	 */
	public boolean UpdateMapInfo(PointChnSensorRelationModel model) {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("UPDATE `point_chn_sensor_relation` " + 
				"SET  " + 
				"`insFactoryID`='"+model.getInsFactoryID()+"', `chnID`='"+model.getChnID()+"', " + 
				"`SensorID`='"+model.getSensorID()+"' " + 
				"WHERE (`monitorName`='"+model.getMonitorName()+"' and `monitorPtName`='"+model.getMonitorPtName()+"');");
		
		int nRes=mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		
		if(nRes>0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 创建对应的历史数据表
	 * @param strMonitorPtName
	 * @return
	 */
	public boolean CreateHistoryDataTable(String strMonitorPtName) {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("CREATE TABLE `insdata_history_"+strMonitorPtName+"` (" + 
				"  `AutoID` int(4) NOT NULL AUTO_INCREMENT," + 
				"  `monitorName` varchar(30) DEFAULT NULL," + 
				"  `monitorPtName` varchar(30) DEFAULT NULL," + 
				"  `insFactoryID` varchar(30) DEFAULT NULL," + 
				"  `chnID` int(4) DEFAULT '0'," + 
				"  `SensorID` varchar(30) DEFAULT NULL," + 
				"  `SensorValueName` varchar(30) DEFAULT NULL," + 
				"  `SensorEUValue` float DEFAULT NULL," + 
				"  `SampleTime` datetime NOT NULL," + 
				"  `Batch` int(4) NOT NULL," + 
				"  `monitorPointZeroAutoID` int(4) DEFAULT NULL," + 
				"  `DataType` int(4) DEFAULT '0'," + 
				"  `SensorEUValueFilePath` varchar(255) DEFAULT NULL," + 
				"  PRIMARY KEY (`AutoID`)" + 
				") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		
		
		mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		
		return true;
	}
	
	/**
	 * 更新station主表中对应的信息
	 * @return
	 */
	public boolean Update_TZTIOT_StationDBInstrument(String strInsFactoryID) {
		
		String strPrjDBName=mysql.PrjDBName(strPrjName);
		
		String strCrmCode=new ConfigInfo().getCrmCode();
		
		String strDBName="tztiot_station";
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("UPDATE `instrument` " + 
				"SET  `ProjectName`='"+strPrjName+"', `ProjectDBName`='"+strPrjDBName+"' " + 
				"WHERE (`insFactoryID`='"+strInsFactoryID+"' and `crmCode`='"+strCrmCode+"'); ");
		
		int nRes=mysql.ExecuteQueryNoneDB(strDBName, sb.toString());
		
		return nRes>0;
	}
	
	
}
