package Model;

/**
 * 结构和测点的匹配信息
 * @author Administrator
 *
 */
public class StructMPointMap {
	/**
	 * 主键ID
	 */
	int AutoID;
	/**
	 * 结构ID
	 */
	int structID;
	/**
	 * 结构名称
	 */
	String structName;
	/**
	 * 监测项目ID
	 */
	int monitorID;
	/**
	 * 监测项目名称
	 */
	String monitorName;
	/**
	 * 测点ID
	 */
	int mpointID;
	/**
	 * 测点名称
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
