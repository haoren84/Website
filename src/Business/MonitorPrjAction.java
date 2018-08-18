package Business;

import java.util.ArrayList;
import java.util.List;

import Model.Monitor;
import Model.MonitorPoint;
import Model.StructMPointMap;
import SqlHelper.MySqlHelper;
import TstDataTable.DataRow;
import TstDataTable.DataTable;

/**
 * �����Ŀ����
 * 
 * @author Administrator
 *
 */
public class MonitorPrjAction {

	String strPrjName = null;

	MySqlHelper mysql = null;

	public MonitorPrjAction(String strPrjName) {

		mysql = new MySqlHelper();

		this.strPrjName = strPrjName;

	}
	
	/**
	 * ��������Ŀ�Ƿ��ظ�
	 * @param strMonitorName
	 * @return true:���ظ�  false:�ظ�
	 */
	public boolean CheckMonitorPrj(String strMonitorName) {
		
		Monitor monitorObj= GetMonitorPrjByName(strMonitorName);
		
		if(monitorObj==null) {
			
			return true;
			
		}
		
		return false;
	}

	/**
	 * ���������Ŀ
	 * 
	 * @param modelMonitor
	 *            �����Ŀ
	 * @return
	 */
	public boolean AddMonitorPrj(Monitor modelMonitor) {
		
		if(!CheckMonitorPrj(modelMonitor.getMonitorName())) {
			
			return false;
			
		}
		
		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO `monitor`"
				+ "(`monitorName`, `monitorMethord`, `monitorPointCount`, `monitorPointPrefix`, `monitorPointStartID`, `monitorPointEndID`, `State`) \r\n"
				+ "VALUES ('" + modelMonitor.getMonitorName() + "', " + "'" + modelMonitor.getMonitorMethord() + "', "
				+ "'" + modelMonitor.getMonitorPointCount() + "', " + "'" + modelMonitor.getMonitorPointPrefix() + "', "
				+ "'" + modelMonitor.getMonitorPointStartID() + "', " + "'" + modelMonitor.getMonitorPointEndID()
				+ "', " + "'" + modelMonitor.getState() + "'); ");

		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());

		if (nRes <= 0) {
			
			return false;
			
		} else {

			// ���������Ŀ�Ĳ��

			AddMonitorPointByPriInfo(modelMonitor.getMonitorName(), modelMonitor.getMonitorPointCount(),
					modelMonitor.getMonitorPointPrefix(), modelMonitor.getMonitorPointStartID(),
					modelMonitor.getMonitorPointEndID());

			return true;
		}
	}

	/**
	 * ������Ŀ���
	 * 
	 * @param strMonitorName
	 *            ��Ŀ����
	 * @param nPointCount
	 *            �����
	 * @param strPointPrefix
	 *            ���ǰ׺
	 * @param nPointStartID
	 *            �����ʼ���
	 * @param nPointEndID
	 *            ���������
	 * @return
	 */
	public boolean AddMonitorPointByPriInfo(String strMonitorName, int nPointCount, String strPointPrefix,
			int nPointStartID, int nPointEndID) {

		List<String> listString = new ArrayList<>();

		for (int i = 0; i < nPointCount; i++) {

			int nCurPID = nPointStartID + i;

			if (nCurPID > nPointEndID) {
				continue;
			}

			String curInfo = String.format("%s%d", strPointPrefix, nCurPID);

			listString.add(curInfo);
		}

		int nlist = listString.size();

		if (nlist <= 0)
			return false;

		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO `monitor_point`" + "(`monitorName`, `monitorPtName`) VALUES");

		for (String item : listString) {

			sb.append("('" + strMonitorName + "','" + item + "'),");

		}

		sb.deleteCharAt(sb.length() - 1);
		sb.append(";");

		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());

		if (nRes <= 0)
			return false;

		return true;
	}

	/**
	 * ��ȡ�����Ŀ�����ݸ���
	 * 
	 * @return
	 */
	public int GetMonitorPrjCount() {

		StringBuffer sb = new StringBuffer();

		sb.append("select count(*) from monitor ;");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		int nRes = 0;

		if (dt == null || dt.getRow() == null || dt.getRow().size() <= 0) {
			nRes = 0;
		} else {
			nRes = Integer.parseInt(dt.getRow().get(0).getCol().get(0).getValue().toString());
		}

		return nRes;
	}

	/**
	 * ��ȡ�����Ŀ�ķ�ҳ����
	 * 
	 * @param page
	 *            ҳ��
	 * @param rows
	 *            ÿҳ����
	 * @return
	 */
	public List<Monitor> GetMonitorPrjPageData(int page, int rows) {

		List<Monitor> listMonitor = new ArrayList<>();

		int nComputcout = (page - 1) * rows;

		StringBuffer sb = new StringBuffer();

		sb.append("select * from `monitor` limit ").append(nComputcout).append(",").append(rows).append(";");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {

					Monitor model = new Monitor();

					model.setAutoID(dr.getIntColumn("autoid"));
					model.setMonitorName(dr.getStringColumn("monitorName"));
					model.setMonitorMethord(dr.getStringColumn("monitorMethord"));
					model.setMonitorPointCount(dr.getIntColumn("monitorPointCount"));
					model.setMonitorPointPrefix(dr.getStringColumn("monitorPointPrefix"));
					model.setMonitorPointStartID(dr.getIntColumn("monitorPointStartID"));
					model.setMonitorPointEndID(dr.getIntColumn("monitorPointEndID"));
					model.setState(dr.getIntColumn("State"));

					listMonitor.add(model);
				}
			}

		}

		return listMonitor;
	}

	/**
	 * ���ݼ����Ŀ���ƻ�ȡ�����Ŀ����
	 * 
	 * @param strMonitorName
	 *            �����Ŀ����
	 * @return
	 */
	public Monitor GetMonitorPrjByName(String strMonitorName) {

		StringBuffer sb = new StringBuffer();

		sb.append("select * from `monitor` where monitorName='").append(strMonitorName).append("';");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {

					Monitor model = new Monitor();

					model.setAutoID(dr.getIntColumn("autoid"));
					model.setMonitorName(dr.getStringColumn("monitorName"));
					model.setMonitorMethord(dr.getStringColumn("monitorMethord"));
					model.setMonitorPointCount(dr.getIntColumn("monitorPointCount"));
					model.setMonitorPointPrefix(dr.getStringColumn("monitorPointPrefix"));
					model.setMonitorPointStartID(dr.getIntColumn("monitorPointStartID"));
					model.setMonitorPointEndID(dr.getIntColumn("monitorPointEndID"));
					model.setState(dr.getIntColumn("State"));

					return model;
				}
			}

		}

		return null;
	}

	/**
	 * ���¼����Ŀ����Ϣ
	 * 
	 * @param model
	 * @return
	 */
	public boolean UpdateMonitor(Monitor model) {

		StringBuffer sb = new StringBuffer();

		sb.append("update `monitor` SET " + "`monitorName`='" + model.getMonitorName() + "'," + "`monitorMethord`='"
				+ model.getMonitorMethord() + "'," + "`monitorPointCount`='" + model.getMonitorPointCount() + "',"
				+ "`monitorPointPrefix`='" + model.getMonitorPointPrefix() + "'," + "`monitorPointStartID`='"
				+ model.getMonitorPointStartID() + "'," + "`monitorPointEndID`='" + model.getMonitorPointEndID() + "',"
				+ "`State`='0' WHERE (`AutoID`='" + model.getAutoID() + "');");

		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());

		if (nRes <= 0)
			return false;

		return true;
	}

	/**
	 * ���������ĸ��������������
	 * 
	 * @param strMonitorName
	 *            �����Ŀ����
	 * @param nAddPointCount
	 *            ��Ҫ�����Ĳ�����
	 * @return
	 */
	public boolean AddMonitorPointByAddCount(String strMonitorName, int nAddPointCount) {

		// �ҵ���Ӧ�ļ����Ŀ����

		Monitor model = GetMonitorPrjByName(strMonitorName);

		if (model == null) {
			return false;
		}

		// �������ݲ�����

		int nCurPoint = model.getMonitorPointCount();

		int nCurEnd = model.getMonitorPointEndID();

		model.setMonitorPointCount(nCurPoint + nAddPointCount);
		model.setMonitorPointEndID(nCurEnd + nAddPointCount);

		UpdateMonitor(model);

		// �������

		boolean bRes = AddMonitorPointByPriInfo(strMonitorName, nAddPointCount, model.getMonitorPointPrefix(), nCurEnd+1,
				nCurEnd + nAddPointCount);

		return bRes;
	}

	/**
	 * ��Ӧ�����Ŀ�Ĳ��ĸ���
	 * 
	 * @param strMonitorName
	 *            �����Ŀ����
	 * @return
	 */
	public int SelectMonitorPointDataCount(String strMonitorName) {

		StringBuffer sb = new StringBuffer();

		sb.append("select count(*) from `monitor_point` where monitorName='").append(strMonitorName).append("';");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		int nRes = 0;

		if (dt == null || dt.getRow() == null || dt.getRow().size() <= 0) {
			nRes = 0;
		} else {
			nRes = Integer.parseInt(dt.getRow().get(0).getCol().get(0).getValue().toString());
		}

		return nRes;
	}

	/**
	 * ��Ӧ�����Ŀ�Ĳ���ҳ����
	 * 
	 * @param strMonitorName
	 *            �����Ŀ����
	 * @param page
	 *            ҳ��
	 * @param rows
	 *            ÿҳ����
	 * @return
	 */
	public List<String> SelectMonitorPointPageData(String strMonitorName, int page, int rows) {

		List<String> list = new ArrayList<>();

		int nComputcout = (page - 1) * rows;

		StringBuffer sb = new StringBuffer();

		sb.append("select * from `monitor_point` where monitorName='" + strMonitorName + "'  limit ")
				.append(nComputcout).append(",").append(rows).append(";");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {

					list.add(dr.getStringColumn("monitorPtName"));
				}
			}

		}

		return list;
	}

	/**
	 * ��Ӧ�����Ŀ�Ĳ������
	 * 
	 * @param strMonitorName
	 *            �����Ŀ����
	 * @return
	 */
	public List<String> SelectMonitorPointByMonitorName(String strMonitorName) {

		List<String> list = new ArrayList<>();

		StringBuffer sb = new StringBuffer();

		sb.append("select * from `monitor_point` where monitorName='" + strMonitorName + "';");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {

					list.add(dr.getStringColumn("monitorPtName"));
				}
			}

		}

		return list;
	}

	/**
	 * ��ȡ���еļ����Ŀ����
	 * 
	 * @return
	 */
	public List<Monitor> GetAllMonitorData() {

		List<Monitor> listMonitor = new ArrayList<>();

		StringBuffer sb = new StringBuffer();

		sb.append("select * from `monitor` ;");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {

					Monitor model = new Monitor();

					model.setAutoID(dr.getIntColumn("autoid"));
					model.setMonitorName(dr.getStringColumn("monitorName"));
					model.setMonitorMethord(dr.getStringColumn("monitorMethord"));
					model.setMonitorPointCount(dr.getIntColumn("monitorPointCount"));
					model.setMonitorPointPrefix(dr.getStringColumn("monitorPointPrefix"));
					model.setMonitorPointStartID(dr.getIntColumn("monitorPointStartID"));
					model.setMonitorPointEndID(dr.getIntColumn("monitorPointEndID"));
					model.setState(dr.getIntColumn("State"));

					listMonitor.add(model);
				}
			}

		}

		return listMonitor;

	}
	
	/**
	 * ��ȡ��Ӧ��Ŀ�����м����
	 * @return
	 */
	public List<MonitorPoint> GetAllMonitorPointData(String strMonitorName) {
		
		List<MonitorPoint> list = new ArrayList<>();
		
		StringBuffer sb = new StringBuffer();

		sb.append("select * from `monitor_point` where monitorName='" + strMonitorName + "';");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {

					MonitorPoint model=new MonitorPoint();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setMonitorName(dr.getStringColumn("monitorName"));
					model.setMonitorPtName(dr.getStringColumn("monitorPtName"));
					list.add(model);
				}
			}

		}

		return list;
	}
	
	
	/**
	 * ���ݽṹID�ͼ����ĿID��ȡ �ṹ����Ŀ����ƥ����Ϣ
	 * @param nStructID �ṹID
	 * @param nMonitorID �����ĿID
	 * @return
	 */
	public List<StructMPointMap> GetStructMPointMapInfoByStructIDAndMonitorID(int nStructID,int nMonitorID){
		
		List<StructMPointMap> list=new ArrayList<>();
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("select * from `struct_mpoint_map` where structID='" + nStructID + "' and monitorID='"+nMonitorID+"';");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {
					StructMPointMap model=new StructMPointMap();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setStructID(dr.getIntColumn("structID"));
					model.setStructName(dr.getStringColumn("structName"));
					model.setMonitorID(dr.getIntColumn("monitorID"));
					model.setMonitorName(dr.getStringColumn("monitorName"));
					model.setMpointID(dr.getIntColumn("mpointID"));
					model.setMpointName(dr.getStringColumn("mpointName"));
					list.add(model);
				}
			}

		}

		return list;
	}
	
	/**
	 * ����ID������ɾ�������Ŀ
	 * @param nAutoID 
	 * @param strMonitorName
	 * @return
	 */
	public boolean DeleteMonitorInfoByIDAndName(int nAutoID,String strMonitorName) {
		
		if(CheckChnSeneorPointInfo(strMonitorName)) {
			return false;
		}
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("delete from `monitor` where monitorName='"+strMonitorName+"' and AutoID='"+nAutoID+"';");
		
		int nRes=mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		
		if(nRes==1) {
			
			//ɾ����������Ϣ
			
			DeleteMonitorPointByMonitorName(strMonitorName);
			
			//ɾ���ṹ�Ĺ������
			
			DeleteStructPointInfo(strMonitorName);
			
			//ɾ��ͨ���ʹ������Ĺ������
			
			DeleteChnSeneorPointInfo(strMonitorName);
			
			return true;
			
		}else {
			
			return false;
		}
	}
	
	/**
	 * ���ݼ����Ŀ����ɾ����Ӧ�Ĳ��
	 * @param strMonitorName
	 * @return
	 */
	public boolean DeleteMonitorPointByMonitorName(String strMonitorName) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("delete from `monitor_point` where monitorName='"+strMonitorName+"';");
		
		int nRes=mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		
		return nRes>=1;
	}
	
	/**
	 * ���ݼ����Ŀ����ɾ���ṹ��ƥ����Ϣ
	 * @param strMonitorName
	 * @return
	 */
	public boolean DeleteStructPointInfo(String strMonitorName) {

		StringBuffer sb = new StringBuffer();
		
		sb.append("delete from `struct_mpoint_map` where monitorName='"+strMonitorName+"';");
		
		int nRes=mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		
		return nRes>=1;
	}
	
	/**
	 * ���ݼ����Ŀ����ɾ��ͨ���ʹ�������ƥ����Ϣ
	 * @param strMonitorName
	 * @return
	 */
	public boolean DeleteChnSeneorPointInfo(String strMonitorName) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("delete from `point_chn_sensor_relation` where monitorName='"+strMonitorName+"';");
		
		int nRes=mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		
		return nRes>=1;
	}
	
	/**
	 * ���ݼ����Ŀ������֤ͨ���ʹ�������ƥ����Ϣ�Ƿ����
	 * @param strMonitorName
	 * @return true:����ƥ����Ϣ  false:������ƥ����Ϣ
	 */
	public boolean CheckChnSeneorPointInfo(String strMonitorName) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("select count(*) from `point_chn_sensor_relation` where monitorName='"+strMonitorName+"';");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		int nRes = 0;

		if (dt == null || dt.getRow() == null || dt.getRow().size() <= 0) {
			nRes = 0;
		} else {
			nRes = Integer.parseInt(dt.getRow().get(0).getCol().get(0).getValue().toString());
		}
		
		if(nRes>0) {
			return true;
		}
		
		return false;
	}
	
	
	
}
