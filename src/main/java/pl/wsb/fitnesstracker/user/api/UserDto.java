package pl.wsb.fitnesstracker.user.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import java.time.LocalDate;

/**
 * Data Transfer Object representing detailed user information.
 * Used for API responses and requests.
 *
 * @param id        the unique identifier of the user (nullable for creation)
 * @param firstName the user's first name
 * @param lastName  the user's last name
 * @param birthdate the user's birthdate in yyyy-MM-dd format
 * @param email     the user's email address
 * @param username  the user's username
 */
public record UserDto(
        @Nullable Long id,
        String firstName,
        String lastName,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
        String email,
        String username
) {}

