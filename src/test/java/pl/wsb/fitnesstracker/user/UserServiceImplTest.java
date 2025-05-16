package pl.wsb.fitnesstracker.user;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.internal.UserRepository;
import pl.wsb.fitnesstracker.user.internal.UserServiceImpl;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Test
    void testCreateUser() {
        UserRepository repo = mock(UserRepository.class);
        UserServiceImpl service = new UserServiceImpl(repo);

        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john@example.com");
        when(repo.save(user)).thenReturn(user);

        User created = service.createUser(user);
        assertEquals("John", created.getFirstName());
    }

    @Test
    void testFindById() {
        UserRepository repo = mock(UserRepository.class);
        UserServiceImpl service = new UserServiceImpl(repo);

        User user = new User("Jane", "Smith", LocalDate.of(1985, 5, 20), "jane@example.com");
        when(repo.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> found = service.findById(1L);
        assertTrue(found.isPresent());
        assertEquals("Jane", found.get().getFirstName());
    }
}