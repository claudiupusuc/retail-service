package app.notifications.mail.config;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendGridConfig {

  @Autowired
  private MailProperties mailProperties;

  @Bean
  public SendGrid sendGrid() {
    return new SendGrid(mailProperties.getKey());

  }
}
