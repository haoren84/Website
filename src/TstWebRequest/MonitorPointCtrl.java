package TstWebRequest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import Business.MonitorPrjAction;

/**
 * �����Ŀ�����ص��������
 * @author Administrator
 *
 */
public class MonitorPointCtrl {
	
	/**
	 * �����������ĸ��������������Ϣ
	 * @param request
	 * @return
	 */
	public boolean AddMonitorByAddCount(HttpServletRequest request) {
		
		String strMonitorName=null;
		int nAddCount=0;
		
		if(request.getParameter("monitorName")!=null) {
			strMonitorName=request.getParameter("monitorName");
		}
		
		if(request.getParameter("addPointCount")!=null) {
			nAddCount=Integer.parseInt(request.getParameter("addPointCount"));
		}
		
		CommonRequestCtrl comCtrl=new CommonRequestCtrl();
		
		String strCurPrjName=comCtrl.GetCurPrjNameByCookie(request);
		
		if(strCurPrjName.isEmpty()) {
			return false;
		}
		
		MonitorPrjAction action=new MonitorPrjAction(strCurPrjName);
		
		boolean bRes=action.AddMonitorPointByAddCount(strMonitorName, nAddCount);
		
		return bRes;
	}
	
	/**
	 * ��ȡ���ķ�ҳ����
	 * @param request
	 * @return
	 */
	public StringBuffer GetMonitorPointPageData(HttpServletRequest request) {
		
		if (request.getParameter("page") == null || request.getParameter("rows") == null) {
			return null;
		}

		int page = Integer.parseInt(request.getParameter("page").toString());

		int rows = Integer.parseInt(request.getParameter("rows").toString());
		
		String strMonitorName=null;
		
		if(request.getParameter("monitorName")!=null) {
			strMonitorName=request.getParameter("monitorName");
		}
		
		CommonRequestCtrl comCtrl=new CommonRequestCtrl();
		
		String strCurPrjName=comCtrl.GetCurPrjNameByCookie(request);
		
		if(strCurPrjName.isEmpty()) {
			return null;
		}
		
		MonitorPrjAction action=new MonitorPrjAction(strCurPrjName);
		
		List<String> list=action.SelectMonitorPointPageData(strMonitorName, page, rows);
		
		int nDataCount=action.SelectMonitorPointDataCount(strMonitorName);
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("{");

		if (nDataCount == 0 || list == null || list.size() <= 0) {
			sb.append("}");
		} else {
			sb.append("\"rows\":[");
			for (String item : list) {
				sb.append("{");
				sb.append(String.format("\"monitorPtName\":\"%s\"", item));
				sb.append("},");
			}
			sb.deleteCharAt(sb.length() - 1);

			sb.append("],\"total\":").append(nDataCount).append("}");
		}

		return sb;
	}
	
	/**
	 * ��ȡ��Ӧ����Ŀ�����еĲ��
	 * @param request
	 * @return
	 */
	public StringBuffer GetAllMonitorPointData(HttpServletRequest request) {
	
		String strMonitorName=null;
		
		if(request.getParameter("monitorName")!=null) {
			strMonitorName=request.getParameter("monitorName");
		}
		
		CommonRequestCtrl comCtrl=new CommonRequestCtrl();
		
		String strCurPrjName=comCtrl.GetCurPrjNameByCookie(request);
		
		if(strCurPrjName.isEmpty()) {
			return null;
		}
		
		MonitorPrjAction action=new MonitorPrjAction(strCurPrjName);
		
		int nDataCount=action.SelectMonitorPointDataCount(strMonitorName);
		
		List<String> list=action.SelectMonitorPointByMonitorName(strMonitorName);
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("{");

		if (nDataCount == 0 || list == null || list.size() <= 0) {
			sb.append("}");
		} else {
			sb.append("\"rows\":[");
			for (String item : list) {
				sb.append("{");
				sb.append(String.format("\"monitorPtName\":\"%s\"", item));
				sb.append("},");
			}
			sb.deleteCharAt(sb.length() - 1);

			sb.append("],\"total\":").append(nDataCount).append("}");
		}

		return sb;
	}

	
}
