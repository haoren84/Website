package Business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import Model.InstrumentTerminalModel;
import SqlHelper.MySqlHelper;
import TstDataTable.DataRow;
import TstDataTable.DataTable;



public class InstrumentTerminalAction 
{
	
	//String strMainDBName = null;

	MySqlHelper mysql = null;
	
	String strPrjName = null;
	
	String strInstrumentTableName = "`instrument_terminal`";

	public InstrumentTerminalAction(String strPrjName) {

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
	public boolean Create(InstrumentTerminalModel modelInfo) 
	{
		// ����Sql���
		StringBuffer sb = new StringBuffer();
		{
			sb.append("insert into").append(strInstrumentTableName);
			sb.append(" ( ");
			// ���ֶ�
			sb.append(MySqlHelper.ToSqlEleme("insFactoryID",true));			
			sb.append(MySqlHelper.ToSqlEleme("insID", true));
			sb.append(MySqlHelper.ToSqlEleme("insType", true));
			sb.append(MySqlHelper.ToSqlEleme("insChnCount", false));
			
			// values
			sb.append(" ) values( ");
			
			// ���ֶ�ֵ
			sb.append(MySqlHelper.ToSqlStr(modelInfo.getinsFactoryID(), true));			
			sb.append(MySqlHelper.ToSqlStr(modelInfo.getinsID(), true));
			sb.append(MySqlHelper.ToSqlStr(modelInfo.getinsType(), true));
			sb.append(MySqlHelper.ToSqlStr(modelInfo.getinsChnCount(),false));
			
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
	public boolean Update(InstrumentTerminalModel modelInfo) 
	{
    	// ����Sql���
		StringBuffer sb = new StringBuffer();
		{
			sb.append("update ").append(strInstrumentTableName);
			// ���ֶ�,�ڶ����ֶ��м���Ҫ����','
			sb.append(" set ")
			  .append(MySqlHelper.ToSqlEleme("insFactoryID",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(modelInfo.getinsFactoryID(), false));			
		
			sb.append(",")
			  .append(MySqlHelper.ToSqlEleme("insID",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(modelInfo.getinsID(), false));
						
			sb.append(",")
			  .append(MySqlHelper.ToSqlEleme("insType",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(modelInfo.getinsType(), false));
			
			sb.append(",")
			  .append(MySqlHelper.ToSqlEleme("insChnCount",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(modelInfo.getinsChnCount(),false));
			
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

	public boolean Delete(String strFactoryID) 
	{
		// ����Sql���
		StringBuffer sb = new StringBuffer();
		{
			sb.append("delete from ").append(strInstrumentTableName);
			
			// values
			sb.append(" where ")
			  .append(MySqlHelper.ToSqlEleme("insFactoryID",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(strFactoryID,false));
		}
		
		// ִ��slq���
		int nRes=mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		return nRes==1;
	}
	
	
	public boolean IsExists(Integer nAutoID)
	{
		InstrumentTerminalModel model = Select(nAutoID);
		return (model != null);
	}
	
	public boolean IsExists(String strSensorID)
	{
		InstrumentTerminalModel model = Select(strSensorID);
		return (model != null);
	}
	
	public InstrumentTerminalModel Select(int nAutoID) 
	{
		HashMap<String, Object> condition = new HashMap<String, Object>();
		condition.put("AutoID", nAutoID);
		List<InstrumentTerminalModel> listModels = Select(condition);
		if(listModels.size() > 0)
		{
			return listModels.get(0);
		}
		
		return null;
	}
	
	public InstrumentTerminalModel Select(String insFactoryID) 
	{
		HashMap<String, Object> condition = new HashMap<String, Object>();
		condition.put("insFactoryID", insFactoryID);
		List<InstrumentTerminalModel> listModels = Select(condition);
		if(listModels.size() > 0)
		{
			return listModels.get(0);
		}
		
		return null;
	}
	
	public int SelectCount(HashMap<String, Object> condition) 
	{
		List<InstrumentTerminalModel> list = new ArrayList<>();
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
					InstrumentTerminalModel model=new InstrumentTerminalModel();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setinsFactoryID(dr.getStringColumn("insFactoryID"));
					String strinsType = dr.getStringColumn("insType");
					model.setinsType(Integer.parseInt(strinsType));
					String strinsChnCount = dr.getStringColumn("insChnCount");
					model.setinsChnCount(Integer.parseInt(strinsChnCount));												
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
	public List<InstrumentTerminalModel> Select(HashMap<String, Object> condition) 
	{
		List<InstrumentTerminalModel> list = new ArrayList<>();
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
					InstrumentTerminalModel model=new InstrumentTerminalModel();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setinsFactoryID(dr.getStringColumn("insFactoryID"));										
					String strinsID = dr.getStringColumn("insID");
					model.setinsID(Integer.parseInt(strinsID));
					String strinsType = dr.getStringColumn("insType");
					model.setinsType(Integer.parseInt(strinsType));
					list.add(model);
				}
			}
		}
		
		return list;
	}
	
	/**
	 * ��ѯ�����ն���Ϣ
	 * @return
	 */
	public List<InstrumentTerminalModel> SelectAllTerminal(){
		
		List<InstrumentTerminalModel> list=new ArrayList<>();
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select * from `instrument_terminal`;");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		if(dt!=null&&dt.getRow()!=null&&dt.getRow().size()>0) 
		{
			for(DataRow dr:dt.getRow()) 
			{
				if(dr!=null&&dr.getCol().size()>0)
				{
					InstrumentTerminalModel model=new InstrumentTerminalModel();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setinsFactoryID(dr.getStringColumn("insFactoryID"));										
					String strinsID = dr.getStringColumn("insID");
					model.setinsID(Integer.parseInt(strinsID));
					String strinsType = dr.getStringColumn("insType");
					model.setinsType(Integer.parseInt(strinsType));
					list.add(model);
				}
			}
		}
		
		return list;
	}
	
	
	/**
	 * ��ȡ�ն˵ķ�ҳ�����ܸ���
	 * @param strInsFactoryID
	 * @param nInsType
	 * @return
	 */
	public int GetSelectPageDataAllCount(String strInsFactoryID,int nInsType) {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select count(*) from `instrument_terminal` where 1=1 ");
		
		if(!strInsFactoryID.isEmpty()&&!strInsFactoryID.equals("ȫ��")) {
			
			sb.append(" and  insFactoryID = '"+strInsFactoryID+"' ");
			
		}
		
		if(nInsType>0) {
			
			sb.append(" and insType = '"+nInsType+"' ");
			
		}
		
		sb.append(";");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {
			
			int nRes=Integer.parseInt(dt.getRow().get(0).getCol().get(0).getValue().toString());
			
			return nRes;
			
		}
		
		
		return 0;
	}
	
	/**
	 * ��ȡ�ն˵ķ�ҳ����
	 * @param strInsFactoryID �ն˵ĳ������
	 * @param nInsType �ն˵Ĳ�������
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<InstrumentTerminalModel> GetSelectPageData(String strInsFactoryID,int nInsType,int page, int rows){
		
		StringBuffer sb=new StringBuffer();
		
		List<InstrumentTerminalModel> list = new ArrayList<>();
		
		int nComputcout = (page - 1) * rows;
		
		sb.append("select * from `instrument_terminal` where 1=1 ");
		
		if(!strInsFactoryID.isEmpty()&&!strInsFactoryID.equals("ȫ��")) {
			
			sb.append(" and  insFactoryID = '"+strInsFactoryID+"' ");
			
		}
		
		if(nInsType>0) {
			
			sb.append(" and insType = '"+nInsType+"' ");
			
		}
		
		
		sb.append(" limit ").append(nComputcout).append(",").append(rows).append(";");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		if(dt!=null&&dt.getRow()!=null&&dt.getRow().size()>0) 
		{
			for(DataRow dr:dt.getRow()) 
			{
				if(dr!=null&&dr.getCol().size()>0)
				{
					InstrumentTerminalModel model=new InstrumentTerminalModel();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setinsFactoryID(dr.getStringColumn("insFactoryID"));										
					String strinsID = dr.getStringColumn("insID");
					model.setinsID(Integer.parseInt(strinsID));
					String strinsType = dr.getStringColumn("insType");
					model.setinsType(Integer.parseInt(strinsType));
					list.add(model);
				}
			}
		}
		
		return list;
	}
	
	
	

	
	
	

}


