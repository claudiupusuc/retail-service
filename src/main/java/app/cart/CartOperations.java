package app.cart;

import app.cart.dao.Cart;

import java.util.List;

public interface CartOperations {

  /**
   * Add products to a cart.
   * @param cartId The cart to add the products to.
   * @param productIds The products to be added to this cart.
   * @return The updated cart.
   */
  Cart addProduct(String cartId, List<String> productIds);
}
