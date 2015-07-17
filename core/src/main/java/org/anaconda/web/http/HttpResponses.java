package org.anaconda.web.http;

import java.util.HashMap;
import java.util.Map;

import org.anaconda.common.json.JsonContent;

public class HttpResponses {

	public final static HttpResponse NoHandlerFoundResponse(HttpRequest request) {
		return new NoHandlerFoundResponse(request);
	}

	
	public static class NoHandlerFoundResponse extends HttpResponse {
		
		private HttpRequest request;
		private JsonContent content;

		protected NoHandlerFoundResponse(HttpRequest request) {
			this.request = request;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", "error");
			map.put("message", "No handler found for request uri " + request.url());
			this.content = JsonContent.create(map);
		}
		
		public HttpRequest request() { 
			return request;
		}

		@Override
		public JsonContent content() {
			return content;
		}
	}
}
