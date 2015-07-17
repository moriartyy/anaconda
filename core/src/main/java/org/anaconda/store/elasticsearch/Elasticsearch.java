package org.anaconda.store.elasticsearch;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.ImmutableSettings.Builder;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

public class Elasticsearch {
    
    private static final Map<Set<InetSocketAddress>, TransportClient> clients = Maps.newHashMap();
    private static final Map<TransportClient, Integer> clientsReference = Maps.newHashMap();
    
    public static TransportClient getClient(Set<InetSocketAddress> nodes) {
        return getClient(null, nodes);
    }

    public synchronized static TransportClient getClient(String clusterName, Set<InetSocketAddress> nodes) {
        TransportClient client = clients.get(nodes);
        if (client == null) {
            client = createClient(clusterName, nodes);
            clients.put(nodes, client);
        }
        incReferenceNumber(client);
        return client;
    }
    
    private static int incReferenceNumber(TransportClient client) {
        Integer number = clientsReference.get(client);
        if (number == null) {
            number = 1;
            clientsReference.put(client, number);
        } else {
            number += 1;
        }
        return number;
    }
    
    private static int decReferenceNumber(TransportClient client) {
        Integer number = clientsReference.get(client);
        if (number == null) {
            number = 0;
            clientsReference.put(client, number);
        } else {
            number -= 1;
            if (number < 0)
                number = 0;
        }
        return number;
    }

    private static TransportClient createClient(String clusterName, Set<InetSocketAddress> nodes) {
        Builder builder = ImmutableSettings.settingsBuilder()
                .put("client.transport.sniff", true)
                .put("client.transport.ping_timeout", TimeValue.timeValueSeconds(5))
                .put("client.transport.nodes_sampler_interval", TimeValue.timeValueSeconds(5));
        
        if (Strings.isNullOrEmpty(clusterName)) {
            builder.put("client.transport.ignore_cluster_name", true);
        } else {
            builder.put("cluster.name", clusterName);
        }
        
         org.elasticsearch.common.settings.Settings esSettings = builder.build();
        TransportClient client = new TransportClient(esSettings);
        for(InetSocketAddress node : nodes) {
            client.addTransportAddress(new InetSocketTransportAddress(node.getAddress(), node.getPort()));
        }
        return client;
    }

    public synchronized static void destroyClient(TransportClient client) {
        int refNumber = decReferenceNumber(client);
        if (refNumber == 0) {
            for (Entry<Set<InetSocketAddress>, TransportClient> entry : clients.entrySet()) {
                if (entry.getValue().equals(client)) {
                    clients.remove(entry.getKey());
                }
            }
            clientsReference.remove(client);
            client.close();
        }
    }
}
