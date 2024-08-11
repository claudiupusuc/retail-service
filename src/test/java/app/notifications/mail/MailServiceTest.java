package app.notifications.mail;

import app.UnitTest;
import app.notifications.mail.api.MailService;
import app.notifications.mail.config.MailProperties;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MailServiceTest extends UnitTest {

  final SendGrid sendGrid = mock(SendGrid.class);
  final MailProperties mailProperties = new MailProperties("key", "from@mail.com");
  final MailService mailService = new MailService(mailProperties, sendGrid);

  @Test
  @DisplayName("Should send order placed email")
  public void sendVerificationEmail() throws IOException {
    // given
    final String email = "user@mail.com";
    final String orderNumber = "234655";

    when(sendGrid.api(any(Request.class))).thenReturn(new Response(200, "OK", Collections.emptyMap()));

    // when
    mailService.sendOrderPlacedEmail(email, orderNumber);

    // then
    ArgumentCaptor<Request> requestArgumentCaptor = ArgumentCaptor.forClass(Request.class);
    verify(sendGrid).api(requestArgumentCaptor.capture());

    final Request request = requestArgumentCaptor.getValue();
    assertThat(request.getBody()).contains("Your order has been placed. Order number <span id=\\\"order-number\\\">234655</span>");

  }
}
