package pl.wsb.fitnesstracker.training.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pl.wsb.fitnesstracker.common.OffsetDateTimePlus00Deserializer;
import pl.wsb.fitnesstracker.common.OffsetDateTimePlus00Serializer;
import pl.wsb.fitnesstracker.training.internal.ActivityType;
import pl.wsb.fitnesstracker.user.api.UserDto;

import java.time.OffsetDateTime;

public record TrainingDto(
        Long id,
        UserDto user,
        @JsonSerialize(using = OffsetDateTimePlus00Serializer.class)
        @JsonDeserialize(using = OffsetDateTimePlus00Deserializer.class)
        OffsetDateTime startTime,
        @JsonSerialize(using = OffsetDateTimePlus00Serializer.class)
        @JsonDeserialize(using = OffsetDateTimePlus00Deserializer.class)
        OffsetDateTime endTime,
        ActivityType activityType,
        double distance,
        double averageSpeed
) {}

