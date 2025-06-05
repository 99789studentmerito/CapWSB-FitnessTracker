package pl.wsb.fitnesstracker.user.api;

/**
 * Data Transfer Object representing a user search result (ID and email).
 *
 * @param id    the unique identifier of the user
 * @param email the user's email address
 */
public record UserSearchResultDto(
        Long id,
        String email
) {}
