package Business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.DictionaryInfo;
import SqlHelper.MySqlHelper;
import TstDataTable.DataRow;
import TstDataTable.DataTable;

/**
 * �ֵ䴦��
 * @author Administrator
 *
 */
public class DictionaryInfoAction {

	String strMainDBName = null;

	MySqlHelper mysql = null;

	public DictionaryInfoAction() {

		mysql = new MySqlHelper();

		strMainDBName = mysql.MainDBName();
	}
	
	/**
	 * �����ֵ���Ϣ
	 * @param model
	 * @return
	 */
	public boolean CreateDicInfo(DictionaryInfo model) {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("INSERT INTO " + 
				"`dictionary` " + 
				"(`DicValue`, `isUsed`, `ParentDic`, `ParentID`) " + 
				"VALUES ('"+model.getDicValue()+"', '"+model.getIsUsed()+"', '"+model.getParentDic()+"', '"+model.getParentID()+"');");
		
		int nRes=mysql.ExecuteQueryNoneDB(strMainDBName, sb.toString());
		
		return nRes==1;
		
	}
	
	/**
	 * �޸��ֵ���Ϣ
	 * @param model
	 * @return
	 */
	public boolean UpdateDicInfo(DictionaryInfo model) {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("UPDATE `dictionary` " + 
				"SET " + 
				"`DicValue`='"+model.getDicValue()+"', " + 
				"`isUsed`='"+model.getIsUsed()+"', " + 
				"`ParentDic`='"+model.getParentDic()+"', " + 
				"`ParentID`='"+model.getParentID()+"' WHERE " + 
				"(`AutoID`='"+model.getAutoID()+"');");
		
		int nRes=mysql.ExecuteQueryNoneDB(strMainDBName, sb.toString());
		
		return nRes==1;
	}
	
	/**
	 * �޸��ֵ�״̬
	 * @param strState
	 * @param nAutoID
	 * @return
	 */
	public boolean UpdateDicInfoState(String strState,int nAutoID) {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("UPDATE `dictionary` SET "
				+"`isUsed`='"+strState+"'"
				+" WHERE " +
				"(`AutoID`='"+nAutoID+"');"
				);
		
		int nRes=mysql.ExecuteQueryNoneDB(strMainDBName, sb.toString());
		
		return nRes==1;
	}
	
	/**
	 * ɾ���ֵ���Ϣ
	 * @param nAutoID
	 * @return
	 */
	public boolean DeleteDicInfo(int nAutoID) {
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("delete from `dictionary` where `AutoID`='"+nAutoID+"';");
		
		int nRes=mysql.ExecuteQueryNoneDB(strMainDBName, sb.toString());
		
		return nRes==1;
	}
	
