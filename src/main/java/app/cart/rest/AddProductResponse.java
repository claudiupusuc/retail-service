package app.cart.rest;

import app.cart.dao.Cart;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddProductResponse {

  private final Cart updatedCart;

  @JsonCreator
  public AddProductResponse(@JsonProperty("updatedCart") Cart updatedCart) {
    this.updatedCart = updatedCart;
  }

  public Cart getUpdatedCart() {
    return updatedCart;
  }

  @Override
  public String toString() {
    return "AddProductToCartResponse{" +
      "updatedCart=" + updatedCart +
      '}';
  }
}
