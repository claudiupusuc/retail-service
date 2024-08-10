package app.cart.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AddProductRequest {

  private final List<String> productIds;

  @JsonCreator
  public AddProductRequest(@JsonProperty("productIds") List<String> productIds) {
    this.productIds = productIds;
  }

  public List<String> getProductIds() {
    return productIds;
  }

  @Override
  public String toString() {
    return "AddProductRequest{" +
      "productIds=" + productIds +
      '}';
  }
}
