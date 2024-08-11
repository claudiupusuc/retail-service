package app.validation;

import app.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest extends UnitTest {

  Validator validator = new Validator();

  @Test
  @DisplayName("Should throw exception when value is null")
  public void validateNull() {
    // given
    RuntimeException exception = new RuntimeException();

    // when - then
    assertThrows(exception.getClass(), () -> validator.validateNotNull(null, exception));
  }

  @ParameterizedTest
  @DisplayName("Should throw exception when value is blank")
  @ValueSource(strings = {"", " "})
  public void validateBlank(String value) {
    // given
    RuntimeException exception = new RuntimeException();

    // when - then
    assertThrows(exception.getClass(), () -> validator.validateNotBlank(value, exception));
  }

  @ParameterizedTest
  @DisplayName("Should throw exception for invalid email")
  @ValueSource(strings = {"john", "john@", "@mail.com"})
  public void validateEmail(String email) {
    // given
    RuntimeException exception = new RuntimeException();

    // when - then
    assertThrows(exception.getClass(), () -> validator.validateEmail(email, exception));
  }
}
