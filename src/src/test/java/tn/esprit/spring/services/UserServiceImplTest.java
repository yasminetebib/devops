package tn.esprit.spring.services;




import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tn.esprit.spring.entities.User;
import tn.esprit.spring.repository.UserRepository;

@TestMethodOrder(OrderAnnotation.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    public UserServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    // -------------------------------------------------------------
    @Test
    @Order(1)
    void testRetrieveAllUsers() {

        // GIVEN
        User u1 = new User(1L, "Alice", "Smith", new Date(), null);
        User u2 = new User(2L, "Bob", "Johnson", new Date(), null);

        when(userRepository.findAll()).thenReturn(Arrays.asList(u1, u2));

        // WHEN
        List<User> list = userService.retrieveAllUsers();

        // THEN
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("Alice", list.get(0).getFirstName());
    }

    // -------------------------------------------------------------
    @Test
    @Order(2)
    void testAddUser() {

        // GIVEN
        User u = new User("Beya", "Testouri", new Date(), null);
        User uSaved = new User(1L, "Beya", "Testouri", u.getDateNaissance(), null);

        when(userRepository.save(u)).thenReturn(uSaved);

        // WHEN
        User saved = userService.addUser(u);

        // THEN
        assertNotNull(saved);
        assertEquals(1L, saved.getId());
        assertEquals("Beya", saved.getFirstName());
    }

    // -------------------------------------------------------------
    @Test
    @Order(3)
    void testUpdateUser() {

        User u = new User(1L, "Updated", "User", new Date(), null);

        when(userRepository.save(u)).thenReturn(u);

        User updated = userService.updateUser(u);

        assertNotNull(updated);
        assertEquals("Updated", updated.getFirstName());
    }

    // -------------------------------------------------------------
    @Test
    @Order(4)
    void testRetrieveUser() {

        User u = new User(1L, "Test", "User", new Date(), null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(u));

        User result = userService.retrieveUser("1");

        assertNotNull(result);
        assertEquals("Test", result.getFirstName());
    }

    // -------------------------------------------------------------
    @Test
    @Order(5)
    void testDeleteUser() {

        doNothing().when(userRepository).deleteById(1L);

        assertDoesNotThrow(() -> userService.deleteUser("1"));

        verify(userRepository, times(1)).deleteById(1L);
    }
}