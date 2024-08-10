package app.cart;

import app.UnitTest;
import app.cart.rest.AddProductRequest;
import app.cart.rest.CartController;
import app.exception.ex.BadRequestException;
import app.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CartControllerTest extends UnitTest {

  CartOperations cartOperationsMock = mock(CartOperations.class);
  Validator validatorMock = mock(Validator.class);
  CartController cartController = new CartController(cartOperationsMock, validatorMock);

  @Test
  @DisplayName("Should add products to an existing cart")
  public void addProductsExistingCart() {
    // given
    String cartId = "sample-cart-1D";
    List<String> products = List.of("prod1", "prod1", "prod2");
    AddProductRequest addProductRequest = new AddProductRequest(cartId, products);

    // when
    cartController.addProducts(addProductRequest);

    // then
    verify(validatorMock).validateNotNull(eq(products), any(BadRequestException.class));
    verify(validatorMock).validateNotBlank(eq(cartId), any(BadRequestException.class));
    verify(cartOperationsMock).addProduct(cartId, products);
  }

  @Test
  @DisplayName("Should add products to a new cart")
  public void addProductsNewCart() {
    // given
    List<String> products = List.of("prod1", "prod1", "prod2");
    AddProductRequest addProductRequest = new AddProductRequest(null, products);

    // when
    cartController.addProducts(addProductRequest);

    // then
    verify(validatorMock).validateNotNull(eq(products), any(BadRequestException.class));
    verifyNoMoreInteractions(validatorMock);
    verify(cartOperationsMock).addProduct(products);
  }
}
