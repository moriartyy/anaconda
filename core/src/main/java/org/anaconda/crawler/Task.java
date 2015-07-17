package org.anaconda.crawler;

import org.anaconda.crawler.page.Page;

public class Task {
	
	private Page page;
	private Crawler crawler;

	public Task(Page page, Crawler crawler) {
		this.page = page;
		this.crawler = crawler;
	}

	public void execute() {
		this.crawler.process(page);
	}

}
