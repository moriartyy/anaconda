package org.anaconda.common.json;

import java.util.Map;

public interface JsonContent {

	public String toJson(boolean prettyPrint);
	
	public static JsonContent create(Map<String, Object> map) {
		return new JsonContent() {
			
			@Override
			public String toJson(boolean prettyPrint) {
				if (prettyPrint) {
					return Json.toPrettyJson(map);
				} else {
					return Json.toJson(map);
				}
			}
		};
	}
}
