package com.zypc.weekly.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class myFilter implements Filter {
	private String REDIRECT_IP = "http://zb.xupt.org";
	private String url = "https://zypc.xupt.edu.cn/oauth/authorize";
	private String response_type = "code";
	private String client_id = "becef674aa44716a272a760c7a49a024a1ded5a7202a3bb1ad9f8519d1f4274a";
	private String redirect_uri = REDIRECT_IP + "/weekly/oauth.action";

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String URL = "redirect:" + url + "?response_type=" + response_type + "&client_id=" + client_id + "&state=1"
				+ "&redirect_uri=" + redirect_uri + "&scope=";
		// TODO Auto-generated method stub
		 HttpServletRequest req =  (HttpServletRequest) request;
	     HttpServletResponse res = (HttpServletResponse) response;
	     Cookie[] cookies = req.getCookies();
	     int i = 0;
		    if (cookies != null) {
		      for (Cookie cookie : cookies) {
		        if (cookie.getName().equals("user_id")) {
		        	i++;
		        	
		        }
		      }
		      if(i==0){
		    	res.sendRedirect(URL);
		      }else{
		    	  chain.doFilter(req, res);
		      }
		    }else{
		    	chain.doFilter(req, res);
		    }
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
