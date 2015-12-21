package com.web.mvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.web.render.Render;
import com.web.render.RenderFactory;

public abstract class Controller {

	private HttpServletRequest request;
	private HttpServletResponse response;

	public void init(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public String getPara(String name) {
		return request.getParameter(name);
	}

	@SuppressWarnings("unchecked")
	public <T> T getSessionAttr(String key) {
		HttpSession session = request.getSession(false);
		return session != null ? (T) session.getAttribute(key) : null;
	}

	@SuppressWarnings("unchecked")
	public <T> T getModel(Class<T> modelClass) {
		return (T) Inject.inject(modelClass, request);
	}

	public Controller setSessionAttr(String key, Object value) {
		request.getSession().setAttribute(key, value);
		return this;
	}

	public String getPara(String name, String defaultValue) {
		String result = request.getParameter(name);
		return result != null && !"".equals(result) ? result : defaultValue;
	}

	public HttpSession getSession() {
		return request.getSession();
	}

	public Map<String, String[]> getParaMap() {
		return request.getParameterMap();
	}

	public Integer[] getParaValuesToInt(String name) {
		String[] values = request.getParameterValues(name);
		if (values == null)
			return null;
		Integer[] result = new Integer[values.length];
		for (int i = 0; i < result.length; i++)
			result[i] = Integer.parseInt(values[i]);
		return result;
	}

	public Long[] getParaValuesToLong(String name) {
		String[] values = request.getParameterValues(name);
		if (values == null)
			return null;
		Long[] result = new Long[values.length];
		for (int i = 0; i < result.length; i++)
			result[i] = Long.parseLong(values[i]);
		return result;
	}

	public Controller setAttrs(Map<String, Object> attrMap) {
		for (Map.Entry<String, Object> entry : attrMap.entrySet())
			request.setAttribute(entry.getKey(), entry.getValue());
		return this;
	}

	public Controller setAttr(String name, Object value) {
		request.setAttribute(name, value);
		return this;
	}

	public Controller removeAttr(String name) {
		request.removeAttribute(name);
		return this;
	}

	//----------------------jsp 页面
	private static final RenderFactory renderFactory = RenderFactory.me();
	private Render render;

	public void render(String view) {
		render = renderFactory.getRender(view);
	}

	public Render getRender() {
		return render;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}
}
