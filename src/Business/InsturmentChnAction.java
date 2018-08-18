package Business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import Model.InstrumentChnModel;
import SqlHelper.MySqlHelper;
import TstDataTable.DataRow;
import TstDataTable.DataTable;

public class InsturmentChnAction 
{
	//String strMainDBName = null;

	MySqlHelper mysql = null;
	
	String strPrjName = null;
	
	String strInstrumentTableName = "`instrument_terminal_chn`";

	public InsturmentChnAction(String strPrjName) {

		mysql = new MySqlHelper();

		//strMainDBName = mysql.MainDBName();
		
		this.strPrjName=strPrjName;
	}
	

	/**
	 * ��������
	 * 
	 * @param modelPrjInfo
	 * @return
	 */
	public boolean Create(InstrumentChnModel modelInfo) 
	{
		// ����Sql���
		StringBuffer sb = new StringBuffer();
		{
			sb.append("insert into").append(strInstrumentTableName);
			sb.append(" ( ");
			// ���ֶ�
			sb.append(MySqlHelper.ToSqlEleme("insFactoryID",true));
			sb.append(MySqlHelper.ToSqlEleme("chnID",false));

			// values
			sb.append(" ) values( ");
			
			// ���ֶ�ֵ
			sb.append(MySqlHelper.ToSqlStr(modelInfo.getinsFactoryID(),true));
			sb.append(MySqlHelper.ToSqlStr(modelInfo.getchnID(),false));

			
			sb.append(" );");

		}
		
		// ִ��slq���
		int nRes=mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		return nRes==1;
	}

