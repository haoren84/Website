package Model;


public class InstrumentTerminalModel 
{
	int autoID = -1;
	
	String insFactoryID = "";	
	int insID = 0;
	int insType = 0;
	int insChnCount = 0;
	/**
	 * ÷’∂À√˚≥∆
	 */
	String terminalName="";
	/**
	 * ÷’∂À¿‡–Õ
	 */
	String terminalType="";
	/**
	 * ÷’∂À√Ë ˆ
	 */
	String terminalDesc="";
	/**
	 * ÷’∂À±∏◊¢
	 */
	String terminalRemark="";
	/**
	 * Õ¯¬ÁID
	 */
	String netID="";
	
	
	public String getTerminalName() {
		return terminalName;
	}

	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}

	public String getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	public String getTerminalDesc() {
		return terminalDesc;
	}

	public void setTerminalDesc(String terminalDesc) {
		this.terminalDesc = terminalDesc;
	}

	public String getTerminalRemark() {
		return terminalRemark;
	}

	public void setTerminalRemark(String terminalRemark) {
		this.terminalRemark = terminalRemark;
	}

	public String getNetID() {
		return netID;
	}

	public void setNetID(String netID) {
		this.netID = netID;
	}

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
		
	public int getinsID() 
	{
		return insID;
	}
	public void setinsID(int ID)
	{
		this.insID = ID;
	}
	
	public int getinsType() 
	{
		return insType;
	}
	public void setinsType(int Type)
	{
		this.insType = Type;
	}
	
	public int getinsChnCount() 
	{
		return insChnCount;
	}
	public void setinsChnCount(int ChnCount)
	{
		this.insChnCount = ChnCount;
	}
}
