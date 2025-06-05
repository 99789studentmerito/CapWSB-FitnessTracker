package pl.wsb.fitnesstracker.user.api;

/**
 * Data Transfer Object representing basic user information (ID and username).
 *
 * @param id       the unique identifier of the user
 * @param username the user's username
 */
public record UserSimpleDto(Long id, String username, String firstName, String lastName) {}
