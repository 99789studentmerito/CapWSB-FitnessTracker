package pl.wsb.fitnesstracker.user.internal;

import pl.wsb.fitnesstracker.user.api.*;
import pl.wsb.fitnesstracker.user.internal.model.User;

/**
 * Mapper for converting between User entity and DTOs.
 */
public class UserMapper {
    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail(),
                user.getUsername()
        );
    }

    public UserSimpleDto toSimpleDto(User user) {
        return new UserSimpleDto(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName());
    }


    public UserSearchResultDto toSearchResultDto(User user) {
        return new UserSearchResultDto(user.getId(), user.getEmail());
    }

    public User toEntity(UserDto dto) {
        String username = dto.username();
        if (username == null || username.isBlank()) {
            username = (dto.firstName() + "." + dto.lastName()).toLowerCase();
        }
        User user = new User(
                dto.firstName(),
                dto.lastName(),
                dto.birthdate(),
                dto.email(),
                username
        );
        user.setId(dto.id());
        return user;
    }
}