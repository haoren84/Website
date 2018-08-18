package Model;

/**
 * 监测项目->测点
 * @author Administrator
 *
 */
public class MonitorPoint {

	/**
	 * 主键ID
	 */
	int AutoID = -1;
	/**
	 * 监测项目
	 */
	String monitorName = "";
	/**
	 * 测点名称
	 */
	String monitorPtName = "";
	public int getAutoID() {
		return AutoID;
	}
	public void setAutoID(int autoID) {
		AutoID = autoID;
	}
	public String getMonitorName() {
		return monitorName;
	}
	public void setMonitorName(String monitorName) {
		this.monitorName = monitorName;
	}
	public String getMonitorPtName() {
		return monitorPtName;
	}
	public void setMonitorPtName(String monitorPtName) {
		this.monitorPtName = monitorPtName;
	}	
}
