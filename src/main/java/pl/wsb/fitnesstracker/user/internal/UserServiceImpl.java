package pl.wsb.fitnesstracker.user.internal;

import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.user.api.*;
import pl.wsb.fitnesstracker.user.internal.model.User;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of UserService for managing FitnessTracker users.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper = new UserMapper();

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setId(null); // Ensure ID is not set for new user
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }
    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toDto);
    }

    @Override
    public Optional<UserDto> getUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email).map(userMapper::toDto);
    }

    @Override
    public Optional<UserDto> getUserByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username).map(userMapper::toDto);
    }

    @Override
    public List<UserSimpleDto> getAllUsersSimple() {
        return userRepository.findAll().stream()
                .map(userMapper::toSimpleDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserSearchResultDto> searchUsersByEmailFragment(String emailFragment) {
        return userRepository.findAll().stream()
                .filter(u -> u.getEmail() != null && u.getEmail().toLowerCase().contains(emailFragment.toLowerCase()))
                .map(userMapper::toSearchResultDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserSearchResultDto> searchUsersByNameFragment(String nameFragment) {
        List<User> users = userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(nameFragment, nameFragment);
        return users.stream()
                .map(userMapper::toSearchResultDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserSearchResultDto> searchUsersOlderThan(int age) {
        LocalDate now = LocalDate.now();
        return userRepository.findAll().stream()
                .filter(u -> u.getBirthdate() != null && Period.between(u.getBirthdate(), now).getYears() > age)
                .map(userMapper::toSearchResultDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setBirthdate(userDto.birthdate());
        user.setEmail(userDto.email());
        if (userDto.username() != null && !userDto.username().isBlank()) {
            user.setUsername(userDto.username());
        }
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}