package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    Product product;

    @BeforeEach
    void setUp(){
        this.product = new Product();
        this.product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.product.setProductName("Sampo Cap Bambang");
        this.product.setProductQuantity(100);
    }

    @Test
    void testGetProductId() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.product.getProductId());
    }

    @Test
    void testGetProductName() {
        assertEquals("Sampo Cap Bambang", this.product.getProductName());
    }

    @Test
    void testGetProductQuantity() {
        assertEquals(100, this.product.getProductQuantity());
    }

    @Test
    void testConstructorWithProvidedProductId() {
        String customId = "custom-product-id";
        Product product2 = new Product(customId, "Product With Custom ID", 10);
        assertEquals(customId, product2.getProductId());
        assertEquals("Product With Custom ID", product2.getProductName());
        assertEquals(10, product2.getProductQuantity());
    }

    @Test
    void testConstructorWithNullProductId() {
        Product product2 = new Product(null, "Product With Null ID", 5);
        assertNotNull(product2.getProductId());
        assertFalse(product2.getProductId().isEmpty());
        assertEquals("Product With Null ID", product2.getProductName());
        assertEquals(5, product2.getProductQuantity());
    }
}
