package org.anaconda.crawler.appstore;

import org.anaconda.crawler.CrawlerModule;
import org.anaconda.plugin.Plugin;

import com.google.inject.Module;

public class AppStorePlugin implements Plugin {

	@Override
	public void processModule(Module module) {
		if (module instanceof CrawlerModule) {
			((CrawlerModule)module).registerCrawler(AppStoreCrawler.class);
		}
	}
}
