package app.cart.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Optional;

public class AddProductRequest {

  private final String cartId;
  private final List<String> productIds;

  @JsonCreator
  public AddProductRequest(@JsonProperty("cartId") String cartId, @JsonProperty("productIds") List<String> productIds) {
    this.productIds = productIds;
    this.cartId = cartId;
  }

  public List<String> getProductIds() {
    return productIds;
  }

  public Optional<String> getCartId() {
    return Optional.ofNullable(cartId);
  }
}
