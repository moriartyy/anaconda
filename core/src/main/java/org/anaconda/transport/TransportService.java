package org.anaconda.transport;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.anaconda.common.env.Environment;
import org.anaconda.common.settings.Settings;
import org.anaconda.components.AbstractLifecycleComponent;

import com.google.inject.Inject;

public class TransportService extends AbstractLifecycleComponent {
	
	private Transport transport;
	private volatile Map<String, TransportRequestHandler<?>> requestHandlers = new ConcurrentHashMap<>();

	@Inject
	public TransportService(Settings settings, Environment environment, Transport transport) {
		this.transport = transport;
	}
	
	public synchronized void registerRequestHandler(String action, TransportRequestHandler<?> requestHandler) {
		Map<String, TransportRequestHandler<?>> map = new ConcurrentHashMap<>(requestHandlers);
		map.put(action, requestHandler);
		requestHandlers = map;
	}

	@Override
	protected void doStart() {
		this.transport.start();
		
	}

	@Override
	protected void doStop() {
		this.transport.stop();
	}

	@Override
	protected void doClose() {
		this.transport.close();
	}
	
	public <Response extends TransportResponse> void sendRequest(TransportRequest request, TransportResponseHandler<Response> handler) {
		transport.sendRequest(request, handler);
	}

}
