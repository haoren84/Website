package Model;

import java.util.Date;

/**
 * 实时数据
 * @author Administrator
 *
 */
public class Insdata_Realtime {
	/**
	 * 主键ID
	 */
	int AutoID;
	/**
	 * 监测项目名称
	 */
	String monitorName;
	/**
	 * 测点
	 */
	String monitorPtName;
	/**
	 * 出厂编号
	 */
	String insFactoryID;
	/**
	 * 通道ID
	 */
	int chnID;
	/**
	 * 传感器名称 ID
	 */
	String SensorID;
	/**
	 * 传感器数据名称
	 */
	String SensorValueName;
	/**
	 * 物理量值
	 */
	float SensorEUValue;
	/**
	 * 采样时间
	 */
	Date SampleTime;
	/**
	 * 采样批次
	 */
	int Batch;
	/**
	 * 零点值索引
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
