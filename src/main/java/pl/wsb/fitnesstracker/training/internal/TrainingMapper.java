package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.user.internal.model.User;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserProvider;

import java.time.ZoneId;
import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
public class TrainingMapper {

    private final UserProvider userProvider;

    private UserDto toUserDto(User user) {
        if (user == null) return null;
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail(),
                user.getUsername()
        );
    }

    public TrainingDto toDto(Training training) {
        return new TrainingDto(
                training.getId(),
                toUserDto(training.getUser()),
                training.getStartTimeLdt() != null
                        ? training.getStartTimeLdt().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).toOffsetDateTime()
                        : null,
                training.getEndTimeLdt() != null
                        ? training.getEndTimeLdt().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).toOffsetDateTime()
                        : null,
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed()
        );
    }

    public Training toEntity(TrainingDto dto) {
        User user = null;
        if (dto.user() != null && dto.user().id() != null) {
            user = userProvider.getUser(dto.user().id())
                    .orElseThrow(() -> new IllegalArgumentException("User not found for id: " + dto.user().id()));
        }
        return new Training(
                user,
                dto.startTime() != null ? dto.startTime().toLocalDateTime() : null,
                dto.endTime() != null ? dto.endTime().toLocalDateTime() : null,
                dto.activityType(),
                dto.distance(),
                dto.averageSpeed()
        );
    }
}