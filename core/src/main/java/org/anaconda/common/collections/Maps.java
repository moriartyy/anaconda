package org.anaconda.common.collections;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Maps {
	public static Map<String, String> fromProperties(Properties properties) {
		Map<String, String> map = new HashMap<>();
		properties.forEach((k, v) -> map.put((String)k, (String)v));
		return map;
	}
	
	public static <K, V> HashMap<K, V> newHashMap() {
		return new HashMap<K, V>();
	}
}
