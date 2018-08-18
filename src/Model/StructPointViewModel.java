package Model;

/**
 * 用于新增结构时和页面json交互的数据类
 * @author Administrator
 *
 */
public class StructPointViewModel {
	
	/**
	 * 监测项目名称
	 */
	String strMName;
	/**
	 * 监测项目ID
	 */
	int nMid;
	/**
	 * 测点名称
	 */
	String strPName;
	/**
	 * 测点ID
	 */
	int nPid;
	
	public String getStrMName() {
		return strMName;
	}
	public void setStrMName(String strMName) {
		this.strMName = strMName;
	}
	public int getnMid() {
		return nMid;
	}
	public void setnMid(int nMid) {
		this.nMid = nMid;
	}
	public String getStrPName() {
		return strPName;
	}
	public void setStrPName(String strPName) {
		this.strPName = strPName;
	}
	public int getnPid() {
		return nPid;
	}
	public void setnPid(int nPid) {
		this.nPid = nPid;
	}
	
}
