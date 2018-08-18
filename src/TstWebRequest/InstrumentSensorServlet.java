package TstWebRequest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Business.SensorAction;
import Model.SensorModel;

/**
 * Servlet implementation class InstrumentSensorServlet
 */
@WebServlet("/InstrumentSensorServlet")
public class InstrumentSensorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InstrumentSensorServlet() {
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

		// InstrumentSensorCtrl objCtrl=new InstrumentSensorCtrl();
		// response.getWriter().append(objCtrl.ReturnResquest(request, response));
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
	 * 返回请求数据
	 *
	 *
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
		case "GetSensorPageData": {
			sb = GetSensorPageData(request); // 获得传感器分页数据
		}
			break;
		case "GetSensorIdComboData": {
			sb = GetSensorIdComboData(request); // 获得传感器编号数据
		}
			break;
		case "GetSensorTypeComboData": {
			sb = GetSensorTypeComboData(request); // 获得传感器类型数据
		}
			break;
		case "GetSeneorSelectPage":// 获取传感器分页数据
		{
			sb = GetSeneorSelectPage(request);
		}
			break;
		case "GetSeneorNameComboPageData":// 获取传感器可选的下拉数据
		{
			sb = GetSeneorNameComboPageData(request);
		}
			break;
		case "AddSensor":// 新增传感器
		{
			sb = AddSensor(request);
		}
			break;
		case "UpdateSensor":// 修改传感器
		{
			sb = UpdateSensor(request);
		}
			break;
		case "DeleteSensor":// 删除传感器
		{
			sb = DeleteSensor(request);
		}
			break;
		case "CheckSensorMap":// 验证传感器是否匹配
		{
			sb = CheckSensorMap(request);
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
	 *
	 */
	private String GetCurPrjName(HttpServletRequest request) {

		CommonRequestCtrl comCtrl = new CommonRequestCtrl();

		String strCurPrjName = comCtrl.GetCurPrjNameByCookie(request);

		return strCurPrjName;
	}

	/**
	 * 获得传感器分页数据
	 *
	 *
	 */
	private StringBuffer GetSensorPageData(HttpServletRequest request) {
		String strCurPrjName = GetCurPrjName(request);
		SensorAction action = new SensorAction(strCurPrjName);

		String strSensorID = null;
		String strSensorType = null;
		if (request.getParameter("SensorID") != null) {
			strSensorID = request.getParameter("SensorID").toString();
		}
		if (request.getParameter("SensorMeasureType") != null) {
			strSensorType = request.getParameter("SensorMeasureType").toString();
		}

		int nDataCount = action.GetSelectPageDataAllCount(strSensorID, strSensorType);

		StringBuffer sb = new StringBuffer();

		if (nDataCount <= 0) {

			sb.append("{\"total\":0,\"rows\":[]}");

			return sb;

		}

		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));

		List<SensorModel> list = action.GetSelectPageData(strSensorID, strSensorType, page, rows);

		sb.append("{");
		sb.append("\"rows\":[");

		// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		for (SensorModel item : list) {

			sb.append("{");

