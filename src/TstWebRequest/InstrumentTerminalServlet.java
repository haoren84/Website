package TstWebRequest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Business.DictionaryInfoAction;
import Business.InstrumentTerminalAction;
import Model.InstrumentTerminalModel;
import Model.StructMPointMap;

/**
 * Servlet implementation class InstrumentTerminalServlet
 */
@WebServlet("/InstrumentTerminalServlet")
public class InstrumentTerminalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InstrumentTerminalServlet() {
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
        
		/*InstrumentTerminalCtrl objCtrl=new InstrumentTerminalCtrl();
		
		response.getWriter().append(objCtrl.ReturnResquest(request, response));*/
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
		case "GetTerminalPageData"://终端的分页数据
		{
			sb=GetTerminalPageData(request);
		}
		break;
		case "GetTerminalNameComboData"://终端名称的下拉信息
		{
			sb=GetTerminalNameComboData(request);
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
    * 终端的分页数据
    * @param request
    * @return
    */
   private StringBuffer GetTerminalPageData(HttpServletRequest request) {
	   
	   String strInsFactoryID=null;int nInsType=0;
	   if (request.getParameter("insFactoryID") != null) {
		   strInsFactoryID=request.getParameter("insFactoryID").toString();
			}
	   if (request.getParameter("insType") != null) {
		   nInsType=Integer.parseInt(request.getParameter("insType").toString());
			}
	   
	   String strCurPrjName = GetCurPrjName(request);
	   
	   InstrumentTerminalAction action = new InstrumentTerminalAction(strCurPrjName);
		
		int nDataCount=action.GetSelectPageDataAllCount(strInsFactoryID, nInsType);
		
		StringBuffer sb=new StringBuffer();
		
		if(nDataCount<=0) {
			
			sb.append("{\"total\":0,\"rows\":[]}");
			
			return sb;
			
		}
		
		int page=Integer.parseInt(request.getParameter("page"));
		int rows=Integer.parseInt(request.getParameter("rows"));
		
		//获取测量类型的map信息
		DictionaryInfoAction dicAction=new DictionaryInfoAction();
		
		Map<Integer,String> mapDic=dicAction.GetDicMapInfo("测量类型");
		
		List<InstrumentTerminalModel> list=action.GetSelectPageData(strInsFactoryID, nInsType, page, rows);
	   
		sb.append("{");
		sb.append("\"rows\":[");
		
		//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for(InstrumentTerminalModel  item : list) {
			
			sb.append("{");
			
			sb.append(String.format("\"autoid\":\"%s\",", item.getAutoID()));
			sb.append(String.format("\"insFactoryID\":\"%s\",", item.getinsFactoryID()));
			if(mapDic.containsKey(item.getinsType()))
			{
			sb.append(String.format("\"strType\":\"%s\",", mapDic.get(item.getinsType())));
			}else {
				sb.append(String.format("\"strType\":\"%s\",", "类型未定"));
			}
			sb.append(String.format("\"chncount\":\"%s\",", item.getinsChnCount()));
			sb.append(String.format("\"netNO\":\"%s\",", item.getinsFactoryID()));
			sb.append(String.format("\"samplepace\":\"%s\",", "30"));
			sb.append(String.format("\"lasttime\":\"%s\",", "2017-07-19 11:00:00"));
			sb.append(String.format("\"quantity\":\"%s\"", "80%"));
			sb.append("},");
			
		}
		
		sb.deleteCharAt(sb.length() - 1);

		sb.append("],\"total\":").append(nDataCount).append("}");
		
	   return sb;
   } 
   
   /**
    * 终端的名称下拉数据
    * @param request
    * @return
    */
   private StringBuffer GetTerminalNameComboData(HttpServletRequest request) {
	   
	   String strCurPrjName = GetCurPrjName(request);
	   
	   InstrumentTerminalAction action = new InstrumentTerminalAction(strCurPrjName);
	   
	   List<InstrumentTerminalModel> list=action.SelectAllTerminal();
	   
	   StringBuffer sb=new StringBuffer();
	   
	   if (list == null || list.size() <= 0) {

			sb.append("[]");

			return sb;
		}
		
		sb.append("[");
		
		sb.append("{");
		sb.append(String.format("\"id\":\"%d\",",0));
		sb.append(String.format("\"text\":\"%s\"","全部"));
		sb.append("},");
		
		for(InstrumentTerminalModel item:list) {
			sb.append("{");
			sb.append(String.format("\"id\":\"%d\",",item.getAutoID()));
			sb.append(String.format("\"text\":\"%s\"",item.getinsFactoryID()));
			sb.append("},");
		}
		
		sb.deleteCharAt(sb.length() - 1);

		sb.append("]");

		return sb;
	   
   }
   
   
}
