package com.web.core;

import com.zhucheng.database.WebSources;

public abstract class WebConfig {

	public abstract void configRoute(Routes me);
	public abstract void configPlugin(WebSources db);
}
