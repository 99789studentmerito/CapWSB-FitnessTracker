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
) {
        public TrainingCreateRequest {
                if (userId == null) throw new NullPointerException("userId cannot be null");
                if (startTime == null) throw new NullPointerException("startTime cannot be null");
                if (endTime == null) throw new NullPointerException("endTime cannot be null");
                if (activityType == null) throw new NullPointerException("activityType cannot be null");
        }
}