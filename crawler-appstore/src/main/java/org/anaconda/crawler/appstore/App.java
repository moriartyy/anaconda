package org.anaconda.crawler.appstore;

import java.util.HashMap;
import java.util.Map;

import org.anaconda.common.util.PropertiesDecorator;
import org.anaconda.crawler.page.Page;
import org.anaconda.store.elasticsearch.ElasticsearchDocument;

public class App implements ElasticsearchDocument {
	
	private String id;

	public static App of(Page page) {
		PropertiesDecorator properties = new PropertiesDecorator(page.properties());
		App app = new App();
		app.id = properties.getString("id", null);
		return app;
	}
	
	private App() {
	}

	@Override
	public String id() {
		return id;
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();
	
		return map;
	}

}
