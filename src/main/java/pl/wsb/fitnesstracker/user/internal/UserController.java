package pl.wsb.fitnesstracker.user.internal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing FitnessTracker users.
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * List all users (full details).
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * List all users (ID and username only).
     */
    @GetMapping("/simple")
    public List<UserSimpleDto> getAllUsersSimple() {
        return userService.getAllUsersSimple();
    }

    /**
     * Get user details by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get user details by email (case-insensitive).
     */
    @GetMapping("/email")
    public ResponseEntity<List<UserDto>> getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email)
                .map(user -> ResponseEntity.ok(List.of(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get user details by username (case-insensitive).
     */
    @GetMapping("/username")
    public ResponseEntity<UserDto> getUserByUsername(@RequestParam String username) {
        return userService.getUserByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Search users by email fragment (case-insensitive, returns ID and email).
     */
    @GetMapping("/search/email")
    public List<UserSearchResultDto> searchUsersByEmailFragment(@RequestParam String emailFragment) {
        return userService.searchUsersByEmailFragment(emailFragment);
    }

    /**
     * Search users by name fragment (case-insensitive, returns ID and email).
     */
    @GetMapping("/search/name")
    public List<UserSearchResultDto> searchUsersByNameFragment(@RequestParam String nameFragment) {
        return userService.searchUsersByNameFragment(nameFragment);
    }

    /**
     * Search users older than a defined age (returns ID and email).
     */
    @GetMapping("/older-than")
    public List<UserSearchResultDto> searchUsersOlderThan(@RequestParam int age) {
        return userService.searchUsersOlderThan(age);
    }

    @GetMapping("/older/{date}")
    public List<UserDto> getUsersOlderThanDate(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return userService.getAllUsers().stream()
                .filter(u -> u.birthdate() != null && u.birthdate().isBefore(date))
                .collect(Collectors.toList());
    }

    /**
     * Create a new user.
     */
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto created = userService.createUser(userDto);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * Update user by ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        UserDto updated = userService.updateUser(id, userDto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete user by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}