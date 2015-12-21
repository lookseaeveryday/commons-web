package com.web.render;


public class RenderFactory {

	private static final RenderFactory me = new RenderFactory();

	private RenderFactory() {

	}

	// 
	
	public static RenderFactory me() {
		return me;
	}

	public Render getRender(String view) {
		return new JspRender(view);
	}
}
