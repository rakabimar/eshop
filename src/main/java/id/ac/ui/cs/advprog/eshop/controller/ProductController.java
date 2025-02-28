package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final String PRODUCT_ATTRIBUTE = "product"; // Constant for product attribute key
    private static final String REDIRECT_PRODUCT_LIST = "redirect:/product/list"; // Constant for redirection

    @Autowired
    private ProductService productService;

    @GetMapping("/create")
    public String createProductPage(Model model) {
        model.addAttribute(PRODUCT_ATTRIBUTE, new Product());
        return "createProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@Valid @ModelAttribute Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute(PRODUCT_ATTRIBUTE, product);
            return "createProduct";
        }
        productService.create(product);
        return REDIRECT_PRODUCT_LIST;
    }

    @GetMapping("/list")
    public String productListPage(Model model) {
        model.addAttribute("products", productService.findAll());
        return "productList";
    }

    @GetMapping("/edit/{id}")
    public String editProductPage(@PathVariable("id") String productId, Model model) {
        Optional<Product> existingProductOpt = productService.findById(productId);
        if (existingProductOpt.isEmpty()) {
            return REDIRECT_PRODUCT_LIST;
        }
        model.addAttribute(PRODUCT_ATTRIBUTE, existingProductOpt.get());
        return "editProduct";
    }

    @PostMapping("/edit")
    public String editProductPost(@ModelAttribute Product product) {
        productService.updateById(product.getProductId(), product);
        return REDIRECT_PRODUCT_LIST;
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") String productId) {
        productService.deleteById(productId);
        return REDIRECT_PRODUCT_LIST;
    }
}
