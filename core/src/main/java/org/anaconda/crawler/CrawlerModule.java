package org.anaconda.crawler;

import java.util.LinkedList;
import java.util.List;

import com.google.inject.Binder;
import com.google.inject.Module;

public class CrawlerModule implements Module {
	
	private List<Class<? extends Crawler>> crawlers = new LinkedList<Class<? extends Crawler>>();
	
	public CrawlerModule() {
		
	}
	
	public <C extends Crawler> void registerCrawler(Class<C> crawlerClass) {
		crawlers.add(crawlerClass);
	}

	@Override
	public void configure(Binder binder) {
		binder.bind(CrawlerService.class).asEagerSingleton();
		crawlers.forEach(c -> binder.bind(c).asEagerSingleton());
	}
}