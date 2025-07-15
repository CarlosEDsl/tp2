package com.TP.account_service.services;

import com.TP.account_service.models.User;
import com.TP.account_service.repositories.UserRepository;
import com.TP.account_service.security.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailsImplServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsImplService userDetailsService;

    private User user;
    private String email;

    @BeforeEach
    void setUp() {
        email = "test@example.com";
        user = new User("testUser", email, "password123");
        user.setId(UUID.randomUUID());
    }

    @Test
    void loadUserByUsername_WithValidEmail_ShouldReturnUserDetails() {
        // Arrange
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));

        // Act
        UserDetails result = userDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(result);
        assertInstanceOf(UserDetailsImpl.class, result);
        assertEquals(user.getUsername(), result.getUsername());

        verify(userRepository).findUserByEmail(email);
    }

    @Test
    void loadUserByUsername_WithInvalidEmail_ShouldThrowRuntimeException() {
        // Arrange
        String invalidEmail = "invalid@example.com";
        when(userRepository.findUserByEmail(invalidEmail)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userDetailsService.loadUserByUsername(invalidEmail));

        assertEquals("Email not found for login", exception.getMessage());
        verify(userRepository).findUserByEmail(invalidEmail);
    }

    @Test
    void loadUserByUsername_WithNullEmail_ShouldCallRepository() {
        // Arrange
        when(userRepository.findUserByEmail(null)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> userDetailsService.loadUserByUsername(null));

        verify(userRepository).findUserByEmail(null);
    }

    @Test
    void loadUserByUsername_WithEmptyEmail_ShouldCallRepository() {
        // Arrange
        String emptyEmail = "";
        when(userRepository.findUserByEmail(emptyEmail)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> userDetailsService.loadUserByUsername(emptyEmail));

        verify(userRepository).findUserByEmail(emptyEmail);
    }

    @Test
    void loadUserByUsername_ShouldCreateUserDetailsImplWithCorrectUser() {
        // Arrange
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));

        // Act
        UserDetails result = userDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(result);
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) result;

        // Verificar se UserDetailsImpl foi criado com o usuário correto
        assertEquals(user.getUsername(), userDetailsImpl.getUsername());

        verify(userRepository).findUserByEmail(email);
    }

    @Test
    void loadUserByUsername_ShouldOnlyCallRepositoryOnce() {
        // Arrange
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));

        // Act
        userDetailsService.loadUserByUsername(email);

        // Assert
        verify(userRepository, times(1)).findUserByEmail(email);
        verify(userRepository, never()).findById(any());
        verify(userRepository, never()).save(any());
        verify(userRepository, never()).delete(any());
    }

    @Test
    void loadUserByUsername_WithDifferentEmails_ShouldCallRepositoryWithCorrectEmail() {
        // Arrange
        String email1 = "user1@example.com";
        String email2 = "user2@example.com";
        User user1 = new User("user1", email1, "password1");
        User user2 = new User("user2", email2, "password2");

        when(userRepository.findUserByEmail(email1)).thenReturn(Optional.of(user1));
        when(userRepository.findUserByEmail(email2)).thenReturn(Optional.of(user2));

        // Act
        UserDetails result1 = userDetailsService.loadUserByUsername(email1);
        UserDetails result2 = userDetailsService.loadUserByUsername(email2);

        // Assert
        assertEquals(user1.getUsername(), result1.getUsername());
        assertEquals(user2.getUsername(), result2.getUsername());

        verify(userRepository).findUserByEmail(email1);
        verify(userRepository).findUserByEmail(email2);
    }
}
