package com.web.core;

import com.web.handler.Handlers;
import com.zhucheng.database.WebSources;

public class Config {

	private static final Handlers handlers = new Handlers();
	private static final Routes routes = new Routes(){public void config() {}};
	private static final WebSources plugins = new WebSources();
	
	static void configWeb(WebConfig webConfig) {
		webConfig.configRoute(routes);
		webConfig.configPlugin(plugins);
	}
	
	public static Routes getRoutes() {
		return routes;
	}
	
	public static Handlers getHandlers() {
		return handlers;
	}
}
