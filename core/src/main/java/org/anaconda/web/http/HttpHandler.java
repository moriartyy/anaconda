package org.anaconda.web.http;

public interface HttpHandler {

	void handleRequest(HttpRequest request, HttpChannel channel);
}
