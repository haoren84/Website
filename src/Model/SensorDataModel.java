package Model;

/**
 * 传感器子表数据
 * @author Administrator
 *
 */
public class SensorDataModel {
	
	int AutoID;
	String SensorID;
	String ValueName;
	String ValueUnit;
	
	public int getAutoID() {
		return AutoID;
	}
	public void setAutoID(int autoID) {
		AutoID = autoID;
	}
	public String getSensorID() {
		return SensorID;
	}
	public void setSensorID(String sensorID) {
		SensorID = sensorID;
	}
	public String getValueName() {
		return ValueName;
	}
	public void setValueName(String valueName) {
		ValueName = valueName;
	}
	public String getValueUnit() {
		return ValueUnit;
	}
	public void setValueUnit(String valueUnit) {
		ValueUnit = valueUnit;
	}
	
	
	  
}
