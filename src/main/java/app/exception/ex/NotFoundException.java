package app.exception.ex;

import app.exception.dao.Error;

public class NotFoundException extends AppException {

  public NotFoundException(String message, Error error) {
    super(message, error);
  }

  public NotFoundException(String message) {
    super(message, Error.NOT_FOUND);
  }
}
