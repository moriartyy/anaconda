package org.anaconda.store.elasticsearch;

import java.util.Map;

import org.anaconda.store.Storage;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;

public abstract class ElasticsearchStorage<Document extends ElasticsearchDocument> implements Storage<String, Document> {

    private TransportClient client;
    private String index;
    private String type;
    
    public ElasticsearchStorage(ElasticsearchConfig config, String index, String type) {
        this.client = initializeElasticseachClient(config);
        this.index = index;
        this.type = type;
    }
    
    private TransportClient initializeElasticseachClient(ElasticsearchConfig config) {
        return Elasticsearch.getClient(config.nodes());
    }
    
    public void refresh() {
        this.client.admin().indices().refresh(new RefreshRequest(this.index)).actionGet();
    }
    
    @Override
    public Document get(String id) {
        GetResponse response = this.client.prepareGet(index, type, id).execute().actionGet();
        Map<String, Object> source = response.getSource();
        return toDocument(id, source);
    }

    @Override
    public void save(Document doc) {
        this.client.prepareIndex(index, type, doc.id()).setSource(doc.toMap())
                .execute().actionGet();
    }

    @Override
    public void remove(String id) {
        this.client.prepareDelete(index, type, id).execute().actionGet();
    }
    
    @Override
    public void close() {
        Elasticsearch.destroyClient(client);
    }
    
    protected abstract Document toDocument(String id, Map<String, Object> source);

}
