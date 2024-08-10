package app.products;

import app.UnitTest;
import app.products.api.ProductService;
import app.products.dao.Product;
import app.products.rest.ProductController;
import app.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ProductControllerTest extends UnitTest {

  Validator validatorMock = mock(Validator.class);
  ProductService productServiceMock = mock(ProductService.class);
  ProductController productController = new ProductController(productServiceMock, validatorMock);

  @ParameterizedTest
  @ValueSource(strings = {"rice", "  rice  "})
  @DisplayName("Should search products when the product name is present on the request")
  public void searchProducts(String productName) {
    // given
    when(productServiceMock.searchProducts(productName)).thenReturn(List.of(new Product("1", "Rice", 5.50)));
    doNothing().when(validatorMock).validateNotBlank(eq(productName), any(RuntimeException.class));

    // when
    productController.list(productName);

    // then
    verify(validatorMock).validateNotBlank(eq("rice"), any(RuntimeException.class));
    verify(productServiceMock).searchProducts("rice");
  }
}
