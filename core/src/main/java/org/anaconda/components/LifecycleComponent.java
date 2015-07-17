package org.anaconda.components;

public interface LifecycleComponent {
	void start();
	void stop();
	void close();
	Lifecycle.State state();
}
 