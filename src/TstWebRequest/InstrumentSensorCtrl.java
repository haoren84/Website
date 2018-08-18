package TstWebRequest;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Business.ErrorCode;
import Business.SensorAction;
import Model.SensorModel;

public class InstrumentSensorCtrl
{
	public String ReturnResquest(HttpServletRequest request, HttpServletResponse response) 
    {

		if (request.getParameter("action") == null) {
			return "";
		}

		String strActionName = new String(request.getParameter("action"));
		String strRet = null;
		StringBuffer sb = new StringBuffer();
		if (strActionName == null || strActionName.isEmpty()) {
			return sb.toString();
		}
        
		switch (strActionName) 
        {
            case "CreateInsSensor":
            {
            	strRet = CreateSensor(request);
                break;
            }
            case "UpdateInsSensor":
            {
            	strRet = UpdateSensor(request);
                break;
            }
            case "DeleteInsSensor":
            {
            	strRet = DeleteSensor(request);
                break;
            }
            case "SelectInsSensor":
            {
            	strRet = SelectSensor(request);
                break;
            }
		default:
			break;
		}

		return strRet;
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
    
    
    public String CreateSensor(HttpServletRequest request) 
    {
    	String strCurPrjName = GetCurPrjName(request);
    	
		SensorModel model = new SensorModel();

		// 1. У��ǰ̨����Ĳ���
		if (request.getParameter("SensorID") != null) {
			model.setSensorID(request.getParameter("SensorID").toString());
		} 
        else 
        {
        	return ErrorCode.ReqParamError("����request��������!!!");
		}

		if (request.getParameter("SensorMeasureType") != null) 
		{
			model.setSensorMeasureType(request.getParameter("SensorMeasureType").toString());
		}
		
		if (request.getParameter("SensorSpec") != null) 
		{
			model.setSensorSpec(request.getParameter("SensorSpec").toString());
		}
		
		if (request.getParameter("SensorFactory") != null) 
		{
			model.setSensorFactory(request.getParameter("SensorFactory").toString());
		}

		if (request.getParameter("Param1") != null) 
		{
			String strParam1 = request.getParameter("Param1").toString();
			model.setParam1(Float.parseFloat(strParam1));
		}
		
		if (request.getParameter("Param2") != null) 
		{
			String strParam2 = request.getParameter("Param2").toString();
			model.setParam2(Float.parseFloat(strParam2));
		}
		
		if (request.getParameter("Param3") != null) 
		{
			String strParam3 = request.getParameter("Param3").toString();
			model.setParam3(Float.parseFloat(strParam3));
		}
		
		if (request.getParameter("Param4") != null) 
		{
			String strParam4 = request.getParameter("Param4").toString();
			model.setParam4(Float.parseFloat(strParam4));
		}
		
		if (request.getParameter("Param5") != null) 
		{
			String strParam5 = request.getParameter("Param5").toString();
			model.setParam5(Float.parseFloat(strParam5));
		}
		
		if (request.getParameter("Param6") != null) 
		{
			String strParam6 = request.getParameter("Param6").toString();
			model.setParam6(Float.parseFloat(strParam6));
		}
		
		if (request.getParameter("Param7") != null) 
		{
			String strParam7 = request.getParameter("Param7").toString();
			model.setParam7(Float.parseFloat(strParam7));
		}
		
		if (request.getParameter("Param8") != null) 
		{
			String strParam8 = request.getParameter("Param8").toString();
			model.setParam8(Float.parseFloat(strParam8));
		}

		// 2. ҵ���߼�У��
		// 2.1 ����ID�������ظ�
		SensorAction action = new SensorAction(strCurPrjName);
    	if(action.IsExists(model.getSensorID()))
    	{
        	return ErrorCode.BusinessError("��ʾ���������Ѿ����ڣ���ȷ��!!!");
    	}
    		
    	// 3. ִ�����ݿ����
		if(action.Create(model)==false)
		{
			return ErrorCode.DBError("�쳣�����ݿ���������쳣!!!");
		}

		return ErrorCode.Success("��ʾ����ӳɹ�!!!");
	}
    
    
    public String UpdateSensor(HttpServletRequest request) 
    {
    	SensorModel model = new SensorModel();

		// 1. У��ǰ̨����Ĳ���
    	if (request.getParameter("AutoID") != null) 
		{
			model.setAutoID(Integer.parseInt(request.getParameter("AutoID")));
		} 
        else 
        {
        	return ErrorCode.ReqParamError("����request��������!!!");
		}

		if (request.getParameter("SensorID") != null) 
		{
			model.setSensorID(request.getParameter("SensorID").toString());
			if(model.getSensorID().isEmpty())
			{
				return ErrorCode.ReqParamError("����request�������󣬴�������Ų�����Ϊ��!!!");
			}
		}
		
		if (request.getParameter("SensorMeasureType") != null) 
		{
			model.setSensorMeasureType(request.getParameter("SensorMeasureType").toString());
			if(model.getSensorMeasureType().isEmpty())
			{
				return ErrorCode.ReqParamError("����request�������󣬴������������Ͳ�����Ϊ��!!!");
			}
		}
		
		if (request.getParameter("SensorSpec") != null) 
		{
			model.setSensorSpec(request.getParameter("SensorSpec").toString());
			if(model.getSensorSpec().isEmpty())
			{
				return ErrorCode.ReqParamError("����request�������󣬴������������Ϊ��!!!");
			}
		}
		
		if (request.getParameter("SensorFactory") != null) 
		{
			model.setSensorFactory(request.getParameter("SensorFactory").toString());
			if(model.getSensorFactory().isEmpty())
			{
				return ErrorCode.ReqParamError("����request�������󣬴������������Ҳ�����Ϊ��!!!");
			}
		}		
		
		if (request.getParameter("Param1") != null) 
		{
			String strParam1 = request.getParameter("Param1").toString();
			model.setParam1(Float.parseFloat(strParam1));
		}
		
		if (request.getParameter("Param2") != null) 
		{
			String strParam2 = request.getParameter("Param2").toString();
			model.setParam2(Float.parseFloat(strParam2));
		}
		
		if (request.getParameter("Param3") != null) 
		{
			String strParam3 = request.getParameter("Param3").toString();
			model.setParam3(Float.parseFloat(strParam3));
		}
		
		if (request.getParameter("Param4") != null) 
		{
			String strParam4 = request.getParameter("Param4").toString();
			model.setParam4(Float.parseFloat(strParam4));
		}
		
		if (request.getParameter("Param5") != null) 
		{
			String strParam5 = request.getParameter("Param5").toString();
			model.setParam5(Float.parseFloat(strParam5));
		}
		
		if (request.getParameter("Param6") != null) 
		{
			String strParam6 = request.getParameter("Param6").toString();
			model.setParam6(Float.parseFloat(strParam6));
		}
		
		if (request.getParameter("Param7") != null) 
		{
			String strParam7 = request.getParameter("Param7").toString();
			model.setParam7(Float.parseFloat(strParam7));
		}

		if (request.getParameter("Param8") != null) 
		{
			String strParam8 = request.getParameter("Param8").toString();
			model.setParam8(Float.parseFloat(strParam8));
		}

		String strCurPrjName = GetCurPrjName(request);
		// 2. ҵ���߼�У��
		SensorAction action = new SensorAction(strCurPrjName);
		// 2.1 ��ѯԭ�еļ�¼
		SensorModel modelOld = action.Select(model.getAutoID());
		if(modelOld == null)
		{
			return ErrorCode.BusinessError("��ʾ�����޸ĵ����ݲ�����!!!");
		}
		
		// 2.2����޸�����������ţ� �ɼ��ǳ�����Ų������ظ�
		if(modelOld.getSensorID().equals(model.getSensorID()) == false)
		{
			if(action.IsExists(model.getSensorID()) == true)
	    	{
	        	return ErrorCode.BusinessError("��ʾ������������Ѿ����ڣ���ȷ��!!!");
	    	}
		}
    		
    	// 3. ִ�����ݿ����
		if(action.Update(model)==false)
		{
			return ErrorCode.DBError("�쳣�����ݿ���������쳣!!!");
		}

		return ErrorCode.Success("��ʾ�����³ɹ�!!!");
	}
    
    
    public String DeleteSensor(HttpServletRequest request) 
    {
    	SensorModel model = new SensorModel();

		// 1. У��ǰ̨����Ĳ���
		if (request.getParameter("AutoID") != null) 
		{
			model.setAutoID(Integer.parseInt(request.getParameter("AutoID")));
		} 
        else 
        {
        	return ErrorCode.ReqParamError("����request��������!!!");
		}

		String strCurPrjName = GetCurPrjName(request);
		// 2. ҵ���߼�У��
		// 2.1 �������µļ�¼�Ƿ����
		SensorAction action = new SensorAction(strCurPrjName);
    	if(action.IsExists(model.getAutoID()) == false)
    	{
        	return ErrorCode.BusinessError("��ʾ�������������ڣ���ȷ��!!!");
    	}
    		
    	// 3. ִ�����ݿ����
		if(action.Delete(model.getAutoID())==false)
		{
			return ErrorCode.DBError("�쳣�����ݿ���������쳣!!!");
		}

		return ErrorCode.Success("��ʾ�����³ɹ�!!!");
	}
    
    public String SelectSensor(HttpServletRequest request) 
    {
    	HashMap<String, Object> condition=new HashMap<String, Object>();
        
		// 1. У��ǰ̨����Ĳ���
		if (request.getParameter("page") != null) 
		{
			condition.put("page", Integer.parseInt(request.getParameter("page")));
		} 
        if (request.getParameter("rows") != null) 
		{
			condition.put("rows", Integer.parseInt(request.getParameter("rows")));
		} 
        if (request.getParameter("SensorID") != null) 
		{
			condition.put("SensorID", request.getParameter("SensorID"));
		} 
        
        String strCurPrjName = GetCurPrjName(request);
		// 2. ҵ���߼�У��
		// 2.1 �������µļ�¼�Ƿ����
		SensorAction action = new SensorAction(strCurPrjName);
		List<SensorModel> list = action.Select(condition);
    	int nTotalCount = action.SelectCount(condition);
    		
    	// 3. ִ�����ݿ����
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		if (list == null || list.size() <= 0) 
		{
			sb.append("}");
		} 
		else
		{
			sb.append("\"rows\":[");
			for (SensorModel item : list)
			{
				sb.append("{");
				sb.append(String.format("\"AutoID\":\"%d\",", item.getAutoID()));
				sb.append(String.format("\"SensorID\":\"%s\",", item.getSensorID()));
				sb.append(String.format("\"SensorMeasureType\":\"%s\",", item.getSensorMeasureType()));
				sb.append(String.format("\"SensorSpec\":\"%s\",", item.getSensorSpec()));
				sb.append(String.format("\"SensorFactory\":\"%s\",", item.getSensorFactory()));
				sb.append(String.format("\"Param1\":\"%.2f\",", item.getParam1()));
				sb.append(String.format("\"Param2\":\"%.2f\",", item.getParam2()));
				sb.append(String.format("\"Param3\":\"%.2f\",", item.getParam3()));
				sb.append(String.format("\"Param4\":\"%.2f\",", item.getParam4()));
				sb.append(String.format("\"Param5\":\"%.2f\",", item.getParam5()));
				sb.append(String.format("\"Param6\":\"%.2f\",", item.getParam6()));
				sb.append(String.format("\"Param7\":\"%.2f\",", item.getParam7()));
				sb.append(String.format("\"Param8\":\"%.2f\"", item.getParam8()));							
				sb.append("},");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("],\"total\":").append(nTotalCount).append("}");
		}

		return sb.toString();
	}
}
