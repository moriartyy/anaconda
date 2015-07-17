package org.anaconda.transport;

public interface TransportResponseHandler<Response extends TransportResponse> {
	
	Response newInstance();

    void handleResponse(TransportResponse response);

    void handleException(TransportException exp);
}
