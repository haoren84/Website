package Business;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Common.CommonConvert;
import Common.UnZipOrRarUtils;
import Model.Insdata_Realtime;
import Model.Monitor;
import Model.MonitorPoint;
import Model.Struct;
import Model.StructMPointMap;
import PropertyInfo.ConfigInfo;
import SqlHelper.MySqlHelper;
import TstDataTable.DataRow;
import TstDataTable.DataTable;

/**
 * ���ݹ�����Ҫ�Ĳ�����
 * 
 * @author Administrator
 *
 */
public class DataManageAction {

	String strPrjName = null;

	MySqlHelper mysql = null;

	public DataManageAction(String strPrjName) {

		mysql = new MySqlHelper();

		this.strPrjName = strPrjName;

	}

	/**
	 * ���ݹ�����Ҫ�Ľṹ����Ϣ
	 * 
	 * �Ȼ�ȡ���е��¼��ṹ�� �¼��ṹ open-closed�жϣ� �Ƿ����¼��ṹ �Ƿ��ж�Ӧ���
	 * 
	 * @param autoid
	 *            �ṹ��ID
	 * @return
	 */
	public StringBuffer GetDataManageStructTree(int autoid) {

		PrjStructAction action = new PrjStructAction(this.strPrjName);

		StringBuffer sb = new StringBuffer();

		List<Struct> listStruct = action.SelectStructByParent(String.valueOf(autoid));

		if (listStruct != null && listStruct.size() > 0) {

			// �����¼��ṹ�����¼��ṹ��Ϊ������

			sb.append("[");

			for (Struct item : listStruct) {

				sb.append("{");

				sb.append(String.format("\"id\":\"%d\",", item.getAutoID()));

				sb.append(String.format("\"text\":\"%s\",", item.getStructName()));

				// ÿ���ڵ���Ҫ�ж��Ƿ�open-closed

				String strState = "closed";

				List<Struct> listItemStruct = action.SelectStructByParent(String.valueOf(item.getAutoID()));

				if (listItemStruct == null || listItemStruct.size() <= 0) {

					List<StructMPointMap> listItemMPoint = action.GetStructMPointMapByStructID(item.getAutoID());

					if (listItemMPoint == null || listItemMPoint.size() <= 0) {
						strState = "closed";
					}
				}

				sb.append(String.format("\"state\":\"%s\"", strState));

				sb.append("},");
			}

			sb.deleteCharAt(sb.length() - 1);

			sb.append("]");

		} else {

			// ���¼��ṹ����Ϊ��ȡ��Ӧ�ļ����Ŀ��Ϣ

			sb = GetDataManageStructMonitorTree(autoid);

		}

		return sb;
	}

	/**
	 * ���ݹ�����Ҫ�Ľṹ-�����Ŀ��Ϣ��
	 * 
	 * @param nStructID
	 *            �ṹID
	 * @return
	 */
	public StringBuffer GetDataManageStructMonitorTree(int nStructID) {

		StringBuffer sb = new StringBuffer();

		PrjStructAction action = new PrjStructAction(this.strPrjName);

		List<StructMPointMap> list = action.GetStructMonitorGroupByStructID(nStructID);

		if (list == null || list.size() <= 0) {
			sb.append("[]");
			return sb;
		}

		sb.append("[");

		for (StructMPointMap item : list) {

			sb.append("{");

			int nID = nStructID * 10000 + item.getAutoID();

			sb.append(String.format("\"id\":\"%d\",", nID));

			sb.append(String.format("\"text\":\"%s\",", item.getMonitorName()));

			sb.append(String.format("\"state\":\"%s\",", "closed"));

			sb.append("\"attributes\":{");

			sb.append(String.format("\"structID\":\"%d\",", nStructID));
			sb.append(String.format("\"structName\":\"%s\",", item.getStructName()));
			sb.append(String.format("\"monitorID\":\"%d\",", item.getMonitorID()));
			sb.append(String.format("\"monitorName\":\"%s\"", item.getMonitorName()));
			sb.append("}},");

		}

		sb.deleteCharAt(sb.length() - 1);

		sb.append("]");

		return sb;
	}

	/**
	 * ���ݹ�����Ҫ�Ľṹ-�����Ŀ-�����Ϣ��
	 * 
	 * @param nStructID
	 *            �ṹID
	 * @param nMonitorID
	 *            �����ĿID
	 * @return
	 */
	public StringBuffer GetDataManageMPointTree(int nStructID, int nMonitorID) {

		StringBuffer sb = new StringBuffer();

		PrjStructAction action = new PrjStructAction(this.strPrjName);

		List<StructMPointMap> list = action.GetStructMPointMapByStructIDAndMonitorID(nStructID, nMonitorID);

		if (list == null || list.size() <= 0) {

			sb.append("[]");

			return sb;
		}

		sb.append("[");

		for (StructMPointMap item : list) {

			sb.append("{");

			int nID = nStructID * 100000 + nMonitorID * 10000 + item.getAutoID();

			sb.append(String.format("\"id\":\"%d\",", nID));

			sb.append(String.format("\"text\":\"%s\",", item.getMpointName()));

			sb.append(String.format("\"state\":\"%s\",", "open"));

			sb.append("\"attributes\":{");

			sb.append(String.format("\"structID\":\"%d\",", nStructID));
			sb.append(String.format("\"structName\":\"%s\",", item.getStructName()));
			sb.append(String.format("\"monitorID\":\"%d\",", item.getMonitorID()));
			sb.append(String.format("\"monitorName\":\"%s\",", item.getMonitorName()));
			sb.append(String.format("\"mpointID\":\"%d\",", item.getMpointID()));
			sb.append(String.format("\"mpointName\":\"%s\"", item.getMpointName()));
			sb.append("}},");

		}

		sb.deleteCharAt(sb.length() - 1);

		sb.append("]");

		return sb;
	}

