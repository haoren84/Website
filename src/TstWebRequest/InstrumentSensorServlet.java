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
	 * ������������
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
			sb = GetSensorPageData(request); // ��ô�������ҳ����
		}
			break;
		case "GetSensorIdComboData": {
			sb = GetSensorIdComboData(request); // ��ô������������
		}
			break;
		case "GetSensorTypeComboData": {
			sb = GetSensorTypeComboData(request); // ��ô�������������
		}
			break;
		case "GetSeneorSelectPage":// ��ȡ��������ҳ����
		{
			sb = GetSeneorSelectPage(request);
		}
			break;
		case "GetSeneorNameComboPageData":// ��ȡ��������ѡ����������
		{
			sb = GetSeneorNameComboPageData(request);
		}
			break;
		case "AddSensor":// ����������
		{
			sb = AddSensor(request);
		}
			break;
		case "UpdateSensor":// �޸Ĵ�����
		{
			sb = UpdateSensor(request);
		}
			break;
		case "DeleteSensor":// ɾ��������
		{
			sb = DeleteSensor(request);
		}
			break;
		case "CheckSensorMap":// ��֤�������Ƿ�ƥ��
		{
			sb = CheckSensorMap(request);
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
	 *
	 */
	private String GetCurPrjName(HttpServletRequest request) {

		CommonRequestCtrl comCtrl = new CommonRequestCtrl();

		String strCurPrjName = comCtrl.GetCurPrjNameByCookie(request);

		return strCurPrjName;
	}

	/**
	 * ��ô�������ҳ����
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
	 * ��ô������������
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
		sb.append(String.format("\"text\":\"%s\"", "ȫ��"));
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
	 * ��ô�������������
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
		sb.append(String.format("\"text\":\"%s\"", "ȫ��"));
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
	 * ��ȡ��������ҳ����
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
	 * ��ȡ�����������ķ�ҳ����
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
	 * ����������
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer AddSensor(HttpServletRequest request) {

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");

		SensorModel model = new SensorModel();

		// 1. У��ǰ̨����Ĳ���
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

			if (strSensorName.indexOf("����") > 0) {
				model.setSensorMeasureType("����");
			} else if (strSensorName.indexOf("Ӧ��") > 0) {
				model.setSensorMeasureType("Ӧ��");
			} else if (strSensorName.indexOf("�¶�") > 0 || strSensorName.indexOf("��ʪ��") > 0) {
				model.setSensorMeasureType("�¶�");
			} else if (strSensorName.indexOf("���") > 0) {
				model.setSensorMeasureType("���");
			} else if (strSensorName.indexOf("����") > 0) {
				model.setSensorMeasureType("����");
			} else if (strSensorName.indexOf("����") > 0) {
				model.setSensorMeasureType("����");
			} else if (strSensorName.indexOf("λ��") > 0) {
				model.setSensorMeasureType("λ��");
			} else if (strSensorName.indexOf("�Ӷ�") > 0) {
				model.setSensorMeasureType("�Ӷ�");
			} else if (strSensorName.indexOf("�ѷ�") > 0) {
				model.setSensorMeasureType("�ѷ�");
			} else if (strSensorName.indexOf("����") > 0) {
				model.setSensorMeasureType("����");
			} else if (strSensorName.indexOf("���ٶ�") > 0) {
				model.setSensorMeasureType("���ٶ�");
			} else if (strSensorName.indexOf("��б��") > 0) {
				model.setSensorMeasureType("��б��");
			} else if (strSensorName.indexOf("������") > 0) {
				model.setSensorMeasureType("������");
			} else if (strSensorName.indexOf("����ˮ׼��") > 0) {
				model.setSensorMeasureType("����ˮ׼��");
			} else if (strSensorName.indexOf("��ʽ������") > 0) {
				model.setSensorMeasureType("��ʽ������");
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
			sb.append("{\"result\":\"true\",\"errorMsg\":\"�����ɹ�\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"����ʧ��\"}");
		}

		return sb;
	}

	/**
	 * �޸Ĵ�����
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer UpdateSensor(HttpServletRequest request) {
		SensorModel model = new SensorModel();

		// 1. У��ǰ̨����Ĳ���
		
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

			if (strSensorName.indexOf("����") > 0) {
				model.setSensorMeasureType("����");
			} else if (strSensorName.indexOf("Ӧ��") > 0) {
				model.setSensorMeasureType("Ӧ��");
			} else if (strSensorName.indexOf("�¶�") > 0 || strSensorName.indexOf("��ʪ��") > 0) {
				model.setSensorMeasureType("�¶�");
			} else if (strSensorName.indexOf("���") > 0) {
				model.setSensorMeasureType("���");
			} else if (strSensorName.indexOf("����") > 0) {
				model.setSensorMeasureType("����");
			} else if (strSensorName.indexOf("����") > 0) {
				model.setSensorMeasureType("����");
			} else if (strSensorName.indexOf("λ��") > 0) {
				model.setSensorMeasureType("λ��");
			} else if (strSensorName.indexOf("�Ӷ�") > 0) {
				model.setSensorMeasureType("�Ӷ�");
			} else if (strSensorName.indexOf("�ѷ�") > 0) {
				model.setSensorMeasureType("�ѷ�");
			} else if (strSensorName.indexOf("����") > 0) {
				model.setSensorMeasureType("����");
			} else if (strSensorName.indexOf("���ٶ�") > 0) {
				model.setSensorMeasureType("���ٶ�");
			} else if (strSensorName.indexOf("��б��") > 0) {
				model.setSensorMeasureType("��б��");
			} else if (strSensorName.indexOf("������") > 0) {
				model.setSensorMeasureType("������");
			} else if (strSensorName.indexOf("����ˮ׼��") > 0) {
				model.setSensorMeasureType("����ˮ׼��");
			} else if (strSensorName.indexOf("��ʽ������") > 0) {
				model.setSensorMeasureType("��ʽ������");
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
			sb.append("{\"result\":\"true\",\"errorMsg\":\"�޸ĳɹ�\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"�޸�ʧ��\"}");
		}

		return sb;
	}

	/**
	 * ɾ��������
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer DeleteSensor(HttpServletRequest request) {
		SensorModel model = new SensorModel();

		// 1. У��ǰ̨����Ĳ���
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

			if (strSensorName.indexOf("����") > 0) {
				model.setSensorMeasureType("����");
			} else if (strSensorName.indexOf("Ӧ��") > 0) {
				model.setSensorMeasureType("Ӧ��");
			} else if (strSensorName.indexOf("�¶�") > 0 || strSensorName.indexOf("��ʪ��") > 0) {
				model.setSensorMeasureType("�¶�");
			} else if (strSensorName.indexOf("���") > 0) {
				model.setSensorMeasureType("���");
			} else if (strSensorName.indexOf("����") > 0) {
				model.setSensorMeasureType("����");
			} else if (strSensorName.indexOf("����") > 0) {
				model.setSensorMeasureType("����");
			} else if (strSensorName.indexOf("λ��") > 0) {
				model.setSensorMeasureType("λ��");
			} else if (strSensorName.indexOf("�Ӷ�") > 0) {
				model.setSensorMeasureType("�Ӷ�");
			} else if (strSensorName.indexOf("�ѷ�") > 0) {
				model.setSensorMeasureType("�ѷ�");
			} else if (strSensorName.indexOf("����") > 0) {
				model.setSensorMeasureType("����");
			} else if (strSensorName.indexOf("���ٶ�") > 0) {
				model.setSensorMeasureType("���ٶ�");
			} else if (strSensorName.indexOf("��б��") > 0) {
				model.setSensorMeasureType("��б��");
			} else if (strSensorName.indexOf("������") > 0) {
				model.setSensorMeasureType("������");
			} else if (strSensorName.indexOf("����ˮ׼��") > 0) {
				model.setSensorMeasureType("����ˮ׼��");
			} else if (strSensorName.indexOf("��ʽ������") > 0) {
				model.setSensorMeasureType("��ʽ������");
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
			sb.append("{\"result\":\"true\",\"errorMsg\":\"ɾ���ɹ�\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ɾ��ʧ��\"}");
		}

		return sb;
	}

	/**
	 * ��֤������ƥ��
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer CheckSensorMap(HttpServletRequest request) {

		String strSensorID = "";
		// 1. У��ǰ̨����Ĳ���
		if (request.getParameter("SensorID") != null) {
			strSensorID = request.getParameter("SensorID").toString();
		}
		
		String strCurPrjName = GetCurPrjName(request);
		SensorAction action = new SensorAction(strCurPrjName);

		boolean bRes = action.CheckSeneorMap(strSensorID);

		StringBuffer sb = new StringBuffer();

		if (bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"����ƥ��\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"������ƥ��\"}");
		}

		return sb;
	}
}
