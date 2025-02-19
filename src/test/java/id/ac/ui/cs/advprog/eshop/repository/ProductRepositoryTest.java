package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

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
        Product found = productRepository.findById("id-1");
        assertNotNull(found);
        assertEquals("Product 1", found.getProductName());
    }

    @Test
    void testFindById_NotFound() {
        Product found = productRepository.findById("non-existent-id");
        assertNull(found);
    }

    @Test
    void testFindById_MultipleProducts() {
        // Add multiple products so that the matching one is not the first
        Product product1 = new Product("id-1", "Product 1", 10);
        Product product2 = new Product("id-2", "Product 2", 20);
        productRepository.create(product1);
        productRepository.create(product2);
        // This will iterate product1 (non-match) then product2 (match)
        Product found = productRepository.findById("id-2");
        assertNotNull(found);
        assertEquals("Product 2", found.getProductName());
    }

    @Test
    void testEditProduct() {
        Product product = new Product("id-1", "Product 1", 10);
        productRepository.create(product);
        Product updatedProduct = new Product("id-1", "Product 1 Updated", 15);
        Product result = productRepository.update(updatedProduct);
        assertNotNull(result);
        assertEquals("Product 1 Updated", result.getProductName());
        assertEquals(15, result.getProductQuantity());
    }

    @Test
    void testEditProduct_NotFound() {
        Product updatedProduct = new Product("non-existing-id", "Non Existing Product", 200);
        Product result = productRepository.update(updatedProduct);
        assertNull(result);
    }

    @Test
    void testUpdate_MultipleProducts() {
        // Add multiple products so that the update occurs in the middle of the list
        Product product1 = new Product("id-1", "Product 1", 10);
        Product product2 = new Product("id-2", "Product 2", 20);
        productRepository.create(product1);
        productRepository.create(product2);

        // Update product2 which is not the first element in the list
        Product updatedProduct2 = new Product("id-2", "Updated Product 2", 30);
        Product result = productRepository.update(updatedProduct2);
        assertNotNull(result);
        assertEquals("Updated Product 2", result.getProductName());
        assertEquals(30, result.getProductQuantity());

        // Verify that product1 remains unchanged
        Product found1 = productRepository.findById("id-1");
        assertNotNull(found1);
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
        // Should not throw any exception when the product is not found
        assertDoesNotThrow(() -> productRepository.delete("non-existing-id"));
    }
}
