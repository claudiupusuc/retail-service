package app.products;

import app.UnitTest;
import app.products.api.ProductRepository;
import app.products.api.ProductService;
import app.products.dao.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest extends UnitTest {

  ProductRepository productRepositoryMock = mock(ProductRepository.class);
  ProductService productService = new ProductService(productRepositoryMock);

  @Test
  @DisplayName("Should search for products given a product name")
  public void searchProducts() {
    // given
    String productName = "Rice";
    Product jasmineRice = new Product("1", "Jasmine Rice", 5.50, 1);
    Product roundRice = new Product("1", "Round Rice", 5.50, 1);

    when(productRepositoryMock.searchProducts(productName)).thenReturn(List.of(jasmineRice, roundRice));

    // when
    final List<Product> result = productService.searchProducts(productName);

    // then
    assertThat(result).containsExactlyInAnyOrder(roundRice, jasmineRice);

    verify(productRepositoryMock).searchProducts(productName);
  }

  @Test
  @DisplayName("Should get product by product id")
  public void getProduct() {
    // given
    String productId = "sample-product-1D";

    // when
    productService.getProduct(productId);

    // then
    verify(productRepositoryMock).findById(productId);
  }

  @Test
  @DisplayName("Should save product")
  public void save() {
    // given
    Product product = new Product("1", "Rice", 5.50, 20);

    // when
    productService.save(product);

    // then
    verify(productRepositoryMock).save(product);
  }
}
