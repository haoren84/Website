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
 * 监测项目操作
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
	 * 检验检测项目是否重复
	 * @param strMonitorName
	 * @return true:不重复  false:重复
	 */
	public boolean CheckMonitorPrj(String strMonitorName) {
		
		Monitor monitorObj= GetMonitorPrjByName(strMonitorName);
		
		if(monitorObj==null) {
			
			return true;
			
		}
		
		return false;
	}

	/**
	 * 新增监测项目
	 * 
	 * @param modelMonitor
	 *            监测项目
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

			// 创建监测项目的测点

			AddMonitorPointByPriInfo(modelMonitor.getMonitorName(), modelMonitor.getMonitorPointCount(),
					modelMonitor.getMonitorPointPrefix(), modelMonitor.getMonitorPointStartID(),
					modelMonitor.getMonitorPointEndID());

			return true;
		}
	}

	/**
	 * 新增项目测点
	 * 
	 * @param strMonitorName
	 *            项目名称
	 * @param nPointCount
	 *            测点数
	 * @param strPointPrefix
	 *            测点前缀
	 * @param nPointStartID
	 *            测点起始编号
	 * @param nPointEndID
	 *            测点结束编号
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
	 * 获取监测项目的数据个数
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
	 * 获取监测项目的分页数据
	 * 
	 * @param page
	 *            页数
	 * @param rows
	 *            每页个数
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
	 * 根据监测项目名称获取监测项目数据
	 * 
	 * @param strMonitorName
	 *            监测项目名称
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
	 * 更新监测项目的信息
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
	 * 根据新增的个数来新增监测测点
	 * 
	 * @param strMonitorName
	 *            监测项目名称
	 * @param nAddPointCount
	 *            需要新增的测点个数
	 * @return
	 */
	public boolean AddMonitorPointByAddCount(String strMonitorName, int nAddPointCount) {

		// 找到对应的监测项目数据

		Monitor model = GetMonitorPrjByName(strMonitorName);

		if (model == null) {
			return false;
		}

		// 更新数据测点个数

		int nCurPoint = model.getMonitorPointCount();

		int nCurEnd = model.getMonitorPointEndID();

		model.setMonitorPointCount(nCurPoint + nAddPointCount);
		model.setMonitorPointEndID(nCurEnd + nAddPointCount);

		UpdateMonitor(model);

		// 新增测点

		boolean bRes = AddMonitorPointByPriInfo(strMonitorName, nAddPointCount, model.getMonitorPointPrefix(), nCurEnd+1,
				nCurEnd + nAddPointCount);

		return bRes;
	}

	/**
	 * 对应监测项目的测点的个数
	 * 
	 * @param strMonitorName
	 *            监测项目名称
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
	 * 对应监测项目的测点分页数据
	 * 
	 * @param strMonitorName
	 *            监测项目名称
	 * @param page
	 *            页数
	 * @param rows
	 *            每页个数
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
	 * 对应监测项目的测点数据
	 * 
	 * @param strMonitorName
	 *            监测项目名称
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
	 * 获取所有的监测项目数据
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
	 * 获取对应项目的所有监测测点
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
	 * 根据结构ID和监测项目ID获取 结构和项目测点的匹配信息
	 * @param nStructID 结构ID
	 * @param nMonitorID 监测项目ID
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
	 * 根据ID和名称删除监测项目
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
			
			//删除测点表中信息
			
			DeleteMonitorPointByMonitorName(strMonitorName);
			
			//删除结构的关联测点
			
			DeleteStructPointInfo(strMonitorName);
			
			//删除通道和传感器的关联测点
			
			DeleteChnSeneorPointInfo(strMonitorName);
			
			return true;
			
		}else {
			
			return false;
		}
	}
	
	/**
	 * 根据监测项目名称删除对应的测点
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
	 * 根据监测项目名称删除结构的匹配信息
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
	 * 根据监测项目名称删除通道和传感器的匹配信息
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
	 * 根据监测项目名称验证通道和传感器的匹配信息是否存在
	 * @param strMonitorName
	 * @return true:存在匹配信息  false:不存在匹配信息
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
