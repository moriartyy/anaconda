package org.anaconda.crawler.process;

import java.net.URL;

import org.anaconda.common.rules.Rule;

public class UrlRule implements Rule<URL> {
	
	private String expression;

	public UrlRule(String expression) {
		this.expression = expression;
	}

	@Override
	public boolean eval(URL o) {
		throw new UnsupportedOperationException();
	}

}
