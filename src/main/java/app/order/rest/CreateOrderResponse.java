package app.order.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateOrderResponse {

  private final String orderNumber;

  @JsonCreator
  public CreateOrderResponse(@JsonProperty("orderNumber") String orderNumber) {
    this.orderNumber = orderNumber;
  }

  public String getOrderNumber() {
    return orderNumber;
  }

  @Override
  public String toString() {
    return "CreateOrderResponse{" +
        "orderNumber='" + orderNumber + '\'' +
        '}';
  }
}
