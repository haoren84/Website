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
    
    
    public String CreateInsAcq(HttpServletRequest request) 
    {
    	String strCurPrjName = GetCurPrjName(request);
    	
		InstrumentAcqModel model = new InstrumentAcqModel();

		// 1. 校验前台输入的参数
		if (request.getParameter("insFactoryID") != null) {
			model.setinsFactoryID(request.getParameter("insFactoryID").toString());
		} 
		else 
        {
        	return ErrorCode.ReqParamError("错误：request参数错误!!!");
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
        

		// 2. 业务逻辑校验
		// 2.1 出厂ID不允许重复
		InstrumentAcqAction action = new InstrumentAcqAction(strCurPrjName);
    	if(action.IsExists(model.getinsFactoryID()))
    	{
        	return ErrorCode.BusinessError("提示：采集仪已经存在，请确认!!!");
    	}
    		
    	// 3. 执行数据库操作
		if(action.Create(model)==false)
		{
			return ErrorCode.DBError("异常：数据库操作发生异常!!!"); 
		}

		return ErrorCode.Success("提示：添加成功!!!");
	}
    
    
    public String UpdateInsAcq(HttpServletRequest request) 
    {
    	String strCurPrjName = GetCurPrjName(request);
    	
    	InstrumentAcqModel model = new InstrumentAcqModel();

		// 1. 校验前台输入的参数
    	if (request.getParameter("AutoID") != null) 
		{
			model.setAutoID(Integer.parseInt(request.getParameter("AutoID")));
		} 
        else 
        {
        	return ErrorCode.ReqParamError("错误：request参数错误!!!");
		}

		if (request.getParameter("insFactoryID") != null) 
		{
			model.setinsFactoryID(request.getParameter("insFactoryID").toString());
			if(model.getinsFactoryID().isEmpty())
			{
				return ErrorCode.ReqParamError("错误：request参数错误，仪器编号不允许为空!!!");
			}
		}
		
		if (request.getParameter("insNetID") != null) 
		{
			model.setinsNetID(request.getParameter("insNetID").toString());
			if(model.getinsNetID().isEmpty())
			{
				return ErrorCode.ReqParamError("错误：request参数错误，网络ID不允许为空!!!");
			}
		}
		
		if (request.getParameter("serverAddr") != null) 
		{
			model.setserverAddr(request.getParameter("serverAddr").toString());
			if(model.getserverAddr().isEmpty())
			{
				return ErrorCode.ReqParamError("错误：request参数错误，服务器地址不允许为空!!!");
			}
		}
		
		if (request.getParameter("serverPort") != null) 
		{
			String strserverPort = request.getParameter("serverPort").toString();
			model.setserverPort(Integer.parseInt(strserverPort));
			String strValue = Integer.toString(model.getserverPort()); 
			if(strValue.isEmpty())
			{
				return ErrorCode.ReqParamError("错误：request参数错误，服务器端口不允许为空!!!");
			}
		}

		// 2. 业务逻辑校验
		InstrumentAcqAction action = new InstrumentAcqAction(strCurPrjName);
		// 2.1 查询原有的记录
		InstrumentAcqModel modelOld = action.Select(model.getAutoID());
		if(modelOld == null)
		{
			return ErrorCode.BusinessError("提示：待修改的数据不存在!!!");
		}
		
		// 2.2如果修改仪器出厂编号， 采集仪出厂编号不允许重复
		if(modelOld.getinsFactoryID().equals(model.getinsFactoryID()) == false)
		{
			if(action.IsExists(model.getinsFactoryID()) == true)
	    	{
	        	return ErrorCode.BusinessError("提示：出厂编号已经存在，请确认!!!");
	    	}
		}
    		
    	// 3. 执行数据库操作
		if(action.Update(model)==false)
		{
			return ErrorCode.DBError("异常：数据库操作发生异常!!!");
		}

		return ErrorCode.Success("提示：更新成功!!!");
	}
    
    
    public String DeleteInsAcq(HttpServletRequest request) 
    {
    	String strCurPrjName = GetCurPrjName(request);
    	
    	InstrumentAcqModel model = new InstrumentAcqModel();

		// 1. 校验前台输入的参数
		if (request.getParameter("AutoID") != null) 
		{
			model.setAutoID(Integer.parseInt(request.getParameter("AutoID")));
		} 
        else 
        {
        	return ErrorCode.ReqParamError("错误：request参数错误!!!");
		}

		// 2. 业务逻辑校验
		// 2.1 检查待更新的记录是否存在
		InstrumentAcqAction action = new InstrumentAcqAction(strCurPrjName);
    	if(action.IsExists(model.getAutoID()) == false)
    	{
        	return ErrorCode.BusinessError("提示：仪器不存在，请确认!!!");
    	}
    		
    	// 3. 执行数据库操作
		if(action.Delete(model.getAutoID())==false)
		{
			return ErrorCode.DBError("异常：数据库操作发生异常!!!");
		}

		return ErrorCode.Success("提示：更新成功!!!");
	}
    
    
    public String SelectInsAcq(HttpServletRequest request) 
    {
    	String strCurPrjName = GetCurPrjName(request);
    	
    	HashMap<String, Object> condition=new HashMap<String, Object>();
        
		// 1. 校验前台输入的参数
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
        
        
		// 2. 业务逻辑校验
		// 2.1 检查待更新的记录是否存在
		InstrumentAcqAction action = new InstrumentAcqAction(strCurPrjName);
		List<InstrumentAcqModel> list = action.Select(condition);
    	int nTotalCount = action.SelectCount(condition);
    		
    	// 3. 执行数据库操作
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
