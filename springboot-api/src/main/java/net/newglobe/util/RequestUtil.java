package net.newglobe.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
	/**
	 * 是否是Ajax异步请求
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {

		String accept = request.getHeader("accept");
		if (accept != null && accept.indexOf("application/json") != -1) {
			return true;
		}
		String xRequestedWith = request.getHeader("X-Requested-With");
		if (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1) {
			return true;
		}

		String uri = request.getRequestURI();
//        if (StringUtils.inStringIgnoreCase(uri, ".json", ".xml"))
//        {
//            return true;
//        }
//
//        String ajax = request.getParameter("__ajax");
//        if (StringUtils.inStringIgnoreCase(ajax, "json", "xml"))
//        {
//            return true;
//        }

		return false;
	}
}
