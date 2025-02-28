package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;
import java.util.Optional;

public interface BaseService<T> {
    T create(T entity);
    List<T> findAll();
    Optional<T> findById(String id);
    void updateById(String id, T entity);
    void deleteById(String id);
}
