package org.anaconda.components;

import org.anaconda.common.logging.Logger;
import org.anaconda.common.logging.Loggers;

public abstract class AbstractLifecycleComponent implements LifecycleComponent {
	
	protected Logger logger;
	protected Lifecycle lifecycle = new Lifecycle();

	protected AbstractLifecycleComponent() {
		this.logger = Loggers.getLogger(this.getClass());
	}
	
	@Override
	public Lifecycle.State state() {
		return lifecycle.state();
	}

	@Override
	public void start() {
		if (logger.isInfoEnabled()) {
			logger.info("Starting " + this.getClass().getSimpleName());
		}
		if (!lifecycle.moveStarted()) {
			throw new IllegalStateException("Cann't move to started state.");
		}
		doStart();
		if (logger.isInfoEnabled()) {
			logger.info(this.getClass().getSimpleName() + " started.");
		}
	}

	@Override
	public void stop() {
		if (logger.isInfoEnabled()) {
			logger.info("Stopping " + this.getClass().getSimpleName());
		}
		if (!lifecycle.moveStopped()) {
			throw new IllegalStateException("Cann't move to stopped state.");
		}
		doStop();
		if (logger.isInfoEnabled()) {
			logger.info(this.getClass().getSimpleName() + " stopped.");
		}
	}

	@Override
	public void close() {
		if (logger.isInfoEnabled()) {
			logger.info("Closing " + this.getClass().getSimpleName());
		}
		if (!lifecycle.moveClosed()) {
			throw new IllegalStateException("Cann't move to closed state.");
		}
		doClose();
		if (logger.isInfoEnabled()) {
			logger.info(this.getClass().getSimpleName() + " closed.");
		}
	}
	
	protected abstract void doStart();
	protected abstract void doStop();
	protected abstract void doClose();

}