	/**
	 * ���ݸ��ڵ��ID�Ҵ����е��ֵ�ֵ
	 * @param nParentID
	 * @return
	 */
	public List<DictionaryInfo> GetDicInfoByParentID(int nParentID){
		
		List<DictionaryInfo>  listDict=new ArrayList<>();
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("select * from `dictionary` where ParentID='"+nParentID+"'");
		
		DataTable dt = mysql.ExecuteQueryDB(strMainDBName, sb.toString());
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {

					DictionaryInfo model = new DictionaryInfo();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setDicValue(dr.getStringColumn("DicValue"));
					model.setIsUsed(dr.getIntColumn("isUsed"));
					model.setParentDic(dr.getStringColumn("ParentDic"));
					model.setParentID(dr.getIntColumn("ParentID"));
					listDict.add(model);
				}
			}

		}
		
		return listDict;
	}
	
	/**
	 * �����ϼ��ֵ���Ϣ��ѯ
	 * @param strParentDic
	 * @return
	 */
	public List<DictionaryInfo> GetDicInfoByParenDic(String strParentDic){
		
		List<DictionaryInfo>  listDict=new ArrayList<>();
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("select * from `dictionary` where ParentDic='"+strParentDic+"'");
		
		DataTable dt = mysql.ExecuteQueryDB(strMainDBName, sb.toString());
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

			for (DataRow dr : dt.getRow()) {
				if (dr != null && dr.getCol().size() > 0) {

					DictionaryInfo model = new DictionaryInfo();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setDicValue(dr.getStringColumn("DicValue"));
					model.setIsUsed(dr.getIntColumn("isUsed"));
					model.setParentDic(dr.getStringColumn("ParentDic"));
					model.setParentID(dr.getIntColumn("ParentID"));
					listDict.add(model);
				}
			}

		}
		
		return listDict;
	}
	
	/**
	 * ��֤�ֵ�ֵ�Ƿ��ظ���
	 * @param strParentDic
	 * @param strDicValue
	 * @return
	 */
	public boolean CheckDicValue(String strParentDic,String strDicValue) {
		
		List<DictionaryInfo>  listDict=GetDicInfoByParenDic(strParentDic);
		
		if(listDict==null||listDict.size()<=0) {
			return false;
		}
		
		for(DictionaryInfo item: listDict) {
			if(item.getDicValue().equals(strDicValue)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * ����������͵��ֵ�����
	 * @param model
	 * @return
	 */
	public boolean CreateMonitorDicInfo(DictionaryInfo model) {
		
		StringBuffer sb=new StringBuffer();
		
		if(model.getParentID()==0) {
			//�����Ǽ������
			
			sb.append("INSERT INTO `monitor_type` (`monitorTypeName`) VALUES ('"+model.getDicValue()+"');");
			
		}else {
			//�����Ǽ�����ͷ���
			
			sb.append("INSERT INTO `monitor_type_methord` (`monitorTypeName`, `monitorTypeMethordName`) VALUES ('"+model.getParentDic()+"', '"+model.getDicValue()+"');");
			
		}
		
		int nRes=mysql.ExecuteQueryNoneDB(strMainDBName, sb.toString());
		
		return nRes==1;
	}
	
	/**
	 * �޸ļ�������ֵ���Ϣ
	 * @param model
	 * @return
	 */
	public boolean UpdateMonitorDicInfo(DictionaryInfo model) {
		
		StringBuffer sb=new StringBuffer();
		
		if(model.getParentID()==0) {
			//�����Ǽ������
			
			sb.append("UPDATE `monitor_type` SET `monitorTypeName`='"+model.getDicValue()+"' WHERE (`AutoID`='"+model.getAutoID()+"');");
			
		}else {
			//�����Ǽ�����ͷ���
			
			sb.append("UPDATE `monitor_type_methord` SET `monitorTypeName`='"+model.getParentDic()+"', `monitorTypeMethordName`='"+model.getDicValue()+"' WHERE (`AutoID`='"+model.getAutoID()+"');");
			
		}
			
		int nRes=mysql.ExecuteQueryNoneDB(strMainDBName, sb.toString());
		
		return nRes==1;
	}
	
	/**
	 * ɾ����������ֵ���Ϣ
	 * @param model
	 * @return
	 */
	public boolean DeleteMonitorDicInfo(DictionaryInfo model) {
		
		StringBuffer sb=new StringBuffer();
		
		if(model.getParentID()==0) {
			//�����Ǽ������
			
			sb.append("DELETE FROM `monitor_type` WHERE `AutoID`='"+model.getAutoID()+"';");
			
		}else {
			//�����Ǽ�����ͷ���
			
			sb.append("DELETE FROM `monitor_type_methord` WHERE `AutoID`='"+model.getAutoID()+"';");
			
		}
		
		int nRes=mysql.ExecuteQueryNoneDB(strMainDBName, sb.toString());
		
		return nRes==1;
	}
	 
	/**
	 * ���ݸ��ڵ��ID�Ҵ����еļ�������ֵ�ֵ
	 * @param nParentID
	 * @return
	 */
	public List<DictionaryInfo> GetMonitorDicInfoByParentID(int nParentID){
		
		List<DictionaryInfo>  listDict=new ArrayList<>();
		
		StringBuffer sb = new StringBuffer();
		
		if(nParentID==0) {
			
			sb.append("select * from `monitor_type`");
			
			DataTable dt = mysql.ExecuteQueryDB(strMainDBName, sb.toString());
			
			if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

				for (DataRow dr : dt.getRow()) {
					if (dr != null && dr.getCol().size() > 0) {

						DictionaryInfo model = new DictionaryInfo();
						model.setAutoID(dr.getIntColumn("autoid"));
						model.setDicValue(dr.getStringColumn("monitorTypeName"));
						model.setIsUsed(1);
						model.setParentDic("�������");
						model.setParentID(0);
						listDict.add(model);
					}
				}

			}
			
		} else {
			
			sb.append("SELECT * FROM `monitor_type_methord` WHERE `monitorTypeName` in (SELECT `monitorTypeName` FROM `monitor_type` WHERE (`AutoID`='"+nParentID+"'));");
			
			DataTable dt = mysql.ExecuteQueryDB(strMainDBName, sb.toString());
			
			if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {

				for (DataRow dr : dt.getRow()) {
					if (dr != null && dr.getCol().size() > 0) {

						DictionaryInfo model = new DictionaryInfo();
						model.setAutoID(dr.getIntColumn("autoid"));
						model.setDicValue(dr.getStringColumn("monitorTypeMethordName"));
						model.setIsUsed(1);
						model.setParentDic(dr.getStringColumn("monitorTypeName"));
						model.setParentID(nParentID);
						listDict.add(model);
					}
				}

			}
			
		}
		
		return listDict;
	}

	/**
	 * ���ݸ��ڵ���Ϣ�ҵ���Ӧ���ӽڵ��ƥ���ϵ
	 * @param strParentDic
	 * @return
	 */
	public Map<Integer,String> GetDicMapInfo(String strParentDic){
		
		List<DictionaryInfo> list=GetDicInfoByParenDic(strParentDic);
		
		if(list==null||list.size()<=0) {
			return null;
		}
		
		Map<Integer,String> map=new HashMap<Integer, String>();
		
		for(DictionaryInfo item:list) {
			
			map.put(item.getAutoID(), item.getDicValue());
		}
		
		return map;
	}
	

}
