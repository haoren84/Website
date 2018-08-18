package TstWebRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Business.PrjStructAction;
import Model.Struct;
import Model.StructMPointMap;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class PrjStructServlet ����ͽṹ�йص�����
 */
@WebServlet("/PrjStructServlet")
public class PrjStructServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PrjStructServlet() {
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

		response.getWriter().append(ReturnResquest(request, response));
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
	 * ��������
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

		case "GetStructData":// ��ȡ�ṹ�ķ�ҳ����
		{

			sb = GetStructData(request);

		}
			break;
		case "GetStructNameTree":// ��ȡ�ṹ��������
		{
			sb = GetStructNameTree(request);
		}
			break;

		case "GetStructAllMPoint":// ��ȡ���нṹ��Ӧ�Ĳ������
		{
			sb = GetStructAllMPoint(request);

		}
			break;

		case "GetStructAllMPointWithSelect":// ��ȡ���нṹ��Ӧ�Ĳ�����ݣ������Ƿ�ѡ�е���Ϣ��
		{
			sb = GetStructAllMPointWithSelect(request);
		}
			break;

		case "AddStruct":// �����ṹ��Ϣ
		{
			sb = AddStruct(request);
		}
			break;
		
		case "UpdateStruct"://�޸Ľṹ��Ϣ 
		{
			sb = UpdateStruct(request);
		}
		break;
		
