# Advance Programming Course
This repository contains the modules, tutorials, and exercises for the Advance Programming Course.

## Table of Contents
1. [Module 1](#module-1---coding-standard)

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

### Reflection 2: Unit Testing
#### **Unit Testing Principles**
1. **Feelings about Unit Testing**
   * Writing unit tests initially feels challenging but becomes more natural with practice.
   * Unit testing is essential for ensuring code quality and preventing regressions.
   * It provides confidence that code works as expected and helps identify bugs early.
2. **Quantity of Unit Testing**
   * The number of unit tests should be determined by functionality, not arbitrary metrics
   * Each test should cover a specific scenario, such as:
     * Expected behavior
     * Edge cases
     * Error conditions
     * Integration with other components
3. **Code Coverage and Test Sufficiency**
   * Code coverage is a useful metric but not the sole indicator of test quality
   * 100% code coverage does not guarantee bug-free code because:
     * Tests might not cover all possible input combinations
     * Logic errors can exist even with full path coverage
     * Integration issues may not be detected
     * Edge cases might be missed despite covering all lines
   * Quality tests should focus on:
     * Testing business logic
     * Verifying edge cases
     * Ensuring error handling
     * Validating integration points
4. **Functional Test Code Cleanliness Issues**
   * Code Duplication:
     * Setup procedures are repeated across test classes
     * Instance variables (serverPort, testBaseUrl, baseUrl) are duplicated
     * WebDriver initialization and cleanup code is repeated
   * Reasons for Concern:
     * Violates DRY (Don't Repeat Yourself) principle
     * Makes maintenance more difficult
     * Increases chance of inconsistencies
     * Reduces code readability
   * Suggested Improvements:
     1. **Create a Base Test Class**
        ```java
        public abstract class BaseFunctionalTest {
        @LocalServerPort
        protected int serverPort;

           @Value("${app.baseUrl:http://localhost}")
           protected String testBaseUrl;

           protected String baseUrl;
           protected WebDriver driver;

           @BeforeEach
           void setupTest() {
               baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
           }
        }
        ```
     2. **Implement Page Object Pattern**
        ```java
        public class ProductPage {
            private final WebDriver driver;

            public ProductPage(WebDriver driver) {
                this.driver = driver;
            }

            public void createProduct(String name, String quantity) {
                WebElement nameInput = driver.findElement(By.name("productName"));
                WebElement quantityInput = driver.findElement(By.name("productQuantity"));
                // ... rest of the implementation
            }
        }
        ```
     3. **Create Test Helper Methods**
        ```java
        protected void waitForUrl(String urlPart) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlContains(urlPart));
        }
        
        protected void waitForText(String text) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.tagName("body"), text));
        }
        ```
   * These improvements would:
     * Reduce code duplication
     * Improve maintainability
     * Make tests more readable
     * Create a more sustainable test architecture
     * Make it easier to add new test cases
     * Reduce the likelihood of inconsistencies across test implementations