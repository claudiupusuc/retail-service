package app.order.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlaceOrderRequest {

  private final String cartId;
  private final String name;
  private final String number;
  private final String email;

  @JsonCreator
  public PlaceOrderRequest(@JsonProperty("cartId") String cartId,
                           @JsonProperty("name")String name,
                           @JsonProperty("number")String number,
                           @JsonProperty("email")String email) {
    this.cartId = cartId;
    this.name = name;
    this.number = number;
    this.email = email;
  }

  public String getCartId() {
    return cartId;
  }

  public String getName() {
    return name;
  }

  public String getNumber() {
    return number;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String toString() {
    return "PostOrderRequest{" +
        "cartId='" + cartId + '\'' +
        ", name=obfuscated" +
        ", number=obfuscated" +
        ", email=obfuscated" +
        '}';
  }
}
