package pl.wsb.fitnesstracker.user.api;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing FitnessTracker users.
 */
public interface UserService {
    UserDto createUser(UserDto userDto);
    Optional<UserDto> getUserById(Long id);
    Optional<UserDto> getUserByEmail(String email);
    Optional<UserDto> getUserByUsername(String username);
    List<UserSimpleDto> getAllUsersSimple();
    List<UserDto> getAllUsers();
    List<UserSearchResultDto> searchUsersByEmailFragment(String emailFragment);
    List<UserSearchResultDto> searchUsersByNameFragment(String nameFragment);
    List<UserSearchResultDto> searchUsersOlderThan(int age);
    UserDto updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);
}