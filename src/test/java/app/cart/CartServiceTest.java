package app.cart;

import app.UnitTest;
import app.cart.api.CartRepository;
import app.cart.api.CartService;
import app.products.ProductOperations;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class CartServiceTest extends UnitTest {

  //TODO Implement tests inside this class

  CartRepository cartRepositoryMock = mock(CartRepository.class);
  ProductOperations productOperationsMock = mock(ProductOperations.class);
  CartService cartService = new CartService(cartRepositoryMock, productOperationsMock);

  @Test
  @DisplayName("Should add product to existing cart")
  public void addProductToExistingCart() {

  }

  @Test
  @DisplayName("Should add product to a new cart")
  public void addProductToNewCart() {

  }

  @Test
  @DisplayName("Should throw an exception when adding a product to a cart that is not found")
  public void addProductCartNotFound() {

  }

  @Test
  @DisplayName("Should throw an exception when adding a product that is not found to a cart")
  public void addProductProductNotFound() {

  }
}