			sb.append(String.format("\"autoid\":\"%s\",", item.getAutoID()));
			sb.append(String.format("\"SensorID\":\"%s\",", item.getSensorID()));
			sb.append(String.format("\"SensorMeasureType\":\"%s\",", item.getSensorMeasureType()));
			sb.append(String.format("\"SensorSpec\":\"%s\"", item.getSensorSpec()));
			// sb.append(String.format("\"SensorFactory\":\"%s\",",
			// item.getSensorFactory()));
			// sb.append(String.format("\"Param1\":\"%s\",", item.getParam1()));
			// sb.append(String.format("\"Param2\":\"%s\",", item.getParam2()));
			// sb.append(String.format("\"Param3\":\"%s\",", item.getParam3()));
			// sb.append(String.format("\"Param4\":\"%s\",", item.getParam4()));
			// sb.append(String.format("\"Param5\":\"%s\",", item.getParam5()));
			// sb.append(String.format("\"Param6\":\"%s\",", item.getParam6()));
			// sb.append(String.format("\"Param7\":\"%s\",", item.getParam7()));
			// sb.append(String.format("\"Param8\":\"%s\",", item.getParam8()));
			sb.append("},");

		}

		sb.deleteCharAt(sb.length() - 1);

		sb.append("],\"total\":").append(nDataCount).append("}");

		return sb;
	}

	/**
	 * 获得传感器编号数据
	 *
	 *
	 */
	private StringBuffer GetSensorIdComboData(HttpServletRequest request) {
		String strCurPrjName = GetCurPrjName(request);
		SensorAction action = new SensorAction(strCurPrjName);

		List<SensorModel> list = action.SelectAllSensor();

		StringBuffer sb = new StringBuffer();
		if (list == null || list.size() <= 0) {
			sb.append("[]");
			return sb;
		}
		sb.append("[");
		sb.append("{");
		sb.append(String.format("\"id\":\"%d\",", 0));
		sb.append(String.format("\"text\":\"%s\"", "全部"));
		sb.append("},");
		for (SensorModel item : list) {
			sb.append("{");
			sb.append(String.format("\"id\":\"%d\",", item.getAutoID()));
			sb.append(String.format("\"text\":\"%s\"", item.getSensorID()));
			sb.append("},");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		return sb;
	}

	/**
	 * 获得传感器类型数据
	 *
	 *
	 */
	private StringBuffer GetSensorTypeComboData(HttpServletRequest request) {
		String strCurPrjName = GetCurPrjName(request);
		SensorAction action = new SensorAction(strCurPrjName);

		List<SensorModel> list = action.SelectAllSensor();

		StringBuffer sb = new StringBuffer();
		if (list == null || list.size() <= 0) {
			sb.append("[]");
			return sb;
		}
		sb.append("[");
		sb.append("{");
		sb.append(String.format("\"id\":\"%d\",", 0));
		sb.append(String.format("\"text\":\"%s\"", "全部"));
		sb.append("},");
		for (SensorModel item : list) {
			sb.append("{");
			sb.append(String.format("\"id\":\"%d\",", item.getAutoID()));
			sb.append(String.format("\"text\":\"%s\"", item.getSensorMeasureType()));
			sb.append("},");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		return sb;

	}

	/**
	 * 获取传感器分页数据
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer GetSeneorSelectPage(HttpServletRequest request) {

		String strCurPrjName = GetCurPrjName(request);
		SensorAction action = new SensorAction(strCurPrjName);

		String strSensorName = null;
		String strSensorType = null;
		if (request.getParameter("SensorName") != null) {
			strSensorName = request.getParameter("SensorName").toString();
		}
		if (request.getParameter("SensorType") != null) {
			strSensorType = request.getParameter("SensorType").toString();
		}

		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));

		StringBuffer sb = action.GetSeneorSelectPageData(strSensorName, strSensorType, page, rows);

		return sb;
	}

	/**
	 * 获取传感器下拉的分页数据
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer GetSeneorNameComboPageData(HttpServletRequest request) {

		String strCurPrjName = GetCurPrjName(request);
		SensorAction action = new SensorAction(strCurPrjName);

		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));

		StringBuffer sb = action.GetSeneorNameComboPageData(page, rows);

		return sb;
	}

	/**
	 * 新增传感器
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer AddSensor(HttpServletRequest request) {

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");

		SensorModel model = new SensorModel();

		// 1. 校验前台输入的参数
		if (request.getParameter("SensorID") != null) {
			model.setSensorID(request.getParameter("SensorID").toString());
		} else {
			model.setSensorID(df.format(new Date()));
		}

		if (request.getParameter("SensorSpec") != null) {
			model.setSensorSpec(request.getParameter("SensorSpec").toString());
		}

		if (request.getParameter("SensorFactory") != null) {
			model.setSensorFactory(request.getParameter("SensorFactory").toString());
		}

		if (request.getParameter("Param1") != null) {
			String strParam1 = request.getParameter("Param1").toString();
			
			if(!strParam1.isEmpty()) {
				model.setParam1(Float.parseFloat(strParam1));
			}
		}

		if (request.getParameter("Param2") != null) {
			String strParam2 = request.getParameter("Param2").toString();
			
			if(!strParam2.isEmpty()) {
				model.setParam2(Float.parseFloat(strParam2));
			}
		}

		if (request.getParameter("Param3") != null) {
			String strParam3 = request.getParameter("Param3").toString();
			
			if(!strParam3.isEmpty()) {
			model.setParam3(Float.parseFloat(strParam3));
			}
		}

		if (request.getParameter("Param4") != null) {
			String strParam4 = request.getParameter("Param4").toString();
			
			if(!strParam4.isEmpty()) {
			model.setParam4(Float.parseFloat(strParam4));
			}
		}

		if (request.getParameter("Param5") != null) {
			String strParam5 = request.getParameter("Param5").toString();
			
			if(!strParam5.isEmpty()) {
			model.setParam5(Float.parseFloat(strParam5));
			}
		}

		if (request.getParameter("Param6") != null) {
			String strParam6 = request.getParameter("Param6").toString();
			
			if(!strParam6.isEmpty()) {
			model.setParam6(Float.parseFloat(strParam6));
			}
		}

		if (request.getParameter("Param7") != null) {
			String strParam7 = request.getParameter("Param7").toString();
			
			if(!strParam7.isEmpty()) {
			model.setParam7(Float.parseFloat(strParam7));
			}
		}

		if (request.getParameter("Param8") != null) {
			String strParam8 = request.getParameter("Param8").toString();
			
			if(!strParam8.isEmpty()) {
			model.setParam8(Float.parseFloat(strParam8));
			}
		}

		if (request.getParameter("SensorName") != null) {
			model.setSensorName(request.getParameter("SensorName"));
		}

		if (request.getParameter("SensorMeasureType") != null) {
			model.setSensorMeasureType(request.getParameter("SensorMeasureType").toString());
		} else {

			String strSensorName = model.getSensorName();

			if (strSensorName.indexOf("振弦") > 0) {
				model.setSensorMeasureType("振弦");
			} else if (strSensorName.indexOf("应变") > 0) {
				model.setSensorMeasureType("应变");
			} else if (strSensorName.indexOf("温度") > 0 || strSensorName.indexOf("温湿度") > 0) {
				model.setSensorMeasureType("温度");
			} else if (strSensorName.indexOf("倾角") > 0) {
				model.setSensorMeasureType("倾角");
			} else if (strSensorName.indexOf("电流") > 0) {
				model.setSensorMeasureType("电流");
			} else if (strSensorName.indexOf("索力") > 0) {
				model.setSensorMeasureType("索力");
			} else if (strSensorName.indexOf("位移") > 0) {
				model.setSensorMeasureType("位移");
			} else if (strSensorName.indexOf("挠度") > 0) {
				model.setSensorMeasureType("挠度");
			} else if (strSensorName.indexOf("裂缝") > 0) {
				model.setSensorMeasureType("裂缝");
			} else if (strSensorName.indexOf("风速") > 0) {
				model.setSensorMeasureType("风速");
			} else if (strSensorName.indexOf("加速度") > 0) {
				model.setSensorMeasureType("加速度");
			} else if (strSensorName.indexOf("测斜仪") > 0) {
				model.setSensorMeasureType("测斜仪");
			} else if (strSensorName.indexOf("沉降仪") > 0) {
				model.setSensorMeasureType("沉降仪");
			} else if (strSensorName.indexOf("静力水准仪") > 0) {
				model.setSensorMeasureType("静力水准仪");
			} else if (strSensorName.indexOf("桥式传感器") > 0) {
				model.setSensorMeasureType("桥式传感器");
			}

		}

		if (request.getParameter("SensorDesc") != null) {
			model.setSensorDesc(request.getParameter("SensorDesc"));
		}

		String strCurPrjName = GetCurPrjName(request);
		SensorAction action = new SensorAction(strCurPrjName);

		boolean bRes = action.AddSeneor(model);

		StringBuffer sb = new StringBuffer();

		if (bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"新增成功\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"新增失败\"}");
		}

		return sb;
	}

	/**
	 * 修改传感器
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer UpdateSensor(HttpServletRequest request) {
		SensorModel model = new SensorModel();

		// 1. 校验前台输入的参数
		
		if(request.getParameter("autoid") != null) {
			String strAutoID=request.getParameter("autoid").toString();
			model.setAutoID(Integer.parseInt(strAutoID));
		}
		
		if (request.getParameter("SensorID") != null) {
			model.setSensorID(request.getParameter("SensorID").toString());
		}

		if (request.getParameter("SensorSpec") != null) {
			model.setSensorSpec(request.getParameter("SensorSpec").toString());
		}

		if (request.getParameter("SensorFactory") != null) {
			model.setSensorFactory(request.getParameter("SensorFactory").toString());
		}

		if (request.getParameter("Param1") != null) {
			String strParam1 = request.getParameter("Param1").toString();
			
			if(!strParam1.isEmpty()) {
				model.setParam1(Float.parseFloat(strParam1));
			}
		}

		if (request.getParameter("Param2") != null) {
			String strParam2 = request.getParameter("Param2").toString();
			
			if(!strParam2.isEmpty()) {
				model.setParam2(Float.parseFloat(strParam2));
			}
		}

		if (request.getParameter("Param3") != null) {
			String strParam3 = request.getParameter("Param3").toString();
			
			if(!strParam3.isEmpty()) {
			model.setParam3(Float.parseFloat(strParam3));
			}
		}

		if (request.getParameter("Param4") != null) {
			String strParam4 = request.getParameter("Param4").toString();
			
			if(!strParam4.isEmpty()) {
			model.setParam4(Float.parseFloat(strParam4));
			}
		}

		if (request.getParameter("Param5") != null) {
			String strParam5 = request.getParameter("Param5").toString();
			
			if(!strParam5.isEmpty()) {
			model.setParam5(Float.parseFloat(strParam5));
			}
		}

		if (request.getParameter("Param6") != null) {
			String strParam6 = request.getParameter("Param6").toString();
			
			if(!strParam6.isEmpty()) {
			model.setParam6(Float.parseFloat(strParam6));
			}
		}

		if (request.getParameter("Param7") != null) {
			String strParam7 = request.getParameter("Param7").toString();
			
			if(!strParam7.isEmpty()) {
			model.setParam7(Float.parseFloat(strParam7));
			}
		}

		if (request.getParameter("Param8") != null) {
			String strParam8 = request.getParameter("Param8").toString();
			
			if(!strParam8.isEmpty()) {
			model.setParam8(Float.parseFloat(strParam8));
			}
		}

		if (request.getParameter("SensorName") != null) {
			model.setSensorName(request.getParameter("SensorName"));
		}

		if (request.getParameter("SensorMeasureType") != null) {
			model.setSensorMeasureType(request.getParameter("SensorMeasureType").toString());
		} else {

			String strSensorName = model.getSensorName();

			if (strSensorName.indexOf("振弦") > 0) {
				model.setSensorMeasureType("振弦");
			} else if (strSensorName.indexOf("应变") > 0) {
				model.setSensorMeasureType("应变");
			} else if (strSensorName.indexOf("温度") > 0 || strSensorName.indexOf("温湿度") > 0) {
				model.setSensorMeasureType("温度");
			} else if (strSensorName.indexOf("倾角") > 0) {
				model.setSensorMeasureType("倾角");
			} else if (strSensorName.indexOf("电流") > 0) {
				model.setSensorMeasureType("电流");
			} else if (strSensorName.indexOf("索力") > 0) {
				model.setSensorMeasureType("索力");
			} else if (strSensorName.indexOf("位移") > 0) {
				model.setSensorMeasureType("位移");
			} else if (strSensorName.indexOf("挠度") > 0) {
				model.setSensorMeasureType("挠度");
			} else if (strSensorName.indexOf("裂缝") > 0) {
				model.setSensorMeasureType("裂缝");
			} else if (strSensorName.indexOf("风速") > 0) {
				model.setSensorMeasureType("风速");
			} else if (strSensorName.indexOf("加速度") > 0) {
				model.setSensorMeasureType("加速度");
			} else if (strSensorName.indexOf("测斜仪") > 0) {
				model.setSensorMeasureType("测斜仪");
			} else if (strSensorName.indexOf("沉降仪") > 0) {
				model.setSensorMeasureType("沉降仪");
			} else if (strSensorName.indexOf("静力水准仪") > 0) {
				model.setSensorMeasureType("静力水准仪");
			} else if (strSensorName.indexOf("桥式传感器") > 0) {
				model.setSensorMeasureType("桥式传感器");
			}

		}

		if (request.getParameter("SensorDesc") != null) {
			model.setSensorDesc(request.getParameter("SensorDesc"));
		}

		String strCurPrjName = GetCurPrjName(request);
		SensorAction action = new SensorAction(strCurPrjName);

		boolean bRes = action.UpdateSensor(model);

		StringBuffer sb = new StringBuffer();

		if (bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"修改成功\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"修改失败\"}");
		}

		return sb;
	}

	/**
	 * 删除传感器
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer DeleteSensor(HttpServletRequest request) {
		SensorModel model = new SensorModel();

		// 1. 校验前台输入的参数
		if(request.getParameter("autoid") != null) {
			String strAutoID=request.getParameter("autoid").toString();
			model.setAutoID(Integer.parseInt(strAutoID));
		}
		
		if (request.getParameter("SensorID") != null) {
			model.setSensorID(request.getParameter("SensorID").toString());
		}

		if (request.getParameter("SensorSpec") != null) {
			model.setSensorSpec(request.getParameter("SensorSpec").toString());
		}

		if (request.getParameter("SensorFactory") != null) {
			model.setSensorFactory(request.getParameter("SensorFactory").toString());
		}

		if (request.getParameter("Param1") != null) {
			String strParam1 = request.getParameter("Param1").toString();
			
			if(!strParam1.isEmpty()) {
				model.setParam1(Float.parseFloat(strParam1));
			}
		}

		if (request.getParameter("Param2") != null) {
			String strParam2 = request.getParameter("Param2").toString();
			
			if(!strParam2.isEmpty()) {
				model.setParam2(Float.parseFloat(strParam2));
			}
		}

		if (request.getParameter("Param3") != null) {
			String strParam3 = request.getParameter("Param3").toString();
			
			if(!strParam3.isEmpty()) {
			model.setParam3(Float.parseFloat(strParam3));
			}
		}

		if (request.getParameter("Param4") != null) {
			String strParam4 = request.getParameter("Param4").toString();
			
			if(!strParam4.isEmpty()) {
			model.setParam4(Float.parseFloat(strParam4));
			}
		}

		if (request.getParameter("Param5") != null) {
			String strParam5 = request.getParameter("Param5").toString();
			
			if(!strParam5.isEmpty()) {
			model.setParam5(Float.parseFloat(strParam5));
			}
		}

		if (request.getParameter("Param6") != null) {
			String strParam6 = request.getParameter("Param6").toString();
			
			if(!strParam6.isEmpty()) {
			model.setParam6(Float.parseFloat(strParam6));
			}
		}

		if (request.getParameter("Param7") != null) {
			String strParam7 = request.getParameter("Param7").toString();
			
			if(!strParam7.isEmpty()) {
			model.setParam7(Float.parseFloat(strParam7));
			}
		}

		if (request.getParameter("Param8") != null) {
			String strParam8 = request.getParameter("Param8").toString();
			
			if(!strParam8.isEmpty()) {
			model.setParam8(Float.parseFloat(strParam8));
			}
		}

		if (request.getParameter("SensorName") != null) {
			model.setSensorName(request.getParameter("SensorName"));
		}

		if (request.getParameter("SensorMeasureType") != null) {
			model.setSensorMeasureType(request.getParameter("SensorMeasureType").toString());
		} else {

			String strSensorName = model.getSensorName();

			if (strSensorName.indexOf("振弦") > 0) {
				model.setSensorMeasureType("振弦");
			} else if (strSensorName.indexOf("应变") > 0) {
				model.setSensorMeasureType("应变");
			} else if (strSensorName.indexOf("温度") > 0 || strSensorName.indexOf("温湿度") > 0) {
				model.setSensorMeasureType("温度");
			} else if (strSensorName.indexOf("倾角") > 0) {
				model.setSensorMeasureType("倾角");
			} else if (strSensorName.indexOf("电流") > 0) {
				model.setSensorMeasureType("电流");
			} else if (strSensorName.indexOf("索力") > 0) {
				model.setSensorMeasureType("索力");
			} else if (strSensorName.indexOf("位移") > 0) {
				model.setSensorMeasureType("位移");
			} else if (strSensorName.indexOf("挠度") > 0) {
				model.setSensorMeasureType("挠度");
			} else if (strSensorName.indexOf("裂缝") > 0) {
				model.setSensorMeasureType("裂缝");
			} else if (strSensorName.indexOf("风速") > 0) {
				model.setSensorMeasureType("风速");
			} else if (strSensorName.indexOf("加速度") > 0) {
				model.setSensorMeasureType("加速度");
			} else if (strSensorName.indexOf("测斜仪") > 0) {
				model.setSensorMeasureType("测斜仪");
			} else if (strSensorName.indexOf("沉降仪") > 0) {
				model.setSensorMeasureType("沉降仪");
			} else if (strSensorName.indexOf("静力水准仪") > 0) {
				model.setSensorMeasureType("静力水准仪");
			} else if (strSensorName.indexOf("桥式传感器") > 0) {
				model.setSensorMeasureType("桥式传感器");
			}

		}

		if (request.getParameter("SensorDesc") != null) {
			model.setSensorDesc(request.getParameter("SensorDesc"));
		}

		String strCurPrjName = GetCurPrjName(request);
		SensorAction action = new SensorAction(strCurPrjName);

		boolean bRes = action.DeleteSensor(model);

		StringBuffer sb = new StringBuffer();

		if (bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"删除成功\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"删除失败\"}");
		}

		return sb;
	}

	/**
	 * 验证传感器匹配
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer CheckSensorMap(HttpServletRequest request) {

		String strSensorID = "";
		// 1. 校验前台输入的参数
		if (request.getParameter("SensorID") != null) {
			strSensorID = request.getParameter("SensorID").toString();
		}
		
		String strCurPrjName = GetCurPrjName(request);
		SensorAction action = new SensorAction(strCurPrjName);

		boolean bRes = action.CheckSeneorMap(strSensorID);

		StringBuffer sb = new StringBuffer();

		if (bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"存在匹配\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"不存在匹配\"}");
		}

		return sb;
	}
}
