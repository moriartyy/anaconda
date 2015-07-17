package org.anaconda.common.settings;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;

public class SettingsTests {
	
	@Test
	public void testGetMap() {
		Settings settings = Settings.builder().put("sort", "b:asc,a:desc,e:asc,f:desc,k:asc,d:desc").build();
		Map<String, String> sorts = settings.getAsMap("sort", ImmutableMap.of());
		for (Entry<String, String> entry : sorts.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}

}
