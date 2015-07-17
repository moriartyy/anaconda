package org.anaconda.crawler.appstore;

import org.anaconda.crawler.page.Page;
import org.anaconda.crawler.process.AbstractProcessor;
import org.jsoup.nodes.Document;

public class AppStoreProcessor extends AbstractProcessor {

	@Override
	public boolean canProcess(Page page) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void parseProperties(Document doc, Page page) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isValid(Page page) {
		return true;
	}

	@Override
	protected void doStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doStop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doClose() {
		// TODO Auto-generated method stub
		
	}

}
