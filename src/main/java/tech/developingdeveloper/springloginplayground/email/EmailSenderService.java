package tech.developingdeveloper.springloginplayground.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by Abhishek Saxena on 01-04-2021.
 */

@Service
public class EmailSenderService implements EmailSender {

    private final static Logger LOGGER = LoggerFactory
            .getLogger(EmailSenderService.class);

    private final JavaMailSender mailSender;

    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async
    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    mimeMessage, "utf-8"
            );
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("no-reply@developingdeveloper.tech");

            mailSender.send(mimeMessage);

        } catch (MessagingException exception) {
            String errorMessage = "Failed to send email";
            LOGGER.error(errorMessage, exception);
            throw new IllegalStateException(errorMessage);
        }
    }
}
