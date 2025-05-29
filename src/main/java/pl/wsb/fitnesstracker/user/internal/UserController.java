package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserNotFoundException;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;
    /**
     * Retrieves all users.
     * @return list of all users as UserDto
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }
    /**
     * Retrieves all users in a simplified form.
     * @return list of users as UserSimpleDto
     */
    //
    @GetMapping("/simple")
    public List<UserSimpleDto> getAllSimpleUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::isSimple)
                .toList();
    }
    /**
     * Retrieves a user by ID.
     * @param userId user ID
     * @return user as UserDto
     * @throws UserNotFoundException if the user is not found
     */
    @GetMapping
    @RequestMapping("/{userId}")
    public UserDto getUserById(
            @PathVariable Long userId) {
        return userService.getUser(userId)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

//    @GetMapping
//    @RequestMapping("/email")
//    public List<UserEmailDto> getUserByEmail(@RequestParam String email) {
//        Optional<User> users = userService.getUserByEmail(email);
//
//        if (users.isEmpty()) {
//            throw new EmailNotFoundException("User with email " + email + " not found.");
//        }
//
//        return users.stream()
//                .map(userMapper::isEmail)
//                .toList();
//    }

    /**
     * Retrieves users by email
     *
     * @param email the email address to search for
     * @return list of users as UserEmailDto
     */
    @GetMapping
    @RequestMapping("/email")
    public List<UserEmailDto> getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email)
                .stream()
                .map(userMapper::isEmail)
                .toList();
    }

//    @PostMapping
//    public UserDto addUser(@RequestBody UserDto userDto) throws InterruptedException {
//
//        // TODO: Implement the method to add a new user.
//        //  You can use the @RequestBody annotation to map the request body to the UserDto object.
//
//
//        return null;
//    }

    /**
     * Retrieves users born before a specific date
     *
     * @param date the cutoff birthdate
     * @return list of users as UserDto
     */
    @GetMapping
    @RequestMapping("older/{time}")
    public List<UserDto> getUsersBornBefore(@PathVariable("time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return userService.findAllUsers()
                .stream()
                .filter(user -> user.getBirthdate().isBefore(date))
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Finds all users whose email contains the given fragment
     *
     * @param fragment the email fragment to search for
     * @return list of users as UserEmailDto
     */
    @GetMapping("/search/email")
    public List<UserEmailDto> findAllUsersByEmailFragment(@RequestParam String fragment) {
        return userService.findUsersByEmailFragment(fragment)
                .stream()
                .map(userMapper::isEmail)
                .toList();
    }

    /**
     * Finds all users whose name contains the given fragment
     * @param fragment the name fragment to search for
     * @return list of users as UserEmailDto
     */
    @GetMapping("/search/name")
    public List<UserEmailDto> findAllUsersByNameFragment(@RequestParam String fragment) {
        return userService.findUsersByNameFragment(fragment)
                .stream()
                .map(userMapper::isEmail)
                .toList();
    }

    /**
     * Create a new user
     *
     * @param userDto the user data to create
     * @return the created user as UserDto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User createdUser = userService.createUser(user);
        return userMapper.toDto(createdUser);
    }

    /**
     * Delete a user by ID
     * @param userId the ID of the user to delete
     * @return HTTP 204 No Content if successful
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        // Znalezienie użytkownika po ID
        User user = userService.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));

        // Usuwanie użytkownika
        userService.deleteUser(user);

        // Zwraca odpowiedź z kodem statusu 204 (No Content)
        return ResponseEntity.noContent().build();
    }

    /**
     * Update an existing user.
            * @param userId the ID of the user to update
     * @param userDto the new user data
     * @return the updated user as UserDto
     * @throws UserNotFoundException if the user is not found
     */
    @PutMapping("/{userId}")
    public UserDto updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        User existingUser = userService.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        existingUser.setFirstName(userDto.firstName());
        existingUser.setLastName(userDto.lastName());
        existingUser.setBirthdate(userDto.birthdate());
        existingUser.setEmail(userDto.email());

        User updatedUser = userService.updateUser(existingUser);

        return userMapper.toDto(updatedUser);
    }
}