package org.anaconda.common.stats;

public interface StatsSupplier<T extends Stats> {
	
	T getStats();

}
