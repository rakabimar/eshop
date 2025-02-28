package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.Optional;

public interface ProductService extends BaseService<Product> {
    @Override
    Product create(Product product);

    @Override
    java.util.List<Product> findAll();

    @Override
    Optional<Product> findById(String id);

    @Override
    void updateById(String id, Product product);

    @Override
    void deleteById(String id);
}
