package org.anaconda.crawler.process;

import org.anaconda.common.rules.Rule;
import org.anaconda.crawler.page.Page;

public class ContentTypeRule implements Rule<Page.ContentType> {

	private String expression;

	public ContentTypeRule(String expression) {
		this.expression = expression;
	}

	@Override
	public boolean eval(Page.ContentType contentType) {
		throw new UnsupportedOperationException();
	}

}