	/**
	 * �޸Ĺ��̣����ݵ��Ƕ����autoid
	 * 
	 * @param modelPrjInfo
	 * @return
	 */
	public boolean Update(InstrumentChnModel modelInfo) 
	{
    	// ����Sql���
		StringBuffer sb = new StringBuffer();
		{
			sb.append("update ").append(strInstrumentTableName);
			// ���ֶ�,�ڶ����ֶ��м���Ҫ����','
			sb.append(" set ")
			  .append(MySqlHelper.ToSqlEleme("insFactoryID",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(modelInfo.getinsFactoryID(),false));
			
			sb.append(",")
			  .append(MySqlHelper.ToSqlEleme("chnID",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(modelInfo.getchnID(),false));		
			
			sb.append(" where ")
			  .append(MySqlHelper.ToSqlEleme("autoID",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(modelInfo.getAutoID(),false));
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

	public boolean IsExists(Integer nAutoID)
	{
		InstrumentChnModel model = Select(nAutoID);
		return (model != null);
	}
	
	public boolean IsExists(String strSensorID)
	{
		InstrumentChnModel model = Select(strSensorID);
		return (model != null);
	}
	
	public InstrumentChnModel Select(int nAutoID) 
	{
		HashMap<String, Object> condition = new HashMap<String, Object>();
		condition.put("AutoID", nAutoID);
		List<InstrumentChnModel> listModels = Select(condition);
		if(listModels.size() > 0)
		{
			return listModels.get(0);
		}
		
		return null;
	}
	
	public InstrumentChnModel Select(String SensorID) 
	{
		HashMap<String, Object> condition = new HashMap<String, Object>();
		condition.put("insFactoryID", SensorID);
		List<InstrumentChnModel> listModels = Select(condition);
		if(listModels.size() > 0)
		{
			return listModels.get(0);
		}
		
		return null;
	}
	
	public int SelectCount(HashMap<String, Object> condition) 
	{
		List<InstrumentChnModel> list = new ArrayList<>();
		StringBuffer sbWhere = new StringBuffer(" where 1=1 ");
		StringBuffer sbLimit = new StringBuffer(" ");
		{
			// where, �˴�ȫ��and
			Iterator it = condition.keySet().iterator();  
	        while(it.hasNext()) 
	        {  
	            String key = (String)it.next();  
	            Object value = condition.get(key);
	            
	            if(key == "page" || key == "rows") 
	            {
	            	continue;
	            }
	            
	            if(sbWhere.toString().isEmpty() == false)
	            {
	            	sbWhere.append(" and ");
	            }
	            sbWhere.append(key).append("=").append(MySqlHelper.ToSqlStr(value,false));
	        }
	        
	        // limit
	        if(condition.containsKey("page") && condition.containsKey("rows"))
	        {
 	        	int nPage = Integer.parseInt(condition.get("page").toString());
	        	int nRows = Integer.parseInt(condition.get("rows").toString());
	        	int nOffset = (nPage - 1) * nRows;
	        	sbLimit.append(" limit ").append(nOffset)	        	
            		   .append(",")
	        	       .append(" ").append(nRows);
	        }
	        
					
		}
		// 1. ����sql���
		StringBuffer sb = new StringBuffer();
		{
			sb.append("select * from ").append(strInstrumentTableName)
			  .append(sbWhere.toString())
			  .append("order by autoid DESC")
			  .append(";");
			
		}
	
		
		// 2. ִ��sql
		DataTable dt=mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		
		// 3. ��ִ�н��ת�ɶ���
		if(dt!=null&&dt.getRow()!=null&&dt.getRow().size()>0) 
		{
			for(DataRow dr:dt.getRow()) 
			{
				if(dr!=null&&dr.getCol().size()>0)
				{
					InstrumentChnModel model=new InstrumentChnModel();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setinsFactoryID(dr.getStringColumn("insFactoryID"));

					String strchnID = dr.getStringColumn("chnID");
					model.setchnID(Integer.parseInt(strchnID));
				
					list.add(model);
				}
			}
		}
		
		return list.size();
	}
	
	/**
	 * ���ң�����ȫ���Ĺ�����Ϣ
	 * 
	 * @return
	 */
	public List<InstrumentChnModel> Select(HashMap<String, Object> condition) 
	{
		List<InstrumentChnModel> list = new ArrayList<>();
		StringBuffer sbWhere = new StringBuffer(" where 1=1 ");
		StringBuffer sbLimit = new StringBuffer(" ");
		{
			// where, �˴�ȫ��and
			Iterator it = condition.keySet().iterator();  
	        while(it.hasNext()) 
	        {  
	            String key = (String)it.next();  
	            Object value = condition.get(key);
	            
	            if(key == "page" || key == "rows") 
	            {
	            	continue;
	            }
	            
	            if(sbWhere.toString().isEmpty() == false)
	            {
	            	sbWhere.append(" and ");
	            }
	            sbWhere.append(key).append("=").append(MySqlHelper.ToSqlStr(value,false));
	        }
	        
	        // limit
	        if(condition.containsKey("page") && condition.containsKey("rows"))
	        {
 	        	int nPage = Integer.parseInt(condition.get("page").toString());
	        	int nRows = Integer.parseInt(condition.get("rows").toString());
	        	int nOffset = (nPage - 1) * nRows;
	        	sbLimit.append(" limit ").append(nOffset)	        	
            		   .append(",")
	        	       .append(" ").append(nRows);
	        }
	        
					
		}
		// 1. ����sql���
		StringBuffer sb = new StringBuffer();
		{
			sb.append("select * from ").append(strInstrumentTableName)
			  .append(sbWhere.toString())
			  .append("order by autoid DESC")
			  .append(sbLimit.toString())
			  .append(";");
			
		}
	
		
		// 2. ִ��sql
		DataTable dt=mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		
		// 3. ��ִ�н��ת�ɶ���
		if(dt!=null&&dt.getRow()!=null&&dt.getRow().size()>0) 
		{
			for(DataRow dr:dt.getRow()) 
			{
				if(dr!=null&&dr.getCol().size()>0)
				{
					InstrumentChnModel model=new InstrumentChnModel();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setinsFactoryID(dr.getStringColumn("insFactoryID"));
									
					String strchnID = dr.getStringColumn("chnID");
					model.setchnID(Integer.parseInt(strchnID));

					list.add(model);
				}
			}
		}
		
		return list;
	}
}
