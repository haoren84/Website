package PropertyInfo;

import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件信息
 * @author Administrator
 *
 */
public class ConfigInfo {

	/**
	 * 任务编号
	 */
	String CrmCode;
	
	/**
	 * 运行时使用的文件路径
	 */
	String ExeFolder;
	
	
	
	public String getCrmCode() {
		return CrmCode;
	}

	public void setCrmCode(String crmCode) {
		CrmCode = crmCode;
	}
	
	public String getExeFolder() {
		return ExeFolder;
	}
	
	public void setExeFolder(String exeFolder) {
		ExeFolder=exeFolder;
	}


	
	public ConfigInfo() {
		// 使用properties加载属性文件
		Properties prop = new Properties();
		try {
			InputStream is=ConfigInfo.class.getClassLoader().getResourceAsStream("PropertyInfo/config.properties");
			
			prop.load(is);
			
			setCrmCode(prop.getProperty("crmcode"));
			
			setExeFolder(prop.getProperty("exefolder"));
			
		}
		catch(Exception e) {
			
			e.printStackTrace();
			
		}
	}
	
}
