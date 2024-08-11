package app.order.dao;

public class CustomerDetails {

  private final String name;
  private final String phoneNumber;
  private final String email;

  public CustomerDetails(String name, String phoneNumber, String email) {
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getEmail() {
    return email;
  }
}
