package app.reporting;

import app.products.dao.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ReportingOperations {

  /**
   * Listing top 5 best selling products for today.
   * @return List of top 5 best selling products.
   */
  List<Product> top5Today();

  /**
   * Listing a number of products that were the least sold this month.
   * @param amount Number of products to list.
   * @return A list of products.
   */
  List<Product> leastSellingProductsMonth(int amount);

  /**
   * Total sale amount per day within a date range.
   * @param start Start date.
   * @param end End date.
   * @return Map of day -> total amount.
   */
  Map<LocalDate, Double> saleAmountPerDay(LocalDate start, LocalDate end);
}
