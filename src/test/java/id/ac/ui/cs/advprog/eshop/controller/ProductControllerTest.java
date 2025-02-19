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
        // Inisialisasi mock
        MockitoAnnotations.openMocks(this);
        model = new ExtendedModelMap();
    }

    @Test
    void testCreateProductPage() {
        // Memastikan GET /product/create mengembalikan view "createProduct" dan menginisiasi atribut "product"
        String view = productController.createProductPage(model);
        assertEquals("createProduct", view);
        assertTrue(model.containsAttribute("product"));
        Object productObj = model.getAttribute("product");
        assertNotNull(productObj);
        assertTrue(productObj instanceof Product);
    }

    @Test
    void testCreateProductPost_WithErrors() {
        // Simulasikan kondisi validasi error
        Product product = new Product();
        BindingResult bindingResult = new BeanPropertyBindingResult(product, "product");
        // Menambahkan error agar result.hasErrors() bernilai true
        bindingResult.reject("error", "error message");

        String view = productController.createProductPost(product, bindingResult, model);
        // Jika terdapat error, harus kembali ke form createProduct dengan product yang sama di model
        assertEquals("createProduct", view);
        assertTrue(model.containsAttribute("product"));
        assertSame(product, model.getAttribute("product"));
        verify(productService, never()).create(any(Product.class));
    }

    @Test
    void testCreateProductPost_WithoutErrors() {
        // Simulasikan kondisi validasi sukses (tanpa error)
        Product product = new Product();
        BindingResult bindingResult = new BeanPropertyBindingResult(product, "product");

        String view = productController.createProductPost(product, bindingResult, model);
        // Harus melakukan create product dan mengarahkan ke list
        assertEquals("redirect:/product/list", view);
        verify(productService, times(1)).create(product);
    }

    @Test
    void testProductListPage() {
        // Simulasikan service.findAll() mengembalikan list produk
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
        // Simulasikan ketika produk tidak ditemukan (service.findById mengembalikan null)
        String productId = "non-existent";
        when(productService.findById(productId)).thenReturn(null);

        String view = productController.editProductPage(productId, model);
        // Jika produk tidak ditemukan, harus redirect ke list
        assertEquals("redirect:/product/list", view);
        assertFalse(model.containsAttribute("product"));
    }

    @Test
    void testEditProductPage_ProductFound() {
        // Simulasikan ketika produk ditemukan
        String productId = "existent";
        Product product = new Product();
        when(productService.findById(productId)).thenReturn(product);

        String view = productController.editProductPage(productId, model);
        // Harus menampilkan halaman edit dengan atribut "product" terisi
        assertEquals("editProduct", view);
        assertTrue(model.containsAttribute("product"));
        assertEquals(product, model.getAttribute("product"));
    }

    @Test
    void testEditProductPost() {
        // Memastikan POST /product/edit memanggil service.update dan redirect ke list
        Product product = new Product();
        String view = productController.editProductPost(product);
        assertEquals("redirect:/product/list", view);
        verify(productService, times(1)).update(product);
    }

    @Test
    void testDeleteProduct() {
        // Memastikan GET /product/delete/{id} memanggil service.delete dan redirect ke list
        String productId = "delete-id";
        String view = productController.deleteProduct(productId);
        assertEquals("redirect:/product/list", view);
        verify(productService, times(1)).delete(productId);
    }
}
