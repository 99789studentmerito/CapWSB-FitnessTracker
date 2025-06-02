package pl.wsb.fitnesstracker.mail.internal;

import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.mail.api.EmailSender;
import pl.wsb.fitnesstracker.mail.api.EmailDto;

@Service
public class SimpleEmailSender implements EmailSender {
    @Override
    public void send(EmailDto email) {
        // Actual implementation here (for now just log to console)
        System.out.printf("Sending email to %s: subject '%s', body: %s%n",
                email.toAddress(), email.subject(), email.content());
    }
}