package Model;

/**
 * �����Ŀ����
 * @author Administrator
 *
 */
public class Monitor {

	/**
	 * ����ID
	 */
	int AutoID = -1;
	/**
	 * �����Ŀ���ƣ�Ψһ
	 */
	String monitorName = "";
	/**
	 * ��ⷽ��
	 */
	String  monitorMethord = "";
	/**
	 * �����
	 */
	int monitorPointCount = 0;
	/**
	 * ���ǰ׺
	 */
	String monitorPointPrefix = "";
	/**
	 * �����ʼ���
	 */
	int monitorPointStartID = 0;
	/**
	 * ���������
	 */
	int monitorPointEndID = 0;
	/**
	 * ״̬��0-���ϣ�1-����
	 */
	int State = 0;
	
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
	public String getMonitorMethord() {
		return monitorMethord;
	}
	public void setMonitorMethord(String monitorMethord) {
		this.monitorMethord = monitorMethord;
	}
	public int getMonitorPointCount() {
		return monitorPointCount;
	}
	public void setMonitorPointCount(int monitorPointCount) {
		this.monitorPointCount = monitorPointCount;
	}
	public String getMonitorPointPrefix() {
		return monitorPointPrefix;
	}
	public void setMonitorPointPrefix(String monitorPointPrefix) {
		this.monitorPointPrefix = monitorPointPrefix;
	}
	public int getMonitorPointStartID() {
		return monitorPointStartID;
	}
	public void setMonitorPointStartID(int monitorPointStartID) {
		this.monitorPointStartID = monitorPointStartID;
	}
	public int getMonitorPointEndID() {
		return monitorPointEndID;
	}
	public void setMonitorPointEndID(int monitorPointEndID) {
		this.monitorPointEndID = monitorPointEndID;
	}
	public int getState() {
		return State;
	}
	public void setState(int state) {
		State = state;
	}
	
	
}
