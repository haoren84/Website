package Model;

public class InstrumentAcqModel 
{
	int autoID=-1;
	String insFactoryID = "";	
	String insNetID= "";
	String serverAddr= "";
	int serverPort= 0;
	
	public int getAutoID()
	{
		return autoID;
	}
	
	public void setAutoID(int nAutoID)
	{
		this.autoID = nAutoID;
	}
	
	public String getinsFactoryID() 
	{
		return insFactoryID;
	}
	public void setinsFactoryID(String factoryID)
	{
		this.insFactoryID = factoryID;
	}
	
	public String getinsNetID() 
	{
		return insNetID;
	}
	public void setinsNetID(String NetID)
	{
		this.insNetID = NetID;
	}
	
	public String getserverAddr() 
	{
		return serverAddr;
	}
	public void setserverAddr(String serverAddr)
	{
		this.serverAddr = serverAddr;
	}
	
	public int getserverPort() 
	{
		return serverPort;
	}
	public void setserverPort(int serverPort)
	{
		this.serverPort = serverPort;
	}
	
}
