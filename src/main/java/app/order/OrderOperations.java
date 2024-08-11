package app.order;

import app.order.dao.CustomerDetails;
import app.order.dao.Order;

import java.util.Optional;

public interface OrderOperations {

  /**
   * Place an order.
   * @param cartId The cart id.
   * @param customerDetails Customer details (name, phone, email).
   * @return The customer order number.
   */
  Order placeOrder(String cartId, CustomerDetails customerDetails);

  /**
   * Search for an order.
   * @param orderNumber The order number.
   * @return An order if found or else empty.
   */
  Optional<Order> searchOrder(String orderNumber);
}
