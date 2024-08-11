package app.reporting.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductRepostingResponse {

  private final List<SoldProduct> products;

  @JsonCreator
  public ProductRepostingResponse(@JsonProperty("top5SoldToday") List<SoldProduct> products) {
    this.products = products;
  }

  public List<SoldProduct> getTop5SoldToday() {
    return products;
  }
}