package SqlHelper;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcUtil {

	private String url;
	private String user;
	private String password;

	public JdbcUtil(String DBName) {
		// ʹ��properties���������ļ�
		Properties prop = new Properties();
		
		try {
			InputStream is = JdbcUtil.class.getClassLoader().getResourceAsStream("SqlHelper/jdbc.properties");
			prop.load(is);
			// ע����������ȡ�����ļ��е����ݣ�
			String driverClassName = prop.getProperty("driverClassName");
			Class.forName(driverClassName);
			// ��ȡ�����ļ��е�username,password
			url = prop.getProperty("url");  
			user = prop.getProperty("user");
			password = prop.getProperty("password");
			
			//ƴ���������
			
			StringBuilder sb=new StringBuilder();
			sb.append(url);
			sb.append(DBName);
			sb.append("?useUnicode=true&characterEncoding=utf-8&useSSL=false");
			
			url=sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��ȡ���ݿ�����
	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	// �ͷ���Դ
	public void close(Connection conn, Statement stat, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stat != null) {
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
