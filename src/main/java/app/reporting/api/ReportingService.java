package app.reporting.api;

import app.cart.CartOperations;
import app.cart.dao.Cart;
import app.exception.ex.InternalServerException;
import app.order.OrderOperations;
import app.order.dao.Order;
import app.products.ProductOperations;
import app.products.dao.Product;
import app.reporting.ReportingOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ReportingService implements ReportingOperations {

  private final OrderOperations orderOperations;
  private final CartOperations cartOperations;
  private final ProductOperations productOperations;

  @Autowired
  public ReportingService(OrderOperations orderOperations, CartOperations cartOperations, ProductOperations productOperations) {
    this.orderOperations = orderOperations;
    this.cartOperations = cartOperations;
    this.productOperations = productOperations;
  }

  @Override
  public List<Product> top5Today() {
    final List<Order> ordersToday = orderOperations.listOrders(LocalDate.now(), LocalDate.now().plusDays(1));
    final List<String> cartIds = ordersToday.stream().map(Order::getCartId).toList();

    final Map<Product, Integer> productSellCount = getProductSellCount(cartIds);
    final Map<Product, Integer> sortedProductSellCount = sortByValue(productSellCount, false);

    final List<Product> top5SoldProductsToday = new ArrayList<>();
    int i = 0;
    for (Map.Entry<Product, Integer> entry : sortedProductSellCount.entrySet()) {
      if (i == 5)
        break;

      top5SoldProductsToday.add(entry.getKey());
      i++;
    }

    return top5SoldProductsToday;
  }

  @Override
  public List<Product> leastSellingProductsMonth(int amount) {
    final List<Order> ordersThisMonth = orderOperations.listOrders(LocalDate.now().minusMonths(1), LocalDate.now());
    final List<String> cartIds = ordersThisMonth.stream().map(Order::getCartId).toList();

    final Map<Product, Integer> productSellCount = getProductSellCount(cartIds);
    productOperations.listProducts().forEach(product -> {
      if (!productSellCount.containsKey(product))
        productSellCount.put(product, 0);
    });

    final Map<Product, Integer> sortedProductSellCount = sortByValue(productSellCount, true);

    final List<Product> leastSoldThisMonth = new ArrayList<>();
    int i = 0;
    for (Map.Entry<Product, Integer> entry : sortedProductSellCount.entrySet()) {
      if (i == amount)
        break;

      leastSoldThisMonth.add(entry.getKey());
      i++;
    }

    return leastSoldThisMonth;
  }

  @Override
  public Map<LocalDate, Double> saleAmountPerDay(LocalDate start, LocalDate end) {
    final Map<LocalDate, Double> salesPerDay = new LinkedHashMap<>();

    LocalDate currentDate = start.plusDays(1);
    while (!currentDate.isAfter(end)) {
      salesPerDay.put(currentDate.minusDays(1), saleAmountPerDay(currentDate));
      currentDate = currentDate.plusDays(1);
    }

    return salesPerDay;
  }

  public Double saleAmountPerDay(LocalDate day) {
    final List<Order> orders = orderOperations.listOrders(day, day.plusDays(1));
    final List<String> cartIds = orders.stream().map(Order::getCartId).toList();

    final Map<Product, Integer> productSellCount = getProductSellCount(cartIds);
    double totalAmount = 0;
    for (Map.Entry<Product, Integer> entry : productSellCount.entrySet()) {
      totalAmount += entry.getKey().getPrice() * entry.getValue();
    }

    return totalAmount;
  }

  private Map<Product, Integer> getProductSellCount(List<String> cartIds) {
    final List<Cart> carts = new ArrayList<>();
    cartIds.forEach(cartId -> {
      carts.add(cartOperations.getCart(cartId).orElseThrow(() -> new InternalServerException("Internal Server Error Occurred")));
    });

    final Map<String, Integer> productIdSellCount = new HashMap<>();
    carts.forEach(cart -> cart.getProductQuantity().forEach((product, quantity) -> {
      if (!productIdSellCount.containsKey(product))
        productIdSellCount.put(product, 0);

      productIdSellCount.put(product, productIdSellCount.get(product) + quantity);
    }));

    final Map<Product, Integer> productSellCount = new HashMap<>();
    productIdSellCount.forEach((productId, sellCount) -> {
      final Product product = productOperations.getProduct(productId).orElseThrow(() -> new InternalServerException("Internal Server Error Occurred"));
      productSellCount.put(product, sellCount);
    });

    return productSellCount;
  }

  private Map<Product, Integer> sortByValue(Map<Product, Integer> map, boolean reverse) {
    List<Map.Entry<Product, Integer>> list = new ArrayList<>(map.entrySet());
    list.sort(Map.Entry.comparingByValue());

    if(reverse)
      Collections.reverse(list);

    Map<Product, Integer> result = new LinkedHashMap<>();
    for (Map.Entry<Product, Integer> entry : list) {
      result.put(entry.getKey(), entry.getValue());
    }

    return result;
  }
}
