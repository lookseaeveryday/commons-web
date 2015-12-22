package com.web.mvc;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("unchecked")
class Inject {

	
	public static <T> T inject(Class<?> modelClass, HttpServletRequest request) {
		String modelName = null; 
 		return (T) inject(modelClass, modelName, request);
	}

	private static final <T> T inject(Class<?> modelClass, String modelName, HttpServletRequest request) {
		Object model = null;
		try {
			model = modelClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		injectCommonModel(model, modelName, request, modelClass);
		return (T) model;
	}

	private static final void injectCommonModel(Object model, String modelName, HttpServletRequest request,
			Class<?> modelClass) {
		Method[] methods = modelClass.getMethods();
		// 当modelName为null或者“”时，不添加前缀
		String modelNameAndDot = "";
		if (modelName == "") {
			modelNameAndDot = modelName + ".";
		}
		for (Method method : methods) {
			String methodName = method.getName();
			if (methodName.startsWith("set") == false) // only setter method
				continue;

			Class<?>[] types = method.getParameterTypes();
			if (types.length != 1) // only one parameter
				continue;

			String value = request.getParameter(modelNameAndDot );

			if (value != null) {
				try {
					method.invoke(model, TypeConverter.convert(types[0], value));
				} catch (Exception e) {
						throw new RuntimeException(e);
				}
			}
		}
	}
}
