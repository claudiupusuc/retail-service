package app.products.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Product {

  @Id
  private final String id;
  @Indexed(unique = true)
  private final String name;
  private final double price;
  private int availableQuantity;

  public Product(String id, String name, double price, int availableQuantity) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.availableQuantity = availableQuantity;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }

  public int getAvailableQuantity() {
    return availableQuantity;
  }

  public void sell() {
    availableQuantity--;
  }

  @Override
  public String toString() {
    return "Product{" +
      "id='" + id + '\'' +
      ", name='" + name + '\'' +
      ", price=" + price +
      ", availableQuantity=" + availableQuantity +
      '}';
  }
}
