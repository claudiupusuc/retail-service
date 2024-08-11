package app.notifications.mail.config;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class SendGridConfig {

  @Autowired
  private MailProperties mailProperties;

  @Bean
  public SendGrid sendGrid() {
    return new SendGrid(mailProperties.getKey());

  }
}
