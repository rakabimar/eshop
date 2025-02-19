package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product("1", "Sample Product", 10);
    }

    @Test
    void testCreateProduct_Valid() {
        when(productRepository.create(sampleProduct)).thenReturn(sampleProduct);
        Product created = productService.create(sampleProduct);
        assertNotNull(created);
        assertEquals("Sample Product", created.getProductName());
        verify(productRepository, times(1)).create(sampleProduct);
    }

    @Test
    void testCreateProduct_InvalidBlankName() {
        sampleProduct.setProductName(" ");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> productService.create(sampleProduct));
        assertEquals("Invalid input", exception.getMessage());
        verify(productRepository, never()).create(any());
    }

    @Test
    void testCreateProduct_InvalidQuantity() {
        sampleProduct.setProductQuantity(0);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> productService.create(sampleProduct));
        assertEquals("Invalid input", exception.getMessage());
        verify(productRepository, never()).create(any());
    }

    @Test
    void testFindAll() {
        Product product1 = new Product("1", "Product 1", 10);
        Product product2 = new Product("2", "Product 2", 20);
        List<Product> productList = Arrays.asList(product1, product2);
        when(productRepository.findAll()).thenReturn(productList.iterator());
        List<Product> result = productService.findAll();
        assertEquals(2, result.size());
        assertTrue(result.contains(product1));
        assertTrue(result.contains(product2));
    }

    @Test
    void testFindById() {
        when(productRepository.findById("1")).thenReturn(sampleProduct);
        Product found = productService.findById("1");
        assertNotNull(found);
        assertEquals("Sample Product", found.getProductName());
        verify(productRepository, times(1)).findById("1");
    }

    @Test
    void testEditProduct_Success() {
        when(productRepository.update(sampleProduct)).thenReturn(sampleProduct);
        Product updatedProduct = productService.update(sampleProduct);
        assertNotNull(updatedProduct);
        assertEquals("Sample Product", updatedProduct.getProductName());
        assertEquals(10, updatedProduct.getProductQuantity());
        verify(productRepository, times(1)).update(sampleProduct);
    }

    @Test
    void testEditProduct_Failure_ProductNotFound() {
        when(productRepository.update(sampleProduct)).thenReturn(null);
        Product updatedProduct = productService.update(sampleProduct);
        assertNull(updatedProduct);
        verify(productRepository, times(1)).update(sampleProduct);
    }

    @Test
    void testDeleteProduct_Success() {
        doNothing().when(productRepository).delete("1");
        assertDoesNotThrow(() -> productService.delete("1"));
        verify(productRepository, times(1)).delete("1");
    }

    @Test
    void testDeleteProduct_Failure_ProductNotFound() {
        doNothing().when(productRepository).delete("999");
        assertDoesNotThrow(() -> productService.delete("999"));
        verify(productRepository, times(1)).delete("999");
    }
}
