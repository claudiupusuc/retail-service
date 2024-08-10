package app.products.api;

import app.products.ProductOperations;
import app.products.dao.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements ProductOperations {

  private final ProductRepository productRepository;

  @Autowired
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public List<Product> searchProducts(String productName) {
    return productRepository.searchProducts(productName);
  }
}
