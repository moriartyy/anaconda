package org.anaconda.web.http;

import org.anaconda.common.settings.Settings;
import org.anaconda.components.Service;

public abstract class HttpServer extends Service {

	private HttpController httpController;

	protected HttpServer(Settings settings, HttpController httpController) {
		this.httpController = httpController;
	}
	
	public void dispatchRequest(HttpRequest request, HttpChannel channel) {
		HttpHandler handler = httpController.getHandler(request);
		if (handler == null) {
			channel.sendResponse(HttpResponses.NoHandlerFoundResponse(request));
		} else {
			handler.handleRequest(request, channel);
		}
	}
}
