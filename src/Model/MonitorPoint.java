package Model;

/**
 * �����Ŀ->���
 * @author Administrator
 *
 */
public class MonitorPoint {

	/**
	 * ����ID
	 */
	int AutoID = -1;
	/**
	 * �����Ŀ
	 */
	String monitorName = "";
	/**
	 * �������
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