	/**
	 * ͨ�������Ϣ��ȡ��������ϵ��Ϣ
	 * 
	 * @param strMonitorName
	 *            �����Ŀ����
	 * @param strMPointName
	 *            �������
	 * @return
	 */
	public List<point_chn_sensor_relation> GetSeneorRelationByMonitorNameAndMPointName(String strMonitorName,
			String strMPointName) {

		List<point_chn_sensor_relation> list = new ArrayList<>();

		StringBuffer sb = new StringBuffer();

		sb.append("select * from `point_chn_sensor_relation` where monitorName='" + strMonitorName
				+ "' and monitorPtName='" + strMPointName + "';");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {
					point_chn_sensor_relation model = new point_chn_sensor_relation();
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
	 * ͨ��������ID��ȡ��������������Ϣ
	 * 
	 * @param strSeneorID
	 * @return
	 */
	public List<instrument_sensor_data> GetSeneorDataInfoBySeneorID(String strSensorID) {

		List<instrument_sensor_data> list = new ArrayList<>();

		StringBuffer sb = new StringBuffer();

		sb.append("select * from `instrument_sensor_data` where SensorID='" + strSensorID + "';");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {
					instrument_sensor_data model = new instrument_sensor_data();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setSensorID(dr.getStringColumn("SensorID"));
					model.setValueDBFieldName(dr.getStringColumn("ValueDBFieldName"));
					model.setValueName(dr.getStringColumn("ValueName"));
					model.setValueUnit(dr.getStringColumn("ValueUnit"));
					list.add(model);
				}
			}
		}

		return list;
	}

	/**
	 * ͨ�������Ϣ�����ƻ�ȡ��ϵ��Ϣ
	 * @param strMonitorName
	 * @return
	 */
	public List<point_chn_sensor_relation> GetSeneorRelationByMonitorName(String strMonitorName){
		
		List<point_chn_sensor_relation> list = new ArrayList<>();

		StringBuffer sb = new StringBuffer();
		
		sb.append("select * from `point_chn_sensor_relation` where monitorName='" + strMonitorName
				+ "';");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {
					point_chn_sensor_relation model = new point_chn_sensor_relation();
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
	 * ���ݼ����Ϣ��ȡʵʱ������Ϣ
	 * 
	 * @param strMonitorName
	 *            �����Ŀ����
	 * @param strMPointName
	 *            �������
	 * @return
	 */
	public List<Insdata_Realtime> GetRealTimeDataByMonitorNameAndMPointName(String strMonitorName,
			String strMPointName) {

		List<Insdata_Realtime> list = new ArrayList<>();

		StringBuffer sb = new StringBuffer();

		/*sb.append(
				"select c.AutoID,c.Batch,c.chnID,c.insFactoryID,c.monitorName,c.monitorPointZeroAutoID,c.monitorPtName,c.SampleTime,c.SensorEUValue,"
						+ " c.SensorID,c.SensorValueName"
						+ " from `point_chn_sensor_relation` as a ,`instrument_sensor_data` as b ,`insdata_realtime` as c"
						+ " where a.SensorID=b.SensorID and a.SensorID=c.SensorID and b.ValueName=c.SensorValueName"
						+ " and c.monitorName='" + strMonitorName + "' and c.monitorPtName='" + strMPointName + "' ;");*/
		
		sb.append(
				"select c.AutoID,c.Batch,c.chnID,c.insFactoryID,c.monitorName, " + 
				"c.monitorPointZeroAutoID,c.monitorPtName,c.SampleTime,c.SensorEUValue, " + 
				"c.SensorID,c.SensorValueName " + 
				"from `insdata_realtime` as c " + 
				"where " + 
				" c.monitorName='" + strMonitorName + "' and c.monitorPtName='" + strMPointName + "' ORDER BY AutoID DESC LIMIT 1  ;");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {
					Insdata_Realtime model = new Insdata_Realtime();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setSensorID(dr.getStringColumn("SensorID"));
					model.setBatch(dr.getIntColumn("Batch"));
					model.setChnID(dr.getIntColumn("chnID"));
					model.setInsFactoryID(dr.getStringColumn("insFactoryID"));
					model.setMonitorName(dr.getStringColumn("monitorName"));
					model.setMonitorPointZeroAutoID(dr.getIntColumn("monitorPointZeroAutoID"));
					model.setMonitorPtName(dr.getStringColumn("monitorPtName"));
					model.setSampleTime(dr.getUtilDateColumn("SampleTime"));
					model.setSensorEUValue(dr.getFloatColumn("SensorEUValue"));
					model.setSensorValueName(dr.getStringColumn("SensorValueName"));
					list.add(model);
				}
			}
		}

		return list;
	}
	
	
	
