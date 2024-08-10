package app.exception.dao;

public enum Error {

  BAD_REQUEST(1010)
  ;

  private final int code;

  Error(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
