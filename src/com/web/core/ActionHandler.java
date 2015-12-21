package com.web.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.ext.aop.Invocation;
import com.web.handler.Handler;
import com.web.log.Logger;
import com.web.mvc.Controller;
import com.web.render.Render;

final class ActionHandler extends Handler {

	private final ActionMapping actionMapping;
	private static final Logger log = Logger.getLogger(ActionHandler.class);

	public ActionHandler(ActionMapping actionMapping) {
		this.actionMapping = actionMapping;
	}

	public final void handle(String target, HttpServletRequest request, HttpServletResponse response,
			boolean[] isHandled) {
		if (target.indexOf('.') != -1) {
			return;
		}
		isHandled[0] = true;
		String[] urlPara = { null };
		Action action = actionMapping.getAction(target, urlPara);

		try {
			Controller controller = action.getControllerClass().newInstance();
			controller.init(request, response);

			new Invocation(action, controller).invoke();

			Render render = controller.getRender();
			if (render instanceof ActionRender) {
				String actionUrl = ((ActionRender) render).getActionUrl();
				if (target.equals(actionUrl))
					throw new RuntimeException("The forward action url is the same as before.");
				else
					handle(actionUrl, request, response, isHandled);
				return;
			}

			if (render == null) {
			} else {
				render.setContext(request, response, action.getViewPath()).render();
			}
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				String qs = request.getQueryString();
				log.error(qs == null ? target : target + "?" + qs, e);
			}
		}

	}
}
