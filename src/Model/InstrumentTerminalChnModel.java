package Model;

/**
 * ÖÕ¶Ë->Í¨µÀ
 * @author Administrator
 *
 */
public class InstrumentTerminalChnModel {

	int AutoID = -1;
	
	String insFactoryID = "";
	
	int chnID;

	public int getAutoID() {
		return AutoID;
	}

	public void setAutoID(int autoID) {
		AutoID = autoID;
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
	
	
}
