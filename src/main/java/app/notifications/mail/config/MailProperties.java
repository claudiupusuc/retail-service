package app.notifications.mail.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.mail")
public class MailProperties {

  private final String key;
  private final String fromEmail;

  public MailProperties(String key, String fromEmail) {
    this.key = key;
    this.fromEmail = fromEmail;
  }

  public String getKey() {
    return key;
  }

  public String getFromEmail() {
    return fromEmail;
  }
}
