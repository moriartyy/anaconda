package org.anaconda.web.http;

import java.util.HashMap;
import java.util.Map;

import org.anaconda.common.json.JsonContent;


public abstract class HttpResponse {
	
	protected Map<String, String> headers = new HashMap<String, String>();
	protected final static String contentType = "application/json; charset=UTF-8";
	protected HttpStatus status;
	
	public HttpStatus status() {
		return status;
	}
	
	public void status(HttpStatus status) {
		this.status = status;
	}

	public Map<String, String> headers() {
		return headers;
	}

	public String contentType() {
		return contentType;
	}

	public abstract JsonContent content();

}
