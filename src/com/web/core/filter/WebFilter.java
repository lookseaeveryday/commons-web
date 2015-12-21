package com.web.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.core.Web;
import com.web.core.WebConfig;
import com.web.handler.Handler;



public class WebFilter implements Filter {

	private Handler handler;
	private static final Web web = Web.getInstances();
	private WebConfig webConfig;
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		createJFinalConfig(config.getInitParameter("configClass"));
		
		web.init(webConfig,config.getServletContext());
		
		handler = web.getHandler();
	}

	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		String target = request.getRequestURI();
		
		boolean[] isHandled = {false};
		try {
			handler.handle(target, request, response, isHandled);
		}
		catch (Exception e) {
		}
		
		if (isHandled[0] == false)
			chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}

	private void createJFinalConfig(String configClass) {
		if (configClass == null)
			throw new RuntimeException("Please set configClass parameter of JFinalFilter in web.xml");
		
		Object temp = null;
		try {
			temp = Class.forName(configClass).newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Can not create instance of class: " + configClass, e);
		}
		
		if (temp instanceof WebConfig)
			webConfig = (WebConfig)temp;
		else
			throw new RuntimeException("Can not create instance of class: " + configClass + ". Please check the config in web.xml");
	}



}
