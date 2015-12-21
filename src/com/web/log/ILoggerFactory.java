package com.web.log;

/**
 * ILoggerFactory.
 */
public interface ILoggerFactory {
	
	Logger getLogger(Class<?> clazz);
	
	Logger getLogger(String name);
}
