package Model;

public class DictionaryModel 
{
	int autoID=-1;
	String Name = "";
	String value = "";
	int isUsed = 0;	 	 
	String DictionaryParent = "";
	
	public int getAutoID()
	{
		return autoID;
	}
	
	public void setAutoID(int nAutoID)
	{
		this.autoID = nAutoID;
	}
	
	public String getName() 
	{
		return Name;
	}
	public void setName(String Name)
	{
		this.Name = Name;
	}
	
	public String getValue() 
	{
		return value;
	}
	public void setValue(String value)
	{
		this.value = value;
	}
	
	public int getisUsed() 
	{
		return isUsed;
	}
	public void setisUsed(int isUsed)
	{
		this.isUsed = isUsed;
	}	

	public String getDictionaryParent() 
	{
		return DictionaryParent;
	}
	public void setDictionaryParent(String value)
	{
		this.DictionaryParent = value;
	}	
}
