package id.ac.ui.cs.advprog.eshop.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class Product {
    private String productId;

    @NotBlank(message = "Product name cannot be empty")
    private String productName;

    @Min(value = 1, message = "Product quantity must be at least 1")
    private int productQuantity;

    public Product() {
        this.productId = UUID.randomUUID().toString();
    }

    public Product(String productId, String productName, int productQuantity) {
        this.productId = productId != null ? productId : UUID.randomUUID().toString();
        this.productName = productName;
        this.productQuantity = productQuantity;
    }
}