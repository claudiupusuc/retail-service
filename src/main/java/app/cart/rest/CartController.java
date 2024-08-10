package app.cart.rest;

import app.cart.CartOperations;
import app.cart.dao.Cart;
import app.exception.ex.BadRequestException;
import app.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

  private final CartOperations cartOperations;
  private final Validator validator;

  @Autowired
  public CartController(CartOperations cartOperations, Validator validator) {
    this.cartOperations = cartOperations;
    this.validator = validator;
  }

  @PostMapping
  public ResponseEntity<AddProductResponse> addProducts(@RequestBody AddProductRequest addProductRequest) {
    validator.validateNotNull(addProductRequest.getProductIds(), new BadRequestException("Invalid products"));

    final Cart cart;
    if (addProductRequest.getCartId().isPresent()) {
      final String cartId = addProductRequest.getCartId().get();
      validator.validateNotBlank(cartId, new BadRequestException("Invalid cart"));
      cart = cartOperations.addProduct(cartId, addProductRequest.getProductIds());
    } else
      cart = cartOperations.addProduct(addProductRequest.getProductIds());

    return ResponseEntity.ok(new AddProductResponse(cart));
  }
}
