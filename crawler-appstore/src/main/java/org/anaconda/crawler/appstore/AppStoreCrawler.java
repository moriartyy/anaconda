package org.anaconda.crawler.appstore;

import org.anaconda.common.env.Environment;
import org.anaconda.common.settings.Settings;
import org.anaconda.crawler.Crawler;
import org.anaconda.crawler.CrawlerService;
import org.anaconda.crawler.archive.Archiver;
import org.anaconda.crawler.process.Processor;

import com.google.inject.Inject;

public class AppStoreCrawler extends Crawler {

	@Inject
	public AppStoreCrawler(CrawlerService crawlerService, Settings settings, Environment environment) {
		super(crawlerService, settings, environment);
	}

	@Override
	protected Archiver createArchiver(Settings crawlerSettings) {
		return new AppStoreArchiver(crawlerSettings);
	}

	@Override
	protected Processor createProcessor(Settings crawlerSettings) {
		return new AppStoreProcessor();
	}

	@Override
	protected String name() {
		return "crawler-appstore";
	}

}
