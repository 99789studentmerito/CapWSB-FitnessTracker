package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.internal.TrainingDto;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
class TrainingController {

    private final TrainingServiceImpl trainingService;

    private final TrainingMapper trainingMapper;

    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.findAllTrainings()
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping
    @RequestMapping("/{trainingId}")
    public TrainingDto getTrainingById(
            @PathVariable Long trainingId) {
        return trainingService.getTraining(trainingId)
                .map(trainingMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(trainingId));
    }

    /*@PostMapping
    @ResponseStatus(HttpStatus.CREATED)public TrainingDto createTraining(@RequestBody TrainingDto trainingDto) {
        Training training = trainingMapper.toEntity(trainingDto);
        Training createdTraining = trainingService.createTraining(training);
        return trainingMapper.toDto(createdTraining);}

    @PostMapping
    @RequestMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTraining(@RequestBody TrainingDto trainingDto) {
        Training training = trainingMapper.toEntity(trainingDto);
        trainingMapper.deleteTraining(training);
    }*/

}
