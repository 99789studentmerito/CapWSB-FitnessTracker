package pl.wsb.fitnesstracker.training;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.training.internal.ActivityType;
import pl.wsb.fitnesstracker.user.api.UserDto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TrainingDtoTest {

    //private final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void shouldCreateTrainingDtoWithAllFields() {
        // given
        Long id = 1L;
        UserDto user = new UserDto(1L, "John", "Doe", LocalDate.of(1979, 5, 1) , "john@example.com");
        OffsetDateTime startTime = OffsetDateTime.of(2024, 1, 1, 10, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime endTime = OffsetDateTime.of(2024, 1, 1, 11, 0, 0, 0, ZoneOffset.UTC);
        ActivityType activityType = ActivityType.RUNNING;
        double distance = 10.5;
        double averageSpeed = 12.3;

        // when
        TrainingDto trainingDto = new TrainingDto(id, user, startTime, endTime, activityType, distance, averageSpeed);

        // then
        assertAll(
                () -> assertEquals(id, trainingDto.id()),
                () -> assertEquals(user, trainingDto.user()),
                () -> assertEquals(startTime, trainingDto.startTime()),
                () -> assertEquals(endTime, trainingDto.endTime()),
                () -> assertEquals(activityType, trainingDto.activityType()),
                () -> assertEquals(distance, trainingDto.distance()),
                () -> assertEquals(averageSpeed, trainingDto.averageSpeed())
        );
    }

    @Test
    void shouldBeEqualWhenAllFieldsAreEqual() {
        // given
        UserDto user = new UserDto(1L, "John", "Doe", LocalDate.of(1979, 5, 1) , "john@example.com");
        OffsetDateTime startTime = OffsetDateTime.of(2024, 1, 1, 10, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime endTime = OffsetDateTime.of(2024, 1, 1, 11, 0, 0, 0, ZoneOffset.UTC);

        TrainingDto training1 = new TrainingDto(1L, user, startTime, endTime, ActivityType.RUNNING, 10.5, 12.3);
        TrainingDto training2 = new TrainingDto(1L, user, startTime, endTime, ActivityType.RUNNING, 10.5, 12.3);

        // when & then
        assertAll(
                () -> assertEquals(training1, training2),
                () -> assertEquals(training1.hashCode(), training2.hashCode())
        );
    }

    @Test
    void shouldNotBeEqualWhenFieldsDiffer() {
        // given
        UserDto user = new UserDto(1L, "John", "Doe", LocalDate.of(1979, 5, 1) , "john@example.com");
        OffsetDateTime startTime = OffsetDateTime.of(2024, 1, 1, 10, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime endTime = OffsetDateTime.of(2024, 1, 1, 11, 0, 0, 0, ZoneOffset.UTC);

        TrainingDto training1 = new TrainingDto(1L, user, startTime, endTime, ActivityType.RUNNING, 10.5, 12.3);
        TrainingDto training2 = new TrainingDto(2L, user, startTime, endTime, ActivityType.RUNNING, 10.5, 12.3);

        // when & then
        assertNotEquals(training1, training2);
    }

    @Test
    void shouldSerializeAndDeserializeCorrectly() throws Exception {
        // given
        UserDto user = new UserDto(1L, "John", "Doe", LocalDate.of(1979, 5, 1) , "john@example.com");
        OffsetDateTime startTime = OffsetDateTime.of(2024, 1, 1, 10, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime endTime = OffsetDateTime.of(2024, 1, 1, 11, 0, 0, 0, ZoneOffset.UTC);
        TrainingDto originalDto = new TrainingDto(1L, user, startTime, endTime, ActivityType.RUNNING, 10.5, 12.3);

        // when
        String json = objectMapper.writeValueAsString(originalDto);
        TrainingDto deserializedDto = objectMapper.readValue(json, TrainingDto.class);

        // then
        assertEquals(originalDto, deserializedDto);
    }

    @Test
    void shouldHandleNullUser() {
        // given
        OffsetDateTime startTime = OffsetDateTime.of(2024, 1, 1, 10, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime endTime = OffsetDateTime.of(2024, 1, 1, 11, 0, 0, 0, ZoneOffset.UTC);

        // when
        TrainingDto trainingDto = new TrainingDto(1L, null, startTime, endTime, ActivityType.RUNNING, 10.5, 12.3);

        // then
        assertNull(trainingDto.user());
    }

    @Test
    void shouldCreateToStringRepresentation() {
        // given
        UserDto user = new UserDto(1L, "John", "Doe", LocalDate.of(1979, 5, 1) , "john@example.com");
        OffsetDateTime startTime = OffsetDateTime.of(2024, 1, 1, 10, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime endTime = OffsetDateTime.of(2024, 1, 1, 11, 0, 0, 0, ZoneOffset.UTC);
        TrainingDto trainingDto = new TrainingDto(1L, user, startTime, endTime, ActivityType.RUNNING, 10.5, 12.3);

        // when
        String toString = trainingDto.toString();

        // then
        assertThat(toString)
                .contains("id=1")
                .contains("user=" + user)
                .contains("startTime=" + startTime)
                .contains("endTime=" + endTime)
                .contains("activityType=" + ActivityType.RUNNING)
                .contains("distance=10.5")
                .contains("averageSpeed=12.3");
    }
}