package TstWebRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import Common.CookieHelp;

/**
 * ͨ�õķ�������������
 * 
 * @author Administrator
 *
 */
public class CommonRequestCtrl {

	/**
	 * ͨ��cookie��ȡ��ǰ�Ĺ�������
	 * @param request
	 * @return
	 */
	public String GetCurPrjNameByCookie(HttpServletRequest request) {
		// ��ȡ��ǰ��cookie�м�¼��prjName

		CookieHelp cookieHelp = new CookieHelp();

		String strCurPrjName = cookieHelp.GetCookieStringValue(request, "prjName");

		try {
			strCurPrjName = URLDecoder.decode(strCurPrjName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (strCurPrjName.isEmpty()) {
			return null;
		}

		return strCurPrjName;
	}
}
