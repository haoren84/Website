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
 * ���̽ṹ�Ĳ���
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
	 * �½����̽ṹ
	 * 
	 * @param model
	 *            ���̽ṹ��Ϣ
	 * @return
	 */
	public boolean Create(DictionaryModel model)
	{
		// ����Sql���
		StringBuffer sb = new StringBuffer();
		{
			sb.append("insert into").append(strInstrumentTableName);
			sb.append(" ( ");
			// ���ֶ�
			sb.append(MySqlHelper.ToSqlEleme("Name",true));			
			sb.append(MySqlHelper.ToSqlEleme("value",true));
			sb.append(MySqlHelper.ToSqlEleme("isUsed",true));
			sb.append(MySqlHelper.ToSqlEleme("DictionaryParent",false));			
			
			// values
			sb.append(" ) values( ");
			
			// ���ֶ�ֵ
			sb.append(MySqlHelper.ToSqlStr(model.getName(), true));			
			sb.append(MySqlHelper.ToSqlStr(model.getValue(),true));
			sb.append(MySqlHelper.ToSqlStr(model.getisUsed(),true));
			sb.append(MySqlHelper.ToSqlStr(model.getDictionaryParent(),false));
			
			sb.append(" );");
		}
		// ִ��slq���
		int nRes=mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		return nRes==1;
	}

	public boolean Update(DictionaryModel model) 
	{
    	// ����Sql���
		StringBuffer sb = new StringBuffer();
		{
			sb.append("update ").append(strInstrumentTableName);
			// ���ֶ�,�ڶ����ֶ��м���Ҫ����','
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
		
		// ִ��slq���
		int nRes=mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		return nRes==1;
	}
	
	
	/**
	 * ɾ�����̣�����autoid
	 * 
	 * @param nAutoID
	 * @return
	 */
	public boolean Delete(int nAutoID) 
	{
		// ����Sql���
		StringBuffer sb = new StringBuffer();
		{
			sb.append("delete from ").append(strInstrumentTableName);
			
			// values
			sb.append(" where ")
			  .append(MySqlHelper.ToSqlEleme("autoID",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(nAutoID,false));
		}
		
		// ִ��slq���
		int nRes=mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		return nRes==1;
	}
	
	/**
	 * ����ID�ҵ���Ӧ�Ľṹ
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
	 * ����Name�ҵ���Ӧ�Ľṹ
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
	 * ͨ���ṹ���ƻ�ȡ��Ӧ��ID
	 * 
	 * @param strStrcuctName
	 *            �ṹ����
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
	 * ��ȡ��ҳ���ݵĸ��ڵ�Ϊ��Ľṹ�ܸ���
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
		//����Ǹ��ڵ���Ϊ��
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
	 * ��ȡ���ڵ�Ϊ��Ľṹ��ҳ����
	 * 
	 * @param page
	 *            ҳ��
	 * @param rows
	 *            ÿҳ����
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
	 * ���ݸ��ڵ��ҵ����е��ӽṹ
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
	 * ��ȡ���еĽṹ�����ڵ�
	 * 
	 * @return
	 */
	public StringBuffer GetAllDictionaryTree() {

		// ��ȡ���и��ڵ�Ϊ0�Ľṹ ��һ���ṹ

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
	 * ��ȡ��ӦID�����ṹ
	 * 
	 * @return
	 */
	public void GetDictionaryNameTree(StringBuffer sb, int autoid) {

		if (sb == null) {
			sb = new StringBuffer();
		}

		// ��ǰ��ID
		DictionaryModel curModel = GetDictionaryModel(autoid);

		sb.append("{");
		sb.append(String.format("\"id\":\"%d\",", curModel.getAutoID()));
		sb.append(String.format("\"text\":\"%s\"", curModel.getName()));

		// �ӽڵ�

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
