package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository implements BaseRepository<Product> {

    private final List<Product> productData = new ArrayList<>();

    @Override
    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    @Override
    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    @Override
    public Optional<Product> findById(String id) {
        return productData.stream()
                .filter(product -> product.getProductId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Product> update(Product updatedProduct) {
        for (int i = 0; i < productData.size(); i++) {
            if (productData.get(i).getProductId().equals(updatedProduct.getProductId())) {
                productData.set(i, updatedProduct);
                return Optional.of(updatedProduct);
            }
        }
        return Optional.empty();
    }

    @Override
    public void delete(String id) {
        productData.removeIf(product -> product.getProductId().equals(id));
    }
}
