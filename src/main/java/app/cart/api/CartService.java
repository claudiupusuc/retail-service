package app.cart.api;

import app.cart.CartOperations;
import app.cart.dao.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CartService implements CartOperations {

  private final CartRepository cartRepository;

  @Autowired
  public CartService(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  @Override
  public Cart addProduct(String cartId, List<String> productIds) {
    final Cart cart = cartRepository.findById(cartId).orElse(createCart());
    productIds.forEach(cart::addProduct);

    return cartRepository.save(cart);
  }

  private Cart createCart() {
    return cartRepository.save(new Cart(null, new HashMap<>()));
  }
}
