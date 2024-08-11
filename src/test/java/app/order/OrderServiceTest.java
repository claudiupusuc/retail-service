package app.order;

import app.UnitTest;
import app.cart.CartOperations;
import app.cart.dao.Cart;
import app.exception.ex.NotFoundException;
import app.notifications.mail.MailOperations;
import app.order.api.OrderRepository;
import app.order.api.OrderService;
import app.order.dao.CustomerDetails;
import app.order.dao.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class OrderServiceTest extends UnitTest {

  OrderRepository orderRepositoryMock = mock(OrderRepository.class);
  CartOperations cartOperationsMock = mock(CartOperations.class);
  MailOperations mailOperationsMock = mock(MailOperations.class);
  OrderService orderService = new OrderService(orderRepositoryMock, cartOperationsMock, mailOperationsMock);

  @Test
  @DisplayName("Should place order")
  public void placeOrder() {
    // given
    String cartId = "cart-id";
    String name = "Luigi";
    String number = "01234567";
    String email = "luigi@supermario.com";
    CustomerDetails customerDetails = new CustomerDetails(name, number, email);

    when(cartOperationsMock.getCart(cartId)).thenReturn(Optional.of(new Cart(cartId, Map.of("product1", 1))));

    // when
    orderService.placeOrder(cartId, customerDetails);

    // then
    ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
    verify(orderRepositoryMock).save(orderArgumentCaptor.capture());

    Order order = orderArgumentCaptor.getValue();
    assertThat(order).isNotNull();
    assertThat(order.getOrderId()).isNotBlank();
    assertThat(order.getCartId()).isEqualTo(cartId);
    assertThat(order.getCustomerName()).isEqualTo(name);
    assertThat(order.getMobileNumber()).isEqualTo(number);
    assertThat(order.getEmail()).isEqualTo(email);

    verify(cartOperationsMock).getCart(cartId);
    verify(mailOperationsMock).sendOrderPlacedEmail(email, order.getOrderId());
  }

  @Test
  @DisplayName("Should not place order and throw exception when placing an order with an invalid cart")
  public void placeOrderNoCart() {
    // given
    String cartId = "invalid-cart-id";
    String name = "Luigi";
    String number = "01234567";
    String email = "luigi@supermario.com";
    CustomerDetails customerDetails = new CustomerDetails(name, number, email);

    when(cartOperationsMock.getCart(cartId)).thenReturn(Optional.empty());

    // when - then
    assertThrows(NotFoundException.class, () -> orderService.placeOrder(cartId, customerDetails));
    verifyNoInteractions(orderRepositoryMock);
    verifyNoInteractions(mailOperationsMock);
  }

  @Test
  @DisplayName("Should get order by order number")
  public void getOrder() {
    // given
    String orderNumber = "order-number";

    // when
    orderService.searchOrder(orderNumber);

    // then
    verify(orderRepositoryMock).findByOrderId(orderNumber);
  }

  @Test
  @DisplayName("Should list orders between dates")
  public void listOrders() {
    // given
    LocalDate yesterday = LocalDate.now().minusDays(1);
    LocalDate today = LocalDate.now();

    // when
    orderService.listOrders(yesterday, today);

    // then
    verify(orderRepositoryMock).listOrders(yesterday, today);
  }
}
