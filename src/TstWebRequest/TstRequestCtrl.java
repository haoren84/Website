package TstWebRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Business.ChargePersonAction;
import Business.MainPrjInfoAction;
import Business.MonitorPrjAction;
import Business.MonitorTypeAction;
import Common.CookieHelp;
import Model.ChargePerson;
import Model.MainProjectInfo;
import Model.Monitor;

/**
 * ������
 * 
 * @author Administrator
 *
 */
public class TstRequestCtrl {

	/**
	 * ���ض�Ӧ������ַ���
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String ReturnResquest(HttpServletRequest request, HttpServletResponse response) {

		if (request.getParameter("action") == null) {
			return "";
		}

		String strActionName = new String(request.getParameter("action"));

		StringBuffer sb = new StringBuffer();

		if (strActionName == null || strActionName.isEmpty()) {
			return sb.toString();
		}
		switch (strActionName) {

		case "GetLeftInfoInMap":// ��ȡ��ͼ��ҳ�����˵�ҳ��
		{
			/*
			 * sb = new StringBuffer(
			 * "[{\"name\":\"���ݼ��\",\"type\":\"gallery\",\"id\":29,\"x\":106.47386169433594,\"y\":26.562959671020508,"
			 * +
			 * "\"monitor\":true,\"desc\":\"\",\"address\":\"����ʡ��������������վ·\",\"chargePerson\":\"test\"},{\"name\":\"���Ӹ���\",\"type\":\"gallery\",\"id\":43,\"x\":116.46359252929688,\"y\":39.84120178222656,\"monitor\":true,\"desc\":\"\",\"address\":\"��\",\"chargePerson\":\"test\"},{\"name\":\"�������\",\"type\":\"gallery\",\"id\":44,\"x\":106.5849609375,\"y\":29.561830520629883,\"monitor\":true,\"desc\":\"\",\"address\":\"����\",\"chargePerson\":\"test\"},{\"name\":\"����201707\",\"type\":\"gallery\",\"id\":45,\"monitor\":true,\"desc\":\"\",\"address\":\"����\",\"chargePerson\":\"test\"},{\"name\":\"cesgi\",\"type\":\"gallery\",\"id\":61,\"x\":120.40521240234375,\"y\":31.458370208740234,\"monitor\":true,\"desc\":\"\",\"address\":\"������\",\"chargePerson\":\"test\"}]"
			 * );
			 */
			sb = GetLeftInfoInMap();
		}
			break;

		case "GetChargePersonInfo":// ��ȡ��Ŀ��������Ϣ
		{
			sb = GetChargePersonInfo(request);
		}
			break;

		case "AddPrjInfo":// ������Ŀ��Ϣ
		{
			sb.append("{\"result\":\"").append(AddPrjInfo(request)).append("\"}");
		}
			break;
		case "UpdatePrjInfo"://�޸���Ŀ��Ϣ  
		{
			sb.append("{\"result\":\"").append(UpdatePrjInfo(request)).append("\"}");
		}
		break;
		

		case "GetPrjInfoByID":// ����ID�ҵ���Ŀ��Ϣ
		{
			sb = GetPrjInfoByID(request);
		}
			break;
		case "GetMonitorPrjPageData":// ��ȡ�����Ŀ�ķ�ҳ����
		{
			sb = this.GetMonitorPrjPageData(request);
		}
			break;
		case "AddMonitorPrjInfo":// ���������Ŀ��Ϣ
		{
			sb.append("{\"result\":\"").append(this.AddMonitorPrjInfo(request)).append("\"}");
		}
			break;
		case "GetMonitorTypePageData":// ��ȡ���������ķ�ҳ����
		{
			sb = GetMonitorTypePageData(request);
		}
			break;
		case "GetMonitorMethordPageData":// ��ȡ��Ӧ�������͵ķ����ķ�ҳ����
		{
			sb = GetMonitorMethordPageData(request);
		}
			break;
		case "AddMonitorByAddCount":// �������������������
		{
			MonitorPointCtrl pointCtrl = new MonitorPointCtrl();
			sb.append("{\"result\":\"").append(pointCtrl.AddMonitorByAddCount(request)).append("\"}");
		}
			break;
		case "GetMonitorPointPageData":// ��ȡ���ķ�ҳ����
		{
			MonitorPointCtrl pointCtrl = new MonitorPointCtrl();
			sb = pointCtrl.GetMonitorPointPageData(request);
		}
			break;
		case "GetAllMonitorPointData":// ��ȡ��Ӧ����Ŀ�����еĲ��
		{
			MonitorPointCtrl pointCtrl = new MonitorPointCtrl();
			sb = pointCtrl.GetAllMonitorPointData(request);
		}
			break;
		case "DeleteMonitorInfo"://ɾ�������Ŀ��Ϣ
		{
			//DeleteMonitorPrjInfo
			sb.append("{\"result\":\"").append(DeleteMonitorPrjInfo(request)).append("\"}");
		}
		break;
		case "GetPrjInfoTableData"://
		{
			sb=GetPrjInfoTableData();
		}
		break;

		default:
			break;

		}

		return sb.toString();
	}

	/**
	 * ��ͼ��ҳ�����б���Ϣ
	 * 
	 * @return
	 */
	public StringBuffer GetLeftInfoInMap() {

		MainPrjInfoAction action = new MainPrjInfoAction();

		List<MainProjectInfo> listPrj = action.Retrieve();

		StringBuffer sb = new StringBuffer();

		sb.append("[");

		if (listPrj == null || listPrj.size() <= 0) {
			sb.append("]");
		} else {

			for (MainProjectInfo model : listPrj) {

				sb.append("{");

				sb.append(String.format("\"name\":\"%s\",", model.getPrjName()));
				sb.append(String.format("\"type\":\"%d\",", model.getPrjType()));
				sb.append(String.format("\"id\":\"%d\",", model.getAutoID()));
				sb.append(String.format("\"x\":\"%f\",", model.getPrjLng()));
				sb.append(String.format("\"y\":\"%f\",", model.getPrjLat()));
				sb.append(String.format("\"address\":\"%s\",", model.getPrjAddress()));
				sb.append(String.format("\"remark\":\"%s\",", model.getRemark()));
				sb.append(String.format("\"chargePerson\":\"%s\"", model.getPrjManager()));
				sb.append("},");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("]");
		}

		return sb;
	}
	
	/**
	 * ��ȡ������Ϣ�ı������
	 * @return
	 */
	public StringBuffer GetPrjInfoTableData() {
		
		MainPrjInfoAction action = new MainPrjInfoAction();

		List<MainProjectInfo> listPrj = action.Retrieve();

		StringBuffer sb = new StringBuffer();
		
		if(listPrj==null||listPrj.size()<=0) {
			
			sb.append("{\"total\":0,\"rows\":[]}");

			return sb;
			
		}else {
			
			sb.append("{");
			
			sb.append("\"rows\":[");
			
			for (MainProjectInfo model : listPrj) {

				sb.append("{");

				sb.append(String.format("\"name\":\"%s\",", model.getPrjName()));
				sb.append(String.format("\"type\":\"%d\",", model.getPrjType()));
				sb.append(String.format("\"id\":\"%d\",", model.getAutoID()));
				sb.append(String.format("\"x\":\"%f\",", model.getPrjLng()));
				sb.append(String.format("\"y\":\"%f\",", model.getPrjLat()));
				sb.append(String.format("\"address\":\"%s\",", model.getPrjAddress()));
				sb.append(String.format("\"remark\":\"%s\",", model.getRemark()));
				sb.append(String.format("\"chargePerson\":\"%s\"", model.getPrjManager()));
				sb.append("},");
			}
			
			sb.deleteCharAt(sb.length() - 1);

			sb.append("],\"total\":").append(listPrj.size()).append("}");
		}
		
		return sb;
	}
	
	

	/**
	 * ��ȡ��������Ϣ
	 * 
	 * @param request
	 *            ������Ϣ��page ��ǰҳ�� rows ÿҳ������
	 * @return
	 */
	public StringBuffer GetChargePersonInfo(HttpServletRequest request) {

		if (request.getParameter("page") == null || request.getParameter("rows") == null) {
			return null;
		}

		int page = Integer.parseInt(request.getParameter("page").toString());

		int rows = Integer.parseInt(request.getParameter("rows").toString());

		ChargePersonAction action = new ChargePersonAction();

		int nDataCount = action.GetPersonInfoDataCount();

		List<ChargePerson> listPerson = action.GetPersonInfoData(page, rows);

		StringBuffer sb = new StringBuffer();

		sb.append("{");

		if (nDataCount == 0 || listPerson == null || listPerson.size() <= 0) {
			sb.append("}");
		} else {
			sb.append("\"rows\":[");
			for (ChargePerson person : listPerson) {
				sb.append("{");
				sb.append(String.format("\"id\":\"%d\",", person.getAutoID()));
				sb.append(String.format("\"name\":\"%s\",", person.getPersonName()));
				sb.append(String.format("\"company.name\":\"%s\",", person.getPersonCompany()));
				sb.append(String.format("\"tel\":\"%s\",", person.getPersonTel()));
				sb.append(String.format("\"email\":\"%s\",", person.getPersonEmail()));
				sb.append(String.format("\"sex\":\"%s\",", person.getPersonSex()));
				sb.append(String.format("\"depart.name\":\"%s\"", person.getPersonDepartName()));
				sb.append("},");
			}
			sb.deleteCharAt(sb.length() - 1);

			sb.append("],\"total\":").append(nDataCount).append("}");
		}

		return sb;
	}

	/**
	 * ����������Ϣ
	 * 
	 * @param request
	 *            ������Ϣ
	 * @return
	 */
	public boolean AddPrjInfo(HttpServletRequest request) {

		// ��ȡ������Ϣ�еĹ�����Ϣ����
		MainProjectInfo prjInfo = new MainProjectInfo();

		if (request.getParameter("prjName") != null) {
			prjInfo.setPrjName(request.getParameter("prjName").toString());
		} else {
			return false;
		}

		if (request.getParameter("prjAddress") != null) {
			prjInfo.setPrjAddress(request.getParameter("prjAddress").toString());
		}

		if (request.getParameter("PrjManager") != null) {
			prjInfo.setPrjManager(request.getParameter("PrjManager").toString());
		}

		if (request.getParameter("PrjRemark") != null) {
			prjInfo.setRemark(request.getParameter("PrjRemark").toString());
		}

		if (request.getParameter("prjType") != null) {
			prjInfo.setPrjType(Integer.parseInt(request.getParameter("prjType").toString()));
		}

		if (request.getParameter("prjLng") != null) {
			prjInfo.setPrjLng(Float.parseFloat(request.getParameter("prjLng").toString()));
		}

		if (request.getParameter("PrjLat") != null) {
			prjInfo.setPrjLat(Float.parseFloat(request.getParameter("PrjLat").toString()));
		}

		MainPrjInfoAction action = new MainPrjInfoAction();

		boolean bres = action.Create(prjInfo);

		return bres;
	}
	
	/**
	 * �޸Ĺ�����Ϣ
	 * @param request
	 * @return
	 */
	public boolean UpdatePrjInfo(HttpServletRequest request) {
		
		MainProjectInfo prjInfo = new MainProjectInfo();
		
		if (request.getParameter("prjID") != null) {
			prjInfo.setAutoID(Integer.parseInt(request.getParameter("prjID").toString()));
		}else {
			return false;
		}
		
		if (request.getParameter("prjName") != null) {
			prjInfo.setPrjName(request.getParameter("prjName").toString());
		} else {
			return false;
		}

		if (request.getParameter("prjAddress") != null) {
			prjInfo.setPrjAddress(request.getParameter("prjAddress").toString());
		}

		if (request.getParameter("PrjManager") != null) {
			prjInfo.setPrjManager(request.getParameter("PrjManager").toString());
		}

		if (request.getParameter("PrjRemark") != null) {
			prjInfo.setRemark(request.getParameter("PrjRemark").toString());
		}

		if (request.getParameter("prjType") != null) {
			prjInfo.setPrjType(Integer.parseInt(request.getParameter("prjType").toString()));
		}

		if (request.getParameter("prjLng") != null) {
			prjInfo.setPrjLng(Float.parseFloat(request.getParameter("prjLng").toString()));
		}

		if (request.getParameter("PrjLat") != null) {
			prjInfo.setPrjLat(Float.parseFloat(request.getParameter("PrjLat").toString()));
		}
		
		MainPrjInfoAction action = new MainPrjInfoAction();
		
		boolean bres = action.Update(prjInfo);

		return bres;
	}
	

	/**
	 * ����ID��ȡ������Ϣ
	 * 
	 * @param request
	 * @return
	 */
	public StringBuffer GetPrjInfoByID(HttpServletRequest request) {

		int nAutoID = 0;

		if (request.getParameter("prjID") != null) {
			nAutoID = Integer.parseInt(request.getParameter("prjID").toString());
		} else {
			return null;
		}

		MainPrjInfoAction action = new MainPrjInfoAction();

		MainProjectInfo proInfo = action.RetrievePrjInfoByID(nAutoID);

		StringBuffer sb = new StringBuffer();

		if (proInfo == null) {
			sb.append("{}");
		} else {

			sb.append("{");
			sb.append(String.format("\"prjname\":\"%s\",", proInfo.getPrjName()));
			sb.append(String.format("\"prjtype\":\"%d\",", proInfo.getPrjType()));
			sb.append(String.format("\"id\":\"%d\",", proInfo.getAutoID()));
			sb.append(String.format("\"prjlng\":\"%f\",", proInfo.getPrjLng()));
			sb.append(String.format("\"prjlat\":\"%f\",", proInfo.getPrjLat()));
			sb.append(String.format("\"prjaddress\":\"%s\",", proInfo.getPrjAddress()));
			sb.append(String.format("\"prjmanager\":\"%s\",", proInfo.getPrjManager()));
			sb.append(String.format("\"remark\":\"%s\",", proInfo.getRemark()));
			sb.append("}");
		}

		return sb;
	}

	/**
	 * ��ȡ�����Ŀ�ķ�ҳ����
	 * 
	 * @param request
	 * @return
	 */
	public StringBuffer GetMonitorPrjPageData(HttpServletRequest request) {

		// ��ȡ��ǰ��cookie�м�¼��prjName

		CookieHelp cookieHelp = new CookieHelp();

		String strCurPrjName = cookieHelp.GetCookieStringValue(request, "prjName");

		try {
			strCurPrjName = URLDecoder.decode(strCurPrjName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (strCurPrjName.isEmpty()) {
			return null;
		}

		// ��ȡ��Ӧ��list

		if (request.getParameter("page") == null || request.getParameter("rows") == null) {
			return null;
		}

		int page = Integer.parseInt(request.getParameter("page").toString());

		int rows = Integer.parseInt(request.getParameter("rows").toString());

		MonitorPrjAction action = new MonitorPrjAction(strCurPrjName);

		List<Monitor> listMonitor = action.GetMonitorPrjPageData(page, rows);

		int nDataCount = action.GetMonitorPrjCount();

		StringBuffer sb = new StringBuffer();

		sb.append("{");

		if (nDataCount == 0 || listMonitor == null || listMonitor.size() <= 0) {
			sb.append("}");
		} else {
			sb.append("\"rows\":[");
			for (Monitor item : listMonitor) {
				sb.append("{");
				sb.append(String.format("\"id\":\"%s\",", item.getAutoID()));
				sb.append(String.format("\"monitorName\":\"%s\",", item.getMonitorName()));
				sb.append(String.format("\"monitorMethord\":\"%s\",", item.getMonitorMethord()));
				sb.append(String.format("\"monitorPointCount\":\"%d\",", item.getMonitorPointCount()));
				sb.append(String.format("\"monitorPointPrefix\":\"%s\",", item.getMonitorPointPrefix()));
				sb.append(String.format("\"monitorPointStartID\":\"%s\",", item.getMonitorPointStartID()));
				sb.append(String.format("\"monitorPointEndID\":\"%s\"", item.getMonitorPointEndID()));
				sb.append("},");
			}
			sb.deleteCharAt(sb.length() - 1);

			sb.append("],\"total\":").append(nDataCount).append("}");
		}

		return sb;
	}

	/**
	 * ���������Ŀ��Ϣ
	 * 
	 * @param request
	 * @return
	 */
	public boolean AddMonitorPrjInfo(HttpServletRequest request) {

		// ��ȡ��ǰ��cookie�м�¼��prjName

		CookieHelp cookieHelp = new CookieHelp();

		String strCurPrjName = cookieHelp.GetCookieStringValue(request, "prjName");

		try {
			strCurPrjName = URLDecoder.decode(strCurPrjName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (strCurPrjName.isEmpty()) {
			return false;
		}

		MonitorPrjAction action = new MonitorPrjAction(strCurPrjName);

		Monitor modelMonitor = new Monitor();

		if (request.getParameter("monitorName") != null) {
			// prjInfo.setPrjName(request.getParameter("prjName").toString());
			modelMonitor.setMonitorName(request.getParameter("monitorName"));
		} else {
			return false;
		}

		if (request.getParameter("monitorMethord") != null) {
			modelMonitor.setMonitorMethord(request.getParameter("monitorMethord"));
		} else {
			return false;
		}

		if (request.getParameter("monitorPointCount") != null) {
			modelMonitor.setMonitorPointCount(Integer.parseInt(request.getParameter("monitorPointCount").toString()));
		}

		if (request.getParameter("monitorPointPrefix") != null) {
			modelMonitor.setMonitorPointPrefix(request.getParameter("monitorPointPrefix"));
		}

		if (request.getParameter("monitorPointStartID") != null) {
			modelMonitor
					.setMonitorPointStartID(Integer.parseInt(request.getParameter("monitorPointStartID").toString()));
		}

		if (request.getParameter("monitorPointEndID") != null) {
			modelMonitor.setMonitorPointEndID(Integer.parseInt(request.getParameter("monitorPointEndID").toString()));
		}

		boolean bRes = action.AddMonitorPrj(modelMonitor);

		return bRes;
	}

	/**
	 * ��ȡ������͵ķ�ҳ����
	 * 
	 * @param request
	 * @return
	 */
	public StringBuffer GetMonitorTypePageData(HttpServletRequest request) {

		// ��ȡ��Ӧ��list

		if (request.getParameter("page") == null || request.getParameter("rows") == null) {
			return null;
		}

		int page = Integer.parseInt(request.getParameter("page").toString());

		int rows = Integer.parseInt(request.getParameter("rows").toString());

		MonitorTypeAction action = new MonitorTypeAction("");

		StringBuffer sb = new StringBuffer();

		int nDataCount = action.GetMonitorTypeDataCount();

		List<String> listMonitorType = action.GetMonitorTypePageData(page, rows);

		sb.append("{");

		if (nDataCount == 0 || listMonitorType == null || listMonitorType.size() <= 0) {
			sb.append("}");
		} else {
			sb.append("\"rows\":[");
			for (String item : listMonitorType) {
				sb.append("{");
				sb.append(String.format("\"monitorTypeName\":\"%s\"", item));
				sb.append("},");
			}
			sb.deleteCharAt(sb.length() - 1);

			sb.append("],\"total\":").append(nDataCount).append("}");
		}

		return sb;
	}

	/**
	 * ��ȡ��Ӧ������͵ļ�ⷽ������
	 * 
	 * @param request
	 * @return
	 */
	public StringBuffer GetMonitorMethordPageData(HttpServletRequest request) {

		if (request.getParameter("page") == null || request.getParameter("rows") == null) {
			return null;
		}

		int page = Integer.parseInt(request.getParameter("page").toString());

		int rows = Integer.parseInt(request.getParameter("rows").toString());

		String monitorTypeName = request.getParameter("monitorTypeName").toString();

		MonitorTypeAction action = new MonitorTypeAction("");

		StringBuffer sb = new StringBuffer();

		int nDataCount = action.GetMonitorMethordDataCount(monitorTypeName);

		List<String> listMonitorMethord = action.GetMonitorMethordPageData(monitorTypeName, page, rows);

		sb.append("{");

		if (nDataCount == 0 || listMonitorMethord == null || listMonitorMethord.size() <= 0) {
			sb.append("}");
		} else {
			sb.append("\"rows\":[");
			for (String item : listMonitorMethord) {
				sb.append("{");
				sb.append(String.format("\"monitorTypeMethordName\":\"%s\"", item));
				sb.append("},");
			}
			sb.deleteCharAt(sb.length() - 1);

			sb.append("],\"total\":").append(nDataCount).append("}");
		}

		return sb;
	}
	
	/**
	 * ɾ�������Ŀ
	 * @param request
	 * @return
	 */
	public boolean DeleteMonitorPrjInfo(HttpServletRequest request) {
		
		int nMonitorID=0;String strMonitorName="";
		
		if(request.getParameter("nMonitorID")!=null) {
			nMonitorID=Integer.parseInt(request.getParameter("nMonitorID").toString());
		}
		
		if(request.getParameter("strMonitorName")!=null) {
			strMonitorName=request.getParameter("strMonitorName").toString();
		}
		
		CommonRequestCtrl comCtrl = new CommonRequestCtrl();

		String strCurPrjName = comCtrl.GetCurPrjNameByCookie(request);
		
		MonitorPrjAction action=new MonitorPrjAction(strCurPrjName);
		
		return action.DeleteMonitorInfoByIDAndName(nMonitorID, strMonitorName);
	}

}
