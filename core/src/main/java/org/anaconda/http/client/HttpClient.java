package org.anaconda.http.client;

import org.anaconda.common.settings.Settings;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.google.inject.Inject;

public class HttpClient {

	private CloseableHttpClient httpClient;

	@Inject
	public HttpClient(Settings settings) {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(50);
		connectionManager.setDefaultMaxPerRoute(50);
		this.httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
	}
}
