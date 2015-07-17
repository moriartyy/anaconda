package org.anaconda.crawler.scheduler;

import org.anaconda.common.settings.Settings;

import com.google.inject.Inject;

public class DefaultScheduler extends MemFIFOScheduler {

	@Inject
	public DefaultScheduler(Settings settings) {
		super(settings);
	}
}
