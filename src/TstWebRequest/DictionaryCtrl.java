package TstWebRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Business.ErrorCode;
import Business.InstrumentAcqAction;
import Business.DictionaryAction;
import Model.DictionaryModel;
import Model.InstrumentAcqModel;
import net.sf.json.JSONArray;

public class DictionaryCtrl 
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
	    	case "GetDictionaryData":// 获取字典的分页数据
			{	
				strRet = GetDictionaryData(request);
				break;
			}			
			case "GetDictionaryNameTree":// 获取字典的名称树
			{
				strRet = GetDictionaryNameTree(request);
				break;
			}					
			case "AddDictionary"://新增字典信息
			{
				strRet=AddDictionary(request);
				break;
			}
			case "UpdateDictionary"://更新字典信息
			{
				strRet=UpdateDictionary(request);
				break;
			}					
			case "DeleteDictionary"://更新字典信息
			{
				strRet=DeleteDictionary(request);
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

	private String Index2String(int nIndex) 
	{
		String strReturn = "";
		if (nIndex == 0) 
		{
			strReturn = "不使用";
		}
		else if (nIndex == 1)
		{
			strReturn = "使用";
		}
		
		return strReturn;
	}
	
	/**
	 * 获取结构数据
	 * 
	 * @param request
	 * @return
	 */
	private String GetDictionaryData(HttpServletRequest request)
	{
		String strParent = "";
		StringBuffer sb = new StringBuffer();

		if (request.getParameter("id") != null) {
			strParent = request.getParameter("id").toString();
		}

		String strCurPrjName = GetCurPrjName(request);

		DictionaryAction action = new DictionaryAction(strCurPrjName);

		if (strParent.isEmpty() || strParent.equals("0")) {
			// 获取分页的数据

			int page = Integer.parseInt(request.getParameter("page").toString());

			int rows = Integer.parseInt(request.getParameter("rows").toString());

			List<DictionaryModel> listDictionary = action.GetDictionaryPageData(page, rows);

			int nDataCount = action.GetDictionaryPageDataCount();

			sb.append("{");

			if (nDataCount == 0 || listDictionary == null || listDictionary.size() <= 0) {
				sb.append("}");
			} else {
				sb.append("\"rows\":[");
				for (DictionaryModel item : listDictionary) {
					sb.append("{");
					sb.append(String.format("\"state\":\"closed\","));
					sb.append(String.format("\"id\":\"%d\",", item.getAutoID()));
					sb.append(String.format("\"Name\":\"%s\",", item.getName()));
					sb.append(String.format("\"value\":\"%s\",", item.getValue()));						
					int nSelectIndex = item.getisUsed();  					
					sb.append(String.format("\"isUsed\":\"%s\",", Index2String(nSelectIndex)));					
					sb.append(String.format("\"DictionaryParent\":\"%s\"", item.getDictionaryParent()));
					sb.append("},");
				}
				sb.deleteCharAt(sb.length() - 1);

				sb.append("],\"total\":").append(nDataCount).append("}");
			}

			return sb.toString();

		} else {

			// 获取对应的子结构数据

			List<DictionaryModel> listDictionary = action.SelectDictionaryByParent(strParent);

			sb.append("[");

			if (listDictionary != null && listDictionary.size() > 0) {

				for (DictionaryModel item : listDictionary) {
					sb.append("{");
					sb.append(String.format("\"state\":\"closed\","));
					sb.append(String.format("\"id\":\"%d\",", item.getAutoID()));
					sb.append(String.format("\"Name\":\"%s\",", item.getName()));
					sb.append(String.format("\"value\":\"%s\",", item.getValue()));
					int nSelectIndex = item.getisUsed();  						
					sb.append(String.format("\"isUsed\":\"%s\",", Index2String(nSelectIndex)));
					sb.append(String.format("\"DictionaryParent\":\"%s\"", item.getDictionaryParent()));				
					sb.append("},");
				}

				sb.deleteCharAt(sb.length() - 1);
			}

			sb.append("]");

			return sb.toString();
		}

	}

	/**
	 * 获取结构树数据
	 * 
	 * @param request
	 * @return
	 */
	private String GetDictionaryNameTree(HttpServletRequest request) {

		String strCurPrjName = GetCurPrjName(request);
		
		DictionaryAction action = new DictionaryAction(strCurPrjName);

		StringBuffer sb = action.GetAllDictionaryTree();

		return sb.toString();
	}
	
	
	/**
	 * 新增结构
	 * @param request
	 * @return
	 */
	private String AddDictionary(HttpServletRequest request) 
	{		
		String strCurPrjName = GetCurPrjName(request);

		DictionaryAction action = new DictionaryAction(strCurPrjName);
						
		//1.判断是否重名
		
		DictionaryModel model=new DictionaryModel();
		
		if(request.getParameter("dictionaryName")!=null) 
		{
			model.setName(request.getParameter("dictionaryName"));
			if(model.getName().isEmpty())
			{
				return ErrorCode.ReqParamError("错误：request参数错误，名称不允许为空!!!");
			}
		}
		else 
		{
			return ErrorCode.ReqParamError("错误：缺少字典名称!!!");
		}
		
		if(request.getParameter("dictionaryValue")!=null) 
		{
			model.setValue(request.getParameter("dictionaryValue"));
			if(model.getValue().isEmpty())
			{
				return ErrorCode.ReqParamError("错误：request参数错误，值不允许为空!!!");
			}
		}
		else 
		{
			return ErrorCode.ReqParamError("错误：缺少字典值!!!");
		}
		
		if(request.getParameter("dictionaryisUsed")!=null)
		{
			String strisUsed = request.getParameter("dictionaryisUsed");
			model.setisUsed(Integer.parseInt(strisUsed)); 
			String str = Integer.toString(model.getisUsed());
			if(str.isEmpty())
			{
				return ErrorCode.ReqParamError("错误：request参数错误，请选择是否使用!!!");
			}
		}
		else 
		{
			return ErrorCode.ReqParamError("错误：请选择是否使用!!!");
		}
						
		if(!request.getParameter("dictionaryParent").isEmpty())
		{	
			int nAutoID = action.GetAutoIDByDictionaryName(request.getParameter("dictionaryParent"));
			model.setDictionaryParent(Integer.toString(nAutoID));			
			if(model.getDictionaryParent().isEmpty())
			{
				return ErrorCode.ReqParamError("错误：request参数错误，父节点不允许为空!!!");
			}
		}
		else 
		{
			model.setDictionaryParent("0");
		}			
		
		if(action.GetDictionaryModel(model.getName()) != null)
    	{
        	return ErrorCode.BusinessError("提示：字典已经存在，请确认!!!");
    	}
		
		//写入数据库
		boolean bRes=action.Create(model);
		
		if(!bRes)
		{
			return ErrorCode.ReqParamError("新增字典失败!!!");
		}			
		else
		{
			return ErrorCode.Success("新增字典成功!!!");
		}		
	}
	
    public String UpdateDictionary(HttpServletRequest request) 
    {
    	String strCurPrjName = GetCurPrjName(request);
    	
    	DictionaryModel model = new DictionaryModel();

    	DictionaryAction action = new DictionaryAction(strCurPrjName);
    	
		// 1. 校验前台输入的参数
    	if (request.getParameter("AutoID") != null) 
		{
			model.setAutoID(Integer.parseInt(request.getParameter("AutoID")));
		} 
        else 
        {
        	return ErrorCode.ReqParamError("错误：request参数错误!!!");
		}

		if (request.getParameter("dictionaryName") != null) 
		{
			model.setName(request.getParameter("dictionaryName").toString());
			if(model.getName().isEmpty())
			{
				return ErrorCode.ReqParamError("错误：request参数错误，名称不允许为空!!!");
			}
		}
		
		if (request.getParameter("dictionaryValue") != null) 
		{
			model.setValue(request.getParameter("dictionaryValue").toString());
			if(model.getValue().isEmpty())
			{
				return ErrorCode.ReqParamError("错误：request参数错误，值不允许为空!!!");
			}
		}
		
		if (request.getParameter("dictionaryisUsed") != null) 
		{
			String strisUsed = request.getParameter("dictionaryisUsed");
			if (strisUsed.equals("使用")) 
			{
				strisUsed = "1";
			}
			else if (strisUsed.equals("不使用")) 
			{
				strisUsed = "0";
			}
			model.setisUsed(Integer.parseInt(strisUsed)); 			
			String str = Integer.toString(model.getisUsed());
			if(str.isEmpty())
			{
				return ErrorCode.ReqParamError("错误：request参数错误，请选择是否使用!!!");
			}
		}
		
		if (request.getParameter("dictionaryParent") != null) 
		{			
			int nAutoID = action.GetAutoIDByDictionaryName(request.getParameter("dictionaryParent"));
			model.setDictionaryParent(Integer.toString(nAutoID));				
			if(model.getDictionaryParent().isEmpty())
			{
				return ErrorCode.ReqParamError("错误：request参数错误，父节点不允许为空!!!");
			}
		}

		// 2. 业务逻辑校验		
		// 2.1 查询原有的记录
		DictionaryModel modelOld = action.GetDictionaryModel(model.getAutoID());
		if(modelOld == null)
		{
			return ErrorCode.BusinessError("提示：待修改的数据不存在!!!");
		}
		
		/*// 2.2如果修改仪器出厂编号， 采集仪出厂编号不允许重复
		if(modelOld.getName().equals(model.getName()))
		{
			if(action.GetDictionaryModel(model.getName()) != null)
	    	{
	        	return ErrorCode.BusinessError("提示：字典名称与原名称重复，请确认!!!");
	    	}
		}*/
    		
    	// 3. 执行数据库操作
		if(action.Update(model)==false)
		{
			return ErrorCode.DBError("异常：数据库操作发生异常!!!");
		}

		return ErrorCode.Success("提示：更新成功!!!");
    }
    
    public String DeleteDictionary(HttpServletRequest request) 
    {
    	String strCurPrjName = GetCurPrjName(request);
    	
    	DictionaryModel model = new DictionaryModel();

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
		DictionaryAction action = new DictionaryAction(strCurPrjName);
		
		DictionaryModel selectModel = action.GetDictionaryModel(model.getAutoID());			
		if(action.GetDictionaryModel(selectModel.getName()) == null)
    	{
        	return ErrorCode.BusinessError("提示：字典不存在，请确认!!!");
    	}		
    		
    	// 3. 执行数据库操作
		if(action.Delete(model.getAutoID())==false)
		{
			return ErrorCode.DBError("异常：数据库操作发生异常!!!");
		}

		return ErrorCode.Success("提示：删除成功!!!");
	}
}
