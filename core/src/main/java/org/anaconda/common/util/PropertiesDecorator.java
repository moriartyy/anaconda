package org.anaconda.common.util;

import java.util.Properties;
import java.util.function.Function;

public class PropertiesDecorator {
	
	private Properties properties;

	public PropertiesDecorator(Properties properties) {
		this.properties = properties;
	}
	
	public <T> T get(String key, Function<String, T> convertor, T defaultValue) {
		String v = properties.getProperty(key);
		if (v == null) {
			return defaultValue;
		}
		return convertor.apply(v); 
	}
	
	public int getInt(String key, int defaultValue) {
		return get(key, Integer::valueOf, defaultValue);
	}
	
	public String getString(String key, String defaultValue) {
		return properties.getProperty(key);
	}
	
	public float getFloat(String key, float defaultValue) {
		return get(key, Float::valueOf, defaultValue);
	}
	
	public long getLong(String key, long defaultValue) {
		return get(key, Long::valueOf, defaultValue);
	}
	
	public double getDouble(String key, double defaultValue) {
		return get(key, Double::valueOf, defaultValue);
	}

}
