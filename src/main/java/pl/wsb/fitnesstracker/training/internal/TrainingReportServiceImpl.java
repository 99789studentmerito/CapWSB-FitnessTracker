package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.mail.api.EmailSender;
import pl.wsb.fitnesstracker.mail.api.EmailDto;
import pl.wsb.fitnesstracker.training.api.TrainingReportService;
import pl.wsb.fitnesstracker.training.api.TrainingService;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.internal.UserRepository;

import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingReportServiceImpl implements TrainingReportService {

    private final UserRepository userRepository;
    private final TrainingService trainingService;
    private final EmailSender emailSender;

    @Override
    public void generateAndSendMonthlyReports(YearMonth reportMonth) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            int totalWorkouts = trainingService.findTrainingsByUser(user).size();

            String subject = "Raport: liczba wszystkich treningów";
            String body = String.format(
                    "Cześć %s!\nW sumie zarejestrowałeś(-aś) %d treningów w naszym systemie.",
                    user.getFirstName(), totalWorkouts
            );

            EmailDto email = new EmailDto(user.getEmail(), subject, body);
            emailSender.send(email);
        }
    }
}