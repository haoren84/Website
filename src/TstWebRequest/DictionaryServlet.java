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
	 * ����������Ϣ
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
		case "GetDicTreeGridData":// ��ȡ�ֵ�����ṹ����
		{
			sb = GetDicTreeGridData(request);
		}
			break;
		case "CreateDicInfo":// �½��ֵ�
		{
			sb = CreateDicInfo(request);
		}
			break;
		case "UpdateDicInfo":// �޸��ֵ�
		{
			sb = UpdateDicInfo(request);
		}
			break;
		case "UpdateDicInfoState"://�޸�״̬
		{
			sb=UpdateDicInfoState(request);
		}
		break;
		case "DeleteDicInfo":// ɾ���ֵ�
		{
			sb = DeleteDicInfo(request);
		}
			break;
		case "GetDicUseStateData"://��ȡ�ֵ��״̬����
		{
			sb=GetDicUseStateData(request);
		}
		break;
		case "GetDictionaryComboboxDataByParentDic"://�����ϼ��ֵ�ֵ��ȡ��������
		{
			sb=GetDictionaryComboboxDataByParentDic(request);
		}
		break;
		case "GetDicPrjType"://��ȡ�ֵ��еĹ�����������
		{
			sb=GetDicPrjType(request);
		}
		break;
		case "CreateNewPrjType"://�����ֵ��еĹ�����������
		{
			sb=CreateNewPrjType(request);
		}
		break;
		case "GetDicMonitorTreeGridData"://������͵������ֵ���Ϣ
		{
			sb=GetDicMonitorTreeGridData(request);
		}
		break;
		case "CreateDicMonitorInfo"://����������͵������ֵ�
		{
			sb=CreateDicMonitorInfo(request);
		}
		break;
		case "UpdateDicMonitorInfo"://�޸ļ�����͵������ֵ�
		{
			sb=UpdateDicMonitorInfo(request);
		}
		break;
		case "DeleteDicMonitorInfo"://ɾ��������͵������ֵ�
		{
			sb=DeleteDicMonitorInfo(request);
		}
		break;
		case"GetDicMeasureType"://��ȡ�ֵ��еĲ�����������
		{
			sb=GetDicMeasureType(request);
		}
		break;
		
		
		}

		return sb.toString();
	}

	/**
	 * ��ȡ��ǰ�Ĺ�������
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
	 * ��ȡ�ֵ�����ṹ����
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
					sb.append(String.format("\"UseState\":\"%s\",", "ʹ����"));
				}else
				{
					sb.append(String.format("\"UseState\":\"%s\",", "δʹ��"));
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
	 * �����ֵ���Ϣ
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
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ȱ�ٲ���-�ֵ�����\"}");
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
			sb.append("{\"result\":\"true\",\"errorMsg\":\"�����ɹ�\"}");

			return sb;
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"����ʧ��\"}");

			return sb;
		}
	}

	/**
	 * �޸��ֵ���Ϣ
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
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ȱ�ٲ���\"}");
			return sb;
		}

		if (request.getParameter("DicValue") != null) {
			model.setDicValue(request.getParameter("DicValue").toString());
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ȱ�ٲ���-�ֵ�����\"}");
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
			sb.append("{\"result\":\"true\",\"errorMsg\":\"�޸ĳɹ�\"}");

			return sb;
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"�޸�ʧ��\"}");

			return sb;
		}
	}
	
	/**
	 * �޸��ֵ��״̬
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
			sb.append("{\"result\":\"true\",\"errorMsg\":\"�޸ĳɹ�\"}");

			return sb;
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"�޸�ʧ��\"}");

			return sb;
		}
	}

	/**
	 * ɾ���ֵ�
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
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ȱ�ٲ���\"}");
			return sb;
		}

		DictionaryInfoAction action = new DictionaryInfoAction();

		if (action.DeleteDicInfo(nAutoID)) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"ɾ���ɹ�\"}");

			return sb;
		} else

		{
			sb.append("{\"result\":\"true\",\"errorMsg\":\"ɾ��ʧ��\"}");

			return sb;
		}
	}

	/**
	 * ��ȡ�ֵ��״̬����
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
	 * �����ֵ��ϼ���Ϣ��ȡ��Ӧ������������Ϣ
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
	 * ��ȡ�ֵ��еĹ������͵�����
	 * @param request
	 * @return
	 */
	private StringBuffer GetDicPrjType(HttpServletRequest request) {
		
		StringBuffer sb=new StringBuffer();
		
		List<DictionaryInfo> listInfo = new ArrayList<>();

		DictionaryInfoAction action = new DictionaryInfoAction();

		listInfo = action.GetDicInfoByParenDic("��������");
		
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
	 * �����ֵ��еĹ�����������
	 * @param request
	 * @return
	 */
	private StringBuffer CreateNewPrjType(HttpServletRequest request){
		
		DictionaryInfo model = new DictionaryInfo();

		StringBuffer sb = new StringBuffer();

		if (request.getParameter("DicValue") != null) {
			model.setDicValue(request.getParameter("DicValue").toString());
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ȱ�ٲ���-������������\"}");
			return sb;
		}
		
		DictionaryInfoAction action = new DictionaryInfoAction();
		
		if(action.CheckDicValue("��������", model.getDicValue()))
		{
			sb.append("{\"result\":\"false\",\"errorMsg\":\"�������������ظ�\"}");
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
			sb.append("{\"result\":\"true\",\"errorMsg\":\"�����ɹ�\"}");

			return sb;
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"����ʧ��\"}");

			return sb;
		}
	}

	/**
	 * ������͵������ֵ���Ϣ
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
					sb.append(String.format("\"UseState\":\"%s\",", "ʹ����"));
				}else
				{
					sb.append(String.format("\"UseState\":\"%s\",", "δʹ��"));
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
	 * ����������͵������ֵ�
	 * @param request
	 * @return
	 */
	private StringBuffer CreateDicMonitorInfo(HttpServletRequest request) {
		
		DictionaryInfo model = new DictionaryInfo();

		StringBuffer sb = new StringBuffer();

		if (request.getParameter("DicValue") != null) {
			model.setDicValue(request.getParameter("DicValue").toString());
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ȱ�ٲ���-�ֵ�����\"}");
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
			sb.append("{\"result\":\"true\",\"errorMsg\":\"�����ɹ�\"}");

			return sb;
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"����ʧ��\"}");

			return sb;
		}
	}

	/**
	 * �޸ļ�����͵������ֵ�
	 * @param request
	 * @return
	 */
	private StringBuffer UpdateDicMonitorInfo(HttpServletRequest request) {
		
		DictionaryInfo model = new DictionaryInfo();

		StringBuffer sb = new StringBuffer();

		if (request.getParameter("DicID") != null) {
			model.setAutoID(Integer.parseInt(request.getParameter("DicID")));
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ȱ�ٲ���\"}");
			return sb;
		}

		if (request.getParameter("DicValue") != null) {
			model.setDicValue(request.getParameter("DicValue").toString());
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ȱ�ٲ���-�ֵ�����\"}");
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
			sb.append("{\"result\":\"true\",\"errorMsg\":\"�޸ĳɹ�\"}");

			return sb;
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"�޸�ʧ��\"}");

			return sb;
		}
	}
	
	/**
	 * ɾ��������͵������ֵ�
	 * @param request
	 * @return
	 */
	private StringBuffer DeleteDicMonitorInfo(HttpServletRequest request) {
		
		DictionaryInfo model = new DictionaryInfo();

		StringBuffer sb = new StringBuffer();
		
		if (request.getParameter("DicID") != null) {
			model.setAutoID(Integer.parseInt(request.getParameter("DicID")));
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ȱ�ٲ���\"}");
			return sb;
		}
		
		if (request.getParameter("ParentID") != null) {
			model.setParentID(Integer.parseInt(request.getParameter("ParentID")));
		}else {
			model.setParentID(0);
		}
		
		DictionaryInfoAction action = new DictionaryInfoAction();

		if (action.DeleteMonitorDicInfo(model)) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"ɾ���ɹ�\"}");

			return sb;
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ɾ��ʧ��\"}");

			return sb;
		}
	}
	
	/**
	 * ��ȡ�ֵ��еĲ�����������
	 * @param request
	 * @return
	 */
	private StringBuffer GetDicMeasureType(HttpServletRequest request) {
		
		StringBuffer sb=new StringBuffer();
		
		List<DictionaryInfo> listInfo = new ArrayList<>();

		DictionaryInfoAction action = new DictionaryInfoAction();

		listInfo = action.GetDicInfoByParenDic("��������");
		
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

