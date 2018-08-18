package TstWebRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Business.DictionaryInfoAction;
import Model.DictionaryInfo;
import Model.Monitor;

/**
 * Servlet implementation class InstrumentAcqServlet
 */
@WebServlet("/DictionaryServlet")
public class DictionaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DictionaryServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		response.getWriter().append(this.ReturnResquest(request, response));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * 返回请求信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private String ReturnResquest(HttpServletRequest request, HttpServletResponse response) {

		if (request.getParameter("action") == null) {
			return "";
		}

		String strActionName = new String(request.getParameter("action"));

		StringBuffer sb = new StringBuffer();

		if (strActionName == null || strActionName.isEmpty()) {
			return sb.toString();
		}

		switch (strActionName) {
		case "GetDicTreeGridData":// 获取字典的树结构数据
		{
			sb = GetDicTreeGridData(request);
		}
			break;
		case "CreateDicInfo":// 新建字典
		{
			sb = CreateDicInfo(request);
		}
			break;
		case "UpdateDicInfo":// 修改字典
		{
			sb = UpdateDicInfo(request);
		}
			break;
		case "UpdateDicInfoState"://修改状态
		{
			sb=UpdateDicInfoState(request);
		}
		break;
		case "DeleteDicInfo":// 删除字典
		{
			sb = DeleteDicInfo(request);
		}
			break;
		case "GetDicUseStateData"://获取字典的状态数据
		{
			sb=GetDicUseStateData(request);
		}
		break;
		case "GetDictionaryComboboxDataByParentDic"://根据上级字典值获取下拉数据
		{
			sb=GetDictionaryComboboxDataByParentDic(request);
		}
		break;
		case "GetDicPrjType"://获取字典中的工程类型数据
		{
			sb=GetDicPrjType(request);
		}
		break;
		case "CreateNewPrjType"://新增字典中的工程类型数据
		{
			sb=CreateNewPrjType(request);
		}
		break;
		case "GetDicMonitorTreeGridData"://监测类型的数据字典信息
		{
			sb=GetDicMonitorTreeGridData(request);
		}
		break;
		case "CreateDicMonitorInfo"://新增监测类型的数据字典
		{
			sb=CreateDicMonitorInfo(request);
		}
		break;
		case "UpdateDicMonitorInfo"://修改监测类型的数据字典
		{
			sb=UpdateDicMonitorInfo(request);
		}
		break;
		case "DeleteDicMonitorInfo"://删除监测类型的数据字典
		{
			sb=DeleteDicMonitorInfo(request);
		}
		break;
		case"GetDicMeasureType"://获取字典中的测量类型数据
		{
			sb=GetDicMeasureType(request);
		}
		break;
		
		
		}

