package TstWebRequest;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Business.RelationInsAction;
import Model.InstrumentAcqModel;
import Model.InstrumentTerminalChnModel;
import Model.InstrumentTerminalModel;
import Model.PointChnSensorRelationModel;
import Model.SensorModel;

/**
 * Servlet implementation class RelationInsServlet
 */
@WebServlet("/RelationInsServlet")
public class RelationInsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RelationInsServlet() {
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

		case "GetAllInsAcqData":// ��ȡ���еĲɼ�������
		{
			sb = GetAllInsAcqData(request);
		}
			break;
		case "GetAllInsTerminalData":// ��ȡ���е��ն���Ϣ
		{
			sb = GetAllInsTerminalData(request);
		}
			break;
		case "GetAllSeneorData":// ��ȡ���еĴ�������Ϣ
		{
			sb = GetAllSeneorData(request);
		}
			break;

		case "CreateInstrumentAcq":// �����ɼ���
		{
			sb = CreateInstrumentAcq(request);
		}
			break;

		case "CreateInsTerminal":// �����ն�
		{
			sb = CreateInsTerminal(request);
		}
			break;

		case "CreateSeneor":// ����������
		{
			sb = CreateSeneor(request);
		}
			break;

		case "UpdateInstrumentAcq":// �޸Ĳɼ���
		{
			sb = UpdateInstrumentAcq(request);
		}
			break;

		case "UpdateInsTerminal":// �޸��ն�
		{
			sb = UpdateInsTerminal(request);
		}
			break;

		case "UpdateSensor":// �޸Ĵ�����
		{
			sb = UpdateSensor(request);
		}
			break;

		case "DeleteInstrumentAcq":// ɾ���ɼ���
		{
			sb = DeleteInstrumentAcq(request);
		}
			break;
		case "DeleteInsTerminal":// ɾ���ն�
		{
			sb = DeleteInsTerminal(request);
		}
			break;

		case "DeleteSeneor":// ɾ��������
		{
			sb = DeleteSeneor(request);
		}
			break;

		case "GetInsTerminalComboboxData":// ��ȡ�ն˵���������
		{
			sb = GetInsTerminalComboboxData(request);
		}
			break;

		case "GetInsTerminalChnModelComboboxData":// ��ȡ�ն�ͨ������������
		{
			sb = GetInsTerminalChnModelComboboxData(request);
		}
			break;

		case "GetSeneorComboboxData":// ��ȡ����������������
		{
			sb = GetSeneorComboboxData(request);
		}
			break;

		case "GetAllPointChnSensorRelationModel":// ��ȡ���е�ƥ���ϵ
		{
			sb = GetAllPointChnSensorRelationModel(request);
		}
			break;

		case "DeletePointChnSensorRelationByID":// ����IDɾ��ƥ����Ϣ
		{
			sb = DeletePointChnSensorRelationByID(request);
		}
			break;

		case "GetRelationAllMPoints":// ��ȡ���в��
		{
			sb = GetRelationAllMPoints(request);
		}
			break;

		case "CreatePointChnSensorRelation":// ����ƥ���ϵ
		{
			sb = CreatePointChnSensorRelation(request);
		}
			break;

		case "GetFactoryIDComboboxData":// ������ŵ���������
		{
			sb=GetFactoryIDComboboxData(request);
		}
			break;

		default:
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
	 * ��ȡ���еĲɼ�������
	 * 
	 * @return
	 */
	private StringBuffer GetAllInsAcqData(HttpServletRequest request) {

		String strCurPrjName = GetCurPrjName(request);

		RelationInsAction action = new RelationInsAction(strCurPrjName);

		List<InstrumentAcqModel> listAcq = action.GetAllInstrumentAcqData();

		StringBuffer sb = new StringBuffer();

		if (listAcq == null || listAcq.size() <= 0) {

			sb.append("{\"total\":0,\"rows\":[]}");

			return sb;
		}

		int nLength = listAcq.size();

		sb.append("{\"total\":" + nLength + ",\"rows\":[");

		for (InstrumentAcqModel item : listAcq) {
			sb.append("{");
			sb.append(String.format("\"id\":\"%s\",", item.getAutoID()));
			sb.append(String.format("\"insFactoryID\":\"%s\",", item.getinsFactoryID()));
			sb.append(String.format("\"insNetID\":\"%s\",", item.getinsNetID()));
			sb.append(String.format("\"serverAddr\":\"%s\",", item.getserverAddr()));
			sb.append(String.format("\"serverPort\":\"%d\"", item.getserverPort()));
			sb.append("},");
		}

		sb.deleteCharAt(sb.length() - 1);

		sb.append("]}");

		return sb;
	}

