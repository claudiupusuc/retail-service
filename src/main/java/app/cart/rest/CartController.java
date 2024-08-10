package app.cart.rest;

import app.cart.CartOperations;
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

  @PostMapping("/{cartId}")
  public ResponseEntity<AddProductResponse> addProducts(@PathVariable("cartId") String cartId, @RequestBody AddProductRequest addProductRequest) {
    validator.validateNotNull(addProductRequest.getProductIds(), new BadRequestException("Invalid products"));
    validator.validateNotBlank(cartId, new BadRequestException("Invalid cart"));

    return ResponseEntity.ok(new AddProductResponse(cartOperations.addProduct(cartId, addProductRequest.getProductIds())));
  }
}
