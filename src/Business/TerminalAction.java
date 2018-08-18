package Business;

import Model.InstrumentTerminalModel;
import SqlHelper.MySqlHelper;
import TstDataTable.DataRow;
import TstDataTable.DataTable;

/**
 * �ն˵Ĵ���
 * @author Administrator
 *
 */
public class TerminalAction {
	
	MySqlHelper mysql = null;
	
	String strPrjName = null;
	
	public TerminalAction(String strPrjName) {

		mysql = new MySqlHelper();
		
		this.strPrjName=strPrjName;
	}
	
	/**
	 * ��ȡ�ն˷�ҳ���ݵ��ܸ���
	 * @return
	 */
	public int GetTerminalPageAllData(String strTerminalName,String strTerminalType) {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select count(*) from `instrument_terminal` where 1=1 ");
		
		if(!strTerminalName.isEmpty()) {
			sb.append(" and terminalName like '%"+strTerminalName+"%' ");
		}
		
		if(!strTerminalType.isEmpty()) {
			sb.append(" and terminalType like '%"+strTerminalType+"%' ");
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
	 * @param strTerminalName
	 * @param strTerminalType
	 * @param page
	 * @param rows
	 * @return
	 */
	public StringBuffer GetTerminalPage(String strTerminalName,String strTerminalType,int page, int rows) {
		
		StringBuffer sbRes=new StringBuffer();
		
		int nDataCount=GetTerminalPageAllData(strTerminalName,strTerminalType);
		
		if(nDataCount<=0) {
			sbRes.append("{\"total\":0,\"rows\":[]}");
	
			return sbRes;
		}
		
		int nComputcout = (page - 1) * rows;
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select * from `instrument_terminal` where 1=1 ");
		
		if(!strTerminalName.isEmpty()) {
			sb.append(" and terminalName like '%"+strTerminalName+"%' ");
		}
		
		if(!strTerminalType.isEmpty()) {
			sb.append(" and terminalType like '%"+strTerminalType+"%' ");
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
					sbRes.append(String.format("\"insFactoryID\":\"%s\",", dr.getStringColumn("insFactoryID")));
					sbRes.append(String.format("\"insID\":\"%s\",", dr.getIntColumn("insID")));
					sbRes.append(String.format("\"insType\":\"%s\",", dr.getIntColumn("insType")));
					sbRes.append(String.format("\"insChnCount\":\"%s\",", dr.getIntColumn("insChnCount")));
					sbRes.append(String.format("\"terminalName\":\"%s\",", dr.getStringColumn("terminalName")));
					sbRes.append(String.format("\"terminalType\":\"%s\",", dr.getStringColumn("terminalType")));
					sbRes.append(String.format("\"terminalDesc\":\"%s\",", dr.getStringColumn("terminalDesc")));
					sbRes.append(String.format("\"netID\":\"%s\"", dr.getStringColumn("netID")));
					
					sbRes.append("},");
				}
			}
			sbRes.deleteCharAt(sbRes.length() - 1);
		}
		
		

		sbRes.append("],\"total\":").append(nDataCount).append("}");
		
		return sbRes;
	}
	
	/**
	 * ��ȡ�ն˳���������������и���
	 * @return
	 */
	public int GetTerminalFactoryComboAllCount() {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select count(*) from `instrument` as a LEFT JOIN `instrument_terminal` as b " + 
				"  ON a.insType=b.insType ;");
		
		String strDBName="tztiot_station";
		
		DataTable dt = mysql.ExecuteQueryDB(strDBName, sb.toString());
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {
			
			int nRes=Integer.parseInt(dt.getRow().get(0).getCol().get(0).getValue().toString());
			
			return nRes;
			
		}
		
