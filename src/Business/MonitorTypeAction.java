package Business;

import java.util.ArrayList;
import java.util.List;
import SqlHelper.MySqlHelper;
import TstDataTable.DataRow;
import TstDataTable.DataTable;

/**
 * 监测类型 数据的操作
 * @author Administrator
 *
 */
public class MonitorTypeAction {

	String strPrjName = null;
	
	String strMainDBName = null;

	MySqlHelper mysql = null;

	public MonitorTypeAction(String strPrjName) {

		mysql = new MySqlHelper();

		this.strPrjName = strPrjName;

		strMainDBName = mysql.MainDBName();
	}
	
	
	/**
	 * 获取监测类型的数据个数
	 * @return
	 */
	public int GetMonitorTypeDataCount() {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select count(*) from `monitor_type`;");
		
		DataTable dt=mysql.ExecuteQueryDB(strMainDBName, sb.toString());
		
		int nRes = 0;

		if (dt == null || dt.getRow() == null || dt.getRow().size() <= 0) {
			nRes = 0;
		} else {
			nRes = Integer.parseInt(dt.getRow().get(0).getCol().get(0).getValue().toString());
		}

		return nRes;
	}
	
	/**
	 * 获取监测类型的分页数据
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<String> GetMonitorTypePageData(int page,int rows)
	{
		int nComputcout = (page - 1) * rows;
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select * from `monitor_type` limit ").append(nComputcout).append(",").append(rows).append(";");
		
		DataTable dt=mysql.ExecuteQueryDB(strMainDBName, sb.toString());
		
		List<String> listMonitorType=new ArrayList<>();
		
		if(dt!=null&&dt.getRow()!=null&&dt.getRow().size()>0) {
			for(DataRow dr:dt.getRow()) {
				if(dr!=null&&dr.getCol().size()>0) {
					listMonitorType.add(dr.getStringColumn("monitorTypeName"));
				}
			}
			
		}
		return listMonitorType;
	}
	
	/**
	 * 获取对应监测类型的监测方法数据个数
	 * @param monitorTypeName 监测类型
	 * @return
	 */
	public int GetMonitorMethordDataCount(String monitorTypeName) {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select count(*) from `monitor_type_methord` where monitorTypeName='").append(monitorTypeName).append("';");
		
		DataTable dt=mysql.ExecuteQueryDB(strMainDBName, sb.toString());
		
		int nRes = 0;

		if (dt == null || dt.getRow() == null || dt.getRow().size() <= 0) {
			nRes = 0;
		} else {
			nRes = Integer.parseInt(dt.getRow().get(0).getCol().get(0).getValue().toString());
		}

		return nRes;
	}
	
	/**
	 * 获取对应监测类型的监测方法分页数据
	 * @param monitorTypeName 监测类型
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<String> GetMonitorMethordPageData(String monitorTypeName,int page,int rows){
		
		int nComputcout = (page - 1) * rows;
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select * from `monitor_type_methord` where monitorTypeName='").append(monitorTypeName).append("' limit ").append(nComputcout).append(",").append(rows).append(";");
		
		DataTable dt=mysql.ExecuteQueryDB(strMainDBName, sb.toString());
		
		List<String> listMonitorMethord=new ArrayList<>();
		
		if(dt!=null&&dt.getRow()!=null&&dt.getRow().size()>0) {
			for(DataRow dr:dt.getRow()) {
				if(dr!=null&&dr.getCol().size()>0) {
					listMonitorMethord.add(dr.getStringColumn("monitorTypeMethordName"));
				}
			}
		}
		return listMonitorMethord;
	}
	
	
	
}
