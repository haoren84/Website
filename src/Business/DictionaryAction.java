package Business;

import java.util.ArrayList;
import java.util.List;

import Model.Monitor;
import Model.MonitorPoint;
import Model.DictionaryModel;
import Model.InstrumentAcqModel;
import SqlHelper.MySqlHelper;
import TstDataTable.DataRow;
import TstDataTable.DataTable;

/**
 * 工程结构的操作
 * 
 * @author Administrator
 *
 */
public class DictionaryAction {

	String strPrjName = null;

	MySqlHelper mysql = null;

	String strInstrumentTableName = "`dictionary`";
	
	public DictionaryAction(String strPrjName) {

		mysql = new MySqlHelper();

		this.strPrjName = strPrjName;

	}

	/**
	 * 新建工程结构
	 * 
	 * @param model
	 *            工程结构信息
	 * @return
	 */
	public boolean Create(DictionaryModel model)
	{
		// 创建Sql语句
		StringBuffer sb = new StringBuffer();
		{
			sb.append("insert into").append(strInstrumentTableName);
			sb.append(" ( ");
			// 表字段
			sb.append(MySqlHelper.ToSqlEleme("Name",true));			
			sb.append(MySqlHelper.ToSqlEleme("value",true));
			sb.append(MySqlHelper.ToSqlEleme("isUsed",true));
			sb.append(MySqlHelper.ToSqlEleme("DictionaryParent",false));			
			
			// values
			sb.append(" ) values( ");
			
			// 表字段值
			sb.append(MySqlHelper.ToSqlStr(model.getName(), true));			
			sb.append(MySqlHelper.ToSqlStr(model.getValue(),true));
			sb.append(MySqlHelper.ToSqlStr(model.getisUsed(),true));
			sb.append(MySqlHelper.ToSqlStr(model.getDictionaryParent(),false));
			
			sb.append(" );");
		}
		// 执行slq语句
		int nRes=mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		return nRes==1;
	}

