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

/**
 * Servlet implementation class DictionaryComboServlet
 * 
 * 非字典页面的字典信息的下拉请求
 */
@WebServlet("/DictionaryComboServlet")
public class DictionaryComboServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DictionaryComboServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		response.getWriter().append(this.ReturnResquest(request, response));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	/**
	 * 返回请求信息
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
		case "GetPrjTypeCombo"://获取工程类型的下拉数据
		{
			sb=GetPrjTypeCombo(request);
		}
		break;
		case "GetMeasureTypeCombo"://获取测量类型的下拉数据
		{
			sb=GetMeasureTypeCombo(request);
		}
		break;
		case "GetMeasureTypeComboWithAll"://测量类型的下拉数据（带全部字段）
		{
			sb=GetMeasureTypeComboWithAll(request);
		}
		break;
		}
		
		
		
		return sb.toString();
	}
	
	/**
	 * 获取工程类型的下拉数据
	 * @param request
	 * @return
	 */
	private StringBuffer GetPrjTypeCombo(HttpServletRequest request) {
		
		List<DictionaryInfo> listInfo = new ArrayList<>();

		DictionaryInfoAction action = new DictionaryInfoAction();

		listInfo = action.GetDicInfoByParenDic("工程类型");
		
		StringBuffer sb = new StringBuffer();
		
		if(listInfo==null||listInfo.size()<=0) {
			
			sb.append("[]");

			return sb;
			
		}
		
		sb.append("[");
		
		for (DictionaryInfo item : listInfo) {
			sb.append("{");
			sb.append(String.format("\"id\":\"%s\",", item.getAutoID()));
			sb.append(String.format("\"dicvalue\":\"%s\"", item.getDicValue()));
			sb.append("},");
		}

		sb.deleteCharAt(sb.length() - 1);

		sb.append("]");

		return sb;
	}
	
	/**
	 * 获取测量类型的下拉数据
	 * @param request
	 * @return
	 */
	private StringBuffer GetMeasureTypeCombo(HttpServletRequest request) {
		List<DictionaryInfo> listInfo = new ArrayList<>();

		DictionaryInfoAction action = new DictionaryInfoAction();

		listInfo = action.GetDicInfoByParenDic("测量类型");
		
		StringBuffer sb = new StringBuffer();
		
		if(listInfo==null||listInfo.size()<=0) {
			
			sb.append("[]");

			return sb;
			
		}
		
		sb.append("[");
		
		for (DictionaryInfo item : listInfo) {
			sb.append("{");
			sb.append(String.format("\"id\":\"%s\",", item.getAutoID()));
			sb.append(String.format("\"dicvalue\":\"%s\"", item.getDicValue()));
			sb.append("},");
		}

		sb.deleteCharAt(sb.length() - 1);

		sb.append("]");

		return sb;
	}

	/**
	 * 获取测量类型的下拉数据，数据中有“全部”
	 * @param request
	 * @return
	 */
	private StringBuffer GetMeasureTypeComboWithAll(HttpServletRequest request) {
		List<DictionaryInfo> listInfo = new ArrayList<>();

		DictionaryInfoAction action = new DictionaryInfoAction();

		listInfo = action.GetDicInfoByParenDic("测量类型");
		
		StringBuffer sb = new StringBuffer();
		
		if(listInfo==null||listInfo.size()<=0) {
			
			sb.append("[]");

			return sb;
			
		}
		
		sb.append("[");
		
		sb.append("{");
		sb.append(String.format("\"id\":\"%s\",", 0));
		sb.append(String.format("\"dicvalue\":\"%s\"", "全部"));
		sb.append("},");
		
		for (DictionaryInfo item : listInfo) {
			sb.append("{");
			sb.append(String.format("\"id\":\"%s\",", item.getAutoID()));
			sb.append(String.format("\"dicvalue\":\"%s\"", item.getDicValue()));
			sb.append("},");
		}

		sb.deleteCharAt(sb.length() - 1);

		sb.append("]");

		return sb;
	}
	
}
