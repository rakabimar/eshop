package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final String PRODUCT_ATTRIBUTE = "product"; // Define a constant

    @Autowired
    private ProductService service;

    @GetMapping("/create")
    public String createProductPage(Model model) {
        model.addAttribute(PRODUCT_ATTRIBUTE, new Product()); // Use constant
        return "createProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@Valid @ModelAttribute Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute(PRODUCT_ATTRIBUTE, product); // Use constant
            return "createProduct";
        }
        service.create(product);
        return "redirect:/product/list";
    }

    @GetMapping("/list")
    public String productListPage(Model model) {
        model.addAttribute("products", service.findAll());
        return "productList";
    }

    @GetMapping("/edit/{id}")
    public String editProductPage(@PathVariable("id") String productId, Model model) {
        Product existingProduct = service.findById(productId);
        if (existingProduct == null) {
            return "redirect:/product/list";
        }
        model.addAttribute(PRODUCT_ATTRIBUTE, existingProduct); // Use constant
        return "editProduct";
    }

    @PostMapping("/edit")
    public String editProductPost(@ModelAttribute Product product) {
        service.update(product);
        return "redirect:/product/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") String productId) {
        service.delete(productId);
        return "redirect:/product/list";
    }
}
