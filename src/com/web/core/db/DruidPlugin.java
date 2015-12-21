package com.web.core.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.zhucheng.database.WebSources;

/**
 * 贾志鑫
 * 2015/12/21
 */
public class DruidPlugin extends WebSources {

	private String url;
	private String username;
	private String password;
	private String driverClass = null;
	private DruidDataSource ds;

	public void setPassword(String password) {
		this.password = password;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public DruidPlugin() {
	}

	public DruidPlugin(String url, String username, String password, String driverClass) {
		this.url = url;
		this.username = username;
		this.password = password;
		this.driverClass = driverClass;
	}

	@Override
	public void init() {
		ds = new DruidDataSource();
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		if (driverClass != null)
			ds.setDriverClassName(driverClass);
		super.setDataSource(ds);
	}

}
