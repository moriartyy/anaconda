package org.anaconda.crawler;

import java.io.File;
import java.util.List;

import org.anaconda.common.env.Environment;
import org.anaconda.common.io.Files;
import org.anaconda.common.settings.PropertiesFileSettingsPreparer;
import org.anaconda.common.settings.Settings;
import org.anaconda.common.stats.StatsSupplier;
import org.anaconda.components.AbstractLifecycleComponent;
import org.anaconda.crawler.archive.Archiver;
import org.anaconda.crawler.page.Page;
import org.anaconda.crawler.process.Processor;
import org.anaconda.crawler.scheduler.DefaultScheduler;
import org.anaconda.crawler.scheduler.Scheduler;

public abstract class Crawler extends AbstractLifecycleComponent implements StatsSupplier<CrawlerStats> {
	
	protected Settings crawlerSettings;
	protected Scheduler scheduler;
	protected Processor processor;
	protected final int priority;
	protected Archiver archiver;
	
	protected Crawler(CrawlerService crawlerService, Settings settings, Environment environment) {
		crawlerService.registerCrawler(this);
		File config = Files.file(environment.configDir(), name() + ".conf");
		crawlerSettings = new PropertiesFileSettingsPreparer(config).prepareSettings();
		this.scheduler = createScheduler(crawlerSettings);
		this.processor = createProcessor(crawlerSettings);
		this.archiver = createArchiver(crawlerSettings);
		this.priority = settings.getAsInt("crawler.priority", 1);
	}

	@Override
	protected void doStart() {
		this.scheduler.start();
		this.processor.start();
		
	}
	
	@Override
	protected void doStop() {
		this.scheduler.stop();
		this.processor.stop();
	}
	
	@Override
	protected void doClose() {
		this.scheduler.close();
		this.processor.close();
	}
	
	public int priority() {
		return priority;
	}
	
	@Override
	public CrawlerStats getStats() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Task> acquire(int n) {
		return this.scheduler.acquire(n).stream()
				.map(p -> {return new Task(p, this);})
				.collect(java.util.stream.Collectors.toList());
	}
	
	public void process(Page page) {
		try {
			processor.process(page);
			scheduler.enlist(page);
			archiver.archive(page);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	protected Scheduler createScheduler(Settings crawlerSettings) {
		return new DefaultScheduler(crawlerSettings);
	}

	protected abstract Archiver createArchiver(Settings crawlerSettings);
	protected abstract Processor createProcessor(Settings crawlerSettings);
	protected abstract String name();
}
