package app.order.api;

import app.cart.CartOperations;
import app.cart.dao.Cart;
import app.exception.ex.NotFoundException;
import app.notifications.mail.MailOperations;
import app.order.OrderOperations;
import app.order.dao.CustomerDetails;
import app.order.dao.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService implements OrderOperations {

  private final OrderRepository orderRepository;
  private final CartOperations cartOperations;
  private final MailOperations mailOperations;

  @Autowired
  public OrderService(OrderRepository orderRepository, CartOperations cartOperations, MailOperations mailOperations) {
    this.orderRepository = orderRepository;
    this.cartOperations = cartOperations;
    this.mailOperations = mailOperations;
  }

  @Override
  public Order placeOrder(String cartId, CustomerDetails customerDetails) {
    final Cart cart = cartOperations.getCart(cartId).orElseThrow(() -> new NotFoundException("Cart not found"));

    final String newOrderId = UUID.randomUUID().toString().replace("-", "");
    final Order newOrder = new Order(null, newOrderId, cart.getId(), customerDetails.getName(), customerDetails.getPhoneNumber(), customerDetails.getEmail(), LocalDate.now());

    sendOrderConfirmationEmail(newOrder);

    return orderRepository.save(newOrder);
  }

  @Override
  public Optional<Order> searchOrder(String orderNumber) {
    return orderRepository.findByOrderId(orderNumber);
  }

  @Override
  public List<Order> listOrders(LocalDate start, LocalDate end) {
    return orderRepository.findByDateBetween(start, end);
  }

  /*
        Ideally, with more time to code and test, this would be a message pushed to a queue for example.
        That way we get a few benefits:
        1. Sending the email is less coupled to the order flow
        2. If something goes wrong with the email sending, we could have retry mechanism with re-queue.
        3. In large volumes or orders posted at once, the email sending could be something we can do with a little latency. And so we can define how we consume from the queue.

        But for this assignment, I will make the call to send an email here instead of using a queue.
         */
  private void sendOrderConfirmationEmail(Order order) {
    new Thread(() -> mailOperations.sendOrderPlacedEmail(order.getEmail(), order.getOrderId())).start();
  }
}
