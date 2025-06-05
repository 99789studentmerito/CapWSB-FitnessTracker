package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingProvider;
import pl.wsb.fitnesstracker.training.api.TrainingService;
import pl.wsb.fitnesstracker.user.internal.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingService, TrainingProvider {

    private final TrainingRepository trainingRepository;

    @Override
    public Optional<Training> getTraining(final Long trainingId) {
        return trainingRepository.findById(trainingId);
    }

    @Override
    public Training createTraining(final Training training) {
        log.info("Creating Training {}", training);
        if (training.getId() != null) {
            throw new IllegalArgumentException("Training has already DB ID, update is not permitted!");
        }
        return trainingRepository.save(training);
    }

    @Override
    public Training deleteTraining(Training training) {
        log.info("Deleting Training {}", training);
        if (training.getId() == null) {
            throw new IllegalArgumentException("Training must have a DB ID to be deleted!");
        }
        trainingRepository.delete(training);
        return training;
    }

    @Override
    public List<Training> findAllTrainings() {
        return trainingRepository.findAll();
    }

    // Metoda do znajdowania treningów po użytkownikach
    public List<Training> findTrainingsByUser(User user) {
        return trainingRepository.findByUser(user);
    }
    // metoda do znajdowania treningów po konkretnie zdefiniowanym czasie


    @Override
    public List<Training> findFinishedTrainingsAfter(LocalDateTime afterTime){
        return trainingRepository.findByEndTimeLdtAfter(afterTime);
    }

    // metoda do znajdowania treningów dla konkretnej aktywności
    @Override
    public List<Training> findTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType);
    }

    // Do aktualizacji treningów

    @Override
    public Training updateTraining(final Training training) {
        log.info("Updating Training {}", training);
        if (training.getId() == null) {
            throw new IllegalArgumentException("Training must have a DB ID to be updated!");

        }
        return trainingRepository.save(training);
    }
}