	/**
	 * ���߼����Ŀ�����ƻ�ȡʵʱ����
	 * @param strMonitorName
	 * @return
	 */
	public List<Insdata_Realtime> GetRealTimeDataByMonitorName(String strMonitorName){
		List<Insdata_Realtime> list = new ArrayList<>();

		StringBuffer sb = new StringBuffer();

		sb.append(
				"select c.AutoID,c.Batch,c.chnID,c.insFactoryID,c.monitorName,c.monitorPointZeroAutoID,c.monitorPtName,c.SampleTime,c.SensorEUValue,"
						+ " c.SensorID,c.SensorValueName"
						+ " from `point_chn_sensor_relation` as a ,`instrument_sensor_data` as b ,`insdata_realtime` as c"
						+ " where a.SensorID=b.SensorID and a.SensorID=c.SensorID and b.ValueName=c.SensorValueName"
						+ " and a.monitorName='" + strMonitorName + "' ;");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {
					Insdata_Realtime model = new Insdata_Realtime();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setSensorID(dr.getStringColumn("SensorID"));
					model.setBatch(dr.getIntColumn("Batch"));
					model.setChnID(dr.getIntColumn("chnID"));
					model.setInsFactoryID(dr.getStringColumn("insFactoryID"));
					model.setMonitorName(dr.getStringColumn("monitorName"));
					model.setMonitorPointZeroAutoID(dr.getIntColumn("monitorPointZeroAutoID"));
					model.setMonitorPtName(dr.getStringColumn("monitorPtName"));
					model.setSampleTime(dr.getUtilDateColumn("SampleTime"));
					model.setSensorEUValue(dr.getFloatColumn("SensorEUValue"));
					model.setSensorValueName(dr.getStringColumn("SensorValueName"));
					list.add(model);
				}
			}
		}

		return list;
	}
	
	/**
	 * ���ݼ����Ϣ��ȡ��ʷ���ݵĲ�����,��ͬ�Ĳ������в�ͬ�ı���
	 * @param strMonitorName �����Ŀ����
	 * @param strMPointName �������
	 * @return
	 */
	public List<String> GetSeneorValueName(String strMonitorName,
			String strMPointName) {
		
		List<String> listString=new ArrayList<>();
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("select b.ValueName from `point_chn_sensor_relation` as a,`instrument_sensor_data` as b" + 
				" where a.SensorID=b.SensorID and a.monitorName='"+strMonitorName+"'" + 
				" and a.monitorPtName='"+strMPointName+"';");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {
					listString.add(dr.getStringColumn("ValueName"));
				}
			}
		}

		return listString;
	}
	
	/**
	 * ���ݼ����Ϣ��ȡ��ʷ���ݵı���,��ͬ�Ĳ������в�ͬ�ı���
	 * @param strMonitorName
	 * @param strMPointName
	 * @param strSeneorValueName ������
	 * @return
	 */
	public String GetHistoryTableName(String strMonitorName,
			String strMPointName,String strSeneorValueName) {
		
		//��ȡ��Ӧ�ĳ�����ź�ValueDBFieldName  ���޸�Ϊ  ��Ӧ�Ĳ���autoid��ValueDBFieldName
		
		/*StringBuffer sb = new StringBuffer();
		
		sb.append(" select a.insFactoryID,b.ValueDBFieldName,c.AutoID "
				+ "from `point_chn_sensor_relation` as a,`instrument_sensor_data` as b,`monitor_point` as c " + 
				" where a.SensorID=b.SensorID and b.ValueName='"+strSeneorValueName+"' and a.monitorName='"+strMonitorName+"'" + 
				" and a.monitorPtName='"+strMPointName+"' and c.monitorName=a.monitorName and a.monitorPtName=c.monitorPtName;");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		//String insFactoryID=null;
		String ValueDBFieldName=null;
		int nMPointAutoID=0;
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {
					//insFactoryID=dr.getStringColumn("insFactoryID");
					ValueDBFieldName=dr.getStringColumn("ValueDBFieldName");
					nMPointAutoID=dr.getIntColumn("AutoID");
				}
			}
		}
		
		if(ValueDBFieldName.isEmpty()||nMPointAutoID==0) {
			return null;
		}else {
			
			String strTableName=String.format("insdata_history_%d_%s", nMPointAutoID,ValueDBFieldName);
			
			return strTableName;
		}*/
		
		//2018-04-28  ��ʷ����insdata_history_xxxxxx XxxxxxΪ�������
		
		String strTableName=String.format("insdata_history_%s", strMPointName);
		
		
		return strTableName;
		
		
	}

	/**
	 * ���ݼ����Ŀ�����ƻ�ȡ��ʷ��ı���
	 * @param strMonitorName
	 * @return
	 */
	public List<String> GetHistoryTableNames(String strMonitorName){
		
		List<String> listTableNames=new ArrayList<String>();
		
		StringBuffer sbSql=new StringBuffer();
		
		sbSql.append("Select * from `point_chn_sensor_relation` where monitorName='"+strMonitorName+"';");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sbSql.toString());
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {
					listTableNames.add(String.format("insdata_history_%s", dr.getStringColumn("monitorPtName")));
				}
			}
		}
		
		return listTableNames;
		
	}
	
	/**
	 * ���ݼ����Ŀ�����ƻ�ȡ�������
	 * @param strMonitorName
	 * @return
	 */
	public List<String> GetMonitorPtNames(String strMonitorName){
		
		List<String> listTableNames=new ArrayList<String>();
		
		StringBuffer sbSql=new StringBuffer();
		
		sbSql.append("Select * from `point_chn_sensor_relation` where monitorName='"+strMonitorName+"';");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sbSql.toString());
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {
					listTableNames.add(dr.getStringColumn("monitorPtName"));
				}
			}
		}
		
		return listTableNames;
	}
	
	
	/**
	 * ���ݼ����Ϣ��ȡͨ��ID
	 * @param strMonitorName
	 * @param strMPointName
	 * @return
	 */
	public int GetMapChnID(String strMonitorName,
			String strMPointName) {
		
		StringBuffer sb = new StringBuffer();

		sb.append("select * from `point_chn_sensor_relation` where monitorName='" + strMonitorName
				+ "' and monitorPtName='" + strMPointName + "';");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {
					
					int nChnID=dr.getIntColumn("chnID");
					
					return nChnID;
				}
			}
		}
		
		return 0;
	}
	
	/**
	 * ��ȡ�������ݵ���ʷ���� chart��
	 * @param strMonitorName
	 * @param strMPointName
	 * @param strSeneorValueName ������
	 * @return
	 */
	public StringBuffer GetHistorySampleData(String strMonitorName,
			String strMPointName,String strSensorValueName,String strStartTime,String strEndTime) {
		
		String strHistoryTableName=GetHistoryTableName(strMonitorName,strMPointName,strSensorValueName);
		
		//int nChnID=GetMapChnID(strMonitorName,strMPointName);
		
		StringBuffer sb=new StringBuffer();
		
		if(strHistoryTableName.isEmpty()) {
			sb.append("[]");
			return sb;
		}
		
		
		
		StringBuffer sbSql=new StringBuffer();
		
		sbSql.append("select * from `"+strHistoryTableName+"` where "
				+ " monitorName='"+strMonitorName+"'"
				+ " and monitorPtName='"+strMPointName+"'"
				+ " and SensorValueName='"+strSensorValueName+"'"
				+ " and SampleTime>='"+strStartTime+"' and SampleTime<='"+strEndTime+"';");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sbSql.toString());
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		sb.append("[");
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {
					
					sb.append("{");
					Date SampleTime=dr.getUtilDateColumn("SampleTime");
					sb.append(String.format("\"SampleTime\":\"%s\",", formatter.format(SampleTime)));
					sb.append(String.format("\"SampleValue\":\"%s\"", String.format("%.2f", dr.getFloatColumn("SensorEUValue"))));
					sb.append("},");
				}
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		
		sb.append("]");
		
		return sb;
	}
	

	/**
	 * ���ݽṹID�������ĿID��ȡ�����������
	 * @param nStructID �ṹID
	 * @param nMonitorID �����ĿID
	 * @return
	 */
	public StringBuffer GetMPointComboDataByStructIDAndMonitorID(int nStructID, int nMonitorID) {
		
		StringBuffer sb = new StringBuffer();

		PrjStructAction action = new PrjStructAction(this.strPrjName);

		List<StructMPointMap> list = action.GetStructMPointMapByStructIDAndMonitorID(nStructID, nMonitorID);

		if (list == null || list.size() <= 0) {

			sb.append("[]");

			return sb;
		}
		
		sb.append("[");
		
		for(StructMPointMap item:list) {
			sb.append("{");
			sb.append(String.format("\"id\":\"%d\",",item.getMpointID()));
			sb.append(String.format("\"text\":\"%s\"",item.getMpointName()));
			sb.append("},");
		}
		
		sb.deleteCharAt(sb.length() - 1);

		sb.append("]");

		return sb;
	}
	
	/**
	 * �����Ŀ�����ṹ
	 * @return
	 */
	public StringBuffer GetMonitorTreeData() {
		
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

			sb.append(String.format("\"text\":\"%s\",", item.getMonitorName()));

			sb.append(String.format("\"state\":\"%s\",", "closed"));

			sb.append("\"attributes\":{");
			sb.append(String.format("\"monitorID\":\"%d\",", item.getAutoID()));
			sb.append(String.format("\"monitorName\":\"%s\"", item.getMonitorName()));
			sb.append("}},");
		}
		
		sb.deleteCharAt(sb.length() - 1);
		
		sb.append("]");
		
		return sb;
	}
	
	/**
	 * �����Ŀ�����ṹ-�������
	 * @param strMonitorName
	 * @return
	 */
	public StringBuffer GetMonitorPointTreeData(int nMonitorID,String strMonitorName) {
		
		MonitorPrjAction monitorAction=new MonitorPrjAction(this.strPrjName);
		
		List<MonitorPoint> listMonitorPoint=monitorAction.GetAllMonitorPointData(strMonitorName);
		
		StringBuffer sb=new StringBuffer();
		
		if (listMonitorPoint == null || listMonitorPoint.size() <= 0) {

			sb.append("[]");

			return sb;
		}
		
		sb.append("[");
		
		for(MonitorPoint item:listMonitorPoint) {
			
			sb.append("{");

			int nID = nMonitorID*10000 + item.getAutoID();

			sb.append(String.format("\"id\":\"%d\",", nID));

			sb.append(String.format("\"text\":\"%s\",", item.getMonitorPtName()));

			sb.append(String.format("\"state\":\"%s\",", "open"));

			sb.append("\"attributes\":{");
			sb.append(String.format("\"monitorID\":\"%d\",", nMonitorID));
			sb.append(String.format("\"monitorName\":\"%s\",", item.getMonitorName()));
			sb.append(String.format("\"mpointID\":\"%d\",", item.getAutoID()));
			sb.append(String.format("\"mpointName\":\"%s\"", item.getMonitorPtName()));
			sb.append("}},");
		}
		
		sb.deleteCharAt(sb.length() - 1);
		
		sb.append("]");
		
		return sb;
	}
	
	/**
	 * �����Ŀ�����ṹ-�������
	 * @param nMonitorID
	 * @param strMonitorName
	 * @return
	 */
	public StringBuffer GetMonitorPointTreeDataWithRelation(int nMonitorID,String strMonitorName) {
		
		MonitorPrjAction monitorAction=new MonitorPrjAction(this.strPrjName);
		
		List<MonitorPoint> listMonitorPoint=monitorAction.GetAllMonitorPointData(strMonitorName);
		
		StringBuffer sb=new StringBuffer();
		
		if (listMonitorPoint == null || listMonitorPoint.size() <= 0) {

			sb.append("[]");

			return sb;
		}
		
		List<point_chn_sensor_relation> listRelation=this.GetSeneorRelationByMonitorName(strMonitorName);
		
		sb.append("[");
		
		for(MonitorPoint item:listMonitorPoint) {
			
			//�жϲ���Ƿ�����ӳ���ϵ
			
			boolean bInRelation=false;
			
			for(point_chn_sensor_relation relation : listRelation) {
				
				if(relation.getMonitorPtName().equals(item.getMonitorPtName())) {
					
					bInRelation=true;
					
					break;
				}
				
					
				
			}
			
			if(!bInRelation)
				continue;
			
			
			sb.append("{");

			int nID = nMonitorID*10000 + item.getAutoID();

			sb.append(String.format("\"id\":\"%d\",", nID));

			sb.append(String.format("\"text\":\"%s\",", item.getMonitorPtName()));

			sb.append(String.format("\"state\":\"%s\",", "open"));

			sb.append("\"attributes\":{");
			sb.append(String.format("\"monitorID\":\"%d\",", nMonitorID));
			sb.append(String.format("\"monitorName\":\"%s\",", item.getMonitorName()));
			sb.append(String.format("\"mpointID\":\"%d\",", item.getAutoID()));
			sb.append(String.format("\"mpointName\":\"%s\"", item.getMonitorPtName()));
			sb.append("}},");
			
		}
		
		if(sb.length()>1)
		{
			sb.deleteCharAt(sb.length() - 1);
		}
		
		sb.append("]");
		
		
		return sb;
	}

	/**
	 * ��ѯʱ�䷶Χ����ʷ���ݵĸ���
	 * @param strMonitorName
	 * @param strMPointName
	 * @param strSeneorValueName
	 * @param strStartTime
	 * @param strEndTime
	 * @return
	 */
	public int GetPageDataCount(String strMonitorName,
			String strMPointName,String strSeneorValueName,String strStartTime,String strEndTime) {
		
		String strHistoryTableName=GetHistoryTableName(strMonitorName,strMPointName,strSeneorValueName);
		
		if(strHistoryTableName.isEmpty()) {
			
			return 0;
		}

		StringBuffer sbSql=new StringBuffer();
		
		sbSql.append("select count(*) from `"+strHistoryTableName+"` where "
				+ " monitorName='"+strMonitorName+"'"
				+ " and monitorPtName='"+strMPointName+"'"
				+ " and SensorValueName='"+strSeneorValueName+"'"
				+ " and SampleTime>='"+strStartTime+"' and SampleTime<='"+strEndTime+"';");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sbSql.toString());
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {
			
			int nRes=Integer.parseInt(dt.getRow().get(0).getCol().get(0).getValue().toString());
			
			return nRes;
			
		}
		
		
		return 0;
	}
	
	/**
	 * ��ȡʱ�䷶Χ�ڵķ�ҳ����ʷ����
	 * @param strMonitorName
	 * @param strMPointName
	 * @param strSeneorValueName
	 * @param strStartTime
	 * @param strEndTime
	 * @param page
	 * @param rows
	 * @return
	 */
	public StringBuffer GetHistorySamplePageData(String strMonitorName,
			String strMPointName,String strSeneorValueName,String strStartTime,String strEndTime,int page, int rows) {
		
		String strHistoryTableName=GetHistoryTableName(strMonitorName,strMPointName,strSeneorValueName);
		
		StringBuffer sb=new StringBuffer();
		
		if(strHistoryTableName.isEmpty()) {
			
			sb.append("{\"total\":0,\"rows\":[]}");
			
			return sb;
		}
		
		StringBuffer sbSql=new StringBuffer();
		
		int nComputcout = (page - 1) * rows;
		
		sbSql.append("select * from `"+strHistoryTableName+"` where "
				+ " monitorName='"+strMonitorName+"'"
				+ " and monitorPtName='"+strMPointName+"'"
				+ " and SensorValueName='"+strSeneorValueName+"'"
				+ " and SampleTime>='"+strStartTime+"' and SampleTime<='"+strEndTime+"' limit ").append(nComputcout).append(",").append(rows).append(";");;
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sbSql.toString());
		
		int nDataCount =GetPageDataCount(strMonitorName,strMPointName,strSeneorValueName,strStartTime,strEndTime);
		
		if(nDataCount==0) {
			
			sb.append("{\"total\":0,\"rows\":[]}");
			
			return sb;
			
		}
		
		sb.append("{");
		sb.append("\"rows\":[");
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {
			
			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {
			
					sb.append("{");
					sb.append(String.format("\"id\":\"%s\",", dr.getIntColumn("AutoID")));
					sb.append(String.format("\"monitorName\":\"%s\",", dr.getStringColumn("monitorName")));
					sb.append(String.format("\"monitorPtName\":\"%s\",", dr.getStringColumn("monitorPtName")));
					sb.append(String.format("\"insFactoryID\":\"%s\",", dr.getStringColumn("insFactoryID")));
					sb.append(String.format("\"SensorValueName\":\"%s\",", dr.getStringColumn("SensorValueName")));
					sb.append(String.format("\"SensorEUValue\":\"%s\",", String.format("%.2f", dr.getFloatColumn("SensorEUValue"))));
					Date SampleTime=dr.getUtilDateColumn("SampleTime");
					sb.append(String.format("\"SampleTime\":\"%s\"", formatter.format(SampleTime)));
					sb.append("},");
					
				}
			}
			
		}
		
		sb.deleteCharAt(sb.length() - 1);

		sb.append("],\"total\":").append(nDataCount).append("}");
		
		return sb;
	}

	
	
	/**
	 * ���ݼ����Ŀ�����ƻ�ȡ��Ӧ��
	 * @param strMonitorName
	 * @return
	 */
	public String GetSeneorMeasureType(String strMonitorName) {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select * from `point_chn_sensor_relation` as a,`instrument_sensor` as b where a.SensorID=b.SensorID AND a.monitorName='"+strMonitorName+"';");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		String strSeneorMeasureType="";
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {
			
			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {
					
					strSeneorMeasureType=dr.getStringColumn("SensorMeasureType");
					
					break;
				}
			}
			
		}
		
		return strSeneorMeasureType;
	}
	
	/**
	 * ��ȡ����ˮ׼�Ĳ������
	 * @return
	 */
	public StringBuffer GetJLSZMPointName(String strMonitorName) {
		
		StringBuffer sbSql=new StringBuffer();
		
		sbSql.append("Select * from `point_chn_sensor_relation` where monitorName='"+strMonitorName+"';");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sbSql.toString());	
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("[");
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {
			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {
					
					sb.append("{");
					sb.append(String.format("\"monitorName\":\"%s\",", dr.getStringColumn("monitorName")));
					sb.append(String.format("\"monitorPtName\":\"%s\"", dr.getStringColumn("monitorPtName")));
					sb.append("},");
					
				}
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		
		sb.append("]");
		
		return sb;
	}
	
	/**
	 * ��ȡʱ�䷶Χ�ڵ����ݸ���
	 * @param strMonitorName
	 * @param strStartTime
	 * @param strEndTime
	 * @return
	 */
	public int GetJLSZPageDataCount(String strMonitorName,String strSeneorValueName,String strStartTime,String strEndTime) {
		
		List<String> listTableNames=GetHistoryTableNames(strMonitorName);
		
		if(listTableNames==null||listTableNames.size()<=0) {
			return 0;
		}
		
		int nDataCount=0;
		
		for(String item:listTableNames) {
			
			StringBuffer sbSql=new StringBuffer();
			
			sbSql.append("select count(*) from `"+item+"` where "
					+ " monitorName='"+strMonitorName+"'"
					+ " and SensorValueName='"+strSeneorValueName+"'"
					+ " and SampleTime>='"+strStartTime+"' and SampleTime<='"+strEndTime+"';");
			
			DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sbSql.toString());
			
			if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {
				
				int nRes=Integer.parseInt(dt.getRow().get(0).getCol().get(0).getValue().toString());
				
				if(nRes>nDataCount) {
					nDataCount=nRes;
				}
				
			}
			
		}
		
		return nDataCount;
	}
	
	/**
	 * ��ȡ����ˮ׼�ǵı������
	 * @return
	 */
	public StringBuffer GetHistoryJLSZSamplePageData(String strMonitorName,String strStartTime,String strEndTime,int page, int rows) {
		
		String strSeneorValueName="����ˮ׼��";
		
		List<String> listTableNames=GetMonitorPtNames(strMonitorName);
		
		StringBuffer sb=new StringBuffer();
		
		if(listTableNames==null||listTableNames.size()<=0) {
			
			sb.append("{\"total\":0,\"rows\":[]}");
			
			return sb;
		}
		
		int nDataCount=GetJLSZPageDataCount(strMonitorName,strSeneorValueName, strStartTime, strEndTime);
		
		if(nDataCount==0) {
			
			sb.append("{\"total\":0,\"rows\":[]}");
			
			return sb;
			
		}
		
		
		sb.append("{\"total\":").append(nDataCount).append(",\"rows\":[");
		
		Map<String, DataTable> map = new HashMap<String, DataTable>();
		
		int nRows=0;
		
		for(String item :listTableNames) {
			
			StringBuffer sbSql=new StringBuffer();
			
			int nComputcout = (page - 1) * rows;
			
			sbSql.append("select * from `"+String.format("insdata_history_%s",item)+"` where "
					+ " monitorName='"+strMonitorName+"'"
					+ " and SensorValueName='"+strSeneorValueName+"'"
					+ " and SampleTime>='"+strStartTime+"' and SampleTime<='"+strEndTime+"' limit ").append(nComputcout).append(",").append(rows).append(";");
			
			DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sbSql.toString());
			
			if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {
				
				int ndtrows=dt.getRow().size();
				
				if(ndtrows>nRows) {
					
					nRows=ndtrows;
					
				}
				
			}
			
			map.put(item,dt);
			
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for(int i=0;i<nRows;i++) {
			
			sb.append("{");
			
			sb.append(String.format("\"monitorName\":\"%s\",", strMonitorName));
			
			int j=0;
			
			for(String item: listTableNames) {
				
				//����ʱ��Ͳ��
				
				DataTable dt=map.get(item);
				
				if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {
					
					if(j==0) {
						
						Date SampleTime=dt.getRow().get(i).getUtilDateColumn("SampleTime");
						sb.append(String.format("\"SampleTime\":\"%s\",", formatter.format(SampleTime)));
						
					}
					
					sb.append(String.format("\"%s\":\"%s\",", item,String.format("%.2f", dt.getRow().get(i).getFloatColumn("SensorEUValue"))));
				}else {
					
					
					
				}
				
				j++;
			}
			
			sb.deleteCharAt(sb.length() - 1);
			
			sb.append("},");
			
		}
		
		
		sb.deleteCharAt(sb.length() - 1);
		
		sb.append("]}");
		
		return sb;
		
	}
	
	/**
	 * ����ˮ׼�ǵ�chart����
	 * @param strMonitorName
	 * @param strStartTime
	 * @param strEndTime
	 * @return
	 */
	public StringBuffer GetHistoryJLSZChartData(String strMonitorName,String strStartTime,String strEndTime) {
		
		String strSeneorValueName="����ˮ׼��";
		
		List<String> listTableNames=GetMonitorPtNames(strMonitorName);
		
		StringBuffer sb=new StringBuffer();
		
		Map<String, DataTable> map = new HashMap<String, DataTable>();
		
		int nRows=0;
		
		
		for(String item :listTableNames) {
			
			StringBuffer sbSql=new StringBuffer();
			
			
			
			sbSql.append("select * from `"+String.format("insdata_history_%s",item)+"` where "
					+ " monitorName='"+strMonitorName+"'"
					+ " and SensorValueName='"+strSeneorValueName+"'"
					+ " and SampleTime>='"+strStartTime+"' and SampleTime<='"+strEndTime+"';");
			
			DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sbSql.toString());
			
			if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {
				
				int ndtrows=dt.getRow().size();
				
				if(ndtrows>nRows) {
					
					nRows=ndtrows;
					
				}
				
			}
			
			map.put(item,dt);
			
		}
		
		sb.append("[");
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for(int i=0;i<nRows;i++) {
			
			sb.append("{");
			
			sb.append(String.format("\"monitorName\":\"%s\",", strMonitorName));
			
			int j=0;
			
			for(String item: listTableNames) {
				
				//����ʱ��Ͳ��
				
				DataTable dt=map.get(item);
				
				if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {
					
					if(j==0) {
						
						Date SampleTime=dt.getRow().get(i).getUtilDateColumn("SampleTime");
						sb.append(String.format("\"SampleTime\":\"%s\",", formatter.format(SampleTime)));
						
					}
					
					sb.append(String.format("\"%s\":\"%s\",", item,String.format("%.2f", dt.getRow().get(i).getFloatColumn("SensorEUValue"))));
				}else {
					
					
					
				}
				
				j++;
			}
			
			sb.append("},");
			
		}
		
		if(sb.length()>1) {
			sb.deleteCharAt(sb.length() - 1);
		}
		
		sb.append("]");
		
		return sb;
		
	}
	
	/**
	 * ��̬ʵʱchart����
	 * @param strMonitorName
	 * @param strMPointName
	 * @param strSeneorValueName
	 * @param strStartTime
	 * @param strEndTime
	 * @return
	 */
	public StringBuffer GetDynamicRealTimeChartData(String strMonitorName,
			String strMPointName,String strSeneorValueName,String strStartTime,String strEndTime,int nCurCount) {
		
		StringBuffer sbSql=new StringBuffer();
		
		sbSql.append("select * from `insdata_realtime` where 1=1  "
				+ "and monitorName = '"+strMonitorName+"' "
				+ "and monitorPtName ='"+strMPointName+"' "
				+ "and SensorValueName ='"+strSeneorValueName+"' "
				+ "and SampleTime >= '"+strStartTime+"' "
				+ "and SampleTime < '"+strEndTime+"' ;");
		
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sbSql.toString());
		
		StringBuffer sb=new StringBuffer();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		sb.append("[");
		
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {
			
			for (DataRow dr : dt.getRow()) {
				
				if (dr != null && dr.getCol().size() > 0) {
					
					
					sb.append("{");
					Date SampleTime=dr.getUtilDateColumn("SampleTime");
					sb.append(String.format("\"SampleTime0\":\"%s\",", formatter.format(SampleTime)));
					sb.append(String.format("\"SampleTime\":\"%s\",", nCurCount++));
					sb.append(String.format("\"SampleValue\":\"%s\"", String.format("%.2f", dr.getFloatColumn("SensorEUValue"))));
					sb.append("},");
					
					
				}
			}
			
			sb.deleteCharAt(sb.length() - 1);
		}
		
		
		sb.append("]");
		
		return sb;
	}
	
	/**
	 * �������ݴ洢��ʽ
	 * @param strMonitorName
	 * @return
	 */
	public int GetValueDataType(String strMonitorName) {
		
		//���ݼ����Ŀ�����ҵ���㣬Ȼ����ݲ����е��������ж�
		
		StringBuffer sbSql=new StringBuffer();
		
		String strMPointName="";
		
		sbSql.append("select * from `point_chn_sensor_relation` where monitorName='"+strMonitorName+"';");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sbSql.toString());
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {
			
			for (DataRow dr : dt.getRow()) {
				
				if (dr != null && dr.getCol().size() > 0) {
					
					strMPointName=dr.getStringColumn("monitorPtName").toString();
					
					break;
				}
			}
		}else{
			return 0;
		}
		
		String strTableName=String.format("insdata_history_%s", strMPointName);
		
		sbSql=new StringBuffer();
		
		sbSql.append("select * from `"+strTableName+"` limit 0,1;");
		
		DataTable dtData = mysql.ExecuteQueryPrj(strPrjName, sbSql.toString());
		
		int nRes=0;
		
		if (dtData != null && dtData.getRow() != null && dtData.getRow().size() > 0) {
			
			for (DataRow dr : dtData.getRow()) {
				
				if (dr != null && dr.getCol().size() > 0) {
					
					nRes=dr.getIntColumn("DataType");
					
					return nRes;
				}
			}
		}else{
			return 0;
		}
		
		return 0;
	}
	
	/**
	 * ��ʷ�ļ����ݸ���
	 * @param strMonitorName
	 * @param strMPointName
	 * @param strSeneorValueName
	 * @param strStartTime
	 * @param strEndTime
	 * @return
	 */
	public int GetHistoryFilePageDataCount(String strMonitorName,
			String strMPointName,String strSeneorValueName,String strStartTime,String strEndTime) {
		
		StringBuffer sbSql=new StringBuffer();
		
		String strTableName=String.format("insdata_history_%s", strMPointName);
		
		sbSql.append("select count(*) from `"+strTableName+"` where "
				+ "monitorName='"+strMonitorName+"' "
			    + "and monitorPtName='"+strMPointName+"' "
			    + "and SensorValueName='"+strSeneorValueName+"' "
			    + "and SampleTime>='"+strStartTime+"' "
			    + "and SampleTime<'"+strEndTime+"' "
			    + "and DataType='1' ;");
		
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sbSql.toString());
		
		int nDataCount=0;
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {
			
			nDataCount=Integer.parseInt(dt.getRow().get(0).getCol().get(0).getValue().toString());
		}
		
		return nDataCount;
	}
	
	/**
	 * ��ʷ�ļ���ҳ����
	 * @param strMonitorName
	 * @param strMPointName
	 * @param strSeneorValueName
	 * @param strStartTime
	 * @param strEndTime
	 * @param page
	 * @param rows
	 * @return
	 */
	public StringBuffer GetHistoryFilePageData(String strMonitorName,
			String strMPointName,String strSeneorValueName,String strStartTime,String strEndTime,int page, int rows) {
		
		StringBuffer sb=new StringBuffer();
		
		int nDataCount =GetHistoryFilePageDataCount(strMonitorName,strMPointName,strSeneorValueName,strStartTime,strEndTime);
		
		if(nDataCount<=0) {
			
			sb.append("{\"total\":0,\"rows\":[]}");
			
			return sb;
			
		}
		
		int nComputcout = (page - 1) * rows;
		
		StringBuffer sbSql=new StringBuffer();
		
		String strTableName=String.format("insdata_history_%s", strMPointName);
		
		sbSql.append("select * from `"+strTableName+"` where "
				+ "monitorName='"+strMonitorName+"' "
			    + "and monitorPtName='"+strMPointName+"' "
			    + "and SensorValueName='"+strSeneorValueName+"' "
			    + "and SampleTime>='"+strStartTime+"' "
			    + "and SampleTime<'"+strEndTime+"' "
			    + "and DataType='1' limit ").append(nComputcout).append(",").append(rows).append(";");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sbSql.toString());
		
		sb.append("{");
		sb.append("\"rows\":[");
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {
			
			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {
			
					sb.append("{");
					sb.append(String.format("\"id\":\"%s\",", dr.getIntColumn("AutoID")));
					sb.append(String.format("\"monitorName\":\"%s\",", dr.getStringColumn("monitorName")));
					sb.append(String.format("\"monitorPtName\":\"%s\",", dr.getStringColumn("monitorPtName")));
					sb.append(String.format("\"chnID\":\"%d\",", dr.getIntColumn("chnID")));
					sb.append(String.format("\"insFactoryID\":\"%s\",", dr.getStringColumn("insFactoryID")));
					sb.append(String.format("\"SensorValueName\":\"%s\",", dr.getStringColumn("SensorValueName")));
					
					String strFilePath=dr.getStringColumn("SensorEUValueFilePath");
					
					sb.append(String.format("\"SensorEUValueFilePath\":\"%s\",",strFilePath.replace("\\", "\\\\")));
					Date SampleTime=dr.getUtilDateColumn("SampleTime");
					sb.append(String.format("\"SampleTime\":\"%s\"", formatter.format(SampleTime)));
					sb.append("},");
					
				}
			}
			
			sb.deleteCharAt(sb.length() - 1);
		}
		
		sb.append("],\"total\":").append(nDataCount).append("}");
		
		return sb;
	}
	
	/**
	 * ��ȡ��ѹ���rar�ļ��еĶ�Ӧ��chnid��tim�ļ�·��
	 * @param strRarFilePath
	 * @param nChnID
	 * @return
	 */
	public StringBuffer GetHistoryUnRarFilePath(String strRarFilePath,int nChnID) {
		
		String strUnRarFolder=new ConfigInfo().getExeFolder();
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HHmmss");//�������ڸ�ʽ

		String strCurTime=df.format(new Date());
		
		String strUnRarFile=String.format("%s\\%s\\", strUnRarFolder,strCurTime);
		
		UnZipOrRarUtils.unRarFile(strRarFilePath, strUnRarFile);
		
		StringBuffer sb=new StringBuffer();
		
		//����ͨ���ж϶�Ӧ��tim�ļ�
		
		File file = new File(strUnRarFile);
		
		 
		
		if (file.exists()) {
			
			 File[] files = file.listFiles();
			 
			 if(null == files || files.length == 0) {
				 
				 sb.append("{\"result\":\"nofile\"}");
					
				return sb;
				 
			 }else {
				 
				 String strEndName=String.format("%d.tim", nChnID);
				 
				 for (File file2 : files) {
					 
					 if (file2.isDirectory()) {
						 continue;
					 }else {
						 
						 String file2Name=file2.getName();
						 
						if(file2Name.endsWith(strEndName)){
							
							sb.append("{\"result\":\""+file2.getAbsolutePath().replace("\\", "\\\\")+"\"}");
							
							return sb;
							
						}
						 
						 
						 
					 }
						 
					 
				 }
				 
			 }
			
		}else{
			
			sb.append("{\"result\":\"nofile\"}");
			
			return sb;
			
		}
		
		
		return null;
	}
	
	/**
	 * ��ȡ�ļ����ݵ�chart����
	 * @param strTimFilePath
	 * @return
	 */
	public StringBuffer GetHistoryFileChartData(String strTimFilePath) {
		
		File file = new File(strTimFilePath);
		
		InputStream in = null;
		
		StringBuffer sb=new StringBuffer();
		
		
		
		try {
			
			//k 60
			//b 64
			//count 84
			//sampledata 2048
			
			in = new FileInputStream(file);
			
			//ֱ�Ӷ�ȡ2048���ֽ�
			
			byte[] byte_2048=new byte[2048];
			in.read(byte_2048, 0, 2048);
			
			//��ȡ��������
			
			byte[] byte_k = {byte_2048[60],byte_2048[61],byte_2048[62],byte_2048[63]};
			float fK=CommonConvert.byte2float(byte_k, 0);
			
			byte[] byte_b={byte_2048[64],byte_2048[65],byte_2048[66],byte_2048[67]};
			float fB=CommonConvert.byte2float(byte_b, 0);
			
			byte[] byte_count={byte_2048[84],byte_2048[85],byte_2048[86],byte_2048[87]};
			int nCount=CommonConvert.byteArrayToInt(byte_count);
			
			/*in.skip(60);
			byte[] byte_k = new byte[4];
			in.read(byte_k, 0, 4);
			float fK=CommonConvert.byte2float(byte_k, 0);*/
			
			//in.reset();
			//in.skip(4);
			/*byte[] byte_b=new byte[4];
			in.read(byte_b,0,4);
			float fB=CommonConvert.byte2float(byte_b, 0);*/
			
			//in.reset();
			/*in.skip(16);
			byte[] byte_count=new byte[4];
			in.read(byte_count, 0, 4);
			int nCount=CommonConvert.byteArrayToInt(byte_count);*/
			
			//in.reset();
			//in.skip(1960);
			byte[] byte_SampleData = new byte[nCount*4];
			in.read(byte_SampleData, 0, nCount*4);
			
			in.close();
			
			sb.append("{\"total\":\""+nCount+"\",\"sampledata\":[");
			
			for(int i=0;i<nCount;i++) {
				
				 byte[] valueByte = { byte_SampleData[i * 4], byte_SampleData[i * 4 + 1], byte_SampleData[i * 4 + 2], byte_SampleData[i * 4 + 3] };
				
				 float value=CommonConvert.byte2float(valueByte, 0);
				 
				 float num=(float)(Math.round((fK * (value - fB))*100)/100);//���Ҫ��ȷ4λ��*10000Ȼ��/10000
				 
				 sb.append("{\"sampletime\":\""+i+"\",\"samplevalue\":\""+num+"\"},");
			}
			
			sb.deleteCharAt(sb.length() - 1);
			
			sb.append("]}");
			
			
		}catch(IOException e){
			e.printStackTrace();
			
			sb.append("[]");
			
			return sb;
		}
		
		
		
		
		return sb;
	}
}

