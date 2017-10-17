package com.core.util;

import javax.servlet.http.HttpServletRequest;

/**
 * session 工具类
 * 
 */
public class SessionUtil {
	private static String SESSION_USER_KEY = "_session_user_";
	
	public static void setSessionUser(HttpServletRequest request,SessionUser su){
		request.getSession().setAttribute(SESSION_USER_KEY, su);
	}
	
	public static SessionUser getSessionUser(HttpServletRequest request){
		if(request.getSession().getAttribute(SESSION_USER_KEY) != null){
			return (SessionUser)request.getSession().getAttribute(SESSION_USER_KEY);
		}else{
			return null;
		}
	}
	
	public static void removeSessionUser(HttpServletRequest request){
		request.getSession().removeAttribute(SESSION_USER_KEY);
	}
	
	public static boolean isLogin(HttpServletRequest request){
		return getSessionUser(request) == null ? false : true;
	}
	
	public static boolean isEnable(HttpServletRequest request){
		SessionUser su = getSessionUser(request);
		if(su != null && su.getEnable() != null && su.getEnable() == 1){
			return true;
		}
		return false;
	}
}