		return sb.toString();
	}

	/**
	 * 获取当前的工程名称
	 * 
	 * @param request
	 * @return
	 */
	private String GetCurPrjName(HttpServletRequest request) {

		CommonRequestCtrl comCtrl = new CommonRequestCtrl();

		String strCurPrjName = comCtrl.GetCurPrjNameByCookie(request);

		return strCurPrjName;
	}

	/**
	 * 获取字典的树结构数据
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer GetDicTreeGridData(HttpServletRequest request) {

		int nDicPartentID = 0;

		if (request.getParameter("DicParentID") != null) {

			nDicPartentID = Integer.parseInt(request.getParameter("DicParentID"));

		}

		List<DictionaryInfo> listInfo = new ArrayList<>();

		DictionaryInfoAction action = new DictionaryInfoAction();

		listInfo = action.GetDicInfoByParentID(nDicPartentID);

		StringBuffer sb = new StringBuffer();

		if (listInfo == null || listInfo.size() <= 0) {

			sb.append("[]");

			return sb;
		}

		sb.append("[");

		for (DictionaryInfo item : listInfo) {

			sb.append("{");

			int nID = nDicPartentID * 1000 + item.getAutoID();

			sb.append(String.format("\"id\":\"%d\",", nID));

			sb.append(String.format("\"DicValue\":\"%s\",", item.getDicValue()));

			if (nDicPartentID == 0) {
				sb.append(String.format("\"state\":\"%s\",", "closed"));
				
				sb.append(String.format("\"UseState\":\"%s\",", ""));
				
			} else {
				sb.append(String.format("\"state\":\"%s\",", "open"));
				
				if(item.getIsUsed()==1) {
					sb.append(String.format("\"UseState\":\"%s\",", "使用中"));
				}else
				{
					sb.append(String.format("\"UseState\":\"%s\",", "未使用"));
				}
			}
			
			

			sb.append("\"attributes\":{");

			sb.append(String.format("\"autoid\":\"%d\",", item.getAutoID()));
			sb.append(String.format("\"DicValue\":\"%s\",", item.getDicValue()));
			sb.append(String.format("\"isUsed\":\"%s\",", item.getIsUsed()));
			sb.append(String.format("\"ParentDic\":\"%s\",", item.getParentDic()));
			sb.append(String.format("\"ParentID\":\"%s\"", item.getParentID()));
			sb.append("}},");

		}
		sb.deleteCharAt(sb.length() - 1);

		sb.append("]");

		return sb;

	}

	/**
	 * 创建字典信息
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer CreateDicInfo(HttpServletRequest request) {

		DictionaryInfo model = new DictionaryInfo();

		StringBuffer sb = new StringBuffer();

		if (request.getParameter("DicValue") != null) {
			model.setDicValue(request.getParameter("DicValue").toString());
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"缺少参数-字典名称\"}");
			return sb;
		}

		if (request.getParameter("isUsed") != null) {
			model.setIsUsed(Integer.parseInt(request.getParameter("isUsed")));
		} else {
			model.setIsUsed(1);
		}

		if (request.getParameter("ParentDic") != null) {
			model.setParentDic(request.getParameter("ParentDic").toString());
		}

		if (request.getParameter("ParentID") != null) {
			model.setParentID(Integer.parseInt(request.getParameter("ParentID")));
		}

		DictionaryInfoAction action = new DictionaryInfoAction();

		if (action.CreateDicInfo(model)) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"新增成功\"}");

			return sb;
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"新增失败\"}");

			return sb;
		}
	}

	/**
	 * 修改字典信息
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer UpdateDicInfo(HttpServletRequest request) {

		DictionaryInfo model = new DictionaryInfo();

		StringBuffer sb = new StringBuffer();

		if (request.getParameter("DicID") != null) {
			model.setAutoID(Integer.parseInt(request.getParameter("DicID")));
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"缺少参数\"}");
			return sb;
		}

		if (request.getParameter("DicValue") != null) {
			model.setDicValue(request.getParameter("DicValue").toString());
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"缺少参数-字典名称\"}");
			return sb;
		}

		if (request.getParameter("isUsed") != null) {
			model.setIsUsed(Integer.parseInt(request.getParameter("isUsed")));
		} else {
			model.setIsUsed(1);
		}

		if (request.getParameter("ParentDic") != null) {
			model.setParentDic(request.getParameter("ParentDic").toString());
		}

		if (request.getParameter("ParentID") != null) {
			model.setParentID(Integer.parseInt(request.getParameter("ParentID")));
		}

		DictionaryInfoAction action = new DictionaryInfoAction();

		if (action.UpdateDicInfo(model)) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"修改成功\"}");

			return sb;
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"修改失败\"}");

			return sb;
		}
	}
	
	/**
	 * 修改字典的状态
	 * @param request
	 * @return
	 */
	private StringBuffer UpdateDicInfoState(HttpServletRequest request) {
		
		StringBuffer sb = new StringBuffer();
		
		String strState="";
		int nAutoID=0;
		
		if (request.getParameter("isUsed") != null) {
			strState=request.getParameter("isUsed").toString();
		} 
		
		if (request.getParameter("DicID") != null) {
			nAutoID=Integer.parseInt(request.getParameter("DicID"));
		}
		
		DictionaryInfoAction action = new DictionaryInfoAction();
		
		if(action.UpdateDicInfoState(strState, nAutoID)) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"修改成功\"}");

			return sb;
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"修改失败\"}");

			return sb;
		}
	}

	/**
	 * 删除字典
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer DeleteDicInfo(HttpServletRequest request) {

		StringBuffer sb = new StringBuffer();

		int nAutoID = 0;

		if (request.getParameter("DicID") != null) {
			nAutoID = Integer.parseInt(request.getParameter("DicID"));
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"缺少参数\"}");
			return sb;
		}

		DictionaryInfoAction action = new DictionaryInfoAction();

		if (action.DeleteDicInfo(nAutoID)) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"删除成功\"}");

			return sb;
		} else

		{
			sb.append("{\"result\":\"true\",\"errorMsg\":\"删除失败\"}");

			return sb;
		}
	}

	/**
	 * 获取字典的状态数据
	 * @param request
	 * @return
	 */
	private StringBuffer GetDicUseStateData(HttpServletRequest request) {
		
		int nDicPartentID = 0;

		if (request.getParameter("DicParentID") != null) {

			nDicPartentID = Integer.parseInt(request.getParameter("DicParentID"));

		}
		
		List<DictionaryInfo> listInfo = new ArrayList<>();

		DictionaryInfoAction action = new DictionaryInfoAction();

		listInfo = action.GetDicInfoByParentID(nDicPartentID);
		
		StringBuffer sb = new StringBuffer();
		
		if(listInfo==null||listInfo.size()<=0) {
			
			sb.append("{\"total\":0,\"rows\":[]}");

			return sb;
			
		}
		
		int nLength = listInfo.size();

		sb.append("{\"total\":" + nLength + ",\"rows\":[");
		
		for( DictionaryInfo item :listInfo) {
			
			sb.append("{");
			
			sb.append(String.format("\"id\":\"%s\",", item.getAutoID()));
			sb.append(String.format("\"DicValue\":\"%s\",", item.getDicValue()));
			sb.append(String.format("\"isUsed\":\"%s\",", item.getIsUsed()));
			sb.append(String.format("\"ParentDic\":\"%s\",", item.getParentDic()));
			sb.append(String.format("\"ParentID\":\"%s\"", item.getParentID()));
			
			sb.append("},");
			
			
		}
		sb.deleteCharAt(sb.length() - 1);

		sb.append("]}");

		return sb;
		
	}
	
	/**
	 * 根据字典上级信息获取对应的下拉数据信息
	 * @param request
	 * @return
	 */
	private StringBuffer GetDictionaryComboboxDataByParentDic(HttpServletRequest request) {
		
		String strParentDic="";
		
		if(request.getParameter("ParentDic")!=null) {
			
			strParentDic=request.getParameter("ParentDic").toString();
		}
		
		List<DictionaryInfo> listInfo = new ArrayList<>();

		DictionaryInfoAction action = new DictionaryInfoAction();

		listInfo = action.GetDicInfoByParenDic(strParentDic);
		
		StringBuffer sb = new StringBuffer();
		
		if(listInfo==null||listInfo.size()<=0) {
			
			sb.append("[]");

			return sb;
			
		}
		
		sb.append("[");
		
		for( DictionaryInfo item :listInfo) {
			
			sb.append("{");
			
			sb.append(String.format("\"id\":\"%s\",", item.getAutoID()));
			sb.append(String.format("\"DicValue\":\"%s\",", item.getDicValue()));
			sb.append(String.format("\"isUsed\":\"%s\",", item.getIsUsed()));
			sb.append(String.format("\"ParentDic\":\"%s\",", item.getParentDic()));
			sb.append(String.format("\"ParentID\":\"%s\"", item.getParentID()));
			
			sb.append("},");
			
			
		}
		sb.deleteCharAt(sb.length() - 1);

		sb.append("]");

		return sb;
	}

	/**
	 * 获取字典中的工程类型的数据
	 * @param request
	 * @return
	 */
	private StringBuffer GetDicPrjType(HttpServletRequest request) {
		
		StringBuffer sb=new StringBuffer();
		
		List<DictionaryInfo> listInfo = new ArrayList<>();

		DictionaryInfoAction action = new DictionaryInfoAction();

		listInfo = action.GetDicInfoByParenDic("工程类型");
		
		if(listInfo==null||listInfo.size()<=0) {
			
			sb.append("{\"total\":0,\"rows\":[]}");

			return sb;
			
		}
		
		int nLength=listInfo.size();
		
		sb.append("{\"total\":"+nLength+",\"rows\":[");
		
		for(DictionaryInfo item : listInfo) {
			sb.append("{");
			sb.append(String.format("\"autoid\":\"%s\",", item.getAutoID()));
			sb.append(String.format("\"dicvalue\":\"%s\",", item.getDicValue()));
			sb.append(String.format("\"parentvalue\":\"%s\",", item.getParentDic()));
			sb.append(String.format("\"parentid\":\"%s\"", item.getParentID()));
			sb.append("},");
		}
		
		sb.deleteCharAt(sb.length() - 1);
		
		sb.append("]}");
		
		return sb;
	}
	
	/**
	 * 新增字典中的工程类型数据
	 * @param request
	 * @return
	 */
	private StringBuffer CreateNewPrjType(HttpServletRequest request){
		
		DictionaryInfo model = new DictionaryInfo();

		StringBuffer sb = new StringBuffer();

		if (request.getParameter("DicValue") != null) {
			model.setDicValue(request.getParameter("DicValue").toString());
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"缺少参数-工程类型名称\"}");
			return sb;
		}
		
		DictionaryInfoAction action = new DictionaryInfoAction();
		
		if(action.CheckDicValue("工程类型", model.getDicValue()))
		{
			sb.append("{\"result\":\"false\",\"errorMsg\":\"工程类型名称重复\"}");
			return sb;
		}
		

		if (request.getParameter("isUsed") != null) {
			model.setIsUsed(Integer.parseInt(request.getParameter("isUsed")));
		} else {
			model.setIsUsed(1);
		}

		if (request.getParameter("ParentDic") != null) {
			model.setParentDic(request.getParameter("ParentDic").toString());
		}

		if (request.getParameter("ParentID") != null) {
			model.setParentID(Integer.parseInt(request.getParameter("ParentID")));
		}

		

		if (action.CreateDicInfo(model)) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"新增成功\"}");

			return sb;
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"新增失败\"}");

			return sb;
		}
	}

	/**
	 * 监测类型的数据字典信息
	 * @param request
	 * @return
	 */
	private StringBuffer GetDicMonitorTreeGridData(HttpServletRequest request) {
		
		int nDicPartentID = 0;

		if (request.getParameter("DicParentID") != null) {

			nDicPartentID = Integer.parseInt(request.getParameter("DicParentID"));

		}

		List<DictionaryInfo> listInfo = new ArrayList<>();

		DictionaryInfoAction action = new DictionaryInfoAction();

		listInfo = action.GetMonitorDicInfoByParentID(nDicPartentID);

		StringBuffer sb = new StringBuffer();

		if (listInfo == null || listInfo.size() <= 0) {

			sb.append("[]");

			return sb;
		}

		sb.append("[");

		for (DictionaryInfo item : listInfo) {

			sb.append("{");

			int nID = nDicPartentID * 1000 + item.getAutoID();

			sb.append(String.format("\"id\":\"%d\",", nID));

			sb.append(String.format("\"DicValue\":\"%s\",", item.getDicValue()));

			if (nDicPartentID == 0) {
				sb.append(String.format("\"state\":\"%s\",", "closed"));
				
				sb.append(String.format("\"UseState\":\"%s\",", ""));
				
			} else {
				sb.append(String.format("\"state\":\"%s\",", "open"));
				
				if(item.getIsUsed()==1) {
					sb.append(String.format("\"UseState\":\"%s\",", "使用中"));
				}else
				{
					sb.append(String.format("\"UseState\":\"%s\",", "未使用"));
				}
			}
			
			

			sb.append("\"attributes\":{");

			sb.append(String.format("\"autoid\":\"%d\",", item.getAutoID()));
			sb.append(String.format("\"DicValue\":\"%s\",", item.getDicValue()));
			sb.append(String.format("\"isUsed\":\"%s\",", item.getIsUsed()));
			sb.append(String.format("\"ParentDic\":\"%s\",", item.getParentDic()));
			sb.append(String.format("\"ParentID\":\"%s\"", item.getParentID()));
			sb.append("}},");

		}
		sb.deleteCharAt(sb.length() - 1);

		sb.append("]");

		return sb;
	}
	
	/**
	 * 新增监测类型的数据字典
	 * @param request
	 * @return
	 */
	private StringBuffer CreateDicMonitorInfo(HttpServletRequest request) {
		
		DictionaryInfo model = new DictionaryInfo();

		StringBuffer sb = new StringBuffer();

		if (request.getParameter("DicValue") != null) {
			model.setDicValue(request.getParameter("DicValue").toString());
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"缺少参数-字典名称\"}");
			return sb;
		}

		if (request.getParameter("isUsed") != null) {
			model.setIsUsed(Integer.parseInt(request.getParameter("isUsed")));
		} else {
			model.setIsUsed(1);
		}

		if (request.getParameter("ParentDic") != null) {
			model.setParentDic(request.getParameter("ParentDic").toString());
		}

		if (request.getParameter("ParentID") != null) {
			model.setParentID(Integer.parseInt(request.getParameter("ParentID")));
		}

		DictionaryInfoAction action = new DictionaryInfoAction();

		if (action.CreateMonitorDicInfo(model)) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"新增成功\"}");

			return sb;
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"新增失败\"}");

			return sb;
		}
	}

	/**
	 * 修改监测类型的数据字典
	 * @param request
	 * @return
	 */
	private StringBuffer UpdateDicMonitorInfo(HttpServletRequest request) {
		
		DictionaryInfo model = new DictionaryInfo();

		StringBuffer sb = new StringBuffer();

		if (request.getParameter("DicID") != null) {
			model.setAutoID(Integer.parseInt(request.getParameter("DicID")));
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"缺少参数\"}");
			return sb;
		}

		if (request.getParameter("DicValue") != null) {
			model.setDicValue(request.getParameter("DicValue").toString());
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"缺少参数-字典名称\"}");
			return sb;
		}

		if (request.getParameter("isUsed") != null) {
			model.setIsUsed(Integer.parseInt(request.getParameter("isUsed")));
		} else {
			model.setIsUsed(1);
		}

		if (request.getParameter("ParentDic") != null) {
			model.setParentDic(request.getParameter("ParentDic").toString());
		}

		if (request.getParameter("ParentID") != null) {
			model.setParentID(Integer.parseInt(request.getParameter("ParentID")));
		}

		DictionaryInfoAction action = new DictionaryInfoAction();

		if (action.UpdateMonitorDicInfo(model)) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"修改成功\"}");

			return sb;
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"修改失败\"}");

			return sb;
		}
	}
	
	/**
	 * 删除监测类型的数据字典
	 * @param request
	 * @return
	 */
	private StringBuffer DeleteDicMonitorInfo(HttpServletRequest request) {
		
		DictionaryInfo model = new DictionaryInfo();

		StringBuffer sb = new StringBuffer();
		
		if (request.getParameter("DicID") != null) {
			model.setAutoID(Integer.parseInt(request.getParameter("DicID")));
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"缺少参数\"}");
			return sb;
		}
		
		if (request.getParameter("ParentID") != null) {
			model.setParentID(Integer.parseInt(request.getParameter("ParentID")));
		}else {
			model.setParentID(0);
		}
		
		DictionaryInfoAction action = new DictionaryInfoAction();

		if (action.DeleteMonitorDicInfo(model)) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"删除成功\"}");

			return sb;
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"删除失败\"}");

			return sb;
		}
	}
	
	/**
	 * 获取字典中的测量类型数据
	 * @param request
	 * @return
	 */
	private StringBuffer GetDicMeasureType(HttpServletRequest request) {
		
		StringBuffer sb=new StringBuffer();
		
		List<DictionaryInfo> listInfo = new ArrayList<>();

		DictionaryInfoAction action = new DictionaryInfoAction();

		listInfo = action.GetDicInfoByParenDic("测量类型");
		
		if(listInfo==null||listInfo.size()<=0) {
			
			sb.append("{\"total\":0,\"rows\":[]}");

			return sb;
			
		}
		
		int nLength=listInfo.size();
		
		sb.append("{\"total\":"+nLength+",\"rows\":[");
		
		for(DictionaryInfo item : listInfo) {
			sb.append("{");
			sb.append(String.format("\"autoid\":\"%s\",", item.getAutoID()));
			sb.append(String.format("\"dicvalue\":\"%s\",", item.getDicValue()));
			sb.append(String.format("\"parentvalue\":\"%s\",", item.getParentDic()));
			sb.append(String.format("\"parentid\":\"%s\"", item.getParentID()));
			sb.append("},");
		}
		
		sb.deleteCharAt(sb.length() - 1);
		
		sb.append("]}");
		
		return sb;
	}
	
	
	
	
	
	
	
}

