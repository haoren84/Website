package TstWebRequest;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Business.ErrorCode;
import Business.InstrumentAcqAction;
import Model.InstrumentAcqModel;

public class InstrumentAcqCtrl
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
            case "CreateInsAcq":
            {
            	strRet = CreateInsAcq(request);
                break;
            }
            case "UpdateInsAcq":
            {
            	strRet = UpdateInsAcq(request);
                break;
            }
            case "DeleteInsAcq":
            {
            	strRet = DeleteInsAcq(request);
                break;
            }
            case "SelectInsAcq":
            {
            	strRet = SelectInsAcq(request);
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
    
    
    public String CreateInsAcq(HttpServletRequest request) 
    {
    	String strCurPrjName = GetCurPrjName(request);
    	
		InstrumentAcqModel model = new InstrumentAcqModel();

		// 1. У��ǰ̨����Ĳ���
		if (request.getParameter("insFactoryID") != null) {
			model.setinsFactoryID(request.getParameter("insFactoryID").toString());
		} 
		else 
        {
        	return ErrorCode.ReqParamError("����request��������!!!");
		}
		
		if (request.getParameter("insNetID") != null) 
		{
			model.setinsNetID(request.getParameter("insNetID").toString());
		}
		if (request.getParameter("serverAddr") != null) 
		{
			model.setserverAddr(request.getParameter("serverAddr").toString());
		}
		if (request.getParameter("serverPort") != null) 
		{
			String strserverPort = request.getParameter("serverPort").toString();
			model.setserverPort(Integer.parseInt(strserverPort));
		}
        

		// 2. ҵ���߼�У��
		// 2.1 ����ID�������ظ�
		InstrumentAcqAction action = new InstrumentAcqAction(strCurPrjName);
    	if(action.IsExists(model.getinsFactoryID()))
    	{
        	return ErrorCode.BusinessError("��ʾ���ɼ����Ѿ����ڣ���ȷ��!!!");
    	}
    		
    	// 3. ִ�����ݿ����
		if(action.Create(model)==false)
		{
			return ErrorCode.DBError("�쳣�����ݿ���������쳣!!!"); 
		}

		return ErrorCode.Success("��ʾ����ӳɹ�!!!");
	}
    
    
    public String UpdateInsAcq(HttpServletRequest request) 
    {
    	String strCurPrjName = GetCurPrjName(request);
    	
    	InstrumentAcqModel model = new InstrumentAcqModel();

		// 1. У��ǰ̨����Ĳ���
    	if (request.getParameter("AutoID") != null) 
		{
			model.setAutoID(Integer.parseInt(request.getParameter("AutoID")));
		} 
        else 
        {
        	return ErrorCode.ReqParamError("����request��������!!!");
		}

		if (request.getParameter("insFactoryID") != null) 
		{
			model.setinsFactoryID(request.getParameter("insFactoryID").toString());
			if(model.getinsFactoryID().isEmpty())
			{
				return ErrorCode.ReqParamError("����request��������������Ų�����Ϊ��!!!");
			}
		}
		
		if (request.getParameter("insNetID") != null) 
		{
			model.setinsNetID(request.getParameter("insNetID").toString());
			if(model.getinsNetID().isEmpty())
			{
				return ErrorCode.ReqParamError("����request������������ID������Ϊ��!!!");
			}
		}
		
		if (request.getParameter("serverAddr") != null) 
		{
			model.setserverAddr(request.getParameter("serverAddr").toString());
			if(model.getserverAddr().isEmpty())
			{
				return ErrorCode.ReqParamError("����request�������󣬷�������ַ������Ϊ��!!!");
			}
		}
		
		if (request.getParameter("serverPort") != null) 
		{
			String strserverPort = request.getParameter("serverPort").toString();
			model.setserverPort(Integer.parseInt(strserverPort));
			String strValue = Integer.toString(model.getserverPort()); 
			if(strValue.isEmpty())
			{
				return ErrorCode.ReqParamError("����request�������󣬷������˿ڲ�����Ϊ��!!!");
			}
		}

		// 2. ҵ���߼�У��
		InstrumentAcqAction action = new InstrumentAcqAction(strCurPrjName);
		// 2.1 ��ѯԭ�еļ�¼
		InstrumentAcqModel modelOld = action.Select(model.getAutoID());
		if(modelOld == null)
		{
			return ErrorCode.BusinessError("��ʾ�����޸ĵ����ݲ�����!!!");
		}
		
		// 2.2����޸�����������ţ� �ɼ��ǳ�����Ų������ظ�
		if(modelOld.getinsFactoryID().equals(model.getinsFactoryID()) == false)
		{
			if(action.IsExists(model.getinsFactoryID()) == true)
	    	{
	        	return ErrorCode.BusinessError("��ʾ����������Ѿ����ڣ���ȷ��!!!");
	    	}
		}
    		
    	// 3. ִ�����ݿ����
		if(action.Update(model)==false)
		{
			return ErrorCode.DBError("�쳣�����ݿ���������쳣!!!");
		}

		return ErrorCode.Success("��ʾ�����³ɹ�!!!");
	}
    
    
    public String DeleteInsAcq(HttpServletRequest request) 
    {
    	String strCurPrjName = GetCurPrjName(request);
    	
    	InstrumentAcqModel model = new InstrumentAcqModel();

		// 1. У��ǰ̨����Ĳ���
		if (request.getParameter("AutoID") != null) 
		{
			model.setAutoID(Integer.parseInt(request.getParameter("AutoID")));
		} 
        else 
        {
        	return ErrorCode.ReqParamError("����request��������!!!");
		}

		// 2. ҵ���߼�У��
		// 2.1 �������µļ�¼�Ƿ����
		InstrumentAcqAction action = new InstrumentAcqAction(strCurPrjName);
    	if(action.IsExists(model.getAutoID()) == false)
    	{
        	return ErrorCode.BusinessError("��ʾ�����������ڣ���ȷ��!!!");
    	}
    		
    	// 3. ִ�����ݿ����
		if(action.Delete(model.getAutoID())==false)
		{
			return ErrorCode.DBError("�쳣�����ݿ���������쳣!!!");
		}

		return ErrorCode.Success("��ʾ�����³ɹ�!!!");
	}
    
    
    public String SelectInsAcq(HttpServletRequest request) 
    {
    	String strCurPrjName = GetCurPrjName(request);
    	
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
        if (request.getParameter("insFactoryID") != null) 
		{
			condition.put("insFactoryID", request.getParameter("insFactoryID"));
		} 
        
        
		// 2. ҵ���߼�У��
		// 2.1 �������µļ�¼�Ƿ����
		InstrumentAcqAction action = new InstrumentAcqAction(strCurPrjName);
		List<InstrumentAcqModel> list = action.Select(condition);
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
			for (InstrumentAcqModel item : list)
			{
				sb.append("{");
				sb.append(String.format("\"AutoID\":\"%d\",", item.getAutoID()));
				sb.append(String.format("\"insFactoryID\":\"%s\",", item.getinsFactoryID()));
				sb.append(String.format("\"insNetID\":\"%s\",", item.getinsNetID()));
				sb.append(String.format("\"serverAddr\":\"%s\",", item.getserverAddr()));
				sb.append(String.format("\"serverPort\":\"%d\"", item.getserverPort()));
				sb.append("},");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("],\"total\":").append(nTotalCount).append("}");
		}

		return sb.toString();
	}
    
}
