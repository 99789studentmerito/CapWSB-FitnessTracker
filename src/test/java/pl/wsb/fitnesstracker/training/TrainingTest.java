package pl.wsb.fitnesstracker.training;

import org.junit.jupiter.api.Test;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.internal.ActivityType;
import pl.wsb.fitnesstracker.user.internal.model.User;


import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

class TrainingTest {

    private User createTestUser() {
        return new User(
                "John",
                "Doe",
                LocalDate.of(1990, 1, 1),
                "john.doe@example.com",
                "JohnyDepp"
        );
    }

    @Test
    void shouldCreateTrainingWithLocalDateTime() {
        // given
        User user = createTestUser();
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1);
        ActivityType activityType = ActivityType.RUNNING;
        double distance = 10.0;
        double averageSpeed = 5.0;

        // when
        Training training = new Training(user, startTime, endTime, activityType, distance, averageSpeed);

        // then
        assertNotNull(training);
        assertEquals(user, training.getUser());
        assertEquals(startTime, training.getStartTimeLdt());
        assertEquals(endTime, training.getEndTimeLdt());
        assertEquals(activityType, training.getActivityType());
        assertEquals(distance, training.getDistance());
        assertEquals(averageSpeed, training.getAverageSpeed());
    }

    @Test
    void shouldCreateTrainingWithDate() {
        // given
        User user = createTestUser();
        Date startTime = new Date();
        Date endTime = new Date(startTime.getTime() + 3600000); // +1 hour
        ActivityType activityType = ActivityType.RUNNING;
        double distance = 10.0;
        double averageSpeed = 5.0;

        // when
        Training training = new Training(user, startTime, endTime, activityType, distance, averageSpeed);

        // then
        assertNotNull(training);
        assertEquals(user, training.getUser());
        assertNotNull(training.getStartTimeLdt());
        assertNotNull(training.getEndTimeLdt());
        assertEquals(activityType, training.getActivityType());
        assertEquals(distance, training.getDistance());
        assertEquals(averageSpeed, training.getAverageSpeed());
    }

    @Test
    void shouldConvertBetweenDateAndLocalDateTime() {
        // given
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        Training training = new Training(
                createTestUser(),
                now,
                now.plusHours(1),
                ActivityType.RUNNING,
                10.0,
                5.0
        );

        // when
        Date startTimeDate = training.getStartTime();
        Date endTimeDate = training.getEndTime();

        // then
        assertEquals(
                now.truncatedTo(ChronoUnit.MILLIS),
                startTimeDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().truncatedTo(ChronoUnit.MILLIS)
        );
        assertEquals(
                now.plusHours(1),
                endTimeDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        );
    }

    @Test
    void shouldUpdateTrainingFields() {
        // given
        Training training = new Training(
                createTestUser(),
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                ActivityType.RUNNING,
                10.0,
                5.0
        );

        User newUser = new User(
                "Anna",
                "Doe",
                LocalDate.of(1995, 5, 15),
                "anna.doe@example.com",
                "AnnaPanna"
        );

        LocalDateTime newStartTime = LocalDateTime.now().plusDays(1);
        LocalDateTime newEndTime = newStartTime.plusHours(2);
        ActivityType newActivityType = ActivityType.CYCLING;
        double newDistance = 20.0;
        double newSpeed = 7.0;

        // when
        training.setUser(newUser);
        training.setStartTimeLdt(newStartTime);
        training.setEndTimeLdt(newEndTime);
        training.setActivityType(newActivityType);
        training.setDistance(newDistance);
        training.setAverageSpeed(newSpeed);

        // then
        assertEquals(newUser, training.getUser());
        assertEquals(newStartTime, training.getStartTimeLdt());
        assertEquals(newEndTime, training.getEndTimeLdt());
        assertEquals(newActivityType, training.getActivityType());
        assertEquals(newDistance, training.getDistance());
        assertEquals(newSpeed, training.getAverageSpeed());
    }

        @Test
    void shouldHandleNegativeValues() {
        // given
        Training training = new Training(
                createTestUser(),
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                ActivityType.RUNNING,
                10.0,
                5.0
        );

        // when
        training.setDistance(-10.0);
        training.setAverageSpeed(-5.0);

        // then
        assertEquals(-10.0, training.getDistance());
        assertEquals(-5.0, training.getAverageSpeed());
    }

    @Test
    void shouldImplementToString() {
        // given
        Training training = new Training(
                createTestUser(),
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                ActivityType.RUNNING,
                10.0,
                5.0
        );

        // when
        String toString = training.toString();

        // then
        assertNotNull(toString);
        assertTrue(toString.contains("Training"));
        assertTrue(toString.contains("RUNNING"));
    }
}