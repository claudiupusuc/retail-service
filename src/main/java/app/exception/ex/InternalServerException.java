package app.exception.ex;

import app.exception.dao.Error;

public class InternalServerException extends AppException {

  public InternalServerException(String message, Error error) {
    super(message, error);
  }

  public InternalServerException(String message) {
    super(message, Error.INTERNAL_ERVER_ERROR);
  }
}
