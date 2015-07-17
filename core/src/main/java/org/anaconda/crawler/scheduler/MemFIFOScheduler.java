package org.anaconda.crawler.scheduler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.anaconda.common.settings.Settings;
import org.anaconda.crawler.page.Page;

public class MemFIFOScheduler extends Scheduler {

	private Queue<Page> queue = new LinkedList<Page>();
	
	public MemFIFOScheduler(Settings settings) {
		loadSeeds();
	}
	
	private void loadSeeds() {
		tryEnlist("http://www.hichao.com/");
	}
	
	private void tryEnlist(String url) {
		try {
			enlist(new Page(new URL(url)));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	@Override 
	public Page acquire() {
		return queue.poll();
	}

	@Override
	public void enlist(Page page) {
		queue.offer(page);
	}

	@Override
	public List<Page> acquire(int n) {
		List<Page> pages = new ArrayList<Page>(n);
		int i=0;
		while (i++ < n) {
			Page page = acquire();
			if (page != null) {
				pages.add(page);
			} else {
				break;
			}
		}
		return pages;
	}

	@Override
	public void doStart() {
		
	}

	@Override
	public void doStop() {
		
	}

	@Override
	public void doClose() {
		
	}
}
