package pl.wsb.fitnesstracker.user.internal;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.EmailNotFoundException;
import pl.wsb.fitnesstracker.user.api.UserNotFoundException;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    //
    @GetMapping("/simple")
    public List<UserSimpleDto> getAllSimpleUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::isSimple)
                .toList();
    }

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

    @GetMapping
    @RequestMapping("/email")
    public List<UserEmailDto> getUserByEmail(@RequestParam String email ) {
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

    @GetMapping
    @RequestMapping("older/{time}")
    public List<UserDto> getUsersBornBefore(@PathVariable("time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return userService.findAllUsers()
                .stream()
                .filter(user -> user.getBirthdate().isBefore(date))
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/search/email")
    public List<UserEmailDto> findAllUsersByEmailFragment(@RequestParam String fragment) {
        return userService.findUsersByEmailFragment(fragment)
                .stream()
                .map(userMapper::isEmail)
                .toList();
    }

    @GetMapping("/search/name")
    public List<UserEmailDto> findAllUsersByNameFragment(@RequestParam String fragment) {
        return userService.findUsersByNameFragment(fragment)
                .stream()
                .map(userMapper::isEmail)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User createdUser = userService.createUser(user);
        return userMapper.toDto(createdUser);
    }

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



}