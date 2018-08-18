package SqlHelper;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TstDataTable.DataColumn;
import TstDataTable.DataRow;
import TstDataTable.DataTable;

public class MySqlHelper {
	
	/**
	 * 日志调用
	 */
	static Logger logger = LogManager.getLogger(MySqlHelper.class.getName());

	/**
	 * 获取主数据库的名称
	 * 
	 * @return
	 */
	public String MainDBName() {

		// 使用properties加载属性文件
		Properties prop = new Properties();
		String strMainDBName = null;
		try {
			InputStream is = MySqlHelper.class.getClassLoader().getResourceAsStream("SqlHelper/jdbc.properties");
			prop.load(is);
			strMainDBName = prop.getProperty("maindbname");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}

		return strMainDBName;
	}

	/**
	 * 根据工程名称获取对应的数据库名称
	 * 
	 * @param strPrjName
	 *            工程名称
	 * @return
	 */
	 public String PrjDBName(String strPrjName) {

		String strDBName = null;

		String strMainDBName = MainDBName();

		if (strMainDBName.isEmpty()) {
			return null;
		}

		StringBuffer sb = new StringBuffer();

		sb.append("select * from `project_info` where prjName='"+strPrjName+"';");

		DataTable dt = ExecuteQueryDB(strMainDBName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			strDBName = dt.getRow().get(0).getStringColumn("PrjDBName");
		}

		return strDBName;
	}

	/**
	 * 针对工程名称执行sql语句，返回DataTable
	 * 
	 * @param strPrjName
	 *            工程名称
	 * @param strSql
	 *            sql语句
	 * @return
	 */
	public DataTable ExecuteQueryPrj(String strPrjName, String strSql) {
		// 在主库中找到工程对应的数据库名称

		String strDBName = PrjDBName(strPrjName);

		DataTable dt = ExecuteQueryDB(strDBName, strSql);

		return dt;
	}

	/**
	 * 针对数据库名称执行sql语句，返回DataTable
	 * 
	 * @param strDBName
	 *            数据库名称
	 * @param strSql
	 *            sql语句
	 * @return
	 */
	public DataTable ExecuteQueryDB(String strDBName, String strSql) {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DataTable dt = null;

		JdbcUtil JdbcUtilObj = new JdbcUtil(strDBName);

		try {

			// 通过JdbcUtil获取数据库链接
			conn = JdbcUtilObj.getConnection();
			stat = conn.createStatement();
			rs = stat.executeQuery(strSql);
			/*
			 * while(rs.next()){ System.out.println(rs.getString("name")); }
			 */

			// 将数据转移到DataTable中
			ResultSetMetaData rsmd = rs.getMetaData();

			List<DataRow> row = new ArrayList<DataRow>();// 表所有行集合
			List<DataColumn> col = null;// 行所有列集合
			DataRow r = null; // 单独一行
			DataColumn c = null;// 单独一列

			while (rs.next()) {

				// 初始化行集合，

				// 初始化列集合
				col = new ArrayList<DataColumn>();
				// 此处开始列循环，每次向一行对象插入一列
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					String columnName = rsmd.getColumnName(i);
					Object value = rs.getObject(columnName);
					// 初始化单元列
					c = new DataColumn(columnName, value);
					// 将列信息加入列集合
					col.add(c);
				}
				// 初始化单元行
				r = new DataRow(col);
				// 将行信息降入行结合
				row.add(r);

			}

			// 得到数据表
			dt = new DataTable(row);

		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		} finally {
			// 通过JdbcUtil关闭资源
			JdbcUtilObj.close(conn, stat, rs);
		}
		return dt;

	}

	/**
	 * 针对工程名称执行sql语句，返回影响的数据行数
	 * 
	 * @param strPrjName
	 *            工程名称
	 * @param strSql
	 *            sql语句
	 * @return
	 */
	public int ExecuteQueryNonePrj(String strPrjName, String strSql) {

		String strDBName = PrjDBName(strPrjName);

		int nRes = ExecuteQueryNoneDB(strDBName, strSql);

		return nRes;
	}

	/**
	 * 针对数据库名称执行sql语句，返回影响的数据行数
	 * 
	 * @param strDBName
	 *            数据库名称
	 * @param strSql
	 *            sql语句
	 * @return
	 */
	public int ExecuteQueryNoneDB(String strDBName, String strSql) {

		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;

		JdbcUtil JdbcUtilObj = new JdbcUtil(strDBName);
		int nRes = 0;

		try {
			// 通过JdbcUtil获取数据库链接
			conn = JdbcUtilObj.getConnection();
			stat = conn.createStatement();
			nRes = stat.executeUpdate(strSql);
			/*
			 * while(rs.next()){ System.out.println(rs.getString("name")); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		} finally {
			// 通过JdbcUtil关闭资源
			JdbcUtilObj.close(conn, stat, rs);
		}

		return nRes;
	}
	
	
	
	// 函数封装:对sql语句中的整数进行转换
	public static String ToSqlStr(int num,boolean isNeedBreak)
	{
		StringBuffer sb = new StringBuffer();
		if(isNeedBreak)
		{
			sb.append(Integer.toString(num)).append(",");
		}
		else
		{
			sb.append(Integer.toString(num));
		}
		return sb.toString();
	}

	// 函数封装:对sql语句中的浮点数进行转换
	public static String ToSqlStr(float num,boolean isNeedBreak)
	{
		StringBuffer sb = new StringBuffer();
		if(isNeedBreak)
		{
			sb.append(Float.toString(num)).append(",");
		}
		else
		{
			sb.append(Float.toString(num));
		}
		return sb.toString();
	}
	
	// 函数封装:对sql语句中的字符串添加‘’;
	public static String ToSqlStr(String str,boolean isNeedBreak)
	{
		StringBuffer sb = new StringBuffer();
		
		if(isNeedBreak)
		{
			sb.append("'").append(str).append("'").append(",");
		}
		else
		{
			sb.append("'").append(str).append("'");
		}
		return sb.toString();
	}
	
	// 函数封装:对sql语句中的字符串添加‘’;
		public static String ToSqlStr(Object obj,boolean isNeedBreak)
		{
			StringBuffer sb = new StringBuffer();
			
			if(isNeedBreak)
			{
				sb.append("'").append(obj.toString()).append("'").append(",");
			}
			else
			{
				sb.append("'").append(obj.toString()).append("'");
			}
			return sb.toString();
		}
	
	// 函数封装:对sql语句中的库表元素添加``,避免特殊字符
	public static String ToSqlEleme(String str,boolean isNeedBreak)
	{
		StringBuffer sb = new StringBuffer();
		if(isNeedBreak)
		{
			sb.append("`").append(str).append("`").append(",");			
		}
		else 
		{
			sb.append("`").append(str).append("`");
		}
		
		return sb.toString();
	}
}
