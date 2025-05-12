package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.EmailNotFoundException;
import pl.wsb.fitnesstracker.user.api.UserNotFoundException;

import java.util.List;

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

    @GetMapping
    @RequestMapping("/{Email}")
    public UserDto getUserByEmail(
            @PathVariable String email) {
        return userService.getUserByEmail(email)
                .map(userMapper::toDto)
                .orElseThrow(() -> new EmailNotFoundException("User with email=%s was not found"));
    }


    @PostMapping
    public UserDto addUser(@RequestBody UserDto userDto) throws InterruptedException {

        // TODO: Implement the method to add a new user.
        //  You can use the @RequestBody annotation to map the request body to the UserDto object.


        return null;
    }

}