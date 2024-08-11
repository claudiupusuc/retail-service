package app.order.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;

@Document
public class Order {

  @Id
  private final String id;
  @Indexed
  private final String orderId;
  @Indexed(unique = true)
  private final String cartId;
  private final String customerName;
  private final String mobileNumber;
  private final String email;
  private final LocalDate date;

  public Order(String id, String orderId, String cartId, String customerName, String mobileNumber, String email, LocalDate date) {
    this.id = id;
    this.orderId = orderId;
    this.cartId = cartId;
    this.customerName = customerName;
    this.mobileNumber = mobileNumber;
    this.email = email;
    this.date = date;
  }

  public String getId() {
    return id;
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
        "id='" + id + '\'' +
        ", orderId='" + orderId + '\'' +
        ", cartId='" + cartId + '\'' +
        ", customerName=obfuscated" +
        ", mobileNumber=obfuscated" +
        ", email=obfuscated" +
        ", date='" + date.toString() + '\'' +
        '}';
  }
}
