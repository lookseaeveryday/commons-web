package com.web.core;

import com.web.render.Render;

/**
 * ActionRender
 */
final class ActionRender extends Render {
	
	private String actionUrl;
	
	public ActionRender(String actionUrl) {
		this.actionUrl = actionUrl.trim();
	}
	
	public String getActionUrl() {
		return actionUrl;
	}
	
	public void render() {
		
	}
}
