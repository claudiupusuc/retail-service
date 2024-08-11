package app.notifications.mail.api;

import app.exception.ex.InternalServerException;
import app.notifications.mail.MailOperations;
import app.notifications.mail.config.MailProperties;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

@Service
public class MailService implements MailOperations {

  private final MailProperties mailProperties;
  private final SendGrid sendGrid;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  public MailService(MailProperties mailProperties, SendGrid sendGrid) {
    this.mailProperties = mailProperties;
    this.sendGrid = sendGrid;
  }

  @Override
  public void sendOrderPlacedEmail(String email, String orderNumber) {
    final String orderPlacedBody = getMailTemplateAsString("mail/order-placed.html").replace("{ORDER_NUMBER}", orderNumber);
    sendEmail(email, "Order placed", orderPlacedBody);
  }

  private void sendEmail(String to, String subject, String body) {
    final Content content = new Content("text/html", body);
    final Mail mail = new Mail(new Email(mailProperties.getFromEmail()), subject, new Email(to), content);
    final Request request = new Request();
    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      sendGrid.api(request);
    } catch (IOException e) {
      logger.error("Failed to send mail", e);
    }
  }

  private String getMailTemplateAsString(String path) {
    try {
      return StreamUtils.copyToString( new ClassPathResource(path).getInputStream(), Charset.defaultCharset());
    } catch (IOException e) {
      logger.error("Failed to retrieve mail template with path: {}", path, e);
      throw new InternalServerException("Server error occurred");
    }
  }
}
