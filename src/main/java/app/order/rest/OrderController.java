package app.order.rest;

import app.exception.ex.BadRequestException;
import app.exception.ex.NotFoundException;
import app.order.OrderOperations;
import app.order.dao.CustomerDetails;
import app.order.dao.Order;
import app.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

  private final OrderOperations orderOperations;
  private final Validator validator;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  public OrderController(OrderOperations orderOperations, Validator validator) {
    this.orderOperations = orderOperations;
    this.validator = validator;
  }

  @PostMapping
  public ResponseEntity<PlaceOrderResponse> placeOrder(@RequestBody PlaceOrderRequest placeOrderRequest) {
    validatePlaceOrderRequest(placeOrderRequest);

    final CustomerDetails customerDetails = new CustomerDetails(placeOrderRequest.getName(), placeOrderRequest.getNumber(), placeOrderRequest.getEmail());
    final Order order = orderOperations.placeOrder(placeOrderRequest.getCartId(), customerDetails);
    logger.info("Order placed. Order number: {}", order.getOrderId());

    return ResponseEntity.ok(new PlaceOrderResponse(order.getOrderId()));
  }

  @GetMapping("/{orderNumber}")
  public ResponseEntity<OrderResponse> searchOrder(@PathVariable("orderNumber") String orderNumber) {
    // Here we could also return an OK response and signal inside the response that the order was not found.
    // But since a call to this endpoint should contain a valid order id, then it makes sense to return the 404 Not Found.
    final Order order = orderOperations.searchOrder(orderNumber).orElseThrow(() -> new NotFoundException("Order not found"));

    return ResponseEntity.ok(new OrderResponse(order.getOrderId(), order.getCartId(), order.getCustomerName(), order.getMobileNumber(), order.getEmail(), order.getDate()));
  }

  private void validatePlaceOrderRequest(PlaceOrderRequest placeOrderRequest) {
    validator.validateNotNull(placeOrderRequest, new BadRequestException("Invalid request"));
    validator.validateNotBlank(placeOrderRequest.getCartId(), new BadRequestException("Invalid cart"));
    validator.validateNotBlank(placeOrderRequest.getName(), new BadRequestException("Invalid name"));
    validator.validateNotBlank(placeOrderRequest.getNumber(), new BadRequestException("Invalid number"));
    validator.validateEmail(placeOrderRequest.getEmail(), new BadRequestException("Invalid email"));
  }
}
