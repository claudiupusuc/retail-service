package app.exception.dao;

public enum Error {

  BAD_REQUEST(1010),
  NOT_FOUND(1020),
  INTERNAL_ERVER_ERROR(5000)
  ;

  private final int code;

  Error(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
