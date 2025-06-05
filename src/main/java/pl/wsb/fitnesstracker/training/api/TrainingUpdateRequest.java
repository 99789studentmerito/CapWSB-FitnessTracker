package pl.wsb.fitnesstracker.training.api;

import pl.wsb.fitnesstracker.training.internal.ActivityType;

import java.time.OffsetDateTime;

/**
 * DTO for partial update (PATCH) of Training.
 * All fields are nullable, so only provided fields will be updated.
 */
public class TrainingUpdateRequest {
    private Long userId;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private ActivityType activityType;
    private Double distance;
    private Double averageSpeed;

    public Long getUserId() { return userId; }
    public OffsetDateTime getStartTime() { return startTime; }
    public OffsetDateTime getEndTime() { return endTime; }
    public ActivityType getActivityType() { return activityType; }
    public Double getDistance() { return distance; }
    public Double getAverageSpeed() { return averageSpeed; }

    public void setUserId(Long userId) { this.userId = userId; }
    public void setStartTime(OffsetDateTime startTime) { this.startTime = startTime; }
    public void setEndTime(OffsetDateTime endTime) { this.endTime = endTime; }
    public void setActivityType(ActivityType activityType) { this.activityType = activityType; }
    public void setDistance(Double distance) { this.distance = distance; }
    public void setAverageSpeed(Double averageSpeed) { this.averageSpeed = averageSpeed; }
}