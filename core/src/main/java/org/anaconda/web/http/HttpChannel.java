package org.anaconda.web.http;


public abstract class HttpChannel  {
	
	protected final HttpRequest request;

	protected HttpChannel(HttpRequest request) {
		this.request = request;
	}
	
	public abstract void sendResponse(HttpResponse response);

}
