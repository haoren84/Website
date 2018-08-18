package Common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * cookie的帮助方法
 * @author Administrator
 *
 */
public class CookieHelp {

	/**
	 * 获取字符串的cookie值
	 * @param request
	 * @param strName
	 * @return
	 */
	public String GetCookieStringValue(HttpServletRequest request,String strName) {
		
		Cookie[] cookies = request.getCookies();
		
		for(Cookie item :cookies) {
			if(item.getName().equals(strName)) {
				return item.getValue();
			}
		}
		
		return null;
	}
}
