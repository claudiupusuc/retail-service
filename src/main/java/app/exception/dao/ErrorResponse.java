package app.exception.dao;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ErrorResponse {

  private final String message;
  private final int code;

  @JsonCreator
  public ErrorResponse(String message, int code) {
    this.message = message;
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public int getCode() {
    return code;
  }

  @Override
  public String toString() {
    return "Error{" +
      "message='" + message + '\'' +
      ", code=" + code +
      '}';
  }
}
