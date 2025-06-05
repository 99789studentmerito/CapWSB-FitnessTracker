package pl.wsb.fitnesstracker.training;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import pl.wsb.fitnesstracker.training.api.TrainingCreateRequest;
import pl.wsb.fitnesstracker.training.internal.ActivityType;

import static org.junit.jupiter.api.Assertions.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

class TrainingCreateRequestTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        // rejestracja modułu JSR310
        objectMapper.registerModule(new JavaTimeModule());
        // wyłącz zapisywanie dat jako liczby (timestamp)
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void shouldCreateTrainingRequest() {
        // given
        Long userId = 1L;
        OffsetDateTime startTime = OffsetDateTime.now(ZoneOffset.UTC);
        OffsetDateTime endTime = startTime.plusHours(1);
        ActivityType activityType = ActivityType.RUNNING;
        double distance = 5.0;
        double averageSpeed = 10.0;

        // when
        TrainingCreateRequest request = new TrainingCreateRequest(
                userId,
                startTime,
                endTime,
                activityType,
                distance,
                averageSpeed
        );

        // then
        assertNotNull(request);
        assertEquals(userId, request.userId());
        assertEquals(startTime, request.startTime());
        assertEquals(endTime, request.endTime());
        assertEquals(activityType, request.activityType());
        assertEquals(distance, request.distance());
        assertEquals(averageSpeed, request.averageSpeed());
    }

    @Test
    void shouldSerializeAndDeserialize() throws Exception {
        // given
        TrainingCreateRequest original = new TrainingCreateRequest(
                1L,
                OffsetDateTime.now(ZoneOffset.UTC),
                OffsetDateTime.now(ZoneOffset.UTC).plusHours(1),
                ActivityType.RUNNING,
                5.0,
                10.0
        );

        // when
        String json = objectMapper.writeValueAsString(original);
        TrainingCreateRequest deserialized = objectMapper.readValue(json, TrainingCreateRequest.class);

        // then
        assertEquals(original, deserialized);
    }

    @Test
    void shouldHandleNullValues() {
        // when & then
        assertThrows(NullPointerException.class, () -> new TrainingCreateRequest(
                null,
                null,
                null,
                null,
                0.0,
                0.0
        ));
    }

    @Test
    void shouldValidateTimeOrder() {
        // given
        OffsetDateTime laterTime = OffsetDateTime.now(ZoneOffset.UTC);
        OffsetDateTime earlierTime = laterTime.minusHours(1);

        // when
        TrainingCreateRequest validRequest = new TrainingCreateRequest(
                1L,
                earlierTime,
                laterTime,
                ActivityType.RUNNING,
                5.0,
                10.0
        );

        // then
        assertTrue(validRequest.startTime().isBefore(validRequest.endTime()));
    }

    @Test
    void shouldValidatePositiveDistance() {
        // given
        OffsetDateTime startTime = OffsetDateTime.now(ZoneOffset.UTC);
        OffsetDateTime endTime = startTime.plusHours(1);

        // when & then
        TrainingCreateRequest request = new TrainingCreateRequest(
                1L,
                startTime,
                endTime,
                ActivityType.RUNNING,
                5.0,
                10.0
        );

        assertTrue(request.distance() >= 0, "Distance should be positive");
    }

    @Test
    void shouldValidatePositiveAverageSpeed() {
        // given
        OffsetDateTime startTime = OffsetDateTime.now(ZoneOffset.UTC);
        OffsetDateTime endTime = startTime.plusHours(1);

        // when & then
        TrainingCreateRequest request = new TrainingCreateRequest(
                1L,
                startTime,
                endTime,
                ActivityType.RUNNING,
                5.0,
                10.0
        );

        assertTrue(request.averageSpeed() >= 0, "Average speed should be positive");
    }

    @Test
    void shouldTestJsonDeserialization() throws Exception {
        // given
        String json = """
            {
                "userId": 1,
                "startTime": "2024-01-01T10:00:00+00:00",
                "endTime": "2024-01-01T11:00:00+00:00",
                "activityType": "RUNNING",
                "distance": 5.0,
                "averageSpeed": 10.0
            }
            """;

        // when
        TrainingCreateRequest request = objectMapper.readValue(json, TrainingCreateRequest.class);

        // then
        assertNotNull(request);
        assertEquals(1L, request.userId());
        assertEquals(ActivityType.RUNNING, request.activityType());
        assertEquals(5.0, request.distance());
        assertEquals(10.0, request.averageSpeed());
    }

    @Test
    void shouldTestEquality() {
        // given
        OffsetDateTime startTime = OffsetDateTime.now(ZoneOffset.UTC);
        OffsetDateTime endTime = startTime.plusHours(1);

        TrainingCreateRequest request1 = new TrainingCreateRequest(1L, startTime, endTime, ActivityType.RUNNING, 5.0, 10.0);
        TrainingCreateRequest request2 = new TrainingCreateRequest(1L, startTime, endTime, ActivityType.RUNNING, 5.0, 10.0);
        TrainingCreateRequest request3 = new TrainingCreateRequest(2L, startTime, endTime, ActivityType.RUNNING, 5.0, 10.0);

        // then
        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
        assertEquals(request1.hashCode(), request2.hashCode());
    }
}