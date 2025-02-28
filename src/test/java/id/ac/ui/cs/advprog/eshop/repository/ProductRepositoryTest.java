package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductRepositoryTest {
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);
        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("id-1");
        product1.setProductName("Product 1");
        product1.setProductQuantity(10);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("id-2");
        product2.setProductName("Product 2");
        product2.setProductQuantity(20);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals("id-1", savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals("id-2", savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindById_ProductFound() {
        Product product = new Product("id-1", "Product 1", 10);
        productRepository.create(product);
        Optional<Product> foundOpt = productRepository.findById("id-1");
        assertTrue(foundOpt.isPresent());
        Product found = foundOpt.get();
        assertEquals("Product 1", found.getProductName());
    }

    @Test
    void testFindById_NotFound() {
        Optional<Product> found = productRepository.findById("non-existent-id");
        assertFalse(found.isPresent());
    }

    @Test
    void testFindById_MultipleProducts() {
        Product product1 = new Product("id-1", "Product 1", 10);
        Product product2 = new Product("id-2", "Product 2", 20);
        productRepository.create(product1);
        productRepository.create(product2);
        Optional<Product> foundOpt = productRepository.findById("id-2");
        assertTrue(foundOpt.isPresent());
        Product found = foundOpt.get();
        assertEquals("Product 2", found.getProductName());
    }

    @Test
    void testEditProduct() {
        Product product = new Product("id-1", "Product 1", 10);
        productRepository.create(product);
        Product updatedProduct = new Product("id-1", "Product 1 Updated", 15);
        Optional<Product> resultOpt = productRepository.update(updatedProduct);
        assertTrue(resultOpt.isPresent());
        Product result = resultOpt.get();
        assertEquals("Product 1 Updated", result.getProductName());
        assertEquals(15, result.getProductQuantity());
    }

    @Test
    void testEditProduct_NotFound() {
        Product updatedProduct = new Product("non-existing-id", "Non Existing Product", 200);
        Optional<Product> resultOpt = productRepository.update(updatedProduct);
        assertFalse(resultOpt.isPresent());
    }

    @Test
    void testUpdate_MultipleProducts() {
        Product product1 = new Product("id-1", "Product 1", 10);
        Product product2 = new Product("id-2", "Product 2", 20);
        productRepository.create(product1);
        productRepository.create(product2);

        Product updatedProduct2 = new Product("id-2", "Updated Product 2", 30);
        Optional<Product> resultOpt = productRepository.update(updatedProduct2);
        assertTrue(resultOpt.isPresent());
        Product result = resultOpt.get();
        assertEquals("Updated Product 2", result.getProductName());
        assertEquals(30, result.getProductQuantity());

        Optional<Product> found1Opt = productRepository.findById("id-1");
        assertTrue(found1Opt.isPresent());
        Product found1 = found1Opt.get();
        assertEquals("Product 1", found1.getProductName());
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product("id-1", "Product 1", 10);
        productRepository.create(product);
        productRepository.delete("id-1");
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteProduct_NotFound() {
        assertDoesNotThrow(() -> productRepository.delete("non-existing-id"));
    }
}
