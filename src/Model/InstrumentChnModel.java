package Model;

public class InstrumentChnModel 
{
	int autoID = -1;
	
	String insFactoryID = "";
	int chnID = 0;
	
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
	
	public int getchnID() 
	{
		return chnID;
	}
	public void setchnID(int chnID)
	{
		this.chnID = chnID;
	}
}
