package Business;

import java.util.ArrayList;
import java.util.List;

import Model.Monitor;
import Model.MonitorPoint;
import Model.Struct;
import Model.StructMPointMap;
import SqlHelper.MySqlHelper;
import TstDataTable.DataRow;
import TstDataTable.DataTable;

/**
 * 工程结构的操作
 * 
 * @author Administrator
 *
 */
public class PrjStructAction {

	String strPrjName = null;

	MySqlHelper mysql = null;

	public PrjStructAction(String strPrjName) {

		mysql = new MySqlHelper();

		this.strPrjName = strPrjName;

	}

	/**
	 * 新建工程结构
	 * 
	 * @param model
	 *            工程结构信息
	 * @return
	 */
	public boolean CreatePrjSruct(Struct model) {

		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO `struct`" + "(`structName`, `structCode`,"
				+ "`structType`, `structParent`, `structRemark`, `structAddress`)" + " VALUES ('"
				+ model.getStructName() + "'," + "'" + model.getStructCode() + "'," + "'" + model.getStructType() + "',"
				+ "'" + model.getStructParent() + "'," + "'" + model.getStructRemark() + "'," + "'"
				+ model.getStructAddress() + "');");

		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());

		if (nRes <= 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 根据ID找到对应的结构
	 * 
	 * @param nAutoID
	 * @return
	 */
	public Struct GetStructModel(int nAutoID) {

		StringBuffer sb = new StringBuffer();

		sb.append("select * from `struct` where AutoID='").append(nAutoID).append("';");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {

					Struct model = new Struct();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setStructAddress(dr.getStringColumn("structAddress"));
					model.setStructCode(dr.getStringColumn("structCode"));
					model.setStructName(dr.getStringColumn("structName"));
					model.setStructParent(dr.getStringColumn("structParent"));
					model.setStructRemark(dr.getStringColumn("structRemark"));
					model.setStructType(dr.getStringColumn("structType"));
					return model;
				}
			}

		}
		return null;
	}

	/**
	 * 通过结构名称获取对应的ID
	 * 
	 * @param strStrcuctName
	 *            结构名称
	 * @return
	 */
	public int GetAutoIDByStructName(String strStrcuctName) {

		StringBuffer sb = new StringBuffer();

		sb.append("select * from `struct` where structName='").append(strStrcuctName).append("';");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		int nRes = 0;

		if (dt == null || dt.getRow() == null || dt.getRow().size() <= 0) {
			nRes = 0;
		} else {
			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {
					nRes = dr.getIntColumn("autoid");
				}
			}
		}

		return nRes;
	}

	/**
	 * 新增结构对应的测点信息
	 * 
	 * @param listMap
	 * @return
	 */
	public boolean AddStructMapMPoints(List<StructMPointMap> listMap) {

		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO `struct_mpoint_map`"
				+ "(`structID`, `structName`, `monitorID`, `monitorName`, `mpointID`, `mpointName`)" + "VALUES");

		for (StructMPointMap item : listMap) {
			sb.append("('" + item.getStructID() + "','" + item.getStructName() + "','" + item.getMonitorID() + "','"
					+ item.getMonitorName() + "','" + item.getMpointID() + "','" + item.getMpointName() + "'),");
		}

		sb.deleteCharAt(sb.length() - 1);

		sb.append(";");

		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());

		if (nRes <= 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 获取分页数据的父节点为零的结构总个数
	 * 
	 * @return
	 */
	public int GetStructPageDataCount() {

		StringBuffer sb = new StringBuffer();

		sb.append("select count(*) from `struct` where structParent='0';");

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
	 * 获取父节点为零的结构分页数据
	 * 
	 * @param page
	 *            页数
	 * @param rows
	 *            每页个数
	 * @return
	 */
	public List<Struct> GetStructPageData(int page, int rows) {

		List<Struct> listStruct = new ArrayList<>();

		int nComputcout = (page - 1) * rows;

		StringBuffer sb = new StringBuffer();

		sb.append("select * from `struct` where `structParent`='0' limit ").append(nComputcout).append(",").append(rows)
				.append(";");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {

					Struct model = new Struct();

					model.setAutoID(dr.getIntColumn("autoid"));
					model.setStructAddress(dr.getStringColumn("structAddress"));
					model.setStructCode(dr.getStringColumn("structCode"));
					model.setStructName(dr.getStringColumn("structName"));
					model.setStructParent(dr.getStringColumn("structParent"));
					model.setStructRemark(dr.getStringColumn("structRemark"));
					model.setStructType(dr.getStringColumn("structType"));
					listStruct.add(model);
				}
			}

		}

		return listStruct;
	}

	/**
	 * 根据父节点找到所有的子结构
	 * 
	 * @param strStructParent
	 * @return
	 */
	public List<Struct> SelectStructByParent(String strStructParent) {

		List<Struct> listStruct = new ArrayList<>();

		StringBuffer sb = new StringBuffer();

		sb.append("select * from `struct` where structParent = '" + strStructParent + "';");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {

					Struct model = new Struct();

					model.setAutoID(dr.getIntColumn("autoid"));
					model.setStructAddress(dr.getStringColumn("structAddress"));
					model.setStructCode(dr.getStringColumn("structCode"));
					model.setStructName(dr.getStringColumn("structName"));
					model.setStructParent(dr.getStringColumn("structParent"));
					model.setStructRemark(dr.getStringColumn("structRemark"));
					model.setStructType(dr.getStringColumn("structType"));
					listStruct.add(model);
				}
			}

		}
		return listStruct;
	}

	/**
	 * 获取所有的结构的树节点
	 * 
	 * @return
	 */
	public StringBuffer GetAllStructTree() {

		// 读取所有父节点为0的结构 第一级结构

		List<Struct> listStruct = SelectStructByParent("0");

		StringBuffer sb = new StringBuffer();

		sb.append("[");

		if (listStruct != null && listStruct.size() > 0) {
			for (Struct item : listStruct) {

				StringBuffer sbItem = new StringBuffer();

				GetStructNameTree(sbItem, item.getAutoID());

				sb.append(sbItem.toString());

			}

			sb.deleteCharAt(sb.length() - 1);
		}

		sb.append("]");

		return sb;
	}

	/**
	 * 获取对应ID的树结构
	 * 
	 * @return
	 */
	public void GetStructNameTree(StringBuffer sb, int autoid) {

		if (sb == null) {
			sb = new StringBuffer();
		}

		// 当前的ID
		Struct curModel = GetStructModel(autoid);

		sb.append("{");
		sb.append(String.format("\"id\":\"%d\",", curModel.getAutoID()));
		sb.append(String.format("\"text\":\"%s\"", curModel.getStructName()));
		/*
		 * sb.append(String.format("\"structCode\":\"%s\",", curModel.getStructCode()));
		 * sb.append(String.format("\"structType\":\"%s\",", curModel.getStructType()));
		 * sb.append(String.format("\"structParent\":\"%s\",",
		 * curModel.getStructParent()));
		 * sb.append(String.format("\"structRemark\":\"%s\",",
		 * curModel.getStructRemark()));
		 * sb.append(String.format("\"structAddress\":\"%s\"",
		 * curModel.getStructAddress()));
		 */

		// 子节点

		List<Struct> listStruct = SelectStructByParent(String.valueOf(autoid));

		if (listStruct != null && listStruct.size() > 0) {

			sb.append(",\"state\":\"closed\",");
			sb.append("\"children\":[");

			for (Struct item : listStruct) {

				GetStructNameTree(sb, item.getAutoID());
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("]");
		}

		sb.append("},");

		return;
	}

	/**
	 * 获取结构所能匹配的所有测点
	 * 
	 * @return
	 */
	public String GetStructMPointTree() {

		StringBuffer sb = new StringBuffer();

		MonitorPrjAction action = new MonitorPrjAction(this.strPrjName);

		List<Monitor> listMonitor = action.GetAllMonitorData();

		if (listMonitor == null || listMonitor.size() < 0) {
			return "{}";
		}

		int nParentID = 0;
		int nChildID = 10000;
		int nDataCount = 0;

		sb.append("[");

		for (Monitor itemMonitor : listMonitor) {

			sb.append("{");
			sb.append(String.format("\"id\":\"%d\",", nParentID++));
			sb.append(String.format("\"curid\":\"%d\",", itemMonitor.getAutoID()));
			sb.append(String.format("\"type\":\"%s\",", "监测项目"));
			sb.append("\"state\":\"open\",");
			sb.append(String.format("\"name\":\"%s\"", itemMonitor.getMonitorName()));
			sb.append("},");
			nDataCount++;

			List<MonitorPoint> listMPoint = action.GetAllMonitorPointData(itemMonitor.getMonitorName());

			if (listMPoint == null || listMPoint.size() < 0) {

				continue;

			} else {

				// sb.append(",");

				// sb.append("\"state\":\"open\",");

				// sb.append("\"children\":[");

				for (MonitorPoint itemPoint : listMPoint) {

					sb.append("{");
					sb.append(String.format("\"id\":\"%d\",", nChildID++));
					sb.append(String.format("\"curid\":\"%d\",", itemPoint.getAutoID()));
					sb.append(String.format("\"curpid\":\"%d\",", itemMonitor.getAutoID()));
					sb.append(String.format("\"curpname\":\"%s\",", itemMonitor.getMonitorName()));
					sb.append(String.format("\"type\":\"%s\",", "测点"));
					sb.append(String.format("\"name\":\"%s\",", itemPoint.getMonitorPtName()));
					sb.append(String.format("\"_parentId\":\"%d\"", nParentID - 1));
					sb.append("},");
					nDataCount++;
				}

				// sb.deleteCharAt(sb.length() - 1);

				// sb.append("]");

			}
			// sb.append("},");
		}

		sb.deleteCharAt(sb.length() - 1);

		sb.append("]");

		StringBuffer sbRes = new StringBuffer();

		sbRes.append("{\"total\":\"").append(nDataCount).append("\",\"rows\":").append(sb.toString()).append("}");

		return sbRes.toString();
	}

	/**
	 * 获取结构所能匹配的所有测点(测点带选中信息)
	 * @return
	 */
	public String GetStructMPointTreeWithSelect() {
		
		StringBuffer sb = new StringBuffer();

		MonitorPrjAction action = new MonitorPrjAction(this.strPrjName);

		List<Monitor> listMonitor = action.GetAllMonitorData();

		if (listMonitor == null || listMonitor.size() < 0) {
			return "{}";
		}

		int nParentID = 0;
		int nChildID = 10000;
		int nDataCount = 0;

		sb.append("[");

		for (Monitor itemMonitor : listMonitor) {

			sb.append("{");
			sb.append(String.format("\"id\":\"%d\",", nParentID++));
			sb.append(String.format("\"curid\":\"%d\",", itemMonitor.getAutoID()));
			sb.append(String.format("\"type\":\"%s\",", "监测项目"));
			sb.append("\"state\":\"open\",");
			sb.append(String.format("\"name\":\"%s\"", itemMonitor.getMonitorName()));
			sb.append("},");
			nDataCount++;

			List<MonitorPoint> listMPoint = action.GetAllMonitorPointData(itemMonitor.getMonitorName());//当前监测项目的所有测点
			
			List<MonitorPoint>  listSelectMPoint=GetStructMPointMapByMonitorName(itemMonitor.getMonitorName());//当前结构下对应项目名称选中的测点

			if (listMPoint == null || listMPoint.size() < 0) {

				continue;

			} else {

				// sb.append(",");

				// sb.append("\"state\":\"open\",");

				// sb.append("\"children\":[");

				for (MonitorPoint itemPoint : listMPoint) {
					
					boolean bSelected=false;
					
					for(MonitorPoint itemSelect:listSelectMPoint) {
						
						if(itemPoint.getMonitorPtName().equals(itemSelect.getMonitorPtName())) {
							bSelected=true;
							break;
						}
					}

					sb.append("{");
					sb.append(String.format("\"id\":\"%d\",", nChildID++));
					sb.append(String.format("\"curid\":\"%d\",", itemPoint.getAutoID()));
					sb.append(String.format("\"curpid\":\"%d\",", itemMonitor.getAutoID()));
					sb.append(String.format("\"curpname\":\"%s\",", itemMonitor.getMonitorName()));
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

				// sb.deleteCharAt(sb.length() - 1);

				// sb.append("]");

			}
			// sb.append("},");
		}

		sb.deleteCharAt(sb.length() - 1);

		sb.append("]");

		StringBuffer sbRes = new StringBuffer();

		sbRes.append("{\"total\":\"").append(nDataCount).append("\",\"rows\":").append(sb.toString()).append("}");

		return sbRes.toString();
	}
	
	/**
	 * 根据监测项目名称获取对应的结构下的测点信息
	 * @param strMonitorName
	 * @return
	 */
	public List<MonitorPoint> GetStructMPointMapByMonitorName(String strMonitorName){
		
		List<MonitorPoint> list = new ArrayList<>();
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("select * from `struct_mpoint_map` where monitorName='" + strMonitorName + "';");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {

					MonitorPoint model=new MonitorPoint();
					model.setAutoID(dr.getIntColumn("mpointID"));
					model.setMonitorName(dr.getStringColumn("monitorName"));
					model.setMonitorPtName(dr.getStringColumn("mpointName"));
					list.add(model);
				}
			}

		}

		return list;
	}
	
	
	/**
	 * 根据结构的ID获取对应的监测项目和测点信息
	 * 
	 * @param nStructID
	 *            结构ID
	 * @return
	 */
	public List<StructMPointMap> GetStructMPointMapByStructID(int nStructID) {

		List<StructMPointMap> list = new ArrayList<>();

		StringBuffer sb = new StringBuffer();

		sb.append("select * from `struct_mpoint_map` where structID='").append(nStructID).append("';");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {

					StructMPointMap model = new StructMPointMap();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setMonitorID(dr.getIntColumn("monitorID"));
					model.setMonitorName(dr.getStringColumn("monitorName"));
					model.setMpointID(dr.getIntColumn("mpointID"));
					model.setMpointName(dr.getStringColumn("mpointName"));
					model.setStructID(dr.getIntColumn("structID"));
					model.setStructName(dr.getStringColumn("structName"));
					list.add(model);
				}
			}

		}
		return list;
	}
	
	/**
	 * 根据结构的ID获取对应的监测项目分组信息
	 * @param nStructID  结构ID
	 * @return
	 */
	public List<StructMPointMap> GetStructMonitorGroupByStructID(int nStructID){
		
		List<StructMPointMap> list = new ArrayList<>();

		StringBuffer sb = new StringBuffer();

		sb.append("select * from `struct_mpoint_map` where structID='").append(nStructID).append("' GROUP BY monitorName;");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {

					StructMPointMap model = new StructMPointMap();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setMonitorID(dr.getIntColumn("monitorID"));
					model.setMonitorName(dr.getStringColumn("monitorName"));
					model.setMpointID(dr.getIntColumn("mpointID"));
					model.setMpointName(dr.getStringColumn("mpointName"));
					model.setStructID(dr.getIntColumn("structID"));
					model.setStructName(dr.getStringColumn("structName"));
					list.add(model);
				}
			}

		}
		return list;
	}

	/**
	 * 根据结构的ID,监测项目的ID获取对应的监测项目和测点信息
	 * @param nStructID 结构ID
	 * @param nMonitorID 监测项目ID
	 * @return
	 */
	public List<StructMPointMap> GetStructMPointMapByStructIDAndMonitorID(int nStructID,int nMonitorID){
		
		List<StructMPointMap> list = new ArrayList<>();

		StringBuffer sb = new StringBuffer();

		sb.append("select * from `struct_mpoint_map` where structID='").append(nStructID).append("' and monitorID = '").append(nMonitorID).append("';");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {

					StructMPointMap model = new StructMPointMap();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setMonitorID(dr.getIntColumn("monitorID"));
					model.setMonitorName(dr.getStringColumn("monitorName"));
					model.setMpointID(dr.getIntColumn("mpointID"));
					model.setMpointName(dr.getStringColumn("mpointName"));
					model.setStructID(dr.getIntColumn("structID"));
					model.setStructName(dr.getStringColumn("structName"));
					list.add(model);
				}
			}

		}
		return list;
	}
	
	/**
	 * 修改结构信息
	 * @param model
	 * @return
	 */
	public boolean UpdatePrjStruct(Struct model) {

		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE `struct`" + 
				"SET `structCode`='"+model.getStructCode()+"'," + 
				"`structType`='"+model.getStructType()+"'," + 
				"`structParent`='"+model.getStructParent()+"'," + 
				"`structRemark`='"+model.getStructRemark()+"'," + 
				"`structAddress`='"+model.getStructAddress()+"' WHERE (`AutoID`='"+model.getAutoID()+"' and `structName`='"+model.getStructName()+"');");

		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());

		if (nRes <= 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 删除结构匹配的测点信息
	 * @param nStructID
	 * @return
	 */
	public boolean DeleteStructMapInfo(int nStructID) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("DELETE FROM `struct_mpoint_map` WHERE structID='"+nStructID+"';");
		
		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		
		if (nRes <= 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 删除结构的信息
	 * @param nStructID
	 * @return
	 */
	public boolean DeletePrjStructInfo(int nStructID){
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("DELETE FROM `struct` WHERE AutoID='"+nStructID+"';");
		
		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		
		if (nRes <= 0) {
			return false;
		} else {
			return true;
		}
	}
	
	
}
