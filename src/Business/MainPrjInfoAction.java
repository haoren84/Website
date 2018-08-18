package Business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.MainProjectInfo;
import SqlHelper.MySqlHelper;
import TstDataTable.DataRow;
import TstDataTable.DataTable;

/**
 * 主库的工程信息处理
 * 
 * @author Administrator
 *
 */
public class MainPrjInfoAction {

	String strMainDBName = null;

	MySqlHelper mysql = null;

	public MainPrjInfoAction() {

		mysql = new MySqlHelper();

		strMainDBName = mysql.MainDBName();

	}

	/**
	 * 新增工程
	 * 
	 * @param modelPrjInfo
	 * @return
	 */
	public boolean Create(MainProjectInfo modelPrjInfo) {
		
		if(!CheckPrjName(modelPrjInfo.getPrjName())) {
			return false;
		}
		
		StringBuffer sb = new StringBuffer();

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		modelPrjInfo.setPrjDBName(String.format("t_prj_%s", sdf.format(date)));

		sb.append("insert into `project_info` ( " + "   `prjName` ," + "   `prjType` ," + "   `PrjAddress` ,"
				+ "   `prjManager` ," + "   `prjLng` ," + "   `PrjLat` ," + "   `Remark` ,"
				+ "   `PrjDBName` ) values ( ").append("'" + modelPrjInfo.getPrjName() + "',")
				.append("'" + modelPrjInfo.getPrjType() + "',").append("'" + modelPrjInfo.getPrjAddress() + "',")
				.append("'" + modelPrjInfo.getPrjManager() + "',").append("'" + modelPrjInfo.getPrjLng() + "',")
				.append("'" + modelPrjInfo.getPrjLat() + "',").append("'" + modelPrjInfo.getRemark() + "',")
				.append("'" + modelPrjInfo.getPrjDBName() + "');");

		int nRes = mysql.ExecuteQueryNoneDB(strMainDBName, sb.toString());

		if (nRes == 1) {
			CreatePrjDataBase(modelPrjInfo.getPrjDBName());
		}

		return nRes == 1;
	}

