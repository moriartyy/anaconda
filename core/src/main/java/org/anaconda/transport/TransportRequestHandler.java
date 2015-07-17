package org.anaconda.transport;

public interface TransportRequestHandler<TR extends TransportRequest> {
	
	TR newInstance();

    void messageReceived(TransportRequest request, TransportChannel channel);

}
