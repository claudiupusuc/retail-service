package app.products;

import app.products.dao.Product;

import java.util.List;
import java.util.Optional;

public interface ProductOperations {

  /**
   * Searching for products by product name.
   * @param productName The name or part of the name of the product.
   * @return A list of products that match the given name.
   */
  List<Product> searchProducts(String productName);

  /**
   * Retrieve a product by its id.
   * @param productId The id of the product
   * @return The product or Optional empty if not found.
   */
  Optional<Product> getProduct(String productId);

  /**
   * Save the product.
   * @param product Product to save.
   */
  void save(Product product);

  /**
   * List products
   * @return List of products.
   */
  List<Product> listProducts();
}
