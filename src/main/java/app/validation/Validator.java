package app.validation;

import org.springframework.stereotype.Component;

@Component
public class Validator {

  private static final String VALID_EMAIL_REGEX = "[a-zA-Z\\d_.-]+@[a-zA-Z\\d]+\\.[a-zA-Z\\d]+";

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

  public void validateEmail(String email, RuntimeException e) {
    validateNotNull(email, e);
    if (!email.matches(VALID_EMAIL_REGEX)) {
      throw e;
    }
  }
}
