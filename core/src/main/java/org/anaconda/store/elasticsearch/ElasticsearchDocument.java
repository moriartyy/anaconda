package org.anaconda.store.elasticsearch;

import java.util.Map;

import org.anaconda.store.Entity;

public interface ElasticsearchDocument extends Entity<String> {
    
    Map<String, Object> toMap();
}
