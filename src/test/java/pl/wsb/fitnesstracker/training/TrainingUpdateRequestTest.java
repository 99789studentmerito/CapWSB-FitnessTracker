package pl.wsb.fitnesstracker.training;

import org.junit.jupiter.api.Test;
import pl.wsb.fitnesstracker.training.api.TrainingUpdateRequest;
import pl.wsb.fitnesstracker.training.internal.ActivityType;

import static org.junit.jupiter.api.Assertions.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

class TrainingUpdateRequestTest {

    @Test
    void shouldCreateEmptyRequest() {
        // when
        TrainingUpdateRequest request = new TrainingUpdateRequest();

        // then
        assertNull(request.getUserId());
        assertNull(request.getStartTime());
        assertNull(request.getEndTime());
        assertNull(request.getActivityType());
        assertNull(request.getDistance());
        assertNull(request.getAverageSpeed());
    }

    @Test
    void shouldAllowPartialUpdate_OnlyUserId() {
        // given
        TrainingUpdateRequest request = new TrainingUpdateRequest();
        Long expectedUserId = 1L;

        // when
        request.setUserId(expectedUserId);

        // then
        assertEquals(expectedUserId, request.getUserId());
        assertNull(request.getStartTime());
        assertNull(request.getEndTime());
        assertNull(request.getActivityType());
        assertNull(request.getDistance());
        assertNull(request.getAverageSpeed());
    }

    @Test
    void shouldAllowPartialUpdate_OnlyTimes() {
        // given
        TrainingUpdateRequest request = new TrainingUpdateRequest();
        OffsetDateTime startTime = OffsetDateTime.now(ZoneOffset.UTC);
        OffsetDateTime endTime = startTime.plusHours(1);

        // when
        request.setStartTime(startTime);
        request.setEndTime(endTime);

        // then
        assertNull(request.getUserId());
        assertEquals(startTime, request.getStartTime());
        assertEquals(endTime, request.getEndTime());
        assertNull(request.getActivityType());
        assertNull(request.getDistance());
        assertNull(request.getAverageSpeed());
    }

    @Test
    void shouldAllowPartialUpdate_OnlyActivityType() {
        // given
        TrainingUpdateRequest request = new TrainingUpdateRequest();
        ActivityType expectedType = ActivityType.RUNNING;

        // when
        request.setActivityType(expectedType);

        // then
        assertNull(request.getUserId());
        assertNull(request.getStartTime());
        assertNull(request.getEndTime());
        assertEquals(expectedType, request.getActivityType());
        assertNull(request.getDistance());
        assertNull(request.getAverageSpeed());
    }

    @Test
    void shouldAllowPartialUpdate_OnlyMetrics() {
        // given
        TrainingUpdateRequest request = new TrainingUpdateRequest();
        Double expectedDistance = 10.5;
        Double expectedSpeed = 5.2;

        // when
        request.setDistance(expectedDistance);
        request.setAverageSpeed(expectedSpeed);

        // then
        assertNull(request.getUserId());
        assertNull(request.getStartTime());
        assertNull(request.getEndTime());
        assertNull(request.getActivityType());
        assertEquals(expectedDistance, request.getDistance());
        assertEquals(expectedSpeed, request.getAverageSpeed());
    }

    @Test
    void shouldAllowFullUpdate() {
        // given
        TrainingUpdateRequest request = new TrainingUpdateRequest();
        Long userId = 1L;
        OffsetDateTime startTime = OffsetDateTime.now(ZoneOffset.UTC);
        OffsetDateTime endTime = startTime.plusHours(1);
        ActivityType activityType = ActivityType.RUNNING;
        Double distance = 10.5;
        Double averageSpeed = 5.2;

        // when
        request.setUserId(userId);
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        request.setActivityType(activityType);
        request.setDistance(distance);
        request.setAverageSpeed(averageSpeed);

        // then
        assertEquals(userId, request.getUserId());
        assertEquals(startTime, request.getStartTime());
        assertEquals(endTime, request.getEndTime());
        assertEquals(activityType, request.getActivityType());
        assertEquals(distance, request.getDistance());
        assertEquals(averageSpeed, request.getAverageSpeed());
    }

    @Test
    void shouldHandleNegativeValues() {
        // given
        TrainingUpdateRequest request = new TrainingUpdateRequest();
        Double negativeDistance = -10.0;
        Double negativeSpeed = -5.0;

        // when
        request.setDistance(negativeDistance);
        request.setAverageSpeed(negativeSpeed);

        // then
        assertEquals(negativeDistance, request.getDistance());
        assertEquals(negativeSpeed, request.getAverageSpeed());
    }

    @Test
    void shouldHandleZeroValues() {
        // given
        TrainingUpdateRequest request = new TrainingUpdateRequest();
        Double zeroDistance = 0.0;
        Double zeroSpeed = 0.0;

        // when
        request.setDistance(zeroDistance);
        request.setAverageSpeed(zeroSpeed);

        // then
        assertEquals(zeroDistance, request.getDistance());
        assertEquals(zeroSpeed, request.getAverageSpeed());
    }

    @Test
    void shouldAllowSettingFieldsToNull() {
        // given
        TrainingUpdateRequest request = new TrainingUpdateRequest();

        // First set some values
        request.setUserId(1L);
        request.setStartTime(OffsetDateTime.now(ZoneOffset.UTC));
        request.setDistance(10.0);

        // when - reset to null
        request.setUserId(null);
        request.setStartTime(null);
        request.setDistance(null);

        // then
        assertNull(request.getUserId());
        assertNull(request.getStartTime());
        assertNull(request.getDistance());
    }

    @Test
    void shouldHandleTimeOrderIndependently() {
        // given
        TrainingUpdateRequest request = new TrainingUpdateRequest();
        OffsetDateTime laterTime = OffsetDateTime.now(ZoneOffset.UTC);
        OffsetDateTime earlierTime = laterTime.minusHours(1);

        // when - set end time before start time
        request.setStartTime(laterTime);
        request.setEndTime(earlierTime);

        // then - should allow it as validation might be handled elsewhere
        assertEquals(laterTime, request.getStartTime());
        assertEquals(earlierTime, request.getEndTime());
    }
}