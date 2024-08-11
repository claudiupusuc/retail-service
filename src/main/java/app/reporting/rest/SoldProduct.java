package app.reporting.rest;

import app.products.dao.Product;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SoldProduct {

  private final String name;
  private final int quantityAvailable;
  private final double price;

  @JsonCreator
  public SoldProduct(@JsonProperty("name") String name, @JsonProperty("quantityAvailable") int quantityAvailable, @JsonProperty("price") double price) {
    this.name = name;
    this.quantityAvailable = quantityAvailable;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public int getQuantityAvailable() {
    return quantityAvailable;
  }

  public double getPrice() {
    return price;
  }

  static SoldProduct fromProduct(Product product) {
    return new SoldProduct(product.getName(), product.getAvailableQuantity(), product.getPrice());
  }

  @JsonGetter
  public boolean isLowQuantity() {
    return this.quantityAvailable < 10;
  }
}
