package Business;

import java.util.ArrayList;
import java.util.List;

import Model.ChargePerson;
import SqlHelper.MySqlHelper;
import TstDataTable.DataRow;
import TstDataTable.DataTable;

/**
 * 负责人信息的操作
 * 
 * @author Administrator
 *
 */
public class ChargePersonAction {

	String strMainDBName = null;

	MySqlHelper mysql = null;

	public ChargePersonAction() {

		mysql = new MySqlHelper();

		strMainDBName = mysql.MainDBName();
	}

	/**
	 * 获取负责人信息的总个数
	 * 
	 * @return
	 */
	public int GetPersonInfoDataCount() {

		int nDataCount = 0;

		StringBuffer sb = new StringBuffer();

		sb.append("select count(*) from `project_chargeperson`;");

		DataTable dt = mysql.ExecuteQueryDB(strMainDBName, sb.toString());

		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {
			nDataCount = Integer.parseInt(dt.getRow().get(0).getCol().get(0).getValue().toString());
		}

		return nDataCount;
	}
	
	/**
	 * 获取负责人信息
	 * @param page 页数
	 * @param rows 每页个数
	 * @return
	 */
	public List<ChargePerson> GetPersonInfoData(int page,int rows) {
		
		List<ChargePerson> listPerson=new ArrayList<>();
		
		int nComputcout = (page - 1) * rows;
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select * from `project_chargeperson` limit ").append(nComputcout).append(",").append(rows).append(";");
		
		DataTable dt = mysql.ExecuteQueryDB(strMainDBName, sb.toString());
		
		if (dt != null && dt.getRow() != null && dt.getRow().size() > 0) {
			
			for(DataRow dr:dt.getRow()) {
				if(dr!=null&&dr.getCol().size()>0) {
					ChargePerson model=new ChargePerson();
					model.setAutoID(dr.getIntColumn("autoid"));
					model.setPersonName(dr.getStringColumn("personName"));
					model.setPersonCompany(dr.getStringColumn("personCompany"));
					model.setPersonDepartName(dr.getStringColumn("personDepartName"));
					model.setPersonSex(dr.getStringColumn("personSex"));
					model.setPersonTel(dr.getStringColumn("personTel"));
					model.setPersonEmail(dr.getStringColumn("personEmail"));
					listPerson.add(model);
				}
			}
			
		}
		
		return listPerson;
	}
	
}
