package pl.wsb.fitnesstracker.training.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.user.internal.model.User;

import java.time.LocalDateTime;
import java.util.List;

// Metoda do znajdowania treningów po użytkownikach
interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findByUser(User user);

    // Metoda do znajdowania treningów po określonym czasie
    List<Training> findByEndTimeLdtAfter(LocalDateTime endTimeLdt);

    // metoda do znajdowania treningów dla konkretnej aktywności
    List<Training> findByActivityType(ActivityType activityType);
}