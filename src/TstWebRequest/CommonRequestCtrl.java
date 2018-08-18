package TstWebRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import Common.CookieHelp;

/**
 * 通用的方法，处理请求
 * 
 * @author Administrator
 *
 */
public class CommonRequestCtrl {

	/**
	 * 通过cookie获取当前的工程名称
	 * @param request
	 * @return
	 */
	public String GetCurPrjNameByCookie(HttpServletRequest request) {
		// 获取当前的cookie中记录的prjName

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
