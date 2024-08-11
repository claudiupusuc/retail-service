package app.reporting.rest;

import app.reporting.ReportingOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reports")
public class ReportingController {

  // This is a management endpoint (Ideally, we would have defined access to endpoints by user roles)
  // So here, only a user with an "ADMIN" role for example, could access this endpoint else a 403 Forbidden error would be returned.
  // In the interest of time I left this part out and focused on the requirements of the assessment. I may come back to it later if I do have more time or else we can chat about it in the interview.

  private final ReportingOperations reportingOperations;

  @Autowired
  public ReportingController(ReportingOperations reportingOperations) {
    this.reportingOperations = reportingOperations;
  }

  @GetMapping("/top-5-today")
  public ResponseEntity<ProductRepostingResponse> top5SoldToday() {
    final List<SoldProduct> soldProducts = reportingOperations.top5Today().stream()
      .map(SoldProduct::fromProduct)
      .toList();

    return ResponseEntity.ok(new ProductRepostingResponse(soldProducts));
  }

  @GetMapping("/least-selling-products-month")
  public ResponseEntity<ProductRepostingResponse> leastSellingProductsMonth(@RequestParam(value = "amount", required = false, defaultValue = "5") Integer amount) {
    final List<SoldProduct> leastSoldProducts = reportingOperations.leastSellingProductsMonth(amount).stream()
      .map(SoldProduct::fromProduct)
      .toList();

    return ResponseEntity.ok(new ProductRepostingResponse(leastSoldProducts));
  }

  @GetMapping("/sale-amount-per-day")
  public ResponseEntity<Map<LocalDate, Double>> saleAmountPerDay(@RequestParam("from") LocalDate from, @RequestParam("to") LocalDate to) {
    return ResponseEntity.ok(reportingOperations.saleAmountPerDay(from, to));
  }
}
