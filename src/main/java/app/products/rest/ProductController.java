package app.products.rest;

import app.exception.ex.BadRequestException;
import app.products.ProductOperations;
import app.products.dao.Product;
import app.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductOperations productOperations;
  private final Validator validator;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  public ProductController(ProductOperations productOperations, Validator validator) {
    this.productOperations = productOperations;
    this.validator = validator;
  }

  @GetMapping
  public ResponseEntity<SearchProductsResponse> list(@RequestParam(value = "productName", required = false) final String productName) {
    if (Optional.ofNullable(productName).isPresent())
      return ResponseEntity.ok(new SearchProductsResponse(searchProducts(productName)));

    return ResponseEntity.ok(new SearchProductsResponse(listProducts()));
  }

  private List<Product> listProducts() {
    /*
    Here I would return the list of products paginated with 'offset' and 'limit'.
    Since I don't need to do this for this assessment, I'm just returning an empty list.
    But I am leaving this method here to show how I would do this if needed as it does relate to the search for product.
     */
    return Collections.emptyList();
  }

  private List<Product> searchProducts(String productName) {
    logger.debug("Received request to search for products with product name: {}", productName);

    final String sanitizedProductName = productName.trim();
    validator.validateNotBlank(sanitizedProductName, new BadRequestException("Invalid product name"));

    return productOperations.searchProducts(sanitizedProductName);
  }
}
