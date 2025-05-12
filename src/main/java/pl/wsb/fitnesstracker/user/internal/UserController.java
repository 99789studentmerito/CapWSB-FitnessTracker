package pl.wsb.fitnesstracker.user.internal;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.EmailNotFoundException;
import pl.wsb.fitnesstracker.user.api.UserNotFoundException;
import pl.wsb.fitnesstracker.user.api.User;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)public UserDto createUser(@RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User createdUser = userService.createUser(user);
        return userMapper.toDto(createdUser);}

}