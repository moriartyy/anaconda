package org.anaconda.store;

public interface Storage<ID, E extends Entity<ID>> {
    
    E get(ID id);
    void save(E entity);
    void remove(ID id);
    void close();
}
