package Business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import Model.InstrumentAcqModel;
import SqlHelper.MySqlHelper;
import TstDataTable.DataRow;
import TstDataTable.DataTable;


public class InstrumentAcqAction 
{
	//String strMainDBName = null;

	MySqlHelper mysql = null;
	
	String strPrjName = null;
	
	String strInstrumentTableName = "`instrument_acq`";

	public InstrumentAcqAction(String strPrjName) {

		mysql = new MySqlHelper();

		//strMainDBName = mysql.MainDBName();
		
		this.strPrjName=strPrjName;
	}
	
	

	/**
	 * 新增工程
	 * 
	 * @param modelPrjInfo
	 * @return
	 */
	public boolean Create(InstrumentAcqModel modelInfo) 
	{
		// 创建Sql语句
		StringBuffer sb = new StringBuffer();
		{
			sb.append("insert into").append(strInstrumentTableName);
			sb.append(" ( ");
			// 表字段
			sb.append(MySqlHelper.ToSqlEleme("insFactoryID",true));			
			sb.append(MySqlHelper.ToSqlEleme("insNetID",true));
			sb.append(MySqlHelper.ToSqlEleme("serverAddr",true));
			sb.append(MySqlHelper.ToSqlEleme("serverPort",false));
			
			// values
			sb.append(" ) values( ");
			
			// 表字段值
			sb.append(MySqlHelper.ToSqlStr(modelInfo.getinsFactoryID(), true));			
			sb.append(MySqlHelper.ToSqlStr(modelInfo.getinsNetID(),true));
			sb.append(MySqlHelper.ToSqlStr(modelInfo.getserverAddr(),true));
			sb.append(MySqlHelper.ToSqlStr(modelInfo.getserverPort(),false));
			
			sb.append(" );");

		}
		
		// 执行slq语句
		int nRes=mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		return nRes==1;
	}

	/**
	 * 修改工程，根据的是对象的autoid
	 * 
	 * @param modelPrjInfo
	 * @return
	 */
	public boolean Update(InstrumentAcqModel modelInfo) 
	{
    	// 创建Sql语句
		StringBuffer sb = new StringBuffer();
		{
			sb.append("update ").append(strInstrumentTableName);
			// 表字段,第二个字段中间需要加入','
			sb.append(" set ")
			  .append(MySqlHelper.ToSqlEleme("insFactoryID",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(modelInfo.getinsFactoryID(),false));			
		
			sb.append(",")
			  .append(MySqlHelper.ToSqlEleme("insNetID",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(modelInfo.getinsNetID(),false));
						
			sb.append(",")
			  .append(MySqlHelper.ToSqlEleme("serverAddr",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(modelInfo.getserverAddr(),false));
			
			sb.append(",")
			  .append(MySqlHelper.ToSqlEleme("serverPort",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(modelInfo.getserverPort(),false));
			
			sb.append(" where ")
			  .append(MySqlHelper.ToSqlEleme("autoID",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(modelInfo.getAutoID(),false));
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
	
	public boolean Delete(String strFactoryID) 
	{
		// 创建Sql语句
		StringBuffer sb = new StringBuffer();
		{
			sb.append("delete from ").append(strInstrumentTableName);
			
			// values
			sb.append(" where ")
			  .append(MySqlHelper.ToSqlEleme("insFactoryID",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(strFactoryID,false));
		}
		
		// 执行slq语句
		int nRes=mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		return nRes==1;
	}

	public boolean IsExists(Integer nAutoID)
	{
		InstrumentAcqModel model = Select(nAutoID);
		return (model != null);
	}
	
	public boolean IsExists(String strFactoryID)
	{
		InstrumentAcqModel model = Select(strFactoryID);
		return (model != null);
	}
	
	public InstrumentAcqModel Select(int nAutoID) 
	{
		HashMap<String, Object> condition = new HashMap<String, Object>();
		condition.put("AutoID", nAutoID);
		List<InstrumentAcqModel> listModels = Select(condition);
		if(listModels.size() > 0)
		{
			return listModels.get(0);
		}
		
		return null;
	}
	
	public InstrumentAcqModel Select(String insFactoryID) 
	{
		HashMap<String, Object> condition = new HashMap<String, Object>();
		condition.put("insFactoryID", insFactoryID);
		List<InstrumentAcqModel> listModels = Select(condition);
		if(listModels.size() > 0)
		{
			return listModels.get(0);
		}
		
		return null;
	}
	
	public int SelectCount(HashMap<String, Object> condition) 
	{
		List<InstrumentAcqModel> list = new ArrayList<>();
		StringBuffer sbWhere = new StringBuffer(" where 1=1 ");
		StringBuffer sbLimit = new StringBuffer(" ");
		{
			// where, 此处全用and
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
		// 1. 构造sql语句
		StringBuffer sb = new StringBuffer();
		{
			sb.append("select * from ").append(strInstrumentTableName)
			  .append(sbWhere.toString())
			  .append("order by autoid DESC")
			  .append(";");
			
		}
	
		
		// 2. 执行sql
		DataTable dt=mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		
		// 3. 将执行结果转成对象
		if(dt!=null&&dt.getRow()!=null&&dt.getRow().size()>0) 
		{
			for(DataRow dr:dt.getRow()) 
			{
				if(dr!=null&&dr.getCol().size()>0)
				{
					InstrumentAcqModel model=new InstrumentAcqModel();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setinsFactoryID(dr.getStringColumn("insFactoryID"));
					model.setinsNetID(dr.getStringColumn("insNetID"));
					model.setserverAddr(dr.getStringColumn("serverAddr"));
					String strserverPort = dr.getStringColumn("serverPort");
					model.setserverPort(Integer.parseInt(strserverPort));
										
					list.add(model);
				}
			}
		}
		
		return list.size();
	}
	
	
	/**
	 * 查找，返回全部的工程信息
	 * 
	 * @return
	 */
	public List<InstrumentAcqModel> Select(HashMap<String, Object> condition) 
	{
		List<InstrumentAcqModel> list = new ArrayList<>();
		StringBuffer sbWhere = new StringBuffer(" where 1=1 ");
		StringBuffer sbLimit = new StringBuffer(" ");
		{
			// where, 此处全用and
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
		// 1. 构造sql语句
		StringBuffer sb = new StringBuffer();
		{
			sb.append("select * from ").append(strInstrumentTableName)
			  .append(sbWhere.toString())
			  .append("order by autoid DESC")
			  .append(sbLimit.toString())
			  .append(";");
			
		}
	
		
		// 2. 执行sql
		DataTable dt=mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		
		// 3. 将执行结果转成对象
		if(dt!=null&&dt.getRow()!=null&&dt.getRow().size()>0) 
		{
			for(DataRow dr:dt.getRow()) 
			{
				if(dr!=null&&dr.getCol().size()>0)
				{
					InstrumentAcqModel model=new InstrumentAcqModel();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setinsFactoryID(dr.getStringColumn("insFactoryID"));
					model.setinsNetID(dr.getStringColumn("insNetID"));
					model.setserverAddr(dr.getStringColumn("serverAddr"));
					String strserverPort = dr.getStringColumn("serverPort");
					model.setserverPort(Integer.parseInt(strserverPort));
										
					list.add(model);
				}
			}
		}
		
		return list;
	}
}