/**
 * ���_ͨ��_��������ϵ
 * 
 * @author Administrator
 *
 */
class point_chn_sensor_relation {
	/**
	 * ����ID
	 */
	int AutoID;
	/**
	 * �����Ŀ����
	 */
	String monitorName;
	/**
	 * �������
	 */
	String monitorPtName;
	/**
	 * �����������
	 */
	String insFactoryID;
	/**
	 * ͨ����
	 */
	int chnID;
	/**
	 * ����������
	 */
	String SensorID;

	public int getAutoID() {
		return AutoID;
	}

	public void setAutoID(int autoID) {
		AutoID = autoID;
	}

	public String getMonitorName() {
		return monitorName;
	}

	public void setMonitorName(String monitorName) {
		this.monitorName = monitorName;
	}

	public String getMonitorPtName() {
		return monitorPtName;
	}

	public void setMonitorPtName(String monitorPtName) {
		this.monitorPtName = monitorPtName;
	}

	public String getInsFactoryID() {
		return insFactoryID;
	}

	public void setInsFactoryID(String insFactoryID) {
		this.insFactoryID = insFactoryID;
	}

	public int getChnID() {
		return chnID;
	}

	public void setChnID(int chnID) {
		this.chnID = chnID;
	}

	public String getSensorID() {
		return SensorID;
	}

