package org.anaconda.crawler.process;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.anaconda.components.AbstractLifecycleComponent;
import org.anaconda.crawler.page.Page;
import org.anaconda.crawler.page.Page.ContentType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public abstract class AbstractProcessor extends AbstractLifecycleComponent implements Processor {
	
	private List<Listener> listeners = new ArrayList<Processor.Listener>();

	protected void onSuccess(Page page, long elapsedTime) {
		listeners.forEach((l) -> {l.onSuccess(page, this, elapsedTime);});
	}
	
	protected void onFailed(Page page, Exception e, long elapsedTime) {
		listeners.forEach((l) -> {l.onFailed(page, this, e, elapsedTime);});
	}
	
	@Override
	public void registerListener(Listener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void process(Page page) {
		long start = System.nanoTime();
		try {
			processInternal(page);
			onSuccess(page, System.nanoTime() - start);
		} catch (Exception e) {
			page.addError(e);
			onFailed(page, e, System.nanoTime() - start);
		}
	}

	protected void processInternal(Page page) {
		if (page.contentType() == ContentType.Html) {
			Document doc = Jsoup.parse(page.html());
			parseLinks(doc, page);
			parseProperties(doc, page);
			return;
		}
	}

	protected abstract void parseProperties(Document doc, Page page);

	protected void parseLinks(Document doc, Page page) {
		Elements links = doc.select("a");
		links.forEach(e -> {
			try {
				URL link = new URL(e.attr("abs:href"));
				Page linkPage = new Page(link);
				if (isValid(linkPage)) {
					page.addLinkPage(linkPage);
				}
			} catch (Exception e1) {
			}
		});
	}

	protected abstract boolean isValid(Page page);
}
