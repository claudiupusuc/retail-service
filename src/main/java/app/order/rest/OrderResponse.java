package app.order.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class OrderResponse {

  private final String orderId;
  private final String cartId;
  private final String customerName;
  private final String mobileNumber;
  private final String email;
  private final LocalDate date;

  @JsonCreator
  public OrderResponse(@JsonProperty("orderId") String orderId,
                       @JsonProperty("cartId") String cartId,
                       @JsonProperty("customerName") String customerName,
                       @JsonProperty("mobileNumber") String mobileNumber,
                       @JsonProperty("email") String email,
                       @JsonProperty("date") LocalDate date) {
    this.orderId = orderId;
    this.cartId = cartId;
    this.customerName = customerName;
    this.mobileNumber = mobileNumber;
    this.email = email;
    this.date = date;
  }

  public String getOrderId() {
    return orderId;
  }

  public String getCartId() {
    return cartId;
  }

  public String getCustomerName() {
    return customerName;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public String getEmail() {
    return email;
  }

  public LocalDate getDate() {
    return date;
  }

  @Override
  public String toString() {
    return "Order{" +
        ", orderId='" + orderId + '\'' +
        ", cartId='" + cartId + '\'' +
        ", customerName=obfuscated" +
        ", mobileNumber=obfuscated" +
        ", email=obfuscated" +
        ", date='" + date.toString() + '\'' +
        '}';
  }
}
