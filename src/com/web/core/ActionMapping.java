package com.web.core;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.web.mvc.Controller;


public class ActionMapping {

	private static final String SLASH = "/";
	private Routes routes;
	private final Map<String, Action> mapping = new HashMap<String, Action>();

	ActionMapping(Routes routes) {
		this.routes = routes;
	}
	
	void buildActionMapping() {
		mapping.clear();
		Set<String> excludedMethodName = buildExcludedMethodName();
		for (Entry<String, Class<? extends Controller>> entry : routes.getEntrySet()) {
			Class<? extends Controller> controllerClass = entry.getValue();
			
			boolean sonOfController = (controllerClass.getSuperclass() == Controller.class);
			Method[] methods = (controllerClass.getDeclaredMethods());
			for (Method method : methods) {
				String methodName = method.getName();
				if (excludedMethodName.contains(methodName) || method.getParameterTypes().length != 0)
					continue ;
				if (sonOfController && !Modifier.isPublic(method.getModifiers()))
					continue ;
				
				String controllerKey = entry.getKey();
				
				String actionKey;
				
				if (methodName.equals("index")) {
					actionKey = controllerKey;
				}
				else {
					actionKey = controllerKey.equals(SLASH) ? SLASH + methodName : controllerKey + SLASH + methodName;
				}
				
				Action action = new Action(controllerKey, actionKey, controllerClass, method, methodName, routes.getViewPath(controllerKey));
				if (mapping.put(actionKey, action) != null) {}
			}
		}
		
		// support url = controllerKey + urlParas with "/" of controllerKey
		Action actoin = mapping.get("/");
		if (actoin != null)
			mapping.put("", actoin);
	}
	
	Action getAction(String url, String[] urlPara) {
		Action action = mapping.get(url);
		if (action != null) {
			return action;
		}
		
		// --------
		int i = url.lastIndexOf(SLASH);
		if (i != -1) {
			action = mapping.get(url.substring(0, i));
			urlPara[0] = url.substring(i + 1);
		}
		
		return action;
	}
	


	private Set<String> buildExcludedMethodName() {
		Set<String> excludedMethodName = new HashSet<String>();
		Method[] methods = Controller.class.getMethods();
		for (Method m : methods) {
			if (m.getParameterTypes().length == 0)
				excludedMethodName.add(m.getName());
		}
		return excludedMethodName;
	}

}
