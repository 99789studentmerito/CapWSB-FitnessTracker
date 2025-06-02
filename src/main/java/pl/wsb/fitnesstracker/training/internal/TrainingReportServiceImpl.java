package pl.wsb.fitnesstracker.training.internal;

import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.training.api.TrainingReportService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

import java.time.YearMonth;

@Service
public class TrainingReportServiceImpl implements TrainingReportService {

    @Override
    public void generateAndSendMonthlyReports(YearMonth yearMonth) {
        // TODO: Implement logic to generate and send reports
        System.out.println("Generowanie i wysyłanie raport dla: " + (yearMonth));

    }

    @Async
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("your.recipient@email.com");
        message.setFrom("john.doe@your.domain");
        message.setSubject(subject);
        message.setText(body);

        .send(message);
    }

}
