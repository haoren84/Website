package Model;

import java.util.Date;

/**
 * ʵʱ����
 * @author Administrator
 *
 */
public class Insdata_Realtime {
	/**
	 * ����ID
	 */
	int AutoID;
	/**
	 * �����Ŀ����
	 */
	String monitorName;
	/**
	 * ���
	 */
	String monitorPtName;
	/**
	 * �������
	 */
	String insFactoryID;
	/**
	 * ͨ��ID
	 */
	int chnID;
	/**
	 * ���������� ID
	 */
	String SensorID;
	/**
	 * ��������������
	 */
	String SensorValueName;
	/**
	 * ������ֵ
	 */
	float SensorEUValue;
	/**
	 * ����ʱ��
	 */
	Date SampleTime;
	/**
	 * ��������
	 */
	int Batch;
	/**
	 * ���ֵ����
	 */
	int monitorPointZeroAutoID;
	
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
	public String getSensorValueName() {
		return SensorValueName;
	}
	public void setSensorValueName(String sensorValueName) {
		SensorValueName = sensorValueName;
	}
	public float getSensorEUValue() {
		return SensorEUValue;
	}
	public void setSensorEUValue(float sensorEUValue) {
		SensorEUValue = sensorEUValue;
	}
	public Date getSampleTime() {
		return SampleTime;
	}
	public void setSampleTime(Date sampleTime) {
		SampleTime = sampleTime;
	}
	public int getBatch() {
		return Batch;
	}
	public void setBatch(int batch) {
		Batch = batch;
	}
	public int getMonitorPointZeroAutoID() {
		return monitorPointZeroAutoID;
	}
	public void setMonitorPointZeroAutoID(int monitorPointZeroAutoID) {
		this.monitorPointZeroAutoID = monitorPointZeroAutoID;
	}
}