		case "DeleteStruct"://ɾ���ṹ��Ϣ
		{
			sb=DeleteStruct(request);
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
	 * ��ȡ�ṹ����
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer GetStructData(HttpServletRequest request) {

		String strParent = "";

		StringBuffer sb = new StringBuffer();

		if (request.getParameter("id") != null) {
			strParent = request.getParameter("id").toString();
		}

		String strCurPrjName = GetCurPrjName(request);

		PrjStructAction action = new PrjStructAction(strCurPrjName);

		if (strParent.isEmpty() || strParent.equals("0")) {
			// ��ȡ��ҳ������

			int page = Integer.parseInt(request.getParameter("page").toString());

			int rows = Integer.parseInt(request.getParameter("rows").toString());

			List<Struct> listStruct = action.GetStructPageData(page, rows);

			int nDataCount = action.GetStructPageDataCount();

			sb.append("{");

			if (nDataCount == 0 || listStruct == null || listStruct.size() <= 0) {
				sb.append("}");
			} else {
				sb.append("\"rows\":[");
				for (Struct item : listStruct) {
					sb.append("{");
					sb.append(String.format("\"state\":\"closed\","));
					sb.append(String.format("\"id\":\"%d\",", item.getAutoID()));
					sb.append(String.format("\"curprjname\":\"%s\",", strCurPrjName));
					sb.append(String.format("\"structName\":\"%s\",", item.getStructName()));
					sb.append(String.format("\"structCode\":\"%s\",", item.getStructCode()));
					sb.append(String.format("\"structType\":\"%s\",", item.getStructType()));
					sb.append(String.format("\"structParent\":\"%s\",", item.getStructParent()));
					sb.append(String.format("\"structRemark\":\"%s\",", item.getStructRemark()));
					sb.append(String.format("\"structAddress\":\"%s\"", item.getStructAddress()));

					sb.append("},");
				}
				sb.deleteCharAt(sb.length() - 1);

				sb.append("],\"total\":").append(nDataCount).append("}");
			}

			return sb;

		} else {

			// ��ȡ��Ӧ���ӽṹ����

			List<Struct> listStruct = action.SelectStructByParent(strParent);

			sb.append("[");

			if (listStruct != null && listStruct.size() > 0) {

				for (Struct item : listStruct) {
					sb.append("{");
					sb.append(String.format("\"state\":\"closed\","));
					sb.append(String.format("\"id\":\"%d\",", item.getAutoID()));
					sb.append(String.format("\"curprjname\":\"%s\",", strCurPrjName));
					sb.append(String.format("\"structName\":\"%s\",", item.getStructName()));
					sb.append(String.format("\"structCode\":\"%s\",", item.getStructCode()));
					sb.append(String.format("\"structType\":\"%s\",", item.getStructType()));
					sb.append(String.format("\"structParent\":\"%s\",", item.getStructParent()));
					sb.append(String.format("\"structRemark\":\"%s\",", item.getStructRemark()));
					sb.append(String.format("\"structAddress\":\"%s\"", item.getStructAddress()));
					sb.append("},");
				}

				sb.deleteCharAt(sb.length() - 1);
			}

			sb.append("]");

			return sb;
		}

	}

	/**
	 * ��ȡ�ṹ������
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer GetStructNameTree(HttpServletRequest request) {

		String strCurPrjName = GetCurPrjName(request);

		PrjStructAction action = new PrjStructAction(strCurPrjName);

		StringBuffer sb = action.GetAllStructTree();

		return sb;
	}

	/**
	 * ��ȡ���нṹ��Ӧ�Ĳ������
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer GetStructAllMPoint(HttpServletRequest request) {

		String strCurPrjName = GetCurPrjName(request);

		PrjStructAction action = new PrjStructAction(strCurPrjName);

		StringBuffer sb = new StringBuffer();

		// sb.append("[{\"id\":\"0\",\"name\":\"�����Ϣ\",\"type\":\"\",\"children\":");

		sb.append(action.GetStructMPointTree());

		// sb.append("}]");

		return sb;
	}

	/**
	 * ��ȡ���нṹ��Ӧ�Ĳ������,��ѡ����Ϣ
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer GetStructAllMPointWithSelect(HttpServletRequest request) {

		String strCurPrjName = GetCurPrjName(request);

		PrjStructAction action = new PrjStructAction(strCurPrjName);

		StringBuffer sb = new StringBuffer();

		// sb.append("[{\"id\":\"0\",\"name\":\"�����Ϣ\",\"type\":\"\",\"children\":");

		sb.append(action.GetStructMPointTreeWithSelect());

		// sb.append("}]");

		return sb;
	}

	/**
	 * �����ṹ
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer AddStruct(HttpServletRequest request) {

		String strCurPrjName = GetCurPrjName(request);

		PrjStructAction action = new PrjStructAction(strCurPrjName);

		StringBuffer sb = new StringBuffer();

		// 1.�ж��Ƿ�����

		Struct model = new Struct();

		if (request.getParameter("structName") != null) {
			model.setStructName(request.getParameter("structName"));
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ȱ�ٽṹ����\"}");
			return sb;
		}

		int nCheckID = action.GetAutoIDByStructName(model.getStructName());

		if (nCheckID > 0) {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"�ṹ�����ظ�\"}");
			return sb;
		}

		if (request.getParameter("structCode") != null) {
			model.setStructCode(request.getParameter("structCode"));
		}

		if (request.getParameter("structType") != null) {
			model.setStructType(request.getParameter("structType"));
		}

		if (request.getParameter("structParent") != null) {
			model.setStructParent(request.getParameter("structParent"));
		} else {
			model.setStructParent("0");
		}

		if (request.getParameter("structRemark") != null) {
			model.setStructRemark(request.getParameter("structRemark"));
		}

		if (request.getParameter("structAddress") != null) {
			model.setStructAddress(request.getParameter("structAddress"));
		}

		boolean bRes = action.CreatePrjSruct(model);

		if (!bRes) {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"�����ṹʧ��\"}");
			return sb;
		}

		// ������Ӧ�Ľڵ�����

		String pointJson = null;

		if (request.getParameter("points") != null) {
			pointJson = request.getParameter("points");
		}

		if (pointJson.isEmpty() || pointJson.equals("[]")) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"�����ṹ�ɹ�\"}");
			return sb;
		}

		nCheckID = action.GetAutoIDByStructName(model.getStructName());// ��ȡ��ǰ�ĽṹID

		JSONArray jsonArray = JSONArray.fromObject(pointJson);

		// ArrayList<StructPointViewModel> viewmodels = new
		// ArrayList<StructPointViewModel>();

		List<StructMPointMap> listMap = new ArrayList<>();

		for (int i = 0; i < jsonArray.size(); i++) {
			StructMPointMap mapItem = new StructMPointMap();
			mapItem.setMonitorID(jsonArray.getJSONObject(i).getInt("nMid"));
			mapItem.setMonitorName(jsonArray.getJSONObject(i).getString("strMName"));
			mapItem.setMpointID(jsonArray.getJSONObject(i).getInt("nPid"));
			mapItem.setMpointName(jsonArray.getJSONObject(i).getString("strPName"));
			mapItem.setStructID(nCheckID);
			mapItem.setStructName(model.getStructName());
			listMap.add(mapItem);
		}

		bRes = action.AddStructMapMPoints(listMap);

		if (bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"�����ṹ�Ͳ��ɹ�\"}");
			return sb;
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"�����ṹ�ɹ����������ʧ��\"}");
			return sb;
		}

	}

	/**
	 * �޸Ľṹ
	 * 
	 * @param request
	 * @return
	 */
	public StringBuffer UpdateStruct(HttpServletRequest request) {

		String strCurPrjName = GetCurPrjName(request);

		PrjStructAction action = new PrjStructAction(strCurPrjName);

		StringBuffer sb = new StringBuffer();

		Struct model = new Struct();

		if (request.getParameter("structName") != null) {
			model.setStructName(request.getParameter("structName"));
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ȱ�ٽṹ����\"}");
			return sb;
		}

		int nStructID = action.GetAutoIDByStructName(model.getStructName());

		if (nStructID <= 0) {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"�ṹ���Ʋ�����\"}");
			return sb;
		} else {
			model.setAutoID(nStructID);
		}

