package com.web.core;

import java.io.File;

/**
 * Global constants definition
 */
public interface Const {
	
	String JFINAL_VERSION = "2.0";
	
	String DEFAULT_ENCODING = "UTF-8";
	
	boolean DEFAULT_DEV_MODE = false;
	
	String DEFAULT_URL_PARA_SEPARATOR = "-";
	
	String DEFAULT_JSP_EXTENSION = ".jsp";
	
	String DEFAULT_FREE_MARKER_EXTENSION = ".html";			// The original is ".ftl", Recommend ".html"
	
	String DEFAULT_VELOCITY_EXTENSION = ".vm";
	
	// "WEB-INF/download" + File.separator maybe better otherwise it can be downloaded by browser directly
	String DEFAULT_FILE_RENDER_BASE_PATH = File.separator + "download" + File.separator;
	
	int DEFAULT_MAX_POST_SIZE = 1024 * 1024 * 10;  			// Default max post size of multipart request: 10 Meg
	
	int DEFAULT_I18N_MAX_AGE_OF_COOKIE = 999999999;
	
	int DEFAULT_FREEMARKER_TEMPLATE_UPDATE_DELAY = 3600;	// For not devMode only
	
	String DEFAULT_TOKEN_NAME = "jfinal_token";
	
	int DEFAULT_SECONDS_OF_TOKEN_TIME_OUT = 900;			// 900 seconds ---> 15 minutes
	
	int MIN_SECONDS_OF_TOKEN_TIME_OUT = 300;				// 300 seconds ---> 5 minutes
}

