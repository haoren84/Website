package Business;

import java.util.ArrayList;
import java.util.List;

import Model.InstrumentAcqModel;
import Model.InstrumentTerminalChnModel;
import Model.InstrumentTerminalModel;
import Model.Monitor;
import Model.MonitorPoint;
import Model.PointChnSensorRelationModel;
import Model.SensorDataModel;
import Model.SensorModel;
import PropertyInfo.ConfigInfo;
import SqlHelper.MySqlHelper;
import TstDataTable.DataRow;
import TstDataTable.DataTable;

/**
 * 仪器测点关联信息操作
 * 
 * @author Administrator
 *
 */
public class RelationInsAction {

	String strPrjName = null;

	MySqlHelper mysql = null;

	public RelationInsAction(String strPrjName) {

		mysql = new MySqlHelper();

		this.strPrjName = strPrjName;

	}

	/**
	 * 获取所有的采集仪信息
	 * 
	 * @return
	 */
	public List<InstrumentAcqModel> GetAllInstrumentAcqData() {

		List<InstrumentAcqModel> list = new ArrayList<>();

		StringBuffer sb = new StringBuffer();

		sb.append("select * from `instrument_acq`;");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {
					InstrumentAcqModel model = new InstrumentAcqModel();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setinsFactoryID(dr.getStringColumn("insFactoryID"));
					model.setinsNetID(dr.getStringColumn("insNetID"));
					model.setserverAddr(dr.getStringColumn("serverAddr"));
					String strserverPort = dr.getStringColumn("serverPort");
					model.setserverPort(Integer.parseInt(strserverPort));

					list.add(model);
				}
			}
		}

		return list;
	}

	
	
	/**
	 * 获取所有的终端信息
	 * 
	 * @return
	 */
	public List<InstrumentTerminalModel> GetAllInstrumentTerminalData() {

		List<InstrumentTerminalModel> list = new ArrayList<>();

		StringBuffer sb = new StringBuffer();

		sb.append("select * from `instrument_terminal`;");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {

					InstrumentTerminalModel model = new InstrumentTerminalModel();

					model.setAutoID(dr.getIntColumn("autoid"));
					model.setinsFactoryID(dr.getStringColumn("insFactoryID"));
					String strinsType = dr.getStringColumn("insType");
					model.setinsType(Integer.parseInt(strinsType));
					String strinsChnCount = dr.getStringColumn("insChnCount");
					model.setinsChnCount(Integer.parseInt(strinsChnCount));
					model.setinsID(dr.getIntColumn("insID"));

					list.add(model);

				}
			}
		}
		return list;
	}

	/**
	 * 获取配置文件中对应的crmcode的终端信息,返回信息中只有出厂编号
	 * @return
	 */
	public List<InstrumentTerminalModel> GetInstrumentTerminalDataByConfigCrmCode(){
		
		List<InstrumentTerminalModel> list = new ArrayList<>();
		
		String strDBName="tztiot_station";
		
		String strCrmCode=new ConfigInfo().getCrmCode();
		
		StringBuffer sb = new StringBuffer();

		sb.append("select * from `instrument` where `crmCode`='"+strCrmCode+"';");
		
		DataTable dt = mysql.ExecuteQueryDB(strDBName, sb.toString());
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {

					InstrumentTerminalModel model = new InstrumentTerminalModel();
					model.setinsFactoryID(dr.getStringColumn("insFactoryID"));
					list.add(model);

				}
			}
		}
		return list;
	}
	
	/**
	 * 根据ID获取终端信息
	 * @param nAutoID
	 * @return
	 */
	public InstrumentTerminalModel GetInstrumentTerminalModelByID(int nAutoID) {
		
		StringBuffer sb = new StringBuffer();

		sb.append("select * from `instrument_terminal` where AutoID='"+nAutoID+"';");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {

					InstrumentTerminalModel model = new InstrumentTerminalModel();

					model.setAutoID(dr.getIntColumn("autoid"));
					model.setinsFactoryID(dr.getStringColumn("insFactoryID"));
					String strinsType = dr.getStringColumn("insType");
					model.setinsType(Integer.parseInt(strinsType));
					String strinsChnCount = dr.getStringColumn("insChnCount");
					model.setinsChnCount(Integer.parseInt(strinsChnCount));
					model.setinsID(dr.getIntColumn("insID"));

					return model;

				}
			}
		}
		
		return null;
	}
	
	
	/**
	 * 获取所有传感器
	 * 
	 * @return
	 */
	public List<SensorModel> GetAllSeneorData() {

		List<SensorModel> list = new ArrayList<>();
		StringBuffer sb = new StringBuffer();

		sb.append("select * from `instrument_sensor`;");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {

					SensorModel model = new SensorModel();

					model.setAutoID(dr.getIntColumn("autoid"));
					model.setSensorID(dr.getStringColumn("SensorID"));
					model.setSensorMeasureType(dr.getStringColumn("SensorMeasureType"));
					model.setSensorSpec(dr.getStringColumn("SensorSpec"));
					model.setSensorFactory(dr.getStringColumn("SensorFactory"));
					String strParam1 = dr.getStringColumn("Param1");
					model.setParam1(Float.parseFloat(strParam1));
					String strParam2 = dr.getStringColumn("Param2");
					model.setParam2(Float.parseFloat(strParam2));
					String strParam3 = dr.getStringColumn("Param3");
					model.setParam3(Float.parseFloat(strParam3));
					String strParam4 = dr.getStringColumn("Param4");
					model.setParam4(Float.parseFloat(strParam4));
					String strParam5 = dr.getStringColumn("Param5");
					model.setParam5(Float.parseFloat(strParam5));
					String strParam6 = dr.getStringColumn("Param6");
					model.setParam6(Float.parseFloat(strParam6));
					String strParam7 = dr.getStringColumn("Param7");
					model.setParam7(Float.parseFloat(strParam7));
					String strParam8 = dr.getStringColumn("Param8");
					model.setParam8(Float.parseFloat(strParam8));

					list.add(model);

				}
			}
		}
		return list;
	}

	/**
	 * 根据ID获取传感器信息
	 * @param nAutoID
	 * @return
	 */
	public SensorModel GetSeneorByID(int nAutoID) {
		
		StringBuffer sb = new StringBuffer();

		sb.append("select * from `instrument_sensor` where autoid='"+nAutoID+"';");
		
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {

					SensorModel model = new SensorModel();

					model.setAutoID(dr.getIntColumn("autoid"));
					model.setSensorID(dr.getStringColumn("SensorID"));
					model.setSensorMeasureType(dr.getStringColumn("SensorMeasureType"));
					model.setSensorSpec(dr.getStringColumn("SensorSpec"));
					model.setSensorFactory(dr.getStringColumn("SensorFactory"));
					String strParam1 = dr.getStringColumn("Param1");
					model.setParam1(Float.parseFloat(strParam1));
					String strParam2 = dr.getStringColumn("Param2");
					model.setParam2(Float.parseFloat(strParam2));
					String strParam3 = dr.getStringColumn("Param3");
					model.setParam3(Float.parseFloat(strParam3));
					String strParam4 = dr.getStringColumn("Param4");
					model.setParam4(Float.parseFloat(strParam4));
					String strParam5 = dr.getStringColumn("Param5");
					model.setParam5(Float.parseFloat(strParam5));
					String strParam6 = dr.getStringColumn("Param6");
					model.setParam6(Float.parseFloat(strParam6));
					String strParam7 = dr.getStringColumn("Param7");
					model.setParam7(Float.parseFloat(strParam7));
					String strParam8 = dr.getStringColumn("Param8");
					model.setParam8(Float.parseFloat(strParam8));

					return model;
				}
			}
		}
		
		
		return null;
	}
	
	
	/**
	 * 新增采集仪
	 * 
	 * @param model
	 * @return
	 */
	public boolean CreateInstrumentAcq(InstrumentAcqModel model) {

		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO `instrument_acq` (`insFactoryID`, `insNetID`, `serverAddr`, `serverPort`) VALUES " + "('"
				+ model.getinsFactoryID() + "', " + "'" + model.getinsNetID() + "', " + "'" + model.getserverAddr()
				+ "', " + "'" + model.getserverPort() + "');");

		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());

		if (nRes == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 新增终端
	 * 
	 * @param model
	 * @return
	 */
	public boolean CreateInstrumentTerminal(InstrumentTerminalModel model) {

		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO `instrument_terminal`" + "(`insFactoryID`, `insID`, `insType`, `insChnCount`)"
				+ "VALUES ('" + model.getinsFactoryID() + "'" + ", '" + model.getinsID() + "'" + ", '"
				+ model.getinsType() + "'" + ", '" + model.getinsChnCount() + "');");

		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());

		if (nRes == 1) {
			
			//新增终端的子表数据
			
			CreateInstrumentTerminalChn(model.getinsFactoryID(),model.getinsChnCount());
			
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 新增终端的子表数据
	 * @param strinsFactoryID 出厂编号
	 * @param ninsChnCount 通道个数
	 * @return
	 */
	public boolean CreateInstrumentTerminalChn(String strinsFactoryID,int ninsChnCount) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("INSERT INTO `instrument_terminal_chn` (`insFactoryID`, `chnID`) VALUES ");
		
		for(int i=0;i<ninsChnCount;i++) {
			
			sb.append("('"+strinsFactoryID+"', '"+i+"'),");
			
		}
		
		sb.deleteCharAt(sb.length() - 1);

		sb.append(";");
		
		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		
		if (nRes > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 新增传感器
	 * 
	 * @param model
	 * @return
	 */
	public boolean CreateSeneor(SensorModel model) {

		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO `instrument_sensor`" + "(`SensorID`, `SensorMeasureType`, "
				+ "`SensorSpec`, `SensorFactory`, `Param1`, `Param2`, "
				+ "`Param3`, `Param4`, `Param5`, `Param6`, `Param7`, `Param8`)" + "VALUES ('" + model.getSensorID()
				+ "', '" + model.getSensorMeasureType() + "', '" + model.getSensorSpec() + "', " + "'"
				+ model.getSensorFactory() + "', '" + model.getParam1() + "', '" + model.getParam2() + "', '"
				+ model.getParam3() + "', " + "'" + model.getParam4() + "', '" + model.getParam5() + "', '"
				+ model.getParam6() + "', '" + model.getParam7() + "', '" + model.getParam8() + "');");

		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		
		if (nRes == 1) {
			
			//创建子表信息
			
			CreateSonsorData(model.getSensorID(),model.getSensorMeasureType());
			
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 新增传感器的子表数据
	 * 
	 * @param strSeneorID
	 *            传感器ID
	 * @param strSensorMeasureType
	 *            传感器测量类型 1-应变；2-桥式传感器；3-温度；4-倾角；5-电流；6-索力；7-振弦；8-静力水准仪；9-风速
	 * @return
	 */
	public boolean CreateSonsorData(String strSeneorID, String strSensorMeasureType) {

		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO `instrument_sensor_data` (`SensorID`, `ValueName`, `ValueUnit`) VALUES ");

		switch (strSensorMeasureType) {

		case "应变": {
			sb.append("('"+strSeneorID+"', '应变', 'uε');");
		}
			break;
		case "位移": {
			sb.append("('"+strSeneorID+"', '位移', 'mm');");
		}
			break;
		case "温度": {
			sb.append("('"+strSeneorID+"', '温度', '℃');");
		}
			break;
		case "倾角": {
			sb.append("('"+strSeneorID+"', '倾角', '°');");
		}
			break;
		case "电流": {
			sb.append("('"+strSeneorID+"', '电流', 'mV');");
		}
			break;
		case "索力": {
			sb.append("('"+strSeneorID+"', '索力', 'm/s²');");
		}
			break;
		case "挠度": {
			sb.append("('"+strSeneorID+"', '挠度', 'mm');");
		}
			break;
		case "裂缝": {
			sb.append("('"+strSeneorID+"', '裂缝', 'mm');");
		}
			break;
		case "振弦": {
			sb.append("('"+strSeneorID+"', '频率', 'Hz'),");
			sb.append("('"+strSeneorID+"', '应变', 'με'),");
			sb.append("('"+strSeneorID+"', '温度', '℃');");
		}
			break;
		case "风速": {
			sb.append("('"+strSeneorID+"', '风速', 'm/s'),");
			sb.append("('"+strSeneorID+"', '风向', '°');");
		}
			break;
		case "加速度": {
			sb.append("('"+strSeneorID+"', '加速度', 'm/s²');");
		}
			break;
		case "测斜仪": {
			sb.append("('"+strSeneorID+"', '测斜仪', '°');");
		}
			break;
		case "沉降仪": {
			sb.append("('"+strSeneorID+"', '沉降仪', 'mm');");
		}
			break;
		case "静力水准仪": {
			sb.append("('"+strSeneorID+"', '静力水准仪', 'mm');");
		}
			break;
		case "桥式传感器": {
			sb.append("('"+strSeneorID+"', '桥式传感器', 'mV');");
		}
			break;
		}
		
		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());

		if (nRes > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 修改采集仪
	 * 
	 * @param model
	 * @return
	 */
	public boolean UpdateInstrumentAcq(InstrumentAcqModel model) {

		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE `instrument_acq`" + "SET `insFactoryID`='" + model.getinsFactoryID() + "', `insNetID`='"
				+ model.getinsNetID() + "'," + "`serverAddr`='" + model.getserverAddr() + "', `serverPort`='"
				+ model.getserverPort() + "' WHERE (`AutoID`='" + model.getAutoID() + "');");

		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());

		if (nRes == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 修改终端
	 * 
	 * @param model
	 * @return
	 */
	public boolean UpdateInstrumentTerminal(InstrumentTerminalModel model) {

		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE `instrument_terminal`" + "SET `insFactoryID`='" + model.getinsFactoryID() + "', `insID`='"
				+ model.getinsID() + "'," + "`insType`='" + model.getinsType() + "', `insChnCount`='"
				+ model.getinsChnCount() + "' WHERE (`AutoID`='" + model.getAutoID() + "');");

		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());

		if (nRes == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 修改传感器
	 * 
	 * @param model
	 * @return
	 */
	public boolean UpdateSeneor(SensorModel model) {

		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE `instrument_sensor`" + "SET `SensorID`='" + model.getSensorID() + "', `SensorMeasureType`='"
				+ model.getSensorMeasureType() + "', " + "`SensorSpec`='" + model.getSensorSpec()
				+ "', `SensorFactory`='" + model.getSensorFactory() + "', `Param1`='" + model.getParam1() + "', "
				+ "`Param2`='" + model.getParam2() + "', `Param3`='" + model.getParam3() + "', `Param4`='"
				+ model.getParam4() + "', `Param5`='" + model.getParam5() + "', " + "`Param6`='" + model.getParam6()
				+ "', `Param7`='" + model.getParam7() + "', `Param8`='" + model.getParam8() + "' WHERE (`AutoID`='"
				+ model.getAutoID() + "');");

		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());

		if (nRes == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除采集仪
	 * 
	 * @param nAutoID
	 * @return
	 */
	public boolean DeleteInsAcq(int nAutoID) {
		StringBuffer sb = new StringBuffer();

		sb.append("DELETE FROM `instrument_acq` WHERE AutoID = '" + nAutoID + "';");

		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());

		if (nRes == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除终端
	 * 
	 * @param nAutoID
	 * @return
	 */
	public boolean DeleteInstrumentTerminal(int nAutoID) {
		
		InstrumentTerminalModel model=this.GetInstrumentTerminalModelByID(nAutoID);
		
		if(model==null) {
			return false;
		}
		
		StringBuffer sb = new StringBuffer();

		sb.append("DELETE FROM `instrument_terminal` WHERE AutoID = '" + nAutoID + "';");

		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());

		if (nRes == 1) {
			
			//删除终端子表
			
			DeleteInstrumentTerminalChnByFactoryID(model.getinsFactoryID());
			
			return true;
			
		} else {
			return false;
		}
	}
	
	
	/**
	 * 删除终端下的子表数据
	 * @param strinsFactoryID
	 * @return
	 */
	public boolean DeleteInstrumentTerminalChnByFactoryID(String strinsFactoryID) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("DELETE FROM `instrument_terminal_chn` WHERE insFactoryID = '" + strinsFactoryID + "';");
		
		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		
		if (nRes > 0 ) {
			return true;
		} else {
			return false;
		}
	}
	
	

	/**
	 * 删除传感器
	 * 
	 * @param nAutoID
	 * @return
	 */
	public boolean DeleteSeneor(int nAutoID) {
		
		SensorModel model=this.GetSeneorByID(nAutoID);
		
		if(model==null) {
			return false;
		}
		
		StringBuffer sb = new StringBuffer();

		sb.append("DELETE FROM `instrument_sensor` WHERE AutoID = '" + nAutoID + "';");

		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());

		if (nRes == 1) {
			
			DeleteSeneorData(model.getSensorID());
			
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 删除传感器子表
	 * @param SensorID
	 * @return
	 */
	public boolean DeleteSeneorData(String SensorID) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("DELETE FROM `instrument_sensor_data` WHERE SensorID = '" + SensorID + "';");
		
		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		
		if (nRes > 0 ) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 根据出厂编号获取对应的终端通道信息
	 * @param strinsFactoryID
	 * @return
	 */
	public List<InstrumentTerminalChnModel> GetInstrumentTerminalChnModelListByinsFactoryID(String strinsFactoryID){
		
		List<InstrumentTerminalChnModel> list=new ArrayList<>();
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select * from `instrument_terminal_chn` where insFactoryID='"+strinsFactoryID+"';");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {

					InstrumentTerminalChnModel model = new InstrumentTerminalChnModel();

					model.setAutoID(dr.getIntColumn("autoid"));
					model.setInsFactoryID(dr.getStringColumn("insFactoryID"));
					model.setChnID(dr.getIntColumn("chnID"));
					
					list.add(model);
				}
			}
		}
		
		return list;
	}
	
	/**
	 * 获取全部的关联匹配信息
	 * @return
	 */
	public List<PointChnSensorRelationModel> GetAllPointChnSensorRelationModelList(){
		
		List<PointChnSensorRelationModel> list=new ArrayList<>();
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select * from `point_chn_sensor_relation`;");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {

					PointChnSensorRelationModel model = new PointChnSensorRelationModel();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setMonitorName(dr.getStringColumn("monitorName"));
					model.setMonitorPtName(dr.getStringColumn("monitorPtName"));
					model.setInsFactoryID(dr.getStringColumn("insFactoryID"));
					model.setChnID(dr.getIntColumn("chnID"));
					model.setSensorID(dr.getStringColumn("SensorID"));
					list.add(model);
				}
			}
		}
		
		return list;
	}
	
	/**
	 * 根据监测项目的名称获取匹配信息
	 * @param strMonitorName
	 * @return
	 */
	public List<PointChnSensorRelationModel> GetPointChnSensorRelationByMonitorName(String strMonitorName){
		
		List<PointChnSensorRelationModel> list=new ArrayList<>();
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select * from `point_chn_sensor_relation` where monitorName='"+strMonitorName+"';");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {

					PointChnSensorRelationModel model = new PointChnSensorRelationModel();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setMonitorName(dr.getStringColumn("monitorName"));
					model.setMonitorPtName(dr.getStringColumn("monitorPtName"));
					model.setInsFactoryID(dr.getStringColumn("insFactoryID"));
					model.setChnID(dr.getIntColumn("chnID"));
					model.setSensorID(dr.getStringColumn("SensorID"));
					list.add(model);
				}
			}
		}
		
		return list;
	}
	
	
	/**
	 * 根据ID删除关联匹配信息
	 * @param nAutoID
	 * @return
	 */
	public boolean DeletePointChnSensorRelationByAutoID(int nAutoID) {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("Delete from `point_chn_sensor_relation` where AutoID='"+nAutoID+"';");
		
		int nRes=mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		
		if(nRes>0) {
			return true;
		}else {
			return false;
		}
		
	}
	
	
	
	/**
	 * 获取所有匹配信息的测点（带有选中项消息）
	 * @return
	 */
	public StringBuffer GetAllMapMPointTree() {
		
		StringBuffer sb = new StringBuffer();

		MonitorPrjAction action = new MonitorPrjAction(this.strPrjName);

		List<Monitor> listMonitor = action.GetAllMonitorData();

		if (listMonitor == null || listMonitor.size() < 0) {
			sb.append("{}");
			return sb;
		}

		int nParentID = 0;
		int nChildID = 10000;
		int nDataCount = 0;

		sb.append("[");
		
		for (Monitor itemMonitor : listMonitor) {
			
			sb.append("{");
			sb.append(String.format("\"id\":\"%d\",", nParentID++));
			sb.append(String.format("\"monitorid\":\"%d\",", itemMonitor.getAutoID()));
			sb.append(String.format("\"type\":\"%s\",", "监测项目"));
			sb.append("\"state\":\"open\",");
			sb.append(String.format("\"name\":\"%s\"", itemMonitor.getMonitorName()));
			sb.append("},");
			nDataCount++;
			
			List<MonitorPoint> listMPoint = action.GetAllMonitorPointData(itemMonitor.getMonitorName());//当前监测项目的所有测点
			
			List<PointChnSensorRelationModel> listSelectMPoint=this.GetPointChnSensorRelationByMonitorName(itemMonitor.getMonitorName());//匹配关系中已经被选中的测点
			
			
			if (listMPoint == null || listMPoint.size() < 0) {

				continue;

			}else {
				
				for (MonitorPoint itemPoint : listMPoint) {
					
					boolean bSelected=false;
					
					if(listSelectMPoint!=null&&listSelectMPoint.size()>0) {
						for(PointChnSensorRelationModel itemSelect:listSelectMPoint) {
							if(itemPoint.getMonitorPtName().equals(itemSelect.getMonitorPtName())) {
								bSelected=true;
								break;
							}
						}
					}
					
					sb.append("{");
					sb.append(String.format("\"id\":\"%d\",", nChildID++));
					sb.append(String.format("\"pointid\":\"%d\",", itemPoint.getAutoID()));
					sb.append(String.format("\"monitorid\":\"%d\",", itemMonitor.getAutoID()));
					sb.append(String.format("\"monitorname\":\"%s\",", itemMonitor.getMonitorName()));
					sb.append(String.format("\"type\":\"%s\",", "测点"));
					if(bSelected) {
						sb.append(String.format("\"isselect\":\"%s\",", "true"));
					}else {
						sb.append(String.format("\"isselect\":\"%s\",", "false"));
					}
					
					sb.append(String.format("\"name\":\"%s\",", itemPoint.getMonitorPtName()));
					sb.append(String.format("\"_parentId\":\"%d\"", nParentID - 1));
					sb.append("},");
					nDataCount++;
					
				}
				
			}
			
			
		}
		
		sb.deleteCharAt(sb.length() - 1);

		sb.append("]");
		
		StringBuffer sbRes = new StringBuffer();

		sbRes.append("{\"total\":\"").append(nDataCount).append("\",\"rows\":").append(sb.toString()).append("}");
		
		return sbRes;
	}
	
	/**
	 * 创建匹配关系数据
	 * @param model
	 * @return
	 */
	public boolean CreatePointChnSensorRelation(PointChnSensorRelationModel model) {
		
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
				"  `SeneorEUValueFilePath` varchar(255) DEFAULT NULL," + 
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
