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
		// 使用properties加载属性文件
		Properties prop = new Properties();
		
		try {
			InputStream is = JdbcUtil.class.getClassLoader().getResourceAsStream("SqlHelper/jdbc.properties");
			prop.load(is);
			// 注册驱动（获取属性文件中的数据）
			String driverClassName = prop.getProperty("driverClassName");
			Class.forName(driverClassName);
			// 获取属性文件中的username,password
			url = prop.getProperty("url");  
			user = prop.getProperty("user");
			password = prop.getProperty("password");
			
			//拼接链接语句
			
			StringBuilder sb=new StringBuilder();
			sb.append(url);
			sb.append(DBName);
			sb.append("?useUnicode=true&characterEncoding=utf-8&useSSL=false");
			
			url=sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取数据库连接
	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 释放资源
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
