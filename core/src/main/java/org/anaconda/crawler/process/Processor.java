package org.anaconda.crawler.process;

import org.anaconda.components.LifecycleComponent;
import org.anaconda.crawler.page.Page;

public interface Processor extends LifecycleComponent {
	void process(Page page);
	void registerListener(Listener listener);
	boolean canProcess(Page page);
	
	public interface Listener {
		void onSuccess(Page page, Processor processor, long elapsedTime);
		void onFailed(Page page, Processor processor, Exception e, long elapsedTime);
	}
}
