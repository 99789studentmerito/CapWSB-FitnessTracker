package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserProvider;
import pl.wsb.fitnesstracker.user.api.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


    public List<User> findUsersByEmailFragment(String fragment) {
        return userRepository.findByEmailIgnoreCaseContaining(fragment);
    }

    public List<User> findUsersByNameFragment(String fragment) {
        return userRepository.findByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(fragment, fragment);
    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    // Metoda do usuwania użytkownika
    public void deleteUser(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User does not have an ID. Cannot delete.");
        }
        userRepository.delete(user);
    }
    // Metoda do updatowania
    public User updateUser(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User must have an ID to be updated.");
        }
        return userRepository.save(user);
    }

}