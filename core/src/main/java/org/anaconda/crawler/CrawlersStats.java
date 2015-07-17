package org.anaconda.crawler;

import java.util.HashMap;
import java.util.Map;

import org.anaconda.common.json.AbstractJsonContent;
import org.anaconda.common.stats.Stats;

public class CrawlersStats extends AbstractJsonContent implements Stats {
	
	private Map<String, CrawlerStats> crawlersStats = new HashMap<>();

	public void add(String crawlerName, CrawlerStats stats) {
		crawlersStats.put(crawlerName, stats);
	}
	
}