	public void setSensorID(String sensorID) {
		SensorID = sensorID;
	}

}

/**
 * ����������
 * 
 * @author Administrator
 *
 */
class instrument_sensor_data {
	/**
	 * ������
	 */
	int AutoID;
	/**
	 * ����������
	 */
	String SensorID;
	/**
	 * ֵ����
	 */
	String ValueName;
	/**
	 * ֵ��λ
	 */
	String ValueUnit;
	/**
	 * ֵ����ʷ���ݱ������yyyy�ֶ�
	 */
	String ValueDBFieldName;

	public int getAutoID() {
		return AutoID;
	}

	public void setAutoID(int autoID) {
		AutoID = autoID;
	}

	public String getSensorID() {
		return SensorID;
	}

	public void setSensorID(String sensorID) {
		SensorID = sensorID;
	}

	public String getValueName() {
		return ValueName;
	}

	public void setValueName(String valueName) {
		ValueName = valueName;
	}

	public String getValueUnit() {
		return ValueUnit;
	}

	public void setValueUnit(String valueUnit) {
		ValueUnit = valueUnit;
	}

	public String getValueDBFieldName() {
		return ValueDBFieldName;
	}

	public void setValueDBFieldName(String valueDBFieldName) {
		ValueDBFieldName = valueDBFieldName;
	}
}
