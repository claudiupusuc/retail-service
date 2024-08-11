package app.order;

import app.order.dao.CustomerDetails;
import app.order.dao.Order;

import java.time.LocalDate;
import java.util.List;
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

  /**
   * List orders between a start and end date.
   * @param start Start date.
   * @param end End date.
   * @return A list of orders that were made between start and end dates.
   */
  List<Order> listOrders(LocalDate start, LocalDate end);
}
