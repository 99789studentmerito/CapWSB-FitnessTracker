package pl.wsb.fitnesstracker.training.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.training.api.Training;

import java.util.Date;

@Component
class TrainingMapper {

    TrainingDto toDto(Training Training) {
        return new TrainingDto(Training.getId(),
                Training.getUser(),
                Training.getStartTime(),
                Training.getEndTime(),
                Training.getActivityType(),
                Training.getDistance(),
                Training.getAverageSpeed());
    }

    /*Training toEntity(TrainingDto TrainingDto) {
        return new Training(
                TrainingDto.getUser(),
                TrainingDto.getStartTime(),
                TrainingDto.getEndTime(),
                TrainingDto.getActivityType(),
                TrainingDto.getDistance(),
                TrainingDto.getAverageSpeed());
    }*/
}
