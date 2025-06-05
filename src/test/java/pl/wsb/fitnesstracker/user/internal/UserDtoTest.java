package pl.wsb.fitnesstracker.user.internal;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import pl.wsb.fitnesstracker.user.api.UserDto;

import java.time.LocalDate;


class UserDtoTest {

    @Test
    void shouldCreateUserDtoWithAllFields() {
        LocalDate birthdate = LocalDate.of(1979, 5, 1);
        UserDto dto = new UserDto(1L, "Hanna", "Panna", birthdate, "hanna@example.com", "hannapanna");

        assertEquals(1L, dto.id());
        assertEquals("Hanna", dto.firstName());
        assertEquals("Panna", dto.lastName());
        assertEquals(birthdate, dto.birthdate());
        assertEquals("hanna@example.com", dto.email());
    }

    @Test
    void shouldAllowNullId() {
        UserDto dto = new UserDto(null, "Janek", "Kowalski", LocalDate.of(1922, 2, 5), "janek.kowalski@example.com", "jasiuklasiu");

        assertNull(dto.id());
    }
    @Test
    void shouldTestEquality() {
        UserDto dto1 = new UserDto(2L, "Alice", "Wonder", LocalDate.of(1975, 6, 3), "a.wonder@example.com", "alicjiusiusia");
        UserDto dto2 = new UserDto(2L, "Alice", "Wonder", LocalDate.of(1975, 6, 3), "a.wonder@example.com", "alicjiusiusia\"");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void shouldTestToString() {
        UserDto dto = new UserDto(3L, "John", "Doe", LocalDate.of(1979, 5, 1), "doe@example.com", "JohnyDepp");
        String str = dto.toString();
        assertTrue(str.contains("John"));
        assertTrue(str.contains("Doe"));
        assertTrue(str.contains("doe@example.com"));
    }
}
