package app.cart.api;

import app.cart.CartOperations;
import app.cart.dao.Cart;
import app.exception.ex.NotFoundException;
import app.products.ProductOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class CartService implements CartOperations {

  private final CartRepository cartRepository;
  private final ProductOperations productOperations;

  @Autowired
  public CartService(CartRepository cartRepository, ProductOperations productOperations) {
    this.cartRepository = cartRepository;
    this.productOperations = productOperations;
  }

  @Override
  public Cart addProduct(String cartId, List<String> productIds) {
    final Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new NotFoundException("Cart not found"));
    return addProductsAndSaveCart(cart, productIds);
  }

  @Override
  public Cart addProduct(List<String> productIds) {
    final Cart cart = new Cart(null, new HashMap<>());
    return addProductsAndSaveCart(cart, productIds);
  }

  @Override
  public Optional<Cart> getCart(String cartId) {
    return cartRepository.findById(cartId);
  }

  private Cart addProductsAndSaveCart(Cart cart, List<String> productIds) {
    productIds.forEach(productId -> {
      productOperations.getProduct(productId).orElseThrow(() -> new NotFoundException("Invalid product"));
      cart.addProduct(productId);
    });

    return cartRepository.save(cart);
  }
}
