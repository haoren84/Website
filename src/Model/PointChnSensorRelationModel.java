package Model;

/**
 * 
 * @author Administrator
 *
 */
public class PointChnSensorRelationModel {

	/**
	 * ����ID
	 */
	int AutoID;
	/**
	 * �����Ŀ����
	 */
	String monitorName;
	/**
	 * �������
	 */
	String monitorPtName;
	/**
	 * �����������
	 */
	String insFactoryID;
	/**
	 * ͨ��ID
	 */
	int chnID;
	/**
	 * ����������
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
