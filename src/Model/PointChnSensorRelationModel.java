package Model;

/**
 * 
 * @author Administrator
 *
 */
public class PointChnSensorRelationModel {

	/**
	 * 主键ID
	 */
	int AutoID;
	/**
	 * 监测项目名称
	 */
	String monitorName;
	/**
	 * 测点名称
	 */
	String monitorPtName;
	/**
	 * 仪器出厂编号
	 */
	String insFactoryID;
	/**
	 * 通道ID
	 */
	int chnID;
	/**
	 * 传感器名称
	 */
	String SensorID;
	
	
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
	public String getInsFactoryID() {
		return insFactoryID;
	}
	public void setInsFactoryID(String insFactoryID) {
		this.insFactoryID = insFactoryID;
	}
	public int getChnID() {
		return chnID;
	}
	public void setChnID(int chnID) {
		this.chnID = chnID;
	}
	public String getSensorID() {
		return SensorID;
	}
	public void setSensorID(String sensorID) {
		SensorID = sensorID;
	}
	
	
	
	
}
