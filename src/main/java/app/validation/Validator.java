package app.validation;

import org.springframework.stereotype.Component;

@Component
public class Validator {

  public void validateNotBlank(String value, RuntimeException e) {
    validateNotNull(value, e);
    if (value.isBlank()) {
      throw e;
    }
  }

  public void validateNotNull(Object value, RuntimeException e) {
    if (value == null) {
      throw e;
    }
  }
}
