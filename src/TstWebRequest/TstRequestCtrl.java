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
 * 请求处理
 * 
 * @author Administrator
 *
 */
public class TstRequestCtrl {

	/**
	 * 返回对应请求的字符串
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

		case "GetLeftInfoInMap":// 获取地图首页的左侧菜单页面
		{
			/*
			 * sb = new StringBuffer(
			 * "[{\"name\":\"房屋监测\",\"type\":\"gallery\",\"id\":29,\"x\":106.47386169433594,\"y\":26.562959671020508,"
			 * +
			 * "\"monitor\":true,\"desc\":\"\",\"address\":\"贵州省贵阳市清镇市云站路\",\"chargePerson\":\"test\"},{\"name\":\"基坑复现\",\"type\":\"gallery\",\"id\":43,\"x\":116.46359252929688,\"y\":39.84120178222656,\"monitor\":true,\"desc\":\"\",\"address\":\"主\",\"chargePerson\":\"test\"},{\"name\":\"重庆测试\",\"type\":\"gallery\",\"id\":44,\"x\":106.5849609375,\"y\":29.561830520629883,\"monitor\":true,\"desc\":\"\",\"address\":\"重庆\",\"chargePerson\":\"test\"},{\"name\":\"测试201707\",\"type\":\"gallery\",\"id\":45,\"monitor\":true,\"desc\":\"\",\"address\":\"北京\",\"chargePerson\":\"test\"},{\"name\":\"cesgi\",\"type\":\"gallery\",\"id\":61,\"x\":120.40521240234375,\"y\":31.458370208740234,\"monitor\":true,\"desc\":\"\",\"address\":\"无锡市\",\"chargePerson\":\"test\"}]"
			 * );
			 */
			sb = GetLeftInfoInMap();
		}
			break;

		case "GetChargePersonInfo":// 获取项目负责人信息
		{
			sb = GetChargePersonInfo(request);
		}
			break;

		case "AddPrjInfo":// 新增项目信息
		{
			sb.append("{\"result\":\"").append(AddPrjInfo(request)).append("\"}");
		}
			break;
		case "UpdatePrjInfo"://修改项目信息  
		{
			sb.append("{\"result\":\"").append(UpdatePrjInfo(request)).append("\"}");
		}
		break;
		

		case "GetPrjInfoByID":// 根据ID找到项目信息
		{
			sb = GetPrjInfoByID(request);
		}
			break;
		case "GetMonitorPrjPageData":// 获取监测项目的分页数据
		{
			sb = this.GetMonitorPrjPageData(request);
		}
			break;
		case "AddMonitorPrjInfo":// 新增监测项目信息
		{
			sb.append("{\"result\":\"").append(this.AddMonitorPrjInfo(request)).append("\"}");
		}
			break;
		case "GetMonitorTypePageData":// 获取测量方法的分页数据
		{
			sb = GetMonitorTypePageData(request);
		}
			break;
		case "GetMonitorMethordPageData":// 获取对应测量类型的方法的分页数据
		{
			sb = GetMonitorMethordPageData(request);
		}
			break;
		case "AddMonitorByAddCount":// 根据新增个数新增测点
		{
			MonitorPointCtrl pointCtrl = new MonitorPointCtrl();
			sb.append("{\"result\":\"").append(pointCtrl.AddMonitorByAddCount(request)).append("\"}");
		}
			break;
		case "GetMonitorPointPageData":// 获取测点的分页数据
		{
			MonitorPointCtrl pointCtrl = new MonitorPointCtrl();
			sb = pointCtrl.GetMonitorPointPageData(request);
		}
			break;
		case "GetAllMonitorPointData":// 获取对应的项目的所有的测点
		{
			MonitorPointCtrl pointCtrl = new MonitorPointCtrl();
			sb = pointCtrl.GetAllMonitorPointData(request);
		}
			break;
		case "DeleteMonitorInfo"://删除监测项目信息
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
	 * 地图首页左侧的列表信息
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
	 * 获取工程信息的表格数据
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
	 * 获取负责人信息
	 * 
	 * @param request
	 *            请求信息：page 当前页数 rows 每页的行数
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
	 * 新增工程信息
	 * 
	 * @param request
	 *            请求信息
	 * @return
	 */
	public boolean AddPrjInfo(HttpServletRequest request) {

		// 获取请求信息中的工程信息参数
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
	 * 修改工程信息
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
	 * 根据ID获取工程信息
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
	 * 获取监测项目的分页数据
	 * 
	 * @param request
	 * @return
	 */
	public StringBuffer GetMonitorPrjPageData(HttpServletRequest request) {

		// 获取当前的cookie中记录的prjName

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

		// 获取对应的list

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
	 * 新增监测项目信息
	 * 
	 * @param request
	 * @return
	 */
	public boolean AddMonitorPrjInfo(HttpServletRequest request) {

		// 获取当前的cookie中记录的prjName

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
	 * 获取监测类型的分页数据
	 * 
	 * @param request
	 * @return
	 */
	public StringBuffer GetMonitorTypePageData(HttpServletRequest request) {

		// 获取对应的list

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
	 * 获取对应监测类型的监测方法数据
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
	 * 删除监测项目
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
