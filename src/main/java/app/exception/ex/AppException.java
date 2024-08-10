package app.exception.ex;

import app.exception.dao.Error;

public abstract class AppException extends RuntimeException {

  private final String message;
  private final Error error;

  public AppException(String message, Error error) {
    this.message = message;
    this.error = error;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public Error getError() {
    return error;
  }

  @Override
  public String toString() {
    return "AppException{" +
      "message='" + message + '\'' +
      ", error=" + error +
      '}';
  }
}
