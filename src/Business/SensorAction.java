package Business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import Model.SensorModel;
import SqlHelper.MySqlHelper;
import TstDataTable.DataRow;
import TstDataTable.DataTable;

public class SensorAction 
{
	//String strMainDBName = null;

	MySqlHelper mysql = null;
	
	String strPrjName = null;
	
	String strInstrumentTableName = "`instrument_sensor`";

	public SensorAction(String strPrjName) {

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
	public boolean Create(SensorModel modelInfo) 
	{
		// 创建Sql语句
		StringBuffer sb = new StringBuffer();
		{
			sb.append("insert into").append(strInstrumentTableName);
			sb.append(" ( ");
			// 表字段
			sb.append(MySqlHelper.ToSqlEleme("SensorID",true));
			sb.append(MySqlHelper.ToSqlEleme("SensorMeasureType",true));
			sb.append(MySqlHelper.ToSqlEleme("SensorSpec",true));
			sb.append(MySqlHelper.ToSqlEleme("SensorFactory",true));
			sb.append(MySqlHelper.ToSqlEleme("Param1",true));
			sb.append(MySqlHelper.ToSqlEleme("Param2",true));
			sb.append(MySqlHelper.ToSqlEleme("Param3",true));
			sb.append(MySqlHelper.ToSqlEleme("Param4",true));
			sb.append(MySqlHelper.ToSqlEleme("Param5",true));
			sb.append(MySqlHelper.ToSqlEleme("Param6",true));
			sb.append(MySqlHelper.ToSqlEleme("Param7",true));
			sb.append(MySqlHelper.ToSqlEleme("Param8",false));
			
			// values
			sb.append(" ) values( ");
			
			// 表字段值
			sb.append(MySqlHelper.ToSqlStr(modelInfo.getSensorID(),true));
			sb.append(MySqlHelper.ToSqlStr(modelInfo.getSensorMeasureType(),true));
			sb.append(MySqlHelper.ToSqlStr(modelInfo.getSensorSpec(),true));
			sb.append(MySqlHelper.ToSqlStr(modelInfo.getSensorFactory(),true));			
			sb.append(MySqlHelper.ToSqlStr(modelInfo.getParam1(),true));
			sb.append(MySqlHelper.ToSqlStr(modelInfo.getParam2(),true));
			sb.append(MySqlHelper.ToSqlStr(modelInfo.getParam3(),true));
			sb.append(MySqlHelper.ToSqlStr(modelInfo.getParam4(),true));
			sb.append(MySqlHelper.ToSqlStr(modelInfo.getParam5(),true));
			sb.append(MySqlHelper.ToSqlStr(modelInfo.getParam6(),true));
			sb.append(MySqlHelper.ToSqlStr(modelInfo.getParam7(),true));
			sb.append(MySqlHelper.ToSqlStr(modelInfo.getParam8(),false));
			
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
	public boolean Update(SensorModel modelInfo) 
	{
    	// 创建Sql语句
		StringBuffer sb = new StringBuffer();
		{
			sb.append("update ").append(strInstrumentTableName);
			// 表字段,第二个字段中间需要加入','
			sb.append(" set ")
			  .append(MySqlHelper.ToSqlEleme("SensorID",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(modelInfo.getSensorID(),false));
			
			sb.append(",")
			  .append(MySqlHelper.ToSqlEleme("SensorMeasureType",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(modelInfo.getSensorMeasureType(),false));
		
			sb.append(",")
			  .append(MySqlHelper.ToSqlEleme("SensorSpec",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(modelInfo.getSensorSpec(),false));
						
			sb.append(",")
			  .append(MySqlHelper.ToSqlEleme("SensorFactory",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(modelInfo.getSensorFactory(),false));
			
			sb.append(",")
			  .append(MySqlHelper.ToSqlEleme("Param1",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(modelInfo.getParam1(),false));
			
			sb.append(",")
			  .append(MySqlHelper.ToSqlEleme("Param2",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(modelInfo.getParam2(),false));
			
			sb.append(",")
			  .append(MySqlHelper.ToSqlEleme("Param3",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(modelInfo.getParam3(),false));
			
			sb.append(",")
			  .append(MySqlHelper.ToSqlEleme("Param4",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(modelInfo.getParam4(),false));
			
			sb.append(",")
			  .append(MySqlHelper.ToSqlEleme("Param5",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(modelInfo.getParam5(),false));
			
			sb.append(",")
			  .append(MySqlHelper.ToSqlEleme("Param6",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(modelInfo.getParam6(),false));
			
			sb.append(",")
			  .append(MySqlHelper.ToSqlEleme("Param7",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(modelInfo.getParam7(),false));
			
			sb.append(",")
			  .append(MySqlHelper.ToSqlEleme("Param8",false)).append("=")			  
			  .append(MySqlHelper.ToSqlStr(modelInfo.getParam8(),false));
			
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

	public boolean IsExists(Integer nAutoID)
	{
		SensorModel model = Select(nAutoID);
		return (model != null);
	}
	
	public boolean IsExists(String strSensorID)
	{
		SensorModel model = Select(strSensorID);
		return (model != null);
	}
	
	public SensorModel Select(int nAutoID) 
	{
		HashMap<String, Object> condition = new HashMap<String, Object>();
		condition.put("AutoID", nAutoID);
		List<SensorModel> listModels = Select(condition);
		if(listModels.size() > 0)
		{
			return listModels.get(0);
		}
		
		return null;
	}
	
	public SensorModel Select(String SensorID) 
	{
		HashMap<String, Object> condition = new HashMap<String, Object>();
		condition.put("SensorID", SensorID);
		List<SensorModel> listModels = Select(condition);
		if(listModels.size() > 0)
		{
			return listModels.get(0);
		}
		
		return null;
	}
	
	public int SelectCount(HashMap<String, Object> condition) 
	{
		List<SensorModel> list = new ArrayList<>();
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
					SensorModel model=new SensorModel();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setSensorID(dr.getStringColumn("SensorID"));
					model.setSensorMeasureType(dr.getStringColumn("SensorMeasureType"));
					model.setSensorSpec(dr.getStringColumn("SensorSpec"));
					model.setSensorFactory(dr.getStringColumn("SensorFactory"));					
					String strParam1 = dr.getStringColumn("Param1");
					model.setParam1(Float.parseFloat(strParam1));
					String strParam2 = dr.getStringColumn("Param2");
					model.setParam2(Float.parseFloat(strParam2));
					String strParam3 = dr.getStringColumn("Param3");
					model.setParam3(Float.parseFloat(strParam3));
					String strParam4 = dr.getStringColumn("Param4");
					model.setParam4(Float.parseFloat(strParam4));
					String strParam5 = dr.getStringColumn("Param5");
					model.setParam5(Float.parseFloat(strParam5));
					String strParam6 = dr.getStringColumn("Param6");
					model.setParam6(Float.parseFloat(strParam6));
					String strParam7 = dr.getStringColumn("Param7");
					model.setParam7(Float.parseFloat(strParam7));
					String strParam8 = dr.getStringColumn("Param8");
					model.setParam8(Float.parseFloat(strParam8));					
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
	public List<SensorModel> Select(HashMap<String, Object> condition) 
	{
		List<SensorModel> list = new ArrayList<>();
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
					SensorModel model=new SensorModel();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setSensorID(dr.getStringColumn("SensorID"));
					model.setSensorMeasureType(dr.getStringColumn("SensorMeasureType"));
					model.setSensorSpec(dr.getStringColumn("SensorSpec"));
					model.setSensorFactory(dr.getStringColumn("SensorFactory"));									
					String strParam1 = dr.getStringColumn("Param1");
					model.setParam1(Float.parseFloat(strParam1));
					String strParam2 = dr.getStringColumn("Param2");
					model.setParam2(Float.parseFloat(strParam2));
					String strParam3 = dr.getStringColumn("Param3");
					model.setParam3(Float.parseFloat(strParam3));
					String strParam4 = dr.getStringColumn("Param4");
					model.setParam4(Float.parseFloat(strParam4));
					String strParam5 = dr.getStringColumn("Param5");
					model.setParam5(Float.parseFloat(strParam5));
					String strParam6 = dr.getStringColumn("Param6");
					model.setParam6(Float.parseFloat(strParam6));
					String strParam7 = dr.getStringColumn("Param7");
					model.setParam7(Float.parseFloat(strParam7));
					String strParam8 = dr.getStringColumn("Param8");
					model.setParam8(Float.parseFloat(strParam8));
					list.add(model);
				}
			}
		}
		
		return list;
	}
	
	/**
	 * 查询所有传感器信息
	 * @return
	 */
	public List<SensorModel> SelectAllSensor(){
		
		List<SensorModel> list = new ArrayList<>();
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("select * from ").append(strInstrumentTableName).append(";");
		
		// 执行sql
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		
		// 将执行结果转成对象
		if(dt!=null&&dt.getRow()!=null&&dt.getRow().size()>0) 
		{
			for(DataRow dr:dt.getRow()) 
			{
				if(dr!=null&&dr.getCol().size()>0)
				{
					SensorModel model=new SensorModel();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setSensorID(dr.getStringColumn("SensorID"));
					model.setSensorMeasureType(dr.getStringColumn("SensorMeasureType"));
					model.setSensorSpec(dr.getStringColumn("SensorSpec"));
					list.add(model);
				}
			}
		}
		
		return list;
	}
	
	/**
	 * 获取传感器的分页数据总个数
	 * @param strSensorID   传感器编号
	 * @param strSensorType 传感器测量类型
	 * @return
	 */
	public int GetSelectPageDataAllCount(String strSensorID,String strSensorType) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("select count(*) from ").append(strInstrumentTableName).append(" where 1=1 ");
		if(!strSensorID.isEmpty()&&!strSensorID.equals("全部")) {
			sb.append(" and  SensorID = '"+strSensorID+"' ");
		}
		if(!strSensorType.isEmpty()&&!strSensorType.equals("全部")) {
			sb.append(" and SensorMeasureType = '"+strSensorType+"' ");
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
	 * 获取传感器的分页数据
	 * @param strSensorID   传感器编号
	 * @param strSensorType 传感器测量类型
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<SensorModel> GetSelectPageData(String strSensorID,String strSensorType,int page, int rows){
		
		StringBuffer sb=new StringBuffer();
		
		List<SensorModel> list = new ArrayList<>();
		
		int nComputcout = (page - 1) * rows;
		
		sb.append("select * from ").append(strInstrumentTableName).append("where 1=1 ");
		
		if(!strSensorID.isEmpty()&&!strSensorID.equals("全部")) {
			sb.append(" and  SensorID = '"+strSensorID+"' ");
		}
		if(!strSensorType.isEmpty()&&!strSensorType.equals("全部")) {
			sb.append(" and SensorMeasureType = '"+strSensorType+"' ");
		}
		sb.append(" limit ").append(nComputcout).append(",").append(rows).append(";");
		
		// 执行sql
		DataTable dt=mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		
		// 将执行结果转成对象
		if(dt!=null&&dt.getRow()!=null&&dt.getRow().size()>0) 
		{
			for(DataRow dr:dt.getRow()) 
			{
				if(dr!=null&&dr.getCol().size()>0)
				{
					SensorModel model=new SensorModel();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setSensorID(dr.getStringColumn("SensorID"));
					model.setSensorMeasureType(dr.getStringColumn("SensorMeasureType"));
					model.setSensorSpec(dr.getStringColumn("SensorSpec"));
					list.add(model);
				}
			}
		}
		
		return list;
	}

	/**
	 * 获取传感器的分页数据的总个数
	 * @param strSeneorName
	 * @param strSensorType
	 * @return
	 */
	public int GetSeneorSelectPageDataAllCount(String strSensorName,String strSensorType) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("select count(*) from `instrument_sensor` where 1=1 ");
		
		if(!strSensorName.isEmpty()) {
			sb.append(" and SensorName like '%"+strSensorName+"%'");
		}
		
		if(!strSensorType.isEmpty()) {
			sb.append(" and SensorSpec like '%"+strSensorType+"%'");
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
	 * 获取传感器的分页数据
	 * @param strSeneorName
	 * @param strSensorType
	 * @param page
	 * @param rows
	 * @return
	 */
	public StringBuffer GetSeneorSelectPageData(String strSensorName,String strSensorType,int page, int rows) {
		
		StringBuffer sbRes=new StringBuffer();
		
		int nDataCount=GetSeneorSelectPageDataAllCount(strSensorName,strSensorType);
		
		if(nDataCount<=0) {
			sbRes.append("{\"total\":0,\"rows\":[]}");
	
			return sbRes;
		}
		
		int nComputcout = (page - 1) * rows;
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select * from `instrument_sensor` where 1=1 ");
		
		if(!strSensorName.isEmpty()) {
			sb.append(" and SensorName like '%"+strSensorName+"%' ");
		}
		
		if(!strSensorType.isEmpty()) {
			sb.append(" and SensorSpec like '%"+strSensorType+"%' ");
		}
		
		sb.append(" limit ").append(nComputcout).append(",").append(rows).append(";");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		sbRes.append("{");
		sbRes.append("\"rows\":[");
		
		if(dt!=null&&dt.getRow()!=null&&dt.getRow().size()>0) 
		{
			for(DataRow dr:dt.getRow()) 
			{
				if(dr!=null&&dr.getCol().size()>0)
				{
					sbRes.append("{");
					
					sbRes.append(String.format("\"autoid\":\"%s\",", dr.getIntColumn("AutoID")));
					sbRes.append(String.format("\"SensorID\":\"%s\",", dr.getStringColumn("SensorID")));
					sbRes.append(String.format("\"SensorMeasureType\":\"%s\",", dr.getStringColumn("SensorMeasureType")));
					sbRes.append(String.format("\"SensorSpec\":\"%s\",", dr.getStringColumn("SensorSpec")));
					sbRes.append(String.format("\"SensorFactory\":\"%s\",", dr.getStringColumn("SensorFactory")));
					sbRes.append(String.format("\"SensorName\":\"%s\",", dr.getStringColumn("SensorName")));
					sbRes.append(String.format("\"SensorDesc\":\"%s\",", dr.getStringColumn("SensorDesc")));
					sbRes.append(String.format("\"Param1\":\"%s\",", dr.getStringColumn("Param1")));
					sbRes.append(String.format("\"Param2\":\"%s\",", dr.getStringColumn("Param2")));
					sbRes.append(String.format("\"Param3\":\"%s\",", dr.getStringColumn("Param3")));
					sbRes.append(String.format("\"Param4\":\"%s\",", dr.getStringColumn("Param4")));
					sbRes.append(String.format("\"Param5\":\"%s\",", dr.getStringColumn("Param5")));
					sbRes.append(String.format("\"Param6\":\"%s\",", dr.getStringColumn("Param6")));
					sbRes.append(String.format("\"Param7\":\"%s\",", dr.getStringColumn("Param7")));
					sbRes.append(String.format("\"Param8\":\"%s\",", dr.getStringColumn("Param8")));
					
					String strSensorMeasureType=dr.getStringColumn("SensorMeasureType");
					
					switch(strSensorMeasureType) {
					
					case "振弦":
					{
						sbRes.append(String.format("\"SensorPara\":\"k:%s,b:%s\"", dr.getStringColumn("Param1"),dr.getStringColumn("Param2")));
					}
					break;
					case "应变":
					{
						sbRes.append(String.format("\"SensorPara\":\"k:%s,b:%s\"", dr.getStringColumn("Param1"),dr.getStringColumn("Param2")));
					}
					break;
					case "温度":
					{
						sbRes.append(String.format("\"SensorPara\":\"k:%s\"", dr.getStringColumn("Param1")));
					}
					break;
					default:
					{
						sbRes.append(String.format("\"SensorPara\":\"b:%s\"", dr.getStringColumn("Param1")));
					}
					break;
					}
					
					
					sbRes.append("},");
				}
			}
			sbRes.deleteCharAt(sbRes.length() - 1);
		}
		
		

		sbRes.append("],\"total\":").append(nDataCount).append("}");
		
		return sbRes;
		
	}

	/**
	 * 获取传感器名称的下拉数据的总个数
	 * @return
	 */
	public int GetSeneorNameComboPageDataAllcount() {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select count(*) from `instrument_sensor` ;");
		
		String strDBName="tztiot_station";
		
		DataTable dt = mysql.ExecuteQueryDB(strDBName, sb.toString());
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {
			
			int nRes=Integer.parseInt(dt.getRow().get(0).getCol().get(0).getValue().toString());
			
			return nRes;
			
		}
		
		return 0;
	}
	
	/**
	 * 获取传感器名称的下拉数据的总个数
	 * @param page
	 * @param rows
	 * @return
	 */
	public StringBuffer GetSeneorNameComboPageData(int page, int rows) {
		
		StringBuffer sbRes=new StringBuffer();
		
		int nDataCount=GetSeneorNameComboPageDataAllcount();
		
		if(nDataCount<=0) {
			sbRes.append("{\"total\":0,\"rows\":[]}");
	
			return sbRes;
		}
		
		int nComputcout = (page - 1) * rows;
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select * from `instrument_sensor`  ");
		
		sb.append(" limit ").append(nComputcout).append(",").append(rows).append(";");
		
		String strDBName="tztiot_station";
		
		DataTable dt = mysql.ExecuteQueryDB(strDBName, sb.toString());
		
		sbRes.append("{");
		sbRes.append("\"rows\":[");
		
		if(dt!=null&&dt.getRow()!=null&&dt.getRow().size()>0) 
		{
			for(DataRow dr:dt.getRow()) 
			{
				if(dr!=null&&dr.getCol().size()>0)
				{
					sbRes.append("{");
					
					sbRes.append(String.format("\"autoid\":\"%s\",", dr.getIntColumn("AutoID")));
					sbRes.append(String.format("\"sensorName\":\"%s\",", dr.getStringColumn("sensorName")));
					sbRes.append(String.format("\"sensorType\":\"%s\",", dr.getStringColumn("sensorType")));
					sbRes.append(String.format("\"sensorDesc\":\"%s\"", dr.getStringColumn("sensorDesc")));
					sbRes.append("},");
				}
			}
			sbRes.deleteCharAt(sbRes.length() - 1);
		}
		
		

		sbRes.append("],\"total\":").append(nDataCount).append("}");
		
		return sbRes;
	}
	
	/**
	 * 新增传感器
	 * @param model
	 * @return
	 */
	public boolean AddSeneor(SensorModel model) {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("INSERT INTO `instrument_sensor` " + 
				"(`SensorID`, `SensorMeasureType`, `SensorSpec`, `SensorFactory`, " + 
				"`Param1`, `Param2`, `Param3`, `Param4`, `Param5`, `Param6`, `Param7`, `Param8`, " + 
				"`SensorName`, `SensorDesc`) " + 
				"VALUES ('"+model.getSensorID()+"', '"+model.getSensorMeasureType()+"', '"+model.getSensorSpec()+"', '"+model.getSensorFactory()+"', " + 
				"'"+model.getParam1()+"', '"+model.getParam2()+"', '"+model.getParam3()+"', '"+model.getParam4()+"', '"+model.getParam5()+"', '"+model.getParam6()+"', '"+model.getParam7()+"', '"+model.getParam8()+"', " + 
				"'"+model.getSensorName()+"', '"+model.getSensorDesc()+"');");
		
		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		
		if (nRes == 1) {
			
			//传感器子表新增
			
			CreateSonsorData(model.getSensorID(),model.getSensorMeasureType());
			
			return true;
		} else {
			return false;
		}
		
	}
	
	/**
	 * 修改传感器信息
	 * @param model
	 * @return
	 */
	public boolean UpdateSensor(SensorModel model) {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("UPDATE `instrument_sensor` SET " + 
				"`SensorID`='"+model.getSensorID()+"', `SensorMeasureType`='"+model.getSensorMeasureType()+"', " + 
				"`SensorSpec`='"+model.getSensorSpec()+"', `SensorFactory`='"+model.getSensorFactory()+"', " + 
				"`Param1`='"+model.getParam1()+"', `Param2`='"+model.getParam2()+"', `Param3`='"+model.getParam3()+"', `Param4`='"+model.getParam4()+"', " + 
				"`Param5`='"+model.getParam5()+"', `Param6`='"+model.getParam6()+"', `Param7`='"+model.getParam7()+"', `Param8`='"+model.getParam8()+"', " + 
				"`SensorName`='"+model.getSensorName()+"', `SensorDesc`='"+model.getSensorDesc()+"' WHERE (`AutoID`='"+model.getAutoID()+"');");
		
		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		
		if (nRes == 1) {
			
			//删除老的信息
			
			DeleteSeneorData(model.getSensorID());
			
			//传感器子表新增
			
			CreateSonsorData(model.getSensorID(),model.getSensorMeasureType());
			
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 删除传感器信息
	 * @param model
	 * @return
	 */
	public boolean DeleteSensor(SensorModel model) {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("delete from `instrument_sensor` where SensorID='"+model.getSensorID()+"';");
		
		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		
		if (nRes == 1) {
			
			DeleteSeneorData(model.getSensorID());
			
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 新增传感器的子表数据
	 * 
	 * @param strSeneorID
	 *            传感器ID
	 * @param strSensorMeasureType
	 *            传感器测量类型 1-应变；2-桥式传感器；3-温度；4-倾角；5-电流；6-索力；7-振弦；8-静力水准仪；9-风速
	 * @return
	 */
	public boolean CreateSonsorData(String strSensorID, String strSensorMeasureType) {

		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO `instrument_sensor_data` (`SensorID`, `ValueName`, `ValueUnit`) VALUES ");

		switch (strSensorMeasureType) {
		case "应变": {
			sb.append("('"+strSensorID+"', '应变', 'uε');");
		}
			break;
		case "位移": {
			sb.append("('"+strSensorID+"', '位移', 'mm');");
		}
			break;
		case "温度": {
			sb.append("('"+strSensorID+"', '温度', '℃');");
		}
			break;
		case "倾角": {
			sb.append("('"+strSensorID+"', '倾角', '°');");
		}
			break;
		case "电流": {
			sb.append("('"+strSensorID+"', '电流', 'mV');");
		}
			break;
		case "索力": {
			sb.append("('"+strSensorID+"', '索力', 'm/s²');");
		}
			break;
		case "挠度": {
			sb.append("('"+strSensorID+"', '挠度', 'mm');");
		}
			break;
		case "裂缝": {
			sb.append("('"+strSensorID+"', '裂缝', 'mm');");
		}
			break;
		case "振弦": {
			sb.append("('"+strSensorID+"', '频率', 'Hz'),");
			sb.append("('"+strSensorID+"', '应变', 'με'),");
			sb.append("('"+strSensorID+"', '温度', '℃');");
		}
			break;
		case "风速": {
			sb.append("('"+strSensorID+"', '风速', 'm/s'),");
			sb.append("('"+strSensorID+"', '风向', '°');");
		}
			break;
		case "测斜仪": {
			sb.append("('"+strSensorID+"', '测斜仪', '°');");
		}
			break;
		case "沉降仪": {
			sb.append("('"+strSensorID+"', '沉降仪', 'mm');");
		}
			break;
		case "静力水准仪": {
			sb.append("('"+strSensorID+"', '静力水准仪', 'mm');");
		}
			break;
		case "桥式传感器": {
			sb.append("('"+strSensorID+"', '桥式传感器', 'mV');");
		}
			break;
		}
		
		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());

		if (nRes > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 删除传感器子表数据
	 * @param strSeneorID
	 * @return
	 */
	public boolean DeleteSeneorData(String strSensorID) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("delete from `instrument_sensor_data` where SensorID='"+strSensorID+"';");
		
		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());

		if (nRes > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 验证传感器是否建立匹配关系
	 * @param strSensorID
	 * @return true:匹配 false:未匹配
	 */
	public boolean CheckSeneorMap(String strSensorID) {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select count(*) from `point_chn_sensor_relation` where SensorID='"+strSensorID+"';");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {
			
			int nRes=Integer.parseInt(dt.getRow().get(0).getCol().get(0).getValue().toString());
			
			if(nRes>0) {
				
				return true;
				
			}
			
		}
		
		return false;
	}
	
}


