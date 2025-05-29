package pl.wsb.fitnesstracker.training.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.wsb.fitnesstracker.common.OffsetDateTimePlus00Deserializer;
import pl.wsb.fitnesstracker.training.internal.ActivityType;

import java.time.OffsetDateTime;

public record TrainingCreateRequest(
        Long userId,
        @JsonDeserialize(using = OffsetDateTimePlus00Deserializer.class)
        OffsetDateTime startTime,
        @JsonDeserialize(using = OffsetDateTimePlus00Deserializer.class)
        OffsetDateTime endTime,
        ActivityType activityType,
        double distance,
        double averageSpeed
) {}