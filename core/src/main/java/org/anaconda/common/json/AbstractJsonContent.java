package org.anaconda.common.json;

public class AbstractJsonContent implements JsonContent {

	@Override
	public String toJson(boolean prettyPrint) {
		if (prettyPrint) {
			return Json.toPrettyJson(this);
		} else {
			return Json.toJson(this);
		}
	}

}
