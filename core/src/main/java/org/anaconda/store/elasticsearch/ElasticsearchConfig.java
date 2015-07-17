package org.anaconda.store.elasticsearch;

import java.net.InetSocketAddress;
import java.util.Set;

import org.anaconda.common.settings.Settings;
import org.anaconda.store.Config;

import com.google.common.collect.ImmutableSet;

public class ElasticsearchConfig implements Config {
    
    private String clusterName;
    private Set<InetSocketAddress> nodes;
    
    public ElasticsearchConfig(Settings settings) {
        this.clusterName = settings.get("cluster_name");
        this.nodes = ImmutableSet.copyOf(settings.getServerNodes("hosts"));
    }
    
    public ElasticsearchConfig(String clusterName, Set<InetSocketAddress> nodes) {
        this.clusterName = clusterName;
        this.nodes = nodes;
    }

    public String clusterName() {
        return clusterName;
    }

    public Set<InetSocketAddress> nodes() {
        return nodes;
    }
}
