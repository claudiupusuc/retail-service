package app.products.rest;

import app.products.dao.Product;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SearchProductsResponse {

  private final List<Product> products;

  @JsonCreator
  public SearchProductsResponse(@JsonProperty("products") List<Product> products) {
    this.products = products;
  }

  public List<Product> getProducts() {
    return products;
  }

  @Override
  public String toString() {
    return "SearchProductsResponse{" +
      "products=" + products +
      '}';
  }
}
