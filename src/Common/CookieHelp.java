package Common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * cookie�İ�������
 * @author Administrator
 *
 */
public class CookieHelp {

	/**
	 * ��ȡ�ַ�����cookieֵ
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