		if (request.getParameter("structCode") != null) {
			model.setStructCode(request.getParameter("structCode"));
		}

		if (request.getParameter("structType") != null) {
			model.setStructType(request.getParameter("structType"));
		}

		if (request.getParameter("structParent") != null) {
			model.setStructParent(request.getParameter("structParent"));
		} else {
			model.setStructParent("0");
		}

		if (request.getParameter("structRemark") != null) {
			model.setStructRemark(request.getParameter("structRemark"));
		}

		if (request.getParameter("structAddress") != null) {
			model.setStructAddress(request.getParameter("structAddress"));
		}

		boolean bRes = action.UpdatePrjStruct(model);

		if (!bRes) {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"�޸Ľṹʧ��\"}");
			return sb;
		}

		// ɾ����Ӧ�Ĳ����Ϣ
		bRes = action.DeleteStructMapInfo(nStructID);

		if (!bRes) {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"�޸Ľṹʧ��\"}");
			return sb;
		}

		// ɾ����- ������Ӧ�Ĳ������

		String pointJson = null;

		if (request.getParameter("points") != null) {
			pointJson = request.getParameter("points");
		}

		if (pointJson.isEmpty() || pointJson.equals("[]")) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"�޸Ľṹ�ɹ�\"}");
			return sb;
		}

		JSONArray jsonArray = JSONArray.fromObject(pointJson);

		// ArrayList<StructPointViewModel> viewmodels = new
		// ArrayList<StructPointViewModel>();

		List<StructMPointMap> listMap = new ArrayList<>();

		for (int i = 0; i < jsonArray.size(); i++) {
			StructMPointMap mapItem = new StructMPointMap();
			mapItem.setMonitorID(jsonArray.getJSONObject(i).getInt("nMid"));
			mapItem.setMonitorName(jsonArray.getJSONObject(i).getString("strMName"));
			mapItem.setMpointID(jsonArray.getJSONObject(i).getInt("nPid"));
			mapItem.setMpointName(jsonArray.getJSONObject(i).getString("strPName"));
			mapItem.setStructID(nStructID);
			mapItem.setStructName(model.getStructName());
			listMap.add(mapItem);
		}

		bRes = action.AddStructMapMPoints(listMap);

		if (bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"�޸Ľṹ�Ͳ��ɹ�\"}");
			return sb;
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"�޸Ľṹ�ɹ�������޸�ʧ��\"}");
			return sb;
		}
	}

	/**
	 * ɾ���ṹ
	 * @param request
	 * @return
	 */
	public StringBuffer DeleteStruct(HttpServletRequest request) {
		
		String strCurPrjName = GetCurPrjName(request);

		PrjStructAction action = new PrjStructAction(strCurPrjName);

		StringBuffer sb = new StringBuffer();
		
		int nStructID=0;
		
		if (request.getParameter("structID") != null) {
			nStructID=Integer.parseInt(request.getParameter("structID").toString());
		}else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ɾ���ṹʧ��\"}");
			return sb;
		}
		
		boolean bRes=action.DeletePrjStructInfo(nStructID);
		
		if(!bRes) {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ɾ���ṹʧ��\"}");
			return sb;
		}
		
		bRes=action.DeleteStructMapInfo(nStructID);
		
		if(bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"ɾ���ṹ�͹������ɹ�\"}");
			return sb;
		}else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ɾ���ṹ�ɹ�\"}");
			return sb;
		}
		
	}
	
	
}
