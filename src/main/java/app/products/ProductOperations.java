package app.products;

import app.products.dao.Product;

import java.util.List;

public interface ProductOperations {

  /**
   * Searching for products by product name.
   * @param productName The name or part of the name of the product.
   * @return A list of products that match the given name.
   */
  List<Product> searchProducts(String productName);
}
