package app.cart.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document
public class Cart {

  @Id
  private final String id;
  private final Map<String, Integer> productQuantity;

  public Cart(String id, Map<String, Integer> productQuantity) {
    this.id = id;
    this.productQuantity = productQuantity;
  }

  public String getId() {
    return id;
  }

  public Map<String, Integer> getProductQuantity() {
    return productQuantity;
  }

  public void addProduct(String productId) {
    if (productQuantity.containsKey(productId))
      productQuantity.put(productId, productQuantity.get(productId) + 1);
    else
      productQuantity.put(productId, 1);
  }

  @Override
  public String toString() {
    return "Cart{" +
      "id='" + id + '\'' +
      ", productQuantity=" + productQuantity +
      '}';
  }
}
