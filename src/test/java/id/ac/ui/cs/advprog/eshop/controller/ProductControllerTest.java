package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        model = new ExtendedModelMap();
    }

    @Test
    void testCreateProductPage() {
        String view = productController.createProductPage(model);
        assertEquals("createProduct", view);
        assertTrue(model.containsAttribute("product"));
        Object productObj = model.getAttribute("product");
        assertNotNull(productObj);
        assertTrue(productObj instanceof Product);
    }

    @Test
    void testCreateProductPost_WithErrors() {
        Product product = new Product();
        BindingResult bindingResult = new BeanPropertyBindingResult(product, "product");
        bindingResult.reject("error", "error message");

        String view = productController.createProductPost(product, bindingResult, model);
        assertEquals("createProduct", view);
        assertTrue(model.containsAttribute("product"));
        assertSame(product, model.getAttribute("product"));
        verify(productService, never()).create(any(Product.class));
    }

    @Test
    void testCreateProductPost_WithoutErrors() {
        Product product = new Product();
        BindingResult bindingResult = new BeanPropertyBindingResult(product, "product");

        String view = productController.createProductPost(product, bindingResult, model);
        assertEquals("redirect:/product/list", view);
        verify(productService, times(1)).create(product);
    }

    @Test
    void testProductListPage() {
        List<Product> productList = new ArrayList<>();
        Product p1 = new Product();
        productList.add(p1);
        when(productService.findAll()).thenReturn(productList);
        String view = productController.productListPage(model);
        assertEquals("productList", view);
        assertTrue(model.containsAttribute("products"));
        assertEquals(productList, model.getAttribute("products"));
    }

    @Test
    void testEditProductPage_ProductNotFound() {
        String productId = "non-existent";
        when(productService.findById(productId)).thenReturn(Optional.empty());
        String view = productController.editProductPage(productId, model);
        assertEquals("redirect:/product/list", view);
        assertFalse(model.containsAttribute("product"));
    }

    @Test
    void testEditProductPage_ProductFound() {
        String productId = "existent";
        Product product = new Product();
        when(productService.findById(productId)).thenReturn(Optional.of(product));
        String view = productController.editProductPage(productId, model);
        assertEquals("editProduct", view);
        assertTrue(model.containsAttribute("product"));
        assertEquals(product, model.getAttribute("product"));
    }

    @Test
    void testEditProductPost() {
        Product product = new Product();
        product.setProductId("some-id");
        String view = productController.editProductPost(product);
        assertEquals("redirect:/product/list", view);
        // Verify updateById is called with the correct parameters.
        verify(productService, times(1)).updateById("some-id", product);
    }

    @Test
    void testDeleteProduct() {
        String productId = "delete-id";
        String view = productController.deleteProduct(productId);
        assertEquals("redirect:/product/list", view);
        verify(productService, times(1)).deleteById(productId);
    }
}
