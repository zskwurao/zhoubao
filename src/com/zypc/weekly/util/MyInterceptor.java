package com.zypc.weekly.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyInterceptor implements HandlerInterceptor {
	private String REDIRECT_IP = "http://127.0.0.1:8080";
	private String url = "https://zypc.xupt.edu.cn/oauth/authorize";
	private String response_type = "code";
	private String client_id = "becef674aa44716a272a760c7a49a024a1ded5a7202a3bb1ad9f8519d1f4274a";
	private String redirect_uri = REDIRECT_IP + "/weekly/oauth.action";


	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object arg2) throws Exception {
		Cookie[] cookies = req.getCookies();
		String URL = url + "?response_type=" + response_type + "&client_id=" + client_id + "&state=1"
				+ "&redirect_uri=" + redirect_uri + "&scope=";
		if(cookies==null){
			req.getRequestDispatcher("/goto.action").forward(req, res);
			return false;
		}else{
			for (Cookie cookie : cookies) {
		        if (cookie.getName().equals("user_id")) {
		        	return true;
		        }
		      }
		}
		req.getRequestDispatcher("/goto.action").forward(req, res);	
		return false;
	}

}
