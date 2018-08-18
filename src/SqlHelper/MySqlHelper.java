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
	 * ��־����
	 */
	static Logger logger = LogManager.getLogger(MySqlHelper.class.getName());

	/**
	 * ��ȡ�����ݿ������
	 * 
	 * @return
	 */
	public String MainDBName() {

		// ʹ��properties���������ļ�
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
	 * ���ݹ������ƻ�ȡ��Ӧ�����ݿ�����
	 * 
	 * @param strPrjName
	 *            ��������
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
	 * ��Թ�������ִ��sql��䣬����DataTable
	 * 
	 * @param strPrjName
	 *            ��������
	 * @param strSql
	 *            sql���
	 * @return
	 */
	public DataTable ExecuteQueryPrj(String strPrjName, String strSql) {
		// ���������ҵ����̶�Ӧ�����ݿ�����

		String strDBName = PrjDBName(strPrjName);

		DataTable dt = ExecuteQueryDB(strDBName, strSql);

		return dt;
	}

	/**
	 * ������ݿ�����ִ��sql��䣬����DataTable
	 * 
	 * @param strDBName
	 *            ���ݿ�����
	 * @param strSql
	 *            sql���
	 * @return
	 */
	public DataTable ExecuteQueryDB(String strDBName, String strSql) {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DataTable dt = null;

		JdbcUtil JdbcUtilObj = new JdbcUtil(strDBName);

		try {

			// ͨ��JdbcUtil��ȡ���ݿ�����
			conn = JdbcUtilObj.getConnection();
			stat = conn.createStatement();
			rs = stat.executeQuery(strSql);
			/*
			 * while(rs.next()){ System.out.println(rs.getString("name")); }
			 */

			// ������ת�Ƶ�DataTable��
			ResultSetMetaData rsmd = rs.getMetaData();

			List<DataRow> row = new ArrayList<DataRow>();// �������м���
			List<DataColumn> col = null;// �������м���
			DataRow r = null; // ����һ��
			DataColumn c = null;// ����һ��

			while (rs.next()) {

				// ��ʼ���м��ϣ�

				// ��ʼ���м���
				col = new ArrayList<DataColumn>();
				// �˴���ʼ��ѭ����ÿ����һ�ж������һ��
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					String columnName = rsmd.getColumnName(i);
					Object value = rs.getObject(columnName);
					// ��ʼ����Ԫ��
					c = new DataColumn(columnName, value);
					// ������Ϣ�����м���
					col.add(c);
				}
				// ��ʼ����Ԫ��
				r = new DataRow(col);
				// ������Ϣ�����н��
				row.add(r);

			}

			// �õ����ݱ�
			dt = new DataTable(row);

		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		} finally {
			// ͨ��JdbcUtil�ر���Դ
			JdbcUtilObj.close(conn, stat, rs);
		}
		return dt;

	}

	/**
	 * ��Թ�������ִ��sql��䣬����Ӱ�����������
	 * 
	 * @param strPrjName
	 *            ��������
	 * @param strSql
	 *            sql���
	 * @return
	 */
	public int ExecuteQueryNonePrj(String strPrjName, String strSql) {

		String strDBName = PrjDBName(strPrjName);

		int nRes = ExecuteQueryNoneDB(strDBName, strSql);

		return nRes;
	}

	/**
	 * ������ݿ�����ִ��sql��䣬����Ӱ�����������
	 * 
	 * @param strDBName
	 *            ���ݿ�����
	 * @param strSql
	 *            sql���
	 * @return
	 */
	public int ExecuteQueryNoneDB(String strDBName, String strSql) {

		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;

		JdbcUtil JdbcUtilObj = new JdbcUtil(strDBName);
		int nRes = 0;

		try {
			// ͨ��JdbcUtil��ȡ���ݿ�����
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
			// ͨ��JdbcUtil�ر���Դ
			JdbcUtilObj.close(conn, stat, rs);
		}

		return nRes;
	}
	
	
	
	// ������װ:��sql����е���������ת��
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

	// ������װ:��sql����еĸ���������ת��
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
	
	// ������װ:��sql����е��ַ�����ӡ���;
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
	
	// ������װ:��sql����е��ַ�����ӡ���;
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
	
	// ������װ:��sql����еĿ��Ԫ�����``,���������ַ�
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
