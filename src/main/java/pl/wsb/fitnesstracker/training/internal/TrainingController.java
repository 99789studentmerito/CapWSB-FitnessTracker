package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.training.api.*;
import pl.wsb.fitnesstracker.user.internal.model.User;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserProvider;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;
    private final TrainingMapper trainingMapper;
    private final UserProvider userProvider;


    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.findAllTrainings()
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }
    // wyszukiwanie treningów po ID
    @GetMapping("/training/{trainingId}")
    public TrainingDto getTrainingById(@PathVariable Long trainingId) {
        return trainingService.getTraining(trainingId)
                .map(trainingMapper::toDto)
                .orElseThrow(() -> new TrainingNotFoundException(trainingId));
    }

    // Metoda do znajdowania treningów po użytkownikach
    @GetMapping("{userId}")
    public List<TrainingDto> getTrainingsByUserId(@PathVariable Long userId) {
        User user = userProvider.getUser(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found for id: " + userId));
        return trainingService.findTrainingsByUser(user)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    // metoda do znajdowania treningów dla konkretnej aktywności
    @GetMapping("/activityType")
    public List<TrainingDto> getTrainingByActivityType(@RequestParam("activityType") ActivityType activityType) {
        return trainingService.findTrainingsByActivityType(activityType)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingDto createTraining(@RequestBody TrainingCreateRequest request) {
        var user = userProvider.getUser(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found for id: " + request.userId()));
        TrainingDto trainingDto = new TrainingDto(
                null,
                new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getBirthdate(), user.getEmail(), user.getUsername()),
                request.startTime(),
                request.endTime(),
                request.activityType(),
                request.distance(),
                request.averageSpeed()
        );
        Training training = trainingMapper.toEntity(trainingDto);
        Training createdTraining = trainingService.createTraining(training);
        return trainingMapper.toDto(createdTraining);
    }

    @DeleteMapping("/{trainingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTraining(@PathVariable Long trainingId) {
        Training training = trainingService.getTraining(trainingId)
                .orElseThrow(() -> new TrainingNotFoundException(trainingId));
        trainingService.deleteTraining(training);
    }

    // metoda do wyszukania treningów zakończonych
    @GetMapping("/finished/{afterTime}")
    public List<TrainingDto> getFinishedTrainingsAfter(@PathVariable String afterTime) {
        LocalDate afterDate = LocalDate.parse(afterTime);
        LocalDateTime afterDateTime = afterDate.atStartOfDay();
        return trainingService.findFinishedTrainingsAfter(afterDateTime)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }
    // Aktualizacja treningów

    @PutMapping("/{trainingId}")
    public TrainingDto updateTraining(
        @PathVariable Long trainingId,
        @RequestBody TrainingCreateRequest request
    ) {
    Training existingTraining = trainingService.getTraining(trainingId)
            .orElseThrow(() -> new TrainingNotFoundException(trainingId));

    var user = userProvider.getUser(request.userId())
            .orElseThrow(() -> new IllegalArgumentException("User not found for id: " + request.userId()));

    existingTraining.setUser(user);
    existingTraining.setStartTimeLdt(request.startTime() != null ? request.startTime().toLocalDateTime() : null);
    existingTraining.setEndTimeLdt(request.endTime() != null ? request.endTime().toLocalDateTime() : null);
    existingTraining.setActivityType(request.activityType());
    existingTraining.setDistance(request.distance());
    existingTraining.setAverageSpeed(request.averageSpeed());

    Training savedTraining = trainingService.updateTraining(existingTraining);
    return trainingMapper.toDto(savedTraining);
    }


    /**
     * PATCH endpoint for partial update of a training.
     * Allows updating only selected fields (e.g., distance).
     */
    @PatchMapping("/{trainingId}")
    public TrainingDto updateTrainingPartially(
            @PathVariable Long trainingId,
            @RequestBody TrainingUpdateRequest request
    ) {
        Training existingTraining = trainingService.getTraining(trainingId)
                .orElseThrow(() -> new TrainingNotFoundException(trainingId));

        if (request.getUserId() != null) {
            var user = userProvider.getUser(request.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found for id: " + request.getUserId()));
            existingTraining.setUser(user);
        }
        if (request.getStartTime() != null) {
            existingTraining.setStartTimeLdt(request.getStartTime().toLocalDateTime());
        }
        if (request.getEndTime() != null) {
            existingTraining.setEndTimeLdt(request.getEndTime().toLocalDateTime());
        }
        if (request.getActivityType() != null) {
            existingTraining.setActivityType(request.getActivityType());
        }
        if (request.getDistance() != null) {
            existingTraining.setDistance(request.getDistance());
        }
        if (request.getAverageSpeed() != null) {
            existingTraining.setAverageSpeed(request.getAverageSpeed());
        }

        Training savedTraining = trainingService.updateTraining(existingTraining);
        return trainingMapper.toDto(savedTraining);
    }
}