	public boolean Update(DictionaryModel model) 
	{
    	// 创建Sql语句
		StringBuffer sb = new StringBuffer();
		{
			sb.append("update ").append(strInstrumentTableName);
			// 表字段,第二个字段中间需要加入','
			sb.append(" set ")
			  .append(MySqlHelper.ToSqlEleme("Name",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(model.getName(),false));			
		
			sb.append(",")
			  .append(MySqlHelper.ToSqlEleme("value",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(model.getValue(),false));
						
			sb.append(",")
			  .append(MySqlHelper.ToSqlEleme("isUsed",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(model.getisUsed(),false));
			
			sb.append(",")
			  .append(MySqlHelper.ToSqlEleme("DictionaryParent",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(model.getDictionaryParent(),false));
			
			sb.append(" where ")
			  .append(MySqlHelper.ToSqlEleme("autoID",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(model.getAutoID(),false));
		}
		
		// 执行slq语句
		int nRes=mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		return nRes==1;
	}
	
	
	/**
	 * 删除工程，根据autoid
	 * 
	 * @param nAutoID
	 * @return
	 */
	public boolean Delete(int nAutoID) 
	{
		// 创建Sql语句
		StringBuffer sb = new StringBuffer();
		{
			sb.append("delete from ").append(strInstrumentTableName);
			
			// values
			sb.append(" where ")
			  .append(MySqlHelper.ToSqlEleme("autoID",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(nAutoID,false));
		}
		
		// 执行slq语句
		int nRes=mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		return nRes==1;
	}
	
	/**
	 * 根据ID找到对应的结构
	 * 
	 * @param nAutoID
	 * @return
	 */
	public DictionaryModel GetDictionaryModel(int nAutoID) {

		StringBuffer sb = new StringBuffer();

		sb.append("select * from `dictionary` where AutoID='").append(nAutoID).append("';");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {

					DictionaryModel model = new DictionaryModel();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setName(dr.getStringColumn("Name"));
					model.setValue(dr.getStringColumn("value"));
					String strIsUsed = dr.getStringColumn("isUsed");
					model.setisUsed(Integer.parseInt(strIsUsed));
					model.setDictionaryParent(dr.getStringColumn("DictionaryParent"));
					return model;
				}
			}

		}
		return null;
	}

	/**
	 * 根据Name找到对应的结构
	 * 
	 * @param nAutoID
	 * @return
	 */
	public DictionaryModel GetDictionaryModel(String Name) 
	{
		StringBuffer sb = new StringBuffer();

		sb.append("select * from `dictionary` where Name='").append(Name).append("';");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {

					DictionaryModel model = new DictionaryModel();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setName(dr.getStringColumn("Name"));
					model.setValue(dr.getStringColumn("value"));
					String strIsUsed = dr.getStringColumn("isUsed");
					model.setisUsed(Integer.parseInt(strIsUsed));
					model.setDictionaryParent(dr.getStringColumn("DictionaryParent"));
					return model;
				}
			}

		}
		return null;
	}
	
	/**
	 * 通过结构名称获取对应的ID
	 * 
	 * @param strStrcuctName
	 *            结构名称
	 * @return
	 */
	public int GetAutoIDByDictionaryName(String strDictionaryName)
	{
		StringBuffer sb = new StringBuffer();

		sb.append("select * from `dictionary` where Name='").append(strDictionaryName).append("';");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		int nRes = 0;

		if (dt == null || dt.getRow() == null || dt.getRow().size() <= 0)
		{
			nRes = 0;
		}
		else 
		{
			for (DataRow dr : dt.getRow())
			{
				if (dr != null && dr.getCol().size() > 0) {
					nRes = dr.getIntColumn("autoid");
			}
		}
	}

		return nRes;
	}
	
	/**
	 * 获取分页数据的父节点为零的结构总个数
	 * 
	 * @return
	 */
	public int GetDictionaryPageDataCount() 
	{
		StringBuffer sb = new StringBuffer();

		sb.append("select count(*) from `dictionary` where DictionaryParent='0';");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		int nRes = 0;

		if (dt == null || dt.getRow() == null || dt.getRow().size() <= 0)
		{
			nRes = 0;
		}
		else
		{
			nRes = Integer.parseInt(dt.getRow().get(0).getCol().get(0).getValue().toString());
		}

		return nRes;
	}

	public  String GetParent(String strAutoID) 
	{
		String strDictionaryParent = "";
		//如果是根节点则为空
		if (strAutoID.equals("0")) 
		{
			strDictionaryParent = "";
		}
		else 
		{
			int nAutoID = Integer.parseInt(strAutoID);
			DictionaryModel model = new DictionaryModel(); 
		    model = GetDictionaryModel(nAutoID);
		    strDictionaryParent = model.getName();
		}
		return strDictionaryParent;
	}
	
	/**
	 * 获取父节点为零的结构分页数据
	 * 
	 * @param page
	 *            页数
	 * @param rows
	 *            每页个数
	 * @return
	 */
	public List<DictionaryModel> GetDictionaryPageData(int page, int rows) {

		List<DictionaryModel> listDictionary = new ArrayList<>();

		int nComputcout = (page - 1) * rows;

		StringBuffer sb = new StringBuffer();

		sb.append("select * from `dictionary` where `DictionaryParent`='0' limit ").append(nComputcout).append(",").append(rows)
				.append(";");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) 
		{
			for (DataRow dr : dt.getRow()) 
			{
				if (dr != null && dr.getCol().size() > 0)
				{

					DictionaryModel model = new DictionaryModel();

					model.setAutoID(dr.getIntColumn("autoid"));
					model.setName(dr.getStringColumn("Name"));
					model.setValue(dr.getStringColumn("value"));
					String str = dr.getStringColumn("isUsed");
					model.setisUsed(Integer.parseInt(str));
					String strAutoID = dr.getStringColumn("DictionaryParent");							
					model.setDictionaryParent(GetParent(strAutoID));			
					listDictionary.add(model);
				}
			}

		}

		return listDictionary;
	}

	/**
	 * 根据父节点找到所有的子结构
	 * 
	 * @param strStructParent
	 * @return
	 */
	public List<DictionaryModel> SelectDictionaryByParent(String strDictionaryParent) {

		List<DictionaryModel> listDictionary = new ArrayList<>();

		StringBuffer sb = new StringBuffer();

		sb.append("select * from `dictionary` where DictionaryParent = '" + strDictionaryParent + "';");

		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {

					DictionaryModel model = new DictionaryModel();

					model.setAutoID(dr.getIntColumn("autoid"));
					model.setName(dr.getStringColumn("Name"));
					model.setValue(dr.getStringColumn("value"));
					String str = dr.getStringColumn("isUsed");
					model.setisUsed(Integer.parseInt(str));													
					String strAutoID = dr.getStringColumn("DictionaryParent");							
					model.setDictionaryParent(GetParent(strAutoID));	
					listDictionary.add(model);
				}
			}

		}
		return listDictionary;
	}

	/**
	 * 获取所有的结构的树节点
	 * 
	 * @return
	 */
	public StringBuffer GetAllDictionaryTree() {

		// 读取所有父节点为0的结构 第一级结构

		List<DictionaryModel> listDictionary = SelectDictionaryByParent("0");

		StringBuffer sb = new StringBuffer();

		sb.append("[");

		if (listDictionary != null && listDictionary.size() > 0) {
			for (DictionaryModel item : listDictionary) {

				StringBuffer sbItem = new StringBuffer();

				GetDictionaryNameTree(sbItem, item.getAutoID());

				sb.append(sbItem.toString());

			}

			sb.deleteCharAt(sb.length() - 1);
		}

		sb.append("]");

		return sb;
	}

	/**
	 * 获取对应ID的树结构
	 * 
	 * @return
	 */
	public void GetDictionaryNameTree(StringBuffer sb, int autoid) {

		if (sb == null) {
			sb = new StringBuffer();
		}

		// 当前的ID
		DictionaryModel curModel = GetDictionaryModel(autoid);

		sb.append("{");
		sb.append(String.format("\"id\":\"%d\",", curModel.getAutoID()));
		sb.append(String.format("\"text\":\"%s\"", curModel.getName()));

		// 子节点

		List<DictionaryModel> listDictionary = SelectDictionaryByParent(String.valueOf(autoid));

		if (listDictionary != null && listDictionary.size() > 0) {

			sb.append(",\"state\":\"closed\",");
			sb.append("\"children\":[");

			for (DictionaryModel item : listDictionary) {

				GetDictionaryNameTree(sb, item.getAutoID());
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("]");
		}

		sb.append("},");

		return;
	}
	
	
}
