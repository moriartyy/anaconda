package org.anaconda.crawler.appstore;

import java.util.Map;

import org.anaconda.common.settings.Settings;
import org.anaconda.crawler.archive.Archiver;
import org.anaconda.crawler.page.Page;
import org.anaconda.store.elasticsearch.ElasticsearchConfig;
import org.anaconda.store.elasticsearch.ElasticsearchStorage;

public class AppStoreArchiver implements Archiver {
	
	private ElasticsearchStorage<App> storage;

	public AppStoreArchiver(Settings settings) {
		ElasticsearchConfig esConfig = null;
		this.storage = new ElasticsearchStorage<App>(esConfig, "", "") {

			@Override
			protected App toDocument(String id, Map<String, Object> source) {
				// TODO Auto-generated method stub
				return null;
			}

		};
	}

	@Override
	public void archive(Page page) {
		// TODO Auto-generated method stub
		
	}

}
