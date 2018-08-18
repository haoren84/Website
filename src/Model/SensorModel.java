package Model;

public class SensorModel 
{
	public String getSensorName() {
		return SensorName;
	}

	public void setSensorName(String sensorName) {
		SensorName = sensorName;
	}

	public String getSensorDesc() {
		return SensorDesc;
	}

	public void setSensorDesc(String sensorDesc) {
		SensorDesc = sensorDesc;
	}

	int autoID = -1;
	String SensorID = "";
	String SensorMeasureType = "";
	String SensorSpec = "";
	String SensorFactory = "";
	float Param1 = 0.f;
	float Param2 = 0.f;
	float Param3 = 0.f;
	float Param4 = 0.f;
	float Param5 = 0.f;
	float Param6 = 0.f;
	float Param7 = 0.f;
	float Param8 = 0.f;
	
	/**
	 * 传感器名称
	 */
	String SensorName="";
	/**
	 * 传感器描述
	 */
	String SensorDesc="";
			
	
	public int getAutoID()
	{
		return autoID;
	}
	
	public void setAutoID(int nAutoID)
	{
		this.autoID = nAutoID;
	}
	
	public String getSensorID() 
	{
		return SensorID;
	}
	public void setSensorID(String SensorID)
	{
		this.SensorID = SensorID;
	}
	
	public String getSensorMeasureType() 
	{
		return SensorMeasureType;
	}
	public void setSensorMeasureType(String SensorMeasureType)
	{
		this.SensorMeasureType = SensorMeasureType;
	}
	
	public String getSensorSpec() 
	{
		return SensorSpec;
	}
	public void setSensorSpec(String SensorSpec)
	{
		this.SensorSpec = SensorSpec;
	}
	
	public String getSensorFactory() 
	{
		return SensorFactory;
	}
	public void setSensorFactory(String SensorFactory)
	{
		this.SensorFactory = SensorFactory;
	}
	
	public float getParam1()
	{
		return Param1;
	}
	
	public void setParam1(float fParam1)
	{
		this.Param1 = fParam1;
	}
	
	public float getParam2()
	{
		return Param2;
	}
	
	public void setParam2(float fParam2)
	{
		this.Param2 = fParam2;
	}
	
	public float getParam3()
	{
		return Param3;
	}
	
	public void setParam3(float fParam3)
	{
		this.Param3 = fParam3;
	}
	
	public float getParam4()
	{
		return Param4;
	}
	
	public void setParam4(float fParam4)
	{
		this.Param4 = fParam4;
	}
	
	public float getParam5()
	{
		return Param5;
	}
	
	public void setParam5(float fParam5)
	{
		this.Param5 = fParam5;
	}
	
	public float getParam6()
	{
		return Param6;
	}
	
	public void setParam6(float fParam6)
	{
		this.Param6 = fParam6;
	}
	
	public float getParam7()
	{
		return Param7;
	}
	
	public void setParam7(float fParam7)
	{
		this.Param7 = fParam7;
	}
	
	public float getParam8()
	{
		return Param8;
	}
	
	public void setParam8(float fParam8)
	{
		this.Param8 = fParam8;
	}
}
