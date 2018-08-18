package PropertyInfo;

import java.io.InputStream;
import java.util.Properties;

/**
 * �����ļ���Ϣ
 * @author Administrator
 *
 */
public class ConfigInfo {

	/**
	 * ������
	 */
	String CrmCode;
	
	/**
	 * ����ʱʹ�õ��ļ�·��
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
		// ʹ��properties���������ļ�
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
