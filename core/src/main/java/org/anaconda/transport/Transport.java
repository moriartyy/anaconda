package org.anaconda.transport;

import org.anaconda.components.LifecycleComponent;
import org.anaconda.cluster.node.DiscoveryNode;
import org.anaconda.transport.TransportRequest;

public interface Transport extends LifecycleComponent {

	 void sendRequest(DiscoveryNode node, long requestId, String action, TransportRequest request);

}
