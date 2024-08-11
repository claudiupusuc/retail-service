package app.cart;

import app.cart.dao.Cart;

import java.util.List;
import java.util.Optional;

public interface CartOperations {

  /**
   * Add products to an existing cart.
   * @param cartId The cart to add the products to.
   * @param productIds The products to be added to this cart.
   * @return The updated cart.
   */
  Cart addProduct(String cartId, List<String> productIds);

  /**
   * Add products to a new cart.
   * @param productIds The products to be added to this cart.
   * @return The updated cart.
   */
  Cart addProduct(List<String> productIds);

  /**
   * Get cart by cart id.
   * @param cartId The id of the cart
   * @return The Cart or empty if not found.
   */
  Optional<Cart> getCart(String cartId);
}
