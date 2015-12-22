package com.web.core;

import javax.servlet.ServletContext;

import com.web.handler.Handler;
import com.web.handler.HandlerFactory;



public class Web {

	private Handler handler;
	private static Web  web = new Web() ;
	private ActionMapping actionMapping;
	private Web () {};
	
	public Handler getHandler() {
		return handler;
	}
	
	public static Web getInstances() {
		return web;
	}
	
	public void init(WebConfig webConfig,ServletContext servletContext) {
		Config.configWeb(webConfig);
		initActionMapping();
		initHandler();
	}

	private void initActionMapping() {
		actionMapping = new ActionMapping(Config.getRoutes());
		actionMapping.buildActionMapping();
	}
	
	private void initHandler() {
		Handler actionHandler = new ActionHandler(actionMapping);
		handler = HandlerFactory.getHandler(Config.getHandlers().getHandlerList(), actionHandler);
	}
}
