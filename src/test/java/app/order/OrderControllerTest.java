package app.order;

import app.UnitTest;
import app.order.dao.CustomerDetails;
import app.order.dao.Order;
import app.order.rest.OrderController;
import app.order.rest.PlaceOrderRequest;
import app.order.rest.PlaceOrderResponse;
import app.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class OrderControllerTest extends UnitTest {

  OrderOperations orderOperationsMock = mock(OrderOperations.class);
  Validator validatorMock = mock(Validator.class);
  OrderController orderController = new OrderController(orderOperationsMock, validatorMock);

  @Test
  @DisplayName("Should place order")
  public void placeOrder() {
    // given
    String cartId = "cart-id";
    String name = "Luigi";
    String number = "01234567";
    String email = "luigi@supermario.com";
    PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest(cartId, name, number, email);

    when(orderOperationsMock.placeOrder(eq(cartId), any(CustomerDetails.class))).thenReturn(new Order("1", "order-number-123", cartId, name, number, email, LocalDate.now()));

    // when
    ResponseEntity<PlaceOrderResponse> response = orderController.placeOrder(placeOrderRequest);

    // then
    verify(validatorMock).validateNotNull(eq(placeOrderRequest), any(RuntimeException.class));
    verify(validatorMock).validateNotBlank(eq(cartId), any(RuntimeException.class));
    verify(validatorMock).validateNotBlank(eq(name), any(RuntimeException.class));
    verify(validatorMock).validateNotBlank(eq(number), any(RuntimeException.class));
    verify(validatorMock).validateEmail(eq(email), any(RuntimeException.class));

    ArgumentCaptor<CustomerDetails> customerDetailsArgumentCaptor = ArgumentCaptor.forClass(CustomerDetails.class);
    verify(orderOperationsMock).placeOrder(eq(cartId), customerDetailsArgumentCaptor.capture());

    CustomerDetails customerDetailsParam = customerDetailsArgumentCaptor.getValue();
    assertThat(customerDetailsParam.getName()).isEqualTo(name);
    assertThat(customerDetailsParam.getPhoneNumber()).isEqualTo(number);
    assertThat(customerDetailsParam.getEmail()).isEqualTo(email);

    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getOrderNumber()).isEqualTo("order-number-123");
  }

  @Test
  @DisplayName("Should search for order by order number")
  public void searchOrder() {
    // given
    String orderNumber = "order-number";
    when(orderOperationsMock.searchOrder(orderNumber)).thenReturn(Optional.of(new Order("1", "123", "4324", "Ion", "012345", "ion@mail.com", LocalDate.now())));

    // when
    orderController.searchOrder(orderNumber);

    // then
    verify(orderOperationsMock).searchOrder(orderNumber);
  }
}
