package pl.wsb.fitnesstracker.training.api;

import pl.wsb.fitnesstracker.training.internal.ActivityType;
import pl.wsb.fitnesstracker.user.internal.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TrainingService {

    Training createTraining(Training training);

    Training deleteTraining(Training training);

    Optional<Training> getTraining(Long trainingId);

    List<Training> findAllTrainings();

    // zdeklarowanie metody, która ma połączyć się z User'em w osobnym folderze
    List<Training> findTrainingsByUser(User user);


    // metoda do znajdowania treningów po konkretnie zdefiniowanym czasie
    List<Training> findFinishedTrainingsAfter(LocalDateTime afterTime);

    // metoda do znajdowania treningów dla konkretnej aktywności

    List<Training> findTrainingsByActivityType(ActivityType activityType);

    // Do aktualizacji treningów
    Training updateTraining(Training training);

}
