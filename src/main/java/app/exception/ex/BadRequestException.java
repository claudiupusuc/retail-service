package app.exception.ex;

import app.exception.dao.Error;

public class BadRequestException extends AppException {

  public BadRequestException(String message, Error error) {
    super(message, error);
  }

  public BadRequestException(String message) {
    super(message, Error.BAD_REQUEST);
  }
}
