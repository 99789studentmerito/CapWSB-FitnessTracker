package pl.wsb.fitnesstracker.user;

import org.junit.jupiter.api.Test;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserSimpleDto;
import pl.wsb.fitnesstracker.user.api.UserSearchResultDto;
import pl.wsb.fitnesstracker.user.internal.UserRepository;
import pl.wsb.fitnesstracker.user.internal.UserServiceImpl;
import pl.wsb.fitnesstracker.user.internal.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Test
    void testCreateUser() {
        UserRepository repo = mock(UserRepository.class);
        UserServiceImpl service = new UserServiceImpl(repo);

        UserDto userDto = new UserDto(null, "John", "Doe", LocalDate.of(1990, 1, 1), "john@example.com", "johndoe");
        User userEntity = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john@example.com", "johndoe");

        when(repo.save(any(User.class))).thenReturn(userEntity);

        UserDto created = service.createUser(userDto);
        assertEquals("John", created.firstName());
        assertEquals("Doe", created.lastName());
        assertEquals("john@example.com", created.email());
        assertEquals("johndoe", created.username());
    }

    @Test
    void testGetUserById() {
        UserRepository repo = mock(UserRepository.class);
        UserServiceImpl service = new UserServiceImpl(repo);

        User userEntity = new User("Jane", "Smith", LocalDate.of(1985, 5, 20), "jane@example.com", "janesmith");
        when(repo.findById(1L)).thenReturn(Optional.of(userEntity));

        Optional<UserDto> found = service.getUserById(1L);
        assertTrue(found.isPresent());
        assertEquals("Jane", found.get().firstName());
        assertEquals("janesmith", found.get().username());
    }

    @Test
    void testGetAllUsersSimple() {
        UserRepository repo = mock(UserRepository.class);
        UserServiceImpl service = new UserServiceImpl(repo);

        User user1 = new User("Alice", "Wonder", LocalDate.of(1992, 3, 10), "alice@example.com", "alicew");
        User user2 = new User("Bob", "Builder", LocalDate.of(1980, 7, 22), "bob@example.com", "bobb");

        when(repo.findAll()).thenReturn(List.of(user1, user2));

        List<UserSimpleDto> users = service.getAllUsersSimple();
        assertEquals(2, users.size());
        assertEquals("alicew", users.get(0).username());
        assertEquals("bobb", users.get(1).username());
    }

    @Test
    void testSearchUsersByEmailFragment() {
        UserRepository repo = mock(UserRepository.class);
        UserServiceImpl service = new UserServiceImpl(repo);

        User user = new User("Charlie", "Day", LocalDate.of(1995, 12, 1), "charlie.day@example.com", "charlied");
        when(repo.findAll()).thenReturn(List.of(user));

        List<UserSearchResultDto> results = service.searchUsersByEmailFragment("day");
        assertEquals(1, results.size());
        assertEquals("charlie.day@example.com", results.get(0).email());
    }
}