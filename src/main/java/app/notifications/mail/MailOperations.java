package app.notifications.mail;

public interface MailOperations {

  void sendOrderPlacedEmail(String email, String orderNumber);
}
