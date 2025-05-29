package pl.wsb.fitnesstracker.training.api;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.wsb.fitnesstracker.training.internal.ActivityType;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(name = "trainings")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTimeLdt;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTimeLdt;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "activity_type", nullable = false)
    private ActivityType activityType;

    @Column(name = "distance")
    private double distance;

    @Column(name = "average_speed")
    private double averageSpeed;

    // Main constructor for LocalDateTime
    public Training(
            final User user,
            final LocalDateTime startTime,
            final LocalDateTime endTime,
            final ActivityType activityType,
            final double distance,
            final double averageSpeed) {
        this.user = user;
        this.startTimeLdt = startTime;
        this.endTimeLdt = endTime;
        this.activityType = activityType;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
    }

    // Compatibility constructor for legacy code using Date
    public Training(
            final User user,
            final Date startTime,
            final Date endTime,
            final ActivityType activityType,
            final double distance,
            final double averageSpeed) {
        this.user = user;
        this.startTimeLdt = startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.endTimeLdt = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.activityType = activityType;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
    }

    // --- Compatibility getters for legacy tests ---

    /**
     * For legacy tests: getStartTime() returns Date.
     */
    @Transient
    public Date getStartTime() {
        return startTimeLdt == null ? null : Date.from(startTimeLdt.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * For legacy tests: getEndTime() returns Date.
     */
    @Transient
    public Date getEndTime() {
        return endTimeLdt == null ? null : Date.from(endTimeLdt.atZone(ZoneId.systemDefault()).toInstant());
    }

    // --- Modern getters for JPA and code ---

    public LocalDateTime getStartTimeLdt() {
        return startTimeLdt;
    }

    public LocalDateTime getEndTimeLdt() {
        return endTimeLdt;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public double getDistance() {
        return distance;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    // Do aktualizacji treningów tworzę settery

    public void setUser(User user) { this.user = user; }
    public void setStartTimeLdt(LocalDateTime startTimeLdt) { this.startTimeLdt = startTimeLdt; }
    public void setEndTimeLdt(LocalDateTime endTimeLdt) { this.endTimeLdt = endTimeLdt; }
    public void setActivityType(ActivityType activityType) { this.activityType = activityType; }
    public void setDistance(double distance) { this.distance = distance; }
    public void setAverageSpeed(double averageSpeed) { this.averageSpeed = averageSpeed; }
}