		return 0;
	}
	
	/**
	 * ��ȡ�ն˳�����������ķ�ҳ����
	 * @param page
	 * @param rows
	 * @return
	 */
	public StringBuffer GetTerminalFactoryComboData(int page, int rows) {
		
		StringBuffer sbRes=new StringBuffer();
		
		int nDataCount=GetTerminalFactoryComboAllCount();
		
		if(nDataCount<=0) {
			sbRes.append("{\"total\":0,\"rows\":[]}");
	
			return sbRes;
		}
		
		int nComputcout = (page - 1) * rows;
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select * from `instrument` as a LEFT JOIN `instrument_terminal` as b " + 
				"  ON a.insType=b.insType ");
		
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
					sbRes.append(String.format("\"insFactoryID\":\"%s\",", dr.getStringColumn("insFactoryID")));
					sbRes.append(String.format("\"insID\":\"%s\",", dr.getIntColumn("caseID")));
					sbRes.append(String.format("\"insType\":\"%s\",", dr.getIntColumn("insType")));
					sbRes.append(String.format("\"insChnCount\":\"%s\",", dr.getIntColumn("chnCount")));
					sbRes.append(String.format("\"terminalName\":\"%s\",", dr.getStringColumn("terminalName")));
					sbRes.append(String.format("\"terminalType\":\"%s\",", dr.getStringColumn("terminalType")));
					sbRes.append(String.format("\"terminalDesc\":\"%s\",", dr.getStringColumn("terminalDesc")));
					sbRes.append(String.format("\"netID\":\"%s\"", dr.getStringColumn("netID")));
					
					sbRes.append("},");
				}
			}
			sbRes.deleteCharAt(sbRes.length() - 1);
		}
		
		

		sbRes.append("],\"total\":").append(nDataCount).append("}");
		
		return sbRes;
	}
	
	/**
	 * �����µ��ն�
	 * @param model
	 * @return
	 */
	public boolean CreateTerminal(InstrumentTerminalModel model) {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("INSERT INTO `instrument_terminal` " + 
				"(`insFactoryID`, `insID`, `insType`, `insChnCount`, " + 
				"`terminalName`, `terminalType`, `terminalDesc`, `terminalRemark`, `netID`) " + 
				" VALUES ('"+model.getinsFactoryID()+"', '"+model.getinsID()+"', '"+model.getinsType()+"', '"+model.getinsChnCount()+"', "
				+ "'"+model.getTerminalName()+"', '"+model.getTerminalType()+"', '"+model.getTerminalDesc()+"', '"+model.getTerminalRemark()+"', '"+model.getNetID()+"');");
		
		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		
		if (nRes == 1) {
			
			//�����ն˵��ӱ�����
			
			CreateInstrumentTerminalChn(model.getinsFactoryID(),model.getinsChnCount());
			
			return true;
		} else {
			return false;
		}
	}
	
	
	
	/**
	 * �޸��ն���Ϣ
	 * @param model
	 * @return
	 */
	public boolean UpdateTerminal(InstrumentTerminalModel model,String strOldFactoryID) {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("UPDATE `instrument_terminal` " + 
				"SET `insFactoryID`='"+model.getinsFactoryID()+"', `insID`='"+model.getinsID()+"', `insType`='"+model.getinsType()+"', `insChnCount`='"+model.getinsChnCount()+"', " + 
				"`terminalName`='"+model.getTerminalName()+"', `terminalType`='"+model.getTerminalType()+"', `terminalDesc`='"+model.getTerminalDesc()+"', `terminalRemark`='"+model.getTerminalRemark()+"', " + 
				"`netID`='"+model.getNetID()+"' WHERE (`AutoID`='"+model.getAutoID()+"');");
		
		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		
		if(nRes == 1) {
			
			//ɾ���ӱ���ԭ�е�
			
			DeleteInstrumentTerminalChn(strOldFactoryID);
			
			//����ͨ���ӱ���Ϣ
			
			CreateInstrumentTerminalChn(model.getinsFactoryID(),model.getinsChnCount());
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * ɾ���ն���Ϣ
	 * @param model
	 * @return
	 */
	public boolean DeleteTerminal(InstrumentTerminalModel model) {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("delete from `instrument_terminal` where `AutoID`='"+model.getAutoID()+"';");
		
		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		
		if(nRes==1) {
			
			//	ɾ���ӱ���ԭ�е�
			
			DeleteInstrumentTerminalChn(model.getinsFactoryID());
			
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * �����ն˵�ͨ����
	 * @param strinsFactoryID
	 * @param ninsChnCount
	 * @return
	 */
	public boolean CreateInstrumentTerminalChn(String strinsFactoryID,int ninsChnCount) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("INSERT INTO `instrument_terminal_chn` (`insFactoryID`, `chnID`) VALUES ");
		
		for(int i=1;i<=ninsChnCount;i++) {
			
			sb.append("('"+strinsFactoryID+"', '"+i+"'),");
			
		}
		
		sb.deleteCharAt(sb.length() - 1);

		sb.append(";");
		
		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		
		if (nRes > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * ɾ���ն˵�ͨ��
	 * @param strinsFactoryID
	 * @return
	 */
	public boolean DeleteInstrumentTerminalChn(String strinsFactoryID) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("delete from `instrument_terminal_chn` where insFactoryID=`"+strinsFactoryID+"`;");
		
		int nRes = mysql.ExecuteQueryNonePrj(strPrjName, sb.toString());
		
		if (nRes > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * ����ն��Ƿ���ƥ��
	 * @param strinsFactoryID
	 * @return true:ƥ�� false:δƥ��
	 */
	public boolean CheckTerminalMap(String strinsFactoryID) {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select count(*) from `point_chn_sensor_relation` where insFactoryID='"+strinsFactoryID+"';");
		
		DataTable dt = mysql.ExecuteQueryPrj(strPrjName, sb.toString());
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {
			
			int nRes=Integer.parseInt(dt.getRow().get(0).getCol().get(0).getValue().toString());
			
			if(nRes>0) {
				
				return true;
				
			}
			
		}
		
		return false;
	}
	
	/**
	 * ����ն˳�������Ƿ����
	 */
	public boolean CheckTerminalisExist(String strinsFactoryID) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from `instrument_terminal` where insFactoryID='"+strinsFactoryID+"';");
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
