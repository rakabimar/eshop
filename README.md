# Advance Programming Course
This repository contains the modules, tutorials, and exercises for the Advance Programming Course.

## Table of Contents
1. [Module 1](#module-1)


## Module 1 - Coding Standard

### Reflection 1: Clean Code and Secure Coding Practices
#### **Clean Code Principles**
1. **Meaningful Names**
  * Using meaningful names for variables, methods, and classes improves code readability.
  * Example:
    ```java
    public Optional<Product> findById(String productId) {
      return productData.stream()
          .filter(product -> product.getProductId().equals(productId))
          .findFirst();
      }
      ```
    * `findById` clearly states that the method retrieves a product by its ID.
    * `productId` is a descriptive variable name that avoids ambiguity.

2. **Functions (Methods) Should Do One Thing**
   * Each function should perform a single, well-defined task.
   * Example:
      ```java
      public String deleteProduct(@PathVariable("id") String productId) {
          service.delete(productId);
          return "redirect:/product/list";
     }
     ``` 
     * The method only handles product deletion and then redirects, keeping it focused.

3. **Use of Objects and Data Structures**
   * The code properly encapsulates product-related logic inside a `Product` class instead of passing around multiple unrelated values.
   * Example:
      ```java
      public class Product {
          private String productId;
          private String productName;
          private int productQuantity;

          public Product() {
              this.productId = UUID.randomUUID().toString();
          }
      }
     ```
     * The constructor automatically assigns a unique ID, ensuring each product has a valid identifier.
     
4. **Error Handling**
    *  Issue: If a product ID does not exist, the app redirects without an error message.
    * Improvement:
       ```java
       @GetMapping("/edit/{id}")
       public String editProductPage(@PathVariable("id") String productId, Model model) {
           Optional<Product> product = service.findById(productId);
           if (product.isPresent()) {
               model.addAttribute("product", product.get());
               return "editProduct";
           } else {
               model.addAttribute("errorMessage", "Product not found.");
               return "errorPage";
           }
       }
      ```
        * This improvement displays an error message instead of silently failing.
#### **Secure Coding Practices**
1. **Input Validation**
   * Proper input validation ensures that user-provided data is safe.
   * Issue: The current code does not validate input when creating a product.
   * Improvement: Add validation in the `Product` model using Spring’s validation annotations.
      ```java
      import jakarta.validation.constraints.Min;
      import jakarta.validation.constraints.NotBlank;

      public class Product {
        private String productId;

        @NotBlank(message = "Product name cannot be empty")
        private String productName;

        @Min(value = 1, message = "Quantity must be at least 1")
        private int productQuantity;
     } 
     ```
        * `@NotBlank` prevents empty product names.
        * `@Min(1)` ensures the quantity is at least 1.
2. **Output Data Encoding**
   * Properly encoding output data prevents cross-site scripting (XSS) attacks.
   * Issue: The current code does not encode product names when displaying them.
   * Improvement: Use Thymeleaf’s `th:text` attribute to encode product names.
      ```html
      <tr th:each="product : ${products}">
        <td th:text="${product.productId}"></td>
        <td th:text="${#strings.escapeXml(product.productName)}"></td>
        <td th:text="${product.productQuantity}"></td>
      </tr>
     ```
        * `th:text="${#strings.escapeXml(product.productName)"` encodes the product name.