package org.anaconda.crawler.scheduler;

import java.util.List;
import org.anaconda.components.AbstractLifecycleComponent;
import org.anaconda.crawler.page.Page;

public abstract class Scheduler extends AbstractLifecycleComponent {
	
	public abstract Page acquire();
	public abstract List<Page> acquire(int n);
	public abstract void enlist(Page page);
}
