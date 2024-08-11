package app.cart;

import app.UnitTest;
import app.cart.api.CartRepository;
import app.cart.api.CartService;
import app.cart.dao.Cart;
import app.exception.ex.NotFoundException;
import app.products.ProductOperations;
import app.products.dao.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartServiceTest extends UnitTest {

  CartRepository cartRepositoryMock = mock(CartRepository.class);
  ProductOperations productOperationsMock = mock(ProductOperations.class);
  CartService cartService = new CartService(cartRepositoryMock, productOperationsMock);

  @Test
  @DisplayName("Should add product to existing cart")
  public void addProductToExistingCart() {
    // given
    String cartId = "cart-id";
    List<String> productIds = List.of("product2", "product3");
    Map<String, Integer> productQuantityMap = new HashMap<>();
    Cart existingCart = new Cart(cartId, productQuantityMap);

    when(cartRepositoryMock.findById(cartId)).thenReturn(Optional.of(existingCart));
    when(productOperationsMock.getProduct("product2")).thenReturn(Optional.of(new Product("product2", "Sample product 2", 3.50)));
    when(productOperationsMock.getProduct("product3")).thenReturn(Optional.of(new Product("product3", "Sample product 3", 5.50)));

    // when
    cartService.addProduct(cartId, productIds);

    // then
    verify(cartRepositoryMock).findById(cartId);
    verify(productOperationsMock).getProduct("product2");
    verify(productOperationsMock).getProduct("product3");
    verify(cartRepositoryMock).save(existingCart);
  }

  @Test
  @DisplayName("Should add product to a new cart")
  public void addProductToNewCart() {
    // given
    List<String> productIds = List.of("product2", "product3");
    Cart savedCart = new Cart("cart-id", Map.of("product2", 1, "product 3", 1));

    when(productOperationsMock.getProduct("product2")).thenReturn(Optional.of(new Product("product2", "Sample product 2", 3.50)));
    when(productOperationsMock.getProduct("product3")).thenReturn(Optional.of(new Product("product3", "Sample product 3", 5.50)));
    when(cartRepositoryMock.save(any(Cart.class))).thenReturn(savedCart);

    // when
    Cart cart = cartService.addProduct(productIds);

    // then
    assertThat(cart).isEqualTo(savedCart);

    verify(productOperationsMock).getProduct("product2");
    verify(productOperationsMock).getProduct("product3");
    verify(cartRepositoryMock).save(any(Cart.class));
  }

  @Test
  @DisplayName("Should throw an exception when adding a product to a cart that is not found")
  public void addProductCartNotFound() {
    when(cartRepositoryMock.findById(anyString())).thenReturn(Optional.empty());

    // when - then
    assertThrows(NotFoundException.class, () -> cartService.addProduct("invalid--cart-id", List.of("product1")));
  }

  @Test
  @DisplayName("Should throw an exception when adding a product that is not found to a cart")
  public void addProductProductNotFound() {
    // given
    String cartId = "cart-id";
    List<String> productIds = List.of("product2", "product3");
    Map<String, Integer> productQuantityMap = new HashMap<>();
    Cart existingCart = new Cart(cartId, productQuantityMap);

    when(cartRepositoryMock.findById(cartId)).thenReturn(Optional.of(existingCart));
    when(productOperationsMock.getProduct("product2")).thenReturn(Optional.of(new Product("product2", "Sample product 2", 3.50)));
    when(productOperationsMock.getProduct("product3")).thenReturn(Optional.empty());

    // when - then
    assertThrows(NotFoundException.class, () -> cartService.addProduct(cartId, productIds));
  }

  @Test
  @DisplayName("Should get cart by cart id")
  public void getCart() {
    // given
    String cartId = "cart-id";

    // when
    cartService.getCart(cartId);

    // then
    verify(cartRepositoryMock).findById(cartId);
  }
}
