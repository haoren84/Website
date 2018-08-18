package Model;

/**
 * 监测项目对象
 * @author Administrator
 *
 */
public class Monitor {

	/**
	 * 主键ID
	 */
	int AutoID = -1;
	/**
	 * 监测项目名称，唯一
	 */
	String monitorName = "";
	/**
	 * 监测方法
	 */
	String  monitorMethord = "";
	/**
	 * 测点数
	 */
	int monitorPointCount = 0;
	/**
	 * 测点前缀
	 */
	String monitorPointPrefix = "";
	/**
	 * 测点起始编号
	 */
	int monitorPointStartID = 0;
	/**
	 * 测点结束编号
	 */
	int monitorPointEndID = 0;
	/**
	 * 状态；0-作废；1-正常
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
