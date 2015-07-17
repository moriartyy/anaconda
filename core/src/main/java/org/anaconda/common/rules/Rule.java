package org.anaconda.common.rules;

public interface Rule<T> {
	boolean eval(T o);
}
