package id.ac.ui.cs.advprog.eshop.repository;

import java.util.Iterator;
import java.util.Optional;

public interface BaseRepository<T> {
    T create(T entity);
    Iterator<T> findAll();
    Optional<T> findById(String id);
    Optional<T> update(T entity);
    void delete(String id);
}
