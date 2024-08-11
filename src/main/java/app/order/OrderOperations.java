package app.order;

import app.order.dao.CustomerDetails;
import app.order.dao.Order;

public interface OrderOperations {

  /**
   * Place an order.
   * @param cartId The cart id.
   * @param customerDetails Customer details (name, phone, email).
   * @return The customer order number.
   */
  Order placeOrder(String cartId, CustomerDetails customerDetails);
}