	/**
	 * 创建对应的工程库
	 * 
	 * @param strPrjDBName
	 * @return
	 */
	private boolean CreatePrjDataBase(String strPrjDBName) {

		StringBuffer sb = new StringBuffer();

		// 创建数据库

		sb.append("create database `" + strPrjDBName + "`;");

		mysql.ExecuteQueryNoneDB(strMainDBName, sb.toString());

		// 创建表

		// dictionary

		sb = new StringBuffer();

		sb.append("CREATE TABLE `dictionary` (" + "  `AutoID` int(4) NOT NULL AUTO_INCREMENT,"
				+ "  `Name` varchar(30) DEFAULT ''," + "  `value` varchar(30) DEFAULT '',"
				+ "  `isUsed` int(4) DEFAULT '0'," + "  `DictionaryParent` varchar(30) DEFAULT '',"
				+ "  PRIMARY KEY (`AutoID`)" + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;" + "" + "");
		mysql.ExecuteQueryNoneDB(strPrjDBName, sb.toString());

		// insdata_realtime
		sb = new StringBuffer();

		sb.append("CREATE TABLE `insdata_realtime` (" + "  `AutoID` int(4) NOT NULL AUTO_INCREMENT,"
				+ "  `monitorName` varchar(30) DEFAULT NULL," + "  `monitorPtName` varchar(30) DEFAULT NULL,"
				+ "  `insFactoryID` varchar(30) DEFAULT NULL," + "  `chnID` int(4) DEFAULT '0',"
				+ "  `SensorID` varchar(30) DEFAULT NULL," + "  `SensorValueName` varchar(30) DEFAULT NULL,"
				+ "  `SensorEUValue` float DEFAULT NULL," + "  `SampleTime` datetime NOT NULL,"
				+ "  `Batch` int(4) NOT NULL," + "  `monitorPointZeroAutoID` int(4) DEFAULT NULL,"
				+ "  PRIMARY KEY (`AutoID`)" + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		mysql.ExecuteQueryNoneDB(strPrjDBName, sb.toString());

		// instrument_acq  遗弃该表
		/*sb = new StringBuffer();

		sb.append("CREATE TABLE `instrument_acq` (" + "  `AutoID` int(4) NOT NULL AUTO_INCREMENT,"
				+ "  `insFactoryID` varchar(30) DEFAULT ''," + "  `insNetID` varchar(20) DEFAULT '',"
				+ "  `serverAddr` varchar(50) DEFAULT ''," + "  `serverPort` int(4) DEFAULT '0',"
				+ "  PRIMARY KEY (`AutoID`)" + ") ENGINE=InnoDB  DEFAULT CHARSET=utf8;");
		mysql.ExecuteQueryNoneDB(strPrjDBName, sb.toString());*/

		// instrument_sensor
		sb = new StringBuffer();

		/*sb.append("CREATE TABLE `instrument_sensor` (" + "  `AutoID` int(4) NOT NULL AUTO_INCREMENT,"
				+ "  `SensorID` varchar(30) DEFAULT ''," + "  `SensorMeasureType` varchar(30) DEFAULT '',"
				+ "  `SensorSpec` varchar(30) DEFAULT ''," + "  `SensorFactory` varchar(30) DEFAULT '',"
				+ "  `Param1` float DEFAULT '0'," + "  `Param2` float DEFAULT '0'," + "  `Param3` float DEFAULT '0',"
				+ "  `Param4` float DEFAULT '0'," + "  `Param5` float DEFAULT '0'," + "  `Param6` float DEFAULT '0',"
				+ "  `Param7` float DEFAULT '0'," + "  `Param8` float DEFAULT '0'," + "  PRIMARY KEY (`AutoID`)"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");*/
		
		sb.append("CREATE TABLE `instrument_sensor` ( " + 
				"  `AutoID` int(4) NOT NULL AUTO_INCREMENT," + 
				"  `SensorID` varchar(30) DEFAULT ''," + 
				"  `SensorMeasureType` varchar(30) DEFAULT ''," + 
				"  `SensorSpec` varchar(30) DEFAULT ''," + 
				"  `SensorFactory` varchar(30) DEFAULT ''," + 
				"	`SensorName` varchar(150) DEFAULT ''," + 
				"	`SensorDesc` varchar(255) DEFAULT ''," + 
				"  `Param1` float DEFAULT '0'," + 
				"  `Param2` float DEFAULT '0'," + 
				"  `Param3` float DEFAULT '0'," + 
				"  `Param4` float DEFAULT '0'," + 
				"  `Param5` float DEFAULT '0'," + 
				"  `Param6` float DEFAULT '0'," + 
				"  `Param7` float DEFAULT '0'," + 
				"  `Param8` float DEFAULT '0'," + 
				"  PRIMARY KEY (`AutoID`)" + 
				") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		
		mysql.ExecuteQueryNoneDB(strPrjDBName, sb.toString());

		// instrument_sensor_data
		sb = new StringBuffer();

		sb.append("CREATE TABLE `instrument_sensor_data` (" + "  `AutoID` int(4) NOT NULL AUTO_INCREMENT,"
				+ "  `SensorID` varchar(30) DEFAULT NULL," + "  `ValueName` varchar(30) DEFAULT NULL,"
				+ "  `ValueUnit` varchar(30) DEFAULT NULL," + "  PRIMARY KEY (`AutoID`)"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		mysql.ExecuteQueryNoneDB(strPrjDBName, sb.toString());

		// instrument_terminal
		sb = new StringBuffer();

		/*sb.append("CREATE TABLE `instrument_terminal` (" + "  `AutoID` int(4) NOT NULL AUTO_INCREMENT,"
				+ "  `insFactoryID` varchar(30) DEFAULT ''," + "  `insID` int(4) DEFAULT '0',"
				+ "  `insType` int(4) DEFAULT '0'," + "  `insChnCount` int(4) DEFAULT '0'," + "  PRIMARY KEY (`AutoID`)"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");*/
		
		sb.append("CREATE TABLE `instrument_terminal` (" + 
				"  `AutoID` int(4) NOT NULL AUTO_INCREMENT," + 
				"  `insFactoryID` varchar(30) DEFAULT ''," + 
				"  `insID` int(4) DEFAULT '0'," + 
				"  `insType` int(4) DEFAULT '0'," + 
				"  `insChnCount` int(4) DEFAULT '0'," + 
				"  `terminalName` varchar(150) DEFAULT ''," + 
				"  `terminalType` varchar(30) DEFAULT ''," + 
				"  `terminalDesc` varchar(255) DEFAULT ''," + 
				"  `terminalRemark` varchar(255) DEFAULT ''," + 
				"  `netID` varchar(30) DEFAULT ''," + 
				"  PRIMARY KEY (`AutoID`)" + 
				") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		
		mysql.ExecuteQueryNoneDB(strPrjDBName, sb.toString());

		// instrument_terminal_chn
		sb = new StringBuffer();

		sb.append("CREATE TABLE `instrument_terminal_chn` (" + "  `AutoID` int(4) NOT NULL AUTO_INCREMENT,"
				+ "  `insFactoryID` varchar(30) DEFAULT ''," + "  `chnID` int(4) DEFAULT '0',"
				+ "  PRIMARY KEY (`AutoID`)" + ") ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8;");
		mysql.ExecuteQueryNoneDB(strPrjDBName, sb.toString());

		// monitor
		sb = new StringBuffer();

		sb.append("CREATE TABLE `monitor` (" + "  `AutoID` int(4) NOT NULL AUTO_INCREMENT,"
				+ "  `monitorName` varchar(30) DEFAULT NULL," + "  `monitorMethord` varchar(50) DEFAULT NULL,"
				+ "  `monitorPointCount` int(11) DEFAULT NULL," + "  `monitorPointPrefix` varchar(10) DEFAULT NULL,"
				+ "  `monitorPointStartID` int(11) DEFAULT NULL," + "  `monitorPointEndID` int(11) DEFAULT NULL,"
				+ "  `State` int(11) DEFAULT NULL," + "  PRIMARY KEY (`AutoID`)"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		mysql.ExecuteQueryNoneDB(strPrjDBName, sb.toString());

		// monitor_point
		sb = new StringBuffer();

		sb.append("CREATE TABLE `monitor_point` (" + "  `AutoID` int(4) NOT NULL AUTO_INCREMENT,"
				+ "  `monitorName` varchar(30) DEFAULT NULL," + "  `monitorPtName` varchar(30) DEFAULT NULL,"
				+ "  PRIMARY KEY (`AutoID`)" + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		mysql.ExecuteQueryNoneDB(strPrjDBName, sb.toString());

		// point_chn_sensor_relation
		sb = new StringBuffer();

		sb.append("CREATE TABLE `point_chn_sensor_relation` (" + "  `AutoID` int(4) NOT NULL AUTO_INCREMENT,"
				+ "  `monitorName` varchar(30) DEFAULT NULL," + "  `monitorPtName` varchar(30) DEFAULT NULL,"
				+ "  `insFactoryID` varchar(30) DEFAULT NULL," + "  `chnID` int(4) DEFAULT '0',"
				+ "  `SensorID` varchar(30) DEFAULT NULL," + "  PRIMARY KEY (`AutoID`)"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		mysql.ExecuteQueryNoneDB(strPrjDBName, sb.toString());

		// struct
		sb = new StringBuffer();

		sb.append("CREATE TABLE `struct` (" + "  `AutoID` int(4) NOT NULL AUTO_INCREMENT,"
				+ "  `structName` varchar(30) DEFAULT NULL," + "  `structCode` varchar(30) DEFAULT NULL,"
				+ "  `structType` varchar(30) DEFAULT NULL," + "  `structParent` varchar(30) DEFAULT NULL,"
				+ "  `structRemark` varchar(200) DEFAULT NULL," + "  `structAddress` varchar(50) DEFAULT NULL,"
				+ "  PRIMARY KEY (`AutoID`)" + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		mysql.ExecuteQueryNoneDB(strPrjDBName, sb.toString());

		// struct_mpoint_map
		sb = new StringBuffer();

		sb.append("CREATE TABLE `struct_mpoint_map` (" + "  `AutoID` int(4) NOT NULL AUTO_INCREMENT,"
				+ "  `structID` int(4) DEFAULT NULL," + "  `structName` varchar(30) DEFAULT NULL,"
				+ "  `monitorID` int(4) DEFAULT NULL," + "  `monitorName` varchar(30) DEFAULT NULL,"
				+ "  `mpointID` int(4) DEFAULT NULL," + "  `mpointName` varchar(30) DEFAULT NULL,"
				+ "  PRIMARY KEY (`AutoID`)" + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		mysql.ExecuteQueryNoneDB(strPrjDBName, sb.toString());

		return false;
	}

	/**
	 * 修改工程，根据的是对象的autoid
	 * 
	 * @param modelPrjInfo
	 * @return
	 */
	public boolean Update(MainProjectInfo modelPrjInfo) {

		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE `project_info` " + "SET " + "`prjName`='" + modelPrjInfo.getPrjName() + "'," + "`prjType`='"
				+ modelPrjInfo.getPrjType() + "'," + "`PrjAddress`='" + modelPrjInfo.getPrjAddress() + "',"
				+ "`prjManager`='" + modelPrjInfo.getPrjManager() + "'," + "`prjLng`='" + modelPrjInfo.getPrjLng()
				+ "'," + "`PrjLat`='" + modelPrjInfo.getPrjLat() + "'," + "`Remark`='" + modelPrjInfo.getRemark() + "'"
				+ "WHERE (`AutoID`='" + modelPrjInfo.getAutoID() + "');");

		int nRes = mysql.ExecuteQueryNoneDB(strMainDBName, sb.toString());

		return nRes == 1;
	}

	/**
	 * 删除工程，根据autoid
	 * 
	 * @param nAutoID
	 * @return
	 */
	public boolean Delete(int nAutoID) {

		return false;
	}
	
	/**
	 * 检验工程名称是否重复
	 * @param strPrjName
	 * @return true:不重复  false:重复
	 */
	public boolean CheckPrjName(String strPrjName) {
		
		MainProjectInfo info=RetrievePrjInfoByName(strPrjName);
		
		if(info==null||info.getAutoID()==0)
			return true;
		
		return false;
	}

	/**
	 * 查找，返回全部的工程信息
	 * 
	 * @return
	 */
	public List<MainProjectInfo> Retrieve() {

		List<MainProjectInfo> list = new ArrayList<>();

		StringBuffer sb = new StringBuffer();

		sb.append("select * from `project_info`;");

		DataTable dt = mysql.ExecuteQueryDB(strMainDBName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {
					MainProjectInfo model = new MainProjectInfo();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setPrjName(dr.getStringColumn("prjName"));
					model.setPrjType(dr.getIntColumn("prjType"));
					model.setPrjAddress(dr.getStringColumn("PrjAddress"));
					model.setPrjManager(dr.getStringColumn("prjManager"));
					model.setPrjLng(dr.getFloatColumn("prjLng"));
					model.setPrjLat(dr.getFloatColumn("prjLat"));
					model.setRemark(dr.getStringColumn("remark"));
					model.setPrjDBName(dr.getStringColumn("prjDBName"));
					list.add(model);
				}
			}

		}

		return list;
	}

	/**
	 * 根据ID获取工程信息
	 * 
	 * @param nAutoID
	 * @return
	 */
	public MainProjectInfo RetrievePrjInfoByID(int nAutoID) {

		StringBuffer sb = new StringBuffer();

		sb.append("select * from `project_info` where autoid='").append(nAutoID).append("';");

		DataTable dt = mysql.ExecuteQueryDB(strMainDBName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {
					MainProjectInfo model = new MainProjectInfo();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setPrjName(dr.getStringColumn("prjName"));
					model.setPrjType(dr.getIntColumn("prjType"));
					model.setPrjAddress(dr.getStringColumn("PrjAddress"));
					model.setPrjManager(dr.getStringColumn("prjManager"));
					model.setPrjLng(dr.getFloatColumn("prjLng"));
					model.setPrjLat(dr.getFloatColumn("prjLat"));
					model.setRemark(dr.getStringColumn("remark"));
					model.setPrjDBName(dr.getStringColumn("prjDBName"));
					return model;
				}
			}

		}

		return null;
	}

	/**
	 * 根据名称获取工程信息
	 * 
	 * @param strPrjName
	 * @return
	 */
	public MainProjectInfo RetrievePrjInfoByName(String strPrjName) {
		StringBuffer sb = new StringBuffer();

		sb.append("select * from `project_info` where prjName='").append(strPrjName).append("';");

		DataTable dt = mysql.ExecuteQueryDB(strMainDBName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {
					MainProjectInfo model = new MainProjectInfo();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setPrjName(dr.getStringColumn("prjName"));
					model.setPrjType(dr.getIntColumn("prjType"));
					model.setPrjAddress(dr.getStringColumn("PrjAddress"));
					model.setPrjManager(dr.getStringColumn("prjManager"));
					model.setPrjLng(dr.getFloatColumn("prjLng"));
					model.setPrjLat(dr.getFloatColumn("prjLat"));
					model.setRemark(dr.getStringColumn("remark"));
					model.setPrjDBName(dr.getStringColumn("prjDBName"));
					return model;
				}
			}

		}

		return null;
	}
	
	

}
