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
	    	case "GetDictionaryData":// ��ȡ�ֵ�ķ�ҳ����
			{	
				strRet = GetDictionaryData(request);
				break;
			}			
			case "GetDictionaryNameTree":// ��ȡ�ֵ��������
			{
				strRet = GetDictionaryNameTree(request);
				break;
			}					
			case "AddDictionary"://�����ֵ���Ϣ
			{
				strRet=AddDictionary(request);
				break;
			}
			case "UpdateDictionary"://�����ֵ���Ϣ
			{
				strRet=UpdateDictionary(request);
				break;
			}					
			case "DeleteDictionary"://�����ֵ���Ϣ
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

	private String Index2String(int nIndex) 
	{
		String strReturn = "";
		if (nIndex == 0) 
		{
			strReturn = "��ʹ��";
		}
		else if (nIndex == 1)
		{
			strReturn = "ʹ��";
		}
		
		return strReturn;
	}
	
	/**
	 * ��ȡ�ṹ����
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
			// ��ȡ��ҳ������

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

			// ��ȡ��Ӧ���ӽṹ����

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
	 * ��ȡ�ṹ������
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
	 * �����ṹ
	 * @param request
	 * @return
	 */
	private String AddDictionary(HttpServletRequest request) 
	{		
		String strCurPrjName = GetCurPrjName(request);

		DictionaryAction action = new DictionaryAction(strCurPrjName);
						
		//1.�ж��Ƿ�����
		
		DictionaryModel model=new DictionaryModel();
		
		if(request.getParameter("dictionaryName")!=null) 
		{
			model.setName(request.getParameter("dictionaryName"));
			if(model.getName().isEmpty())
			{
				return ErrorCode.ReqParamError("����request�����������Ʋ�����Ϊ��!!!");
			}
		}
		else 
		{
			return ErrorCode.ReqParamError("����ȱ���ֵ�����!!!");
		}
		
		if(request.getParameter("dictionaryValue")!=null) 
		{
			model.setValue(request.getParameter("dictionaryValue"));
			if(model.getValue().isEmpty())
			{
				return ErrorCode.ReqParamError("����request��������ֵ������Ϊ��!!!");
			}
		}
		else 
		{
			return ErrorCode.ReqParamError("����ȱ���ֵ�ֵ!!!");
		}
		
		if(request.getParameter("dictionaryisUsed")!=null)
		{
			String strisUsed = request.getParameter("dictionaryisUsed");
			model.setisUsed(Integer.parseInt(strisUsed)); 
			String str = Integer.toString(model.getisUsed());
			if(str.isEmpty())
			{
				return ErrorCode.ReqParamError("����request����������ѡ���Ƿ�ʹ��!!!");
			}
		}
		else 
		{
			return ErrorCode.ReqParamError("������ѡ���Ƿ�ʹ��!!!");
		}
						
		if(!request.getParameter("dictionaryParent").isEmpty())
		{	
			int nAutoID = action.GetAutoIDByDictionaryName(request.getParameter("dictionaryParent"));
			model.setDictionaryParent(Integer.toString(nAutoID));			
			if(model.getDictionaryParent().isEmpty())
			{
				return ErrorCode.ReqParamError("����request�������󣬸��ڵ㲻����Ϊ��!!!");
			}
		}
		else 
		{
			model.setDictionaryParent("0");
		}			
		
		if(action.GetDictionaryModel(model.getName()) != null)
    	{
        	return ErrorCode.BusinessError("��ʾ���ֵ��Ѿ����ڣ���ȷ��!!!");
    	}
		
		//д�����ݿ�
		boolean bRes=action.Create(model);
		
		if(!bRes)
		{
			return ErrorCode.ReqParamError("�����ֵ�ʧ��!!!");
		}			
		else
		{
			return ErrorCode.Success("�����ֵ�ɹ�!!!");
		}		
	}
	
    public String UpdateDictionary(HttpServletRequest request) 
    {
    	String strCurPrjName = GetCurPrjName(request);
    	
    	DictionaryModel model = new DictionaryModel();

    	DictionaryAction action = new DictionaryAction(strCurPrjName);
    	
		// 1. У��ǰ̨����Ĳ���
    	if (request.getParameter("AutoID") != null) 
		{
			model.setAutoID(Integer.parseInt(request.getParameter("AutoID")));
		} 
        else 
        {
        	return ErrorCode.ReqParamError("����request��������!!!");
		}

		if (request.getParameter("dictionaryName") != null) 
		{
			model.setName(request.getParameter("dictionaryName").toString());
			if(model.getName().isEmpty())
			{
				return ErrorCode.ReqParamError("����request�����������Ʋ�����Ϊ��!!!");
			}
		}
		
		if (request.getParameter("dictionaryValue") != null) 
		{
			model.setValue(request.getParameter("dictionaryValue").toString());
			if(model.getValue().isEmpty())
			{
				return ErrorCode.ReqParamError("����request��������ֵ������Ϊ��!!!");
			}
		}
		
		if (request.getParameter("dictionaryisUsed") != null) 
		{
			String strisUsed = request.getParameter("dictionaryisUsed");
			if (strisUsed.equals("ʹ��")) 
			{
				strisUsed = "1";
			}
			else if (strisUsed.equals("��ʹ��")) 
			{
				strisUsed = "0";
			}
			model.setisUsed(Integer.parseInt(strisUsed)); 			
			String str = Integer.toString(model.getisUsed());
			if(str.isEmpty())
			{
				return ErrorCode.ReqParamError("����request����������ѡ���Ƿ�ʹ��!!!");
			}
		}
		
		if (request.getParameter("dictionaryParent") != null) 
		{			
			int nAutoID = action.GetAutoIDByDictionaryName(request.getParameter("dictionaryParent"));
			model.setDictionaryParent(Integer.toString(nAutoID));				
			if(model.getDictionaryParent().isEmpty())
			{
				return ErrorCode.ReqParamError("����request�������󣬸��ڵ㲻����Ϊ��!!!");
			}
		}

		// 2. ҵ���߼�У��		
		// 2.1 ��ѯԭ�еļ�¼
		DictionaryModel modelOld = action.GetDictionaryModel(model.getAutoID());
		if(modelOld == null)
		{
			return ErrorCode.BusinessError("��ʾ�����޸ĵ����ݲ�����!!!");
		}
		
		/*// 2.2����޸�����������ţ� �ɼ��ǳ�����Ų������ظ�
		if(modelOld.getName().equals(model.getName()))
		{
			if(action.GetDictionaryModel(model.getName()) != null)
	    	{
	        	return ErrorCode.BusinessError("��ʾ���ֵ�������ԭ�����ظ�����ȷ��!!!");
	    	}
		}*/
    		
    	// 3. ִ�����ݿ����
		if(action.Update(model)==false)
		{
			return ErrorCode.DBError("�쳣�����ݿ���������쳣!!!");
		}

		return ErrorCode.Success("��ʾ�����³ɹ�!!!");
    }
    
    public String DeleteDictionary(HttpServletRequest request) 
    {
    	String strCurPrjName = GetCurPrjName(request);
    	
    	DictionaryModel model = new DictionaryModel();

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
		DictionaryAction action = new DictionaryAction(strCurPrjName);
		
		DictionaryModel selectModel = action.GetDictionaryModel(model.getAutoID());			
		if(action.GetDictionaryModel(selectModel.getName()) == null)
    	{
        	return ErrorCode.BusinessError("��ʾ���ֵ䲻���ڣ���ȷ��!!!");
    	}		
    		
    	// 3. ִ�����ݿ����
		if(action.Delete(model.getAutoID())==false)
		{
			return ErrorCode.DBError("�쳣�����ݿ���������쳣!!!");
		}

		return ErrorCode.Success("��ʾ��ɾ���ɹ�!!!");
	}
}
