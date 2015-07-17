package org.anaconda.crawler.page;

import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Set;

public class Page {
	
	public static enum ContentType {
		Html("text/html");
		
		private String value;

		ContentType(String s) {
			this.value = s;
		}
		
		public String value() {
			return value;
		}
	}
	
	private Set<Page> links = new HashSet<Page>();
	private Properties properties = new Properties();
	private final URL url;
	private LinkedList<Exception> errors = new LinkedList<Exception>();
	private String html;
	
	public Page(URL url) {
		this.url = url;
	}
	
	public Set<Page> links() {
		return this.links;
	}
	
	public Properties properties() {
		return properties;
	}
	
	public String host() {
		return this.url.getHost();
	}
	
	public URL url() {
		return this.url;
	}
	
	public void addLinkPage(Page page) {
		links.add(page);
	}
	
	public void addProperty(String key, String value) {
		properties.put(key, value);
	}
	
	public void addError(Exception e) {
		errors.add(e);
	}
	
	public LinkedList<Exception> errors() {
		return errors;
	}

	public void html(String html) {
		this.html = html;
	}
	
	public String html() {
		return html;
	}
	
	@Override
	public String toString() {
		return this.url.toString();
	}

	/*
	 * Weather the page been fetched before.
	 */
	public boolean hasBeenFetched() {
		// TODO Auto-generated method stub
		return false;
	}

	public ContentType contentType() {
		// TODO Auto-generated method stub
		return null;
	}
}
