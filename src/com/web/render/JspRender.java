package com.web.render;


public class JspRender extends Render {
	
	public JspRender(String view) {
		this.view = view;
	}
	
	public void render() {
		try {
			System.out.println(request.getServletPath() + "WEB-INF/" + view);
			request.getRequestDispatcher(request.getServletPath() + "WEB-INF/" + view).forward(request, response);
		} catch (Exception e) {
		}
	}
}


