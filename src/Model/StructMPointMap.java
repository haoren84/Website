package Model;

/**
 * �ṹ�Ͳ���ƥ����Ϣ
 * @author Administrator
 *
 */
public class StructMPointMap {
	/**
	 * ����ID
	 */
	int AutoID;
	/**
	 * �ṹID
	 */
	int structID;
	/**
	 * �ṹ����
	 */
	String structName;
	/**
	 * �����ĿID
	 */
	int monitorID;
	/**
	 * �����Ŀ����
	 */
	String monitorName;
	/**
	 * ���ID
	 */
	int mpointID;
	/**
	 * �������
	 */
	String mpointName;
	
	public int getAutoID() {
		return AutoID;
	}
	public void setAutoID(int autoID) {
		AutoID = autoID;
	}
	public int getStructID() {
		return structID;
	}
	public void setStructID(int structID) {
		this.structID = structID;
	}
	public String getStructName() {
		return structName;
	}
	public void setStructName(String structName) {
		this.structName = structName;
	}
	public int getMonitorID() {
		return monitorID;
	}
	public void setMonitorID(int monitorID) {
		this.monitorID = monitorID;
	}
	public String getMonitorName() {
		return monitorName;
	}
	public void setMonitorName(String monitorName) {
		this.monitorName = monitorName;
	}
	public int getMpointID() {
		return mpointID;
	}
	public void setMpointID(int mpointID) {
		this.mpointID = mpointID;
	}
	public String getMpointName() {
		return mpointName;
	}
	public void setMpointName(String mpointName) {
		this.mpointName = mpointName;
	}
}