	/**
	 * ��ȡ���е��ն���Ϣ
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer GetAllInsTerminalData(HttpServletRequest request) {

		String strCurPrjName = GetCurPrjName(request);

		RelationInsAction action = new RelationInsAction(strCurPrjName);

		List<InstrumentTerminalModel> list = action.GetAllInstrumentTerminalData();

		StringBuffer sb = new StringBuffer();

		if (list == null || list.size() <= 0) {

			sb.append("{\"total\":0,\"rows\":[]}");

			return sb;
		}

		int nLength = list.size();

		sb.append("{\"total\":" + nLength + ",\"rows\":[");

		for (InstrumentTerminalModel item : list) {

			sb.append("{");
			sb.append(String.format("\"id\":\"%s\",", item.getAutoID()));
			sb.append(String.format("\"insFactoryID\":\"%s\",", item.getinsFactoryID()));
			sb.append(String.format("\"insType\":\"%s\",", item.getinsType()));
			sb.append(String.format("\"insChnCount\":\"%s\",", item.getinsChnCount()));
			sb.append(String.format("\"insID\":\"%s\"", item.getinsID()));

			sb.append("},");
		}

		sb.deleteCharAt(sb.length() - 1);

		sb.append("]}");

		return sb;
	}

	/**
	 * ��ȡ���еĴ�������Ϣ
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer GetAllSeneorData(HttpServletRequest request) {

		String strCurPrjName = GetCurPrjName(request);

		RelationInsAction action = new RelationInsAction(strCurPrjName);

		List<SensorModel> list = action.GetAllSeneorData();

		StringBuffer sb = new StringBuffer();

		if (list == null || list.size() <= 0) {

			sb.append("{\"total\":0,\"rows\":[]}");

			return sb;
		}

		int nLength = list.size();

		sb.append("{\"total\":" + nLength + ",\"rows\":[");

		for (SensorModel item : list) {

			sb.append("{");
			sb.append(String.format("\"id\":\"%s\",", item.getAutoID()));
			sb.append(String.format("\"SensorID\":\"%s\",", item.getSensorID()));
			sb.append(String.format("\"SensorMeasureType\":\"%s\",", item.getSensorMeasureType()));
			sb.append(String.format("\"SensorSpec\":\"%s\",", item.getSensorSpec()));
			sb.append(String.format("\"SensorFactory\":\"%s\",", item.getSensorFactory()));
			sb.append(String.format("\"Param1\":\"%s\",", item.getParam1()));
			sb.append(String.format("\"Param2\":\"%s\",", item.getParam2()));
			sb.append(String.format("\"Param3\":\"%s\",", item.getParam3()));
			sb.append(String.format("\"Param4\":\"%s\",", item.getParam4()));
			sb.append(String.format("\"Param5\":\"%s\",", item.getParam5()));
			sb.append(String.format("\"Param6\":\"%s\",", item.getParam6()));
			sb.append(String.format("\"Param7\":\"%s\",", item.getParam7()));
			sb.append(String.format("\"Param8\":\"%s\"", item.getParam8()));

			sb.append("},");
		}

		sb.deleteCharAt(sb.length() - 1);

		sb.append("]}");

		return sb;
	}

	/**
	 * �����ɼ���
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer CreateInstrumentAcq(HttpServletRequest request) {

		InstrumentAcqModel model = new InstrumentAcqModel();

		// ��������
		// 1. У��ǰ̨����Ĳ���
		if (request.getParameter("insFactoryID") != null) {
			model.setinsFactoryID(request.getParameter("insFactoryID").toString());
		}

		if (request.getParameter("insNetID") != null) {
			model.setinsNetID(request.getParameter("insNetID").toString());
		}
		if (request.getParameter("serverAddr") != null) {
			model.setserverAddr(request.getParameter("serverAddr").toString());
		}
		if (request.getParameter("serverPort") != null) {
			String strserverPort = request.getParameter("serverPort").toString();
			model.setserverPort(Integer.parseInt(strserverPort));
		}

		String strCurPrjName = GetCurPrjName(request);

		RelationInsAction action = new RelationInsAction(strCurPrjName);

		StringBuffer sb = new StringBuffer();

		boolean bRes = action.CreateInstrumentAcq(model);

		if (bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"�����ɹ�\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"����ʧ��\"}");
		}

		return sb;
	}

	/**
	 * �����ն�
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer CreateInsTerminal(HttpServletRequest request) {

		InstrumentTerminalModel model = new InstrumentTerminalModel();

		// 1. У��ǰ̨����Ĳ���
		if (request.getParameter("insFactoryID") != null) {
			model.setinsFactoryID(request.getParameter("insFactoryID").toString());
		}

		if (request.getParameter("insID") != null) {
			String strinsID = request.getParameter("insID").toString();
			model.setinsID(Integer.parseInt(strinsID));
		}

		if (request.getParameter("insType") != null) {
			String strinsType = request.getParameter("insType").toString();
			model.setinsType(Integer.parseInt(strinsType));
		}

		if (request.getParameter("insChnCount") != null) {
			String strinsChnCount = request.getParameter("insChnCount").toString();
			model.setinsChnCount(Integer.parseInt(strinsChnCount));
		}

		String strCurPrjName = GetCurPrjName(request);

		RelationInsAction action = new RelationInsAction(strCurPrjName);

		StringBuffer sb = new StringBuffer();

		boolean bRes = action.CreateInstrumentTerminal(model);

		if (bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"�����ɹ�\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"����ʧ��\"}");
		}

		return sb;
	}

	/**
	 * ����������
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer CreateSeneor(HttpServletRequest request) {

		SensorModel model = new SensorModel();

		// 1. У��ǰ̨����Ĳ���
		if (request.getParameter("SensorID") != null) {
			model.setSensorID(request.getParameter("SensorID").toString());
		}

		if (request.getParameter("SensorMeasureType") != null) {
			model.setSensorMeasureType(request.getParameter("SensorMeasureType").toString());
		}

		if (request.getParameter("SensorSpec") != null) {
			model.setSensorSpec(request.getParameter("SensorSpec").toString());
		}

		if (request.getParameter("SensorFactory") != null) {
			model.setSensorFactory(request.getParameter("SensorFactory").toString());
		}

		if (request.getParameter("Param1") != null) {
			String strParam1 = request.getParameter("Param1").toString();
			model.setParam1(Float.parseFloat(strParam1));
		}

		if (request.getParameter("Param2") != null) {
			String strParam2 = request.getParameter("Param2").toString();
			model.setParam2(Float.parseFloat(strParam2));
		}

		if (request.getParameter("Param3") != null) {
			String strParam3 = request.getParameter("Param3").toString();
			model.setParam3(Float.parseFloat(strParam3));
		}

		if (request.getParameter("Param4") != null) {
			String strParam4 = request.getParameter("Param4").toString();
			model.setParam4(Float.parseFloat(strParam4));
		}

		if (request.getParameter("Param5") != null) {
			String strParam5 = request.getParameter("Param5").toString();
			model.setParam5(Float.parseFloat(strParam5));
		}

		if (request.getParameter("Param6") != null) {
			String strParam6 = request.getParameter("Param6").toString();
			model.setParam6(Float.parseFloat(strParam6));
		}

		if (request.getParameter("Param7") != null) {
			String strParam7 = request.getParameter("Param7").toString();
			model.setParam7(Float.parseFloat(strParam7));
		}

		if (request.getParameter("Param8") != null) {
			String strParam8 = request.getParameter("Param8").toString();
			model.setParam8(Float.parseFloat(strParam8));
		}

		String strCurPrjName = GetCurPrjName(request);

		RelationInsAction action = new RelationInsAction(strCurPrjName);

		StringBuffer sb = new StringBuffer();

		boolean bRes = action.CreateSeneor(model);

		if (bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"�����ɹ�\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"����ʧ��\"}");
		}

		return sb;
	}

	/**
	 * �޸Ĳɼ���
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer UpdateInstrumentAcq(HttpServletRequest request) {

		InstrumentAcqModel model = new InstrumentAcqModel();

		// ��������
		// 1. У��ǰ̨����Ĳ���

		if (request.getParameter("nAutoID") != null) {
			int nAutoID = Integer.parseInt(request.getParameter("nAutoID").toString());
			model.setAutoID(nAutoID);
		}

		if (request.getParameter("insFactoryID") != null) {
			model.setinsFactoryID(request.getParameter("insFactoryID").toString());
		}

		if (request.getParameter("insNetID") != null) {
			model.setinsNetID(request.getParameter("insNetID").toString());
		}
		if (request.getParameter("serverAddr") != null) {
			model.setserverAddr(request.getParameter("serverAddr").toString());
		}
		if (request.getParameter("serverPort") != null) {
			String strserverPort = request.getParameter("serverPort").toString();
			model.setserverPort(Integer.parseInt(strserverPort));
		}

		String strCurPrjName = GetCurPrjName(request);

		RelationInsAction action = new RelationInsAction(strCurPrjName);

		StringBuffer sb = new StringBuffer();

		boolean bRes = action.UpdateInstrumentAcq(model);

		if (bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"�޸ĳɹ�\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"�޸�ʧ��\"}");
		}

		return sb;
	}

	/**
	 * �޸��ն�
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer UpdateInsTerminal(HttpServletRequest request) {
		InstrumentTerminalModel model = new InstrumentTerminalModel();

		// 1. У��ǰ̨����Ĳ���

		if (request.getParameter("nAutoID") != null) {
			int nAutoID = Integer.parseInt(request.getParameter("nAutoID").toString());
			model.setAutoID(nAutoID);
		}

		if (request.getParameter("insFactoryID") != null) {
			model.setinsFactoryID(request.getParameter("insFactoryID").toString());
		}

		if (request.getParameter("insID") != null) {
			String strinsID = request.getParameter("insID").toString();
			model.setinsID(Integer.parseInt(strinsID));
		}

		if (request.getParameter("insType") != null) {
			String strinsType = request.getParameter("insType").toString();
			model.setinsType(Integer.parseInt(strinsType));
		}

		if (request.getParameter("insChnCount") != null) {
			String strinsChnCount = request.getParameter("insChnCount").toString();
			model.setinsChnCount(Integer.parseInt(strinsChnCount));
		}

		String strCurPrjName = GetCurPrjName(request);

		RelationInsAction action = new RelationInsAction(strCurPrjName);

		StringBuffer sb = new StringBuffer();

		boolean bRes = action.UpdateInstrumentTerminal(model);

		if (bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"�޸ĳɹ�\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"�޸�ʧ��\"}");
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
		if (request.getParameter("nAutoID") != null) {
			int nAutoID = Integer.parseInt(request.getParameter("nAutoID").toString());
			model.setAutoID(nAutoID);
		}

		if (request.getParameter("SensorID") != null) {
			model.setSensorID(request.getParameter("SensorID").toString());
		}

		if (request.getParameter("SensorMeasureType") != null) {
			model.setSensorMeasureType(request.getParameter("SensorMeasureType").toString());
		}

		if (request.getParameter("SensorSpec") != null) {
			model.setSensorSpec(request.getParameter("SensorSpec").toString());
		}

		if (request.getParameter("SensorFactory") != null) {
			model.setSensorFactory(request.getParameter("SensorFactory").toString());
		}

		if (request.getParameter("Param1") != null) {
			String strParam1 = request.getParameter("Param1").toString();
			model.setParam1(Float.parseFloat(strParam1));
		}

		if (request.getParameter("Param2") != null) {
			String strParam2 = request.getParameter("Param2").toString();
			model.setParam2(Float.parseFloat(strParam2));
		}

		if (request.getParameter("Param3") != null) {
			String strParam3 = request.getParameter("Param3").toString();
			model.setParam3(Float.parseFloat(strParam3));
		}

		if (request.getParameter("Param4") != null) {
			String strParam4 = request.getParameter("Param4").toString();
			model.setParam4(Float.parseFloat(strParam4));
		}

		if (request.getParameter("Param5") != null) {
			String strParam5 = request.getParameter("Param5").toString();
			model.setParam5(Float.parseFloat(strParam5));
		}

		if (request.getParameter("Param6") != null) {
			String strParam6 = request.getParameter("Param6").toString();
			model.setParam6(Float.parseFloat(strParam6));
		}

		if (request.getParameter("Param7") != null) {
			String strParam7 = request.getParameter("Param7").toString();
			model.setParam7(Float.parseFloat(strParam7));
		}

		if (request.getParameter("Param8") != null) {
			String strParam8 = request.getParameter("Param8").toString();
			model.setParam8(Float.parseFloat(strParam8));
		}

		String strCurPrjName = GetCurPrjName(request);

		RelationInsAction action = new RelationInsAction(strCurPrjName);

		StringBuffer sb = new StringBuffer();

		boolean bRes = action.UpdateSeneor(model);

		if (bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"�޸ĳɹ�\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"�޸�ʧ��\"}");
		}

		return sb;
	}

	/**
	 * ɾ���ɼ���
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer DeleteInstrumentAcq(HttpServletRequest request) {

		int nAutoID = 0;

		if (request.getParameter("nAutoID") != null) {
			nAutoID = Integer.parseInt(request.getParameter("nAutoID").toString());
		}
		String strCurPrjName = GetCurPrjName(request);

		RelationInsAction action = new RelationInsAction(strCurPrjName);

		StringBuffer sb = new StringBuffer();

		boolean bRes = action.DeleteInsAcq(nAutoID);

		if (bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"ɾ���ɹ�\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ɾ��ʧ��\"}");
		}

		return sb;
	}

	/**
	 * ɾ���ն�
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer DeleteInsTerminal(HttpServletRequest request) {

		int nAutoID = 0;

		if (request.getParameter("nAutoID") != null) {
			nAutoID = Integer.parseInt(request.getParameter("nAutoID").toString());
		}
		String strCurPrjName = GetCurPrjName(request);

		RelationInsAction action = new RelationInsAction(strCurPrjName);

		StringBuffer sb = new StringBuffer();

		boolean bRes = action.DeleteInstrumentTerminal(nAutoID);

		if (bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"ɾ���ɹ�\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ɾ��ʧ��\"}");
		}

		return sb;
	}

	/**
	 * ɾ��������
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer DeleteSeneor(HttpServletRequest request) {

		int nAutoID = 0;

		if (request.getParameter("nAutoID") != null) {
			nAutoID = Integer.parseInt(request.getParameter("nAutoID").toString());
		}

		String strCurPrjName = GetCurPrjName(request);

		RelationInsAction action = new RelationInsAction(strCurPrjName);

		StringBuffer sb = new StringBuffer();

		boolean bRes = action.DeleteSeneor(nAutoID);

		if (bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"ɾ���ɹ�\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ɾ��ʧ��\"}");
		}

		return sb;
	}

	/**
	 * ��ȡ�ն˵�combox��������
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer GetInsTerminalComboboxData(HttpServletRequest request) {

		String strCurPrjName = GetCurPrjName(request);

		RelationInsAction action = new RelationInsAction(strCurPrjName);

		List<InstrumentTerminalModel> list = action.GetAllInstrumentTerminalData();

		StringBuffer sb = new StringBuffer();

		if (list == null || list.size() <= 0) {
			sb.append("[]");

			return sb;
		}

		sb.append("[");

		for (InstrumentTerminalModel item : list) {
			sb.append("{");
			sb.append(String.format("\"id\":\"%s\",", item.getAutoID()));
			sb.append(String.format("\"insFactoryID\":\"%s\",", item.getinsFactoryID()));
			sb.append(String.format("\"insType\":\"%s\",", item.getinsType()));
			sb.append(String.format("\"insChnCount\":\"%s\",", item.getinsChnCount()));
			sb.append(String.format("\"insID\":\"%s\"", item.getinsID()));
			sb.append("},");
		}

		sb.deleteCharAt(sb.length() - 1);

		sb.append("]");

		return sb;
	}

	/**
	 * ��ȡ�ն�ͨ����combox��������
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer GetInsTerminalChnModelComboboxData(HttpServletRequest request) {

		StringBuffer sb = new StringBuffer();

		String strinsFactoryID = null;

		if (request.getParameter("insFactoryID") != null) {

			strinsFactoryID = request.getParameter("insFactoryID").toString();

		} else {
			sb.append("[]");
			return sb;
		}

		String strCurPrjName = GetCurPrjName(request);

		RelationInsAction action = new RelationInsAction(strCurPrjName);

		List<InstrumentTerminalChnModel> list = action.GetInstrumentTerminalChnModelListByinsFactoryID(strinsFactoryID);

		if (list == null || list.size() <= 0) {

			sb.append("[]");

			return sb;
		}

		sb.append("[");

		for (InstrumentTerminalChnModel item : list) {
			sb.append("{");
			sb.append(String.format("\"id\":\"%s\",", item.getAutoID()));
			sb.append(String.format("\"insFactoryID\":\"%s\",", item.getInsFactoryID()));
			sb.append(String.format("\"chnID\":\"%s\",", item.getChnID()));
			sb.append(String.format("\"chnIDName\":\"%s\"", String.format("ͨ��%s", item.getChnID())));
			sb.append("},");
		}

		sb.deleteCharAt(sb.length() - 1);

		sb.append("]");

		return sb;
	}

	/**
	 * ��ȡ��������combox��������
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer GetSeneorComboboxData(HttpServletRequest request) {

		StringBuffer sb = new StringBuffer();

		String strCurPrjName = GetCurPrjName(request);

		RelationInsAction action = new RelationInsAction(strCurPrjName);

		List<SensorModel> list = action.GetAllSeneorData();

		if (list == null || list.size() <= 0) {

			sb.append("[]");

			return sb;
		}

		sb.append("[");

		for (SensorModel item : list) {
			sb.append("{");
			sb.append(String.format("\"id\":\"%s\",", item.getAutoID()));
			sb.append(String.format("\"SensorID\":\"%s\",", item.getSensorID()));
			sb.append(String.format("\"SensorMeasureType\":\"%s\",", item.getSensorMeasureType()));
			sb.append(String.format("\"SensorSpec\":\"%s\",", item.getSensorSpec()));
			sb.append(String.format("\"SensorFactory\":\"%s\",", item.getSensorFactory()));
			sb.append(String.format("\"Param1\":\"%s\",", item.getParam1()));
			sb.append(String.format("\"Param2\":\"%s\",", item.getParam2()));
			sb.append(String.format("\"Param3\":\"%s\",", item.getParam3()));
			sb.append(String.format("\"Param4\":\"%s\",", item.getParam4()));
			sb.append(String.format("\"Param5\":\"%s\",", item.getParam5()));
			sb.append(String.format("\"Param6\":\"%s\",", item.getParam6()));
			sb.append(String.format("\"Param7\":\"%s\",", item.getParam7()));
			sb.append(String.format("\"Param8\":\"%s\"", item.getParam8()));

			sb.append("},");
		}

		sb.deleteCharAt(sb.length() - 1);

		sb.append("]");

		return sb;
	}

	/**
	 * ��ȡȫ����ƥ����Ϣ--ҳ��table��
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer GetAllPointChnSensorRelationModel(HttpServletRequest request) {

		StringBuffer sb = new StringBuffer();

		String strCurPrjName = GetCurPrjName(request);

		RelationInsAction action = new RelationInsAction(strCurPrjName);

		List<PointChnSensorRelationModel> list = action.GetAllPointChnSensorRelationModelList();

		if (list == null || list.size() <= 0) {

			sb.append("[]");

			return sb;
		}

		sb.append("[");

		for (PointChnSensorRelationModel item : list) {
			sb.append("{");
			sb.append(String.format("\"id\":\"%s\",", item.getAutoID()));
			sb.append(String.format("\"monitorName\":\"%s\",", item.getMonitorName()));
			sb.append(String.format("\"monitorPtName\":\"%s\",", item.getMonitorPtName()));
			sb.append(String.format("\"insFactoryID\":\"%s\",", item.getInsFactoryID()));
			sb.append(String.format("\"chnID\":\"%s\",", item.getChnID()));
			sb.append(String.format("\"chnIDName\":\"%s\",", String.format("ͨ��%s", item.getChnID()+1)));
			sb.append(String.format("\"SensorID\":\"%s\"", item.getSensorID()));
			sb.append("},");
		}

		sb.deleteCharAt(sb.length() - 1);

		sb.append("]");

		return sb;
	}

	/**
	 * ����IDɾ��ƥ����Ϣ
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer DeletePointChnSensorRelationByID(HttpServletRequest request) {

		int nAutoID = 0;

		if (request.getParameter("nAutoID") != null) {
			nAutoID = Integer.parseInt(request.getParameter("nAutoID").toString());
		}

		String strCurPrjName = GetCurPrjName(request);

		RelationInsAction action = new RelationInsAction(strCurPrjName);

		StringBuffer sb = new StringBuffer();

		boolean bRes = action.DeletePointChnSensorRelationByAutoID(nAutoID);

		if (bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"ɾ���ɹ�\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"ɾ��ʧ��\"}");
		}

		return sb;
	}

	/**
	 * ��ȡƥ���ϵ����ʹ�õ����в��
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer GetRelationAllMPoints(HttpServletRequest request) {

		String strCurPrjName = GetCurPrjName(request);

		RelationInsAction action = new RelationInsAction(strCurPrjName);

		StringBuffer sb = action.GetAllMapMPointTree();

		return sb;
	}

	/**
	 * ����ƥ���ϵ
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer CreatePointChnSensorRelation(HttpServletRequest request) {

		PointChnSensorRelationModel model = new PointChnSensorRelationModel();

		if (request.getParameter("insFactoryID") != null) {
			model.setInsFactoryID(request.getParameter("insFactoryID").toString());
		}

		if (request.getParameter("monitorName") != null) {
			model.setMonitorName(request.getParameter("monitorName").toString());
		}
		if (request.getParameter("monitorPtName") != null) {
			model.setMonitorPtName(request.getParameter("monitorPtName").toString());
		}
		if (request.getParameter("SensorID") != null) {
			model.setSensorID(request.getParameter("SensorID").toString());
		}
		if (request.getParameter("chnID") != null) {
			model.setChnID(Integer.parseInt(request.getParameter("chnID").toString()));
		}

		String strCurPrjName = GetCurPrjName(request);

		RelationInsAction action = new RelationInsAction(strCurPrjName);

		boolean bRes = action.CreatePointChnSensorRelation(model);

		StringBuffer sb = new StringBuffer();

		if (bRes) {
			sb.append("{\"result\":\"true\",\"errorMsg\":\"�����ɹ�\"}");
		} else {
			sb.append("{\"result\":\"false\",\"errorMsg\":\"����ʧ��\"}");
		}
		return sb;
	}

	/**
	 * ��ȡ������ŵ���������
	 * 
	 * @param request
	 * @return
	 */
	private StringBuffer GetFactoryIDComboboxData(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();

		String strCurPrjName = GetCurPrjName(request);

		RelationInsAction action = new RelationInsAction(strCurPrjName);

		List<InstrumentTerminalModel> list = action.GetInstrumentTerminalDataByConfigCrmCode();

		if (list == null || list.size() <= 0) {
			sb.append("[]");

			return sb;
		}

		sb.append("[");

		for (InstrumentTerminalModel model : list) {

			sb.append("{");
			sb.append(String.format("\"text\":\"%s\"", model.getinsFactoryID()));
			sb.append("},");
		}

		sb.deleteCharAt(sb.length() - 1);

		sb.append("]");

		return sb;

	}

}
