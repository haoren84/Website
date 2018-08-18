package Model;

/**
 * ×ÖµäÊý¾Ý
 * @author Administrator
 *
 */
public class DictionaryInfo {

	int AutoID;
	String DicValue;
	int isUsed;
	String ParentDic;
	int ParentID;
	
	public int getAutoID() {
		return AutoID;
	}
	public void setAutoID(int autoID) {
		AutoID = autoID;
	}
	public String getDicValue() {
		return DicValue;
	}
	public void setDicValue(String dicValue) {
		DicValue = dicValue;
	}
	public int getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
	}
	public String getParentDic() {
		return ParentDic;
	}
	public void setParentDic(String parentDic) {
		ParentDic = parentDic;
	}
	public int getParentID() {
		return ParentID;
	}
	public void setParentID(int parentID) {
		ParentID = parentID;
	}
	
}
