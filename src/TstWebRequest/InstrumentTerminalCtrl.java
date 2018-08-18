package TstWebRequest;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Business.ErrorCode;
import Business.InstrumentTerminalAction;
import Model.InstrumentTerminalModel;
import Business.InsturmentChnAction;
import Model.InstrumentChnModel;


public class InstrumentTerminalCtrl
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
            case "CreateInsTerminal":
            {
            	strRet = CreateInsTerminal(request);
                break;
            }
            case "UpdateInsTerminal":
            {
            	strRet = UpdateInsTerminal(request);
                break;
            }
            case "DeleteInsTerminal":
            {
            	strRet = DeleteInsTerminal(request);
                break;
            }
            case "SelectInsTerminal":
            {
            	strRet = SelectInsTerminal(request);
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
    
    public String CreateInsTerminalChn(HttpServletRequest request) 
    {
    	InstrumentChnModel model = new InstrumentChnModel();

		// 1. 校验前台输入的参数
		if (request.getParameter("insFactoryID") != null) {
			model.setinsFactoryID(request.getParameter("insFactoryID").toString());
		} 
        else 
        {
        	return ErrorCode.ReqParamError("错误：request参数错误!!!");
		}

		int nChn = 0;
		if (request.getParameter("insChnCount") != null) {
			String strinsChnCount = request.getParameter("insChnCount").toString();
			//model.setchnID(Integer.parseInt(strinsChnCount));
			nChn = Integer.parseInt(strinsChnCount);
		}
		
		String strCurPrjName = GetCurPrjName(request);
		//写入数据库
		InsturmentChnAction actionInsChn = new InsturmentChnAction(strCurPrjName);
		for(int i = 0; i < nChn; i++)
		{
			model.setchnID(i);
			if(false == actionInsChn.Create(model)) 
			{
				return ErrorCode.DBError("异常：数据库操作发生异常!!!");
			}
		}				
		
		return "Success";
    }
    
    public String UpdateInsTerminalChn(HttpServletRequest request) 
    {
    	InstrumentChnModel model = new InstrumentChnModel();

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
				return ErrorCode.ReqParamError("错误：request参数错误，终端编号不允许为空!!!");
			}
		}
		
		int nChn = 0;
		if (request.getParameter("chnID") != null) 
		{
			String strchnID = request.getParameter("chnID").toString();
			//model.setchnID(Integer.parseInt(strchnID));
			nChn = Integer.parseInt(strchnID);
		}	

		String strCurPrjName = GetCurPrjName(request);
		// 2. 业务逻辑校验
		InsturmentChnAction action = new InsturmentChnAction(strCurPrjName);
		// 2.1 查询原有的记录		
		InstrumentChnModel modelOld = action.Select(model.getAutoID());
		if(modelOld == null)
		{
			return ErrorCode.BusinessError("提示：待修改的数据不存在!!!");
		}
		
		// 2.2如果修改仪器出厂编号， 采集仪出厂编号不允许重复
		if(modelOld.getinsFactoryID().equals(model.getinsFactoryID()) == false)
		{
			if(action.IsExists(model.getinsFactoryID()) == true)
	    	{
	        	return ErrorCode.BusinessError("提示：终端编号已经存在，请确认!!!");
	    	}
		}
    		
		//写入数据库
		InsturmentChnAction actionInsChn = new InsturmentChnAction(strCurPrjName);
		for(int i = 0; i < nChn; i++)
		{
			model.setchnID(i);
			if(false == actionInsChn.Update(model)) 
			{
				return ErrorCode.DBError("异常：数据库操作发生异常!!!");
			}
		}				
		
		return "Success";
	}
    
    public String CreateInsTerminal(HttpServletRequest request) 
    {
    	String strCurPrjName = GetCurPrjName(request);
    	
		InstrumentTerminalModel model = new InstrumentTerminalModel();
		

		// 1. 校验前台输入的参数
		if (request.getParameter("insFactoryID") != null) {
			model.setinsFactoryID(request.getParameter("insFactoryID").toString());
		} 
        else 
        {
        	return ErrorCode.ReqParamError("错误：request参数错误!!!");
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
						
		// 2. 业务逻辑校验
		// 2.1 出厂ID不允许重复
		InstrumentTerminalAction action = new InstrumentTerminalAction(strCurPrjName);
    	if(action.IsExists(model.getinsFactoryID()))
    	{
        	return ErrorCode.BusinessError("提示：终端已经存在，请确认!!!");
    	}
    		
    	// 3. 执行数据库操作
		if(action.Create(model)==false)
		{
			return ErrorCode.DBError("异常：数据库操作发生异常!!!");
		}
		
		//添加InstrumentTerminal对应的InstrumentTerminal_Chn
		if ("Success" != CreateInsTerminalChn(request)) 
		{
			return ErrorCode.DBError("异常：数据库操作发生异常!!!");
		}
		
		return ErrorCode.Success("提示：添加成功!!!");
	}
    
    
    public String UpdateInsTerminal(HttpServletRequest request) 
    {
    	String strCurPrjName = GetCurPrjName(request);
    	
    	InstrumentTerminalModel model = new InstrumentTerminalModel();

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
				return ErrorCode.ReqParamError("错误：request参数错误，终端编号不允许为空!!!");
			}
		}
		
		if (request.getParameter("insID") != null) 
		{
			String strinsID = request.getParameter("insID").toString();
			model.setinsID(Integer.parseInt(strinsID));
			String strValue = Integer.toString(model.getinsID());
			if(strValue.isEmpty())
			{
				return ErrorCode.ReqParamError("错误：request参数错误，机号不允许为空!!!");
			}
		}
		
		if (request.getParameter("insType") != null) 
		{
			String strinsType = request.getParameter("insType").toString();
			model.setinsType(Integer.parseInt(strinsType));
			String strValue = Integer.toString(model.getinsType());
			if(strValue.isEmpty())
			{
				return ErrorCode.ReqParamError("错误：request参数错误，仪器类型不允许为空!!!");
			}
		}
		
		if (request.getParameter("insType") != null) 
		{
			String strinsType = request.getParameter("insType").toString();
			model.setinsType(Integer.parseInt(strinsType));
			String strValue = Integer.toString(model.getinsType());
			if(strValue.isEmpty())
			{
				return ErrorCode.ReqParamError("错误：request参数错误，仪器类型不允许为空!!!");
			}
		}
		
		if (request.getParameter("insChnCount") != null) 
		{
			String strinsChnCount = request.getParameter("insChnCount").toString();
			model.setinsChnCount(Integer.parseInt(strinsChnCount));
			String strValue = Integer.toString(model.getinsChnCount());
			if(strValue.isEmpty())
			{
				return ErrorCode.ReqParamError("错误：request参数错误，通道个数不允许为空!!!");
			}
		}		

		// 2. 业务逻辑校验
		InstrumentTerminalAction action = new InstrumentTerminalAction(strCurPrjName);
		// 2.1 查询原有的记录		
		InstrumentTerminalModel modelOld = action.Select(model.getAutoID());
		if(modelOld == null)
		{
			return ErrorCode.BusinessError("提示：待修改的数据不存在!!!");
		}
		
		// 2.2如果修改仪器出厂编号， 采集仪出厂编号不允许重复
		if(modelOld.getinsFactoryID().equals(model.getinsFactoryID()) == false)
		{
			if(action.IsExists(model.getinsFactoryID()) == true)
	    	{
	        	return ErrorCode.BusinessError("提示：终端编号已经存在，请确认!!!");
	    	}
		}
    		
    	// 3. 执行数据库操作
		if(action.Update(model)==false)
		{
			return ErrorCode.DBError("异常：数据库操作发生异常!!!");
		}

		//更新InstrumentTerminal对应的InstrumentTerminal_Chn
		if ("Success" != UpdateInsTerminalChn(request)) 
		{
			return ErrorCode.DBError("异常：数据库操作发生异常!!!");
		}
		
		return ErrorCode.Success("提示：添加成功!!!");			
	}
    
    
    public String DeleteInsTerminal(HttpServletRequest request) 
    {
    	String strCurPrjName = GetCurPrjName(request);
    	
    	InstrumentTerminalModel model = new InstrumentTerminalModel();

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
		InstrumentTerminalAction action = new InstrumentTerminalAction(strCurPrjName);
    	if(action.IsExists(model.getAutoID()) == false)
    	{
        	return ErrorCode.BusinessError("提示：终端不存在，请确认!!!");
    	}
    		
    	// 3. 执行数据库操作
		if(action.Delete(model.getAutoID())==false)
		{
			return ErrorCode.DBError("异常：数据库操作发生异常!!!");
		}

		return ErrorCode.Success("提示：更新成功!!!");
	}
    
    public String SelectInsTerminal(HttpServletRequest request) 
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
        InstrumentTerminalAction action = new InstrumentTerminalAction(strCurPrjName);
		List<InstrumentTerminalModel> list = action.Select(condition);
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
			for (InstrumentTerminalModel item : list)
			{
				sb.append("{");
				sb.append(String.format("\"AutoID\":\"%d\",", item.getAutoID()));
				sb.append(String.format("\"insFactoryID\":\"%s\",", item.getinsFactoryID()));
				sb.append(String.format("\"insID\":\"%d\",", item.getinsID()));
				sb.append(String.format("\"insType\":\"%d\",", item.getinsType()));
				sb.append(String.format("\"insChnCount\":\"%d\"", item.getinsChnCount()));						
				sb.append("},");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("],\"total\":").append(nTotalCount).append("}");
		}

		return sb.toString();
	}
    
    public String SelectInsTerminalChn(HttpServletRequest request) 
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
        InsturmentChnAction action = new InsturmentChnAction(strCurPrjName);
		List<InstrumentChnModel> list = action.Select(condition);
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
			for (InstrumentChnModel item : list)
			{
				sb.append("{");
				sb.append(String.format("\"AutoID\":\"%d\",", item.getAutoID()));
				sb.append(String.format("\"insFactoryID\":\"%s\",", item.getinsFactoryID()));
				sb.append(String.format("\"chnID\":\"%d\"", item.getchnID()));						
				sb.append("},");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("],\"total\":").append(nTotalCount).append("}");
		}

		return sb.toString();
	}
}

