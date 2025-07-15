package com.TP.account_service.services;

import com.TP.account_service.factory.UserFactory;
import com.TP.account_service.models.DTOs.PublicProfileResponseDTO;
import com.TP.account_service.models.DTOs.UserRequestDTO;
import com.TP.account_service.models.DTOs.UserUpdateReqDTO;
import com.TP.account_service.models.User;
import com.TP.account_service.repositories.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserFactory userFactory;

    @InjectMocks
    private UserService userService;

    private User user;
    private UUID userId;
    private UserRequestDTO userRequestDTO;
    private UserUpdateReqDTO userUpdateReqDTO;
    private GoogleIdToken.Payload googlePayload;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        user = new User("testUser", "test@example.com", "password123");
        user.setId(userId);

        userRequestDTO = new UserRequestDTO("testUser", "test@example.com", "password123", "avatar.jpg", "bio");
        userUpdateReqDTO = new UserUpdateReqDTO("updatedUser", "updated@example.com", "hashedPassword", "avatarUrl", "bio",
                java.time.Instant.now(), java.time.Instant.now());

        googlePayload = mock(GoogleIdToken.Payload.class);
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        // Arrange
        List<User> expectedUsers = List.of(user);
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertEquals(expectedUsers, result);
        verify(userRepository).findAll();
    }

    @Test
    void createUser_WithValidData_ShouldCreateUser() {
        // Arrange
        when(userFactory.fromRequest(userRequestDTO)).thenReturn(user);
        when(userRepository.findUserByEmail(userRequestDTO.email())).thenReturn(Optional.empty());

        // Act
        userService.createUser(userRequestDTO);

        // Assert
        verify(userFactory).fromRequest(userRequestDTO);
        verify(userRepository).findUserByEmail(userRequestDTO.email());
        verify(userRepository).save(user);
    }

    @Test
    void createUser_WithDuplicateEmail_ShouldThrowException() {
        // Arrange
        when(userFactory.fromRequest(userRequestDTO)).thenReturn(user);
        when(userRepository.findUserByEmail(userRequestDTO.email())).thenReturn(Optional.of(user));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.createUser(userRequestDTO));

        assertEquals("Este email já está cadastrado", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void findUser_WithValidId_ShouldReturnUser() {
        // Arrange
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        User result = userService.findUser(userId);

        // Assert
        assertEquals(user, result);
        verify(userRepository).findById(userId);
    }

    @Test
    void findUser_WithInvalidId_ShouldThrowException() {
        // Arrange
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.findUser(userId));

        assertEquals("Usuário não encontrado no ID específicado", exception.getMessage());
        verify(userRepository).findById(userId);
    }

    @Test
    void findUserByEmail_WithValidEmail_ShouldReturnUser() {
        // Arrange
        String email = "test@example.com";
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));

        // Act
        User result = userService.findUserByEmail(email);

        // Assert
        assertEquals(user, result);
        verify(userRepository).findUserByEmail(email);
    }

    @Test
    void findUserByEmail_WithInvalidEmail_ShouldThrowException() {
        // Arrange
        String email = "invalid@example.com";
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.findUserByEmail(email));

        assertEquals("Email não encontrado entre os cadastros", exception.getMessage());
        verify(userRepository).findUserByEmail(email);
    }

    @Test
    void updateUser_WithSameEmail_ShouldNotCheckEmailUniqueness() {
        // Arrange
        User updatedUser = new User("updatedUser", "test@example.com", "password123");
        UserUpdateReqDTO sameEmailUpdate = new UserUpdateReqDTO("updatedUser", "test@example.com", "hashedPassword", "avatarUrl", "bio",
                java.time.Instant.now(), java.time.Instant.now());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userFactory.fromRequestUpdate(user, sameEmailUpdate)).thenReturn(updatedUser);

        // Act
        User result = userService.updateUser(userId, sameEmailUpdate);

        // Assert
        assertEquals(updatedUser, result);
        verify(userRepository).findById(userId);
        verify(userFactory).fromRequestUpdate(user, sameEmailUpdate);
        verify(userRepository).save(updatedUser);
        verify(userRepository, never()).findUserByEmail(anyString());
    }

    @Test
    void updateUser_WithNullEmail_ShouldNotCheckEmailUniqueness() {
        // Arrange
        User updatedUser = new User("updatedUser", "test@example.com", "password123");
        UserUpdateReqDTO nullEmailUpdate = new UserUpdateReqDTO("updatedUser", null, "hashedPassword", "avatarUrl", "bio",
                java.time.Instant.now(), java.time.Instant.now());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userFactory.fromRequestUpdate(user, nullEmailUpdate)).thenReturn(updatedUser);

        // Act
        User result = userService.updateUser(userId, nullEmailUpdate);

        // Assert
        assertEquals(updatedUser, result);
        verify(userRepository).findById(userId);
        verify(userFactory).fromRequestUpdate(user, nullEmailUpdate);
        verify(userRepository).save(updatedUser);
        verify(userRepository, never()).findUserByEmail(anyString());
    }

    @Test
    void deleteUser_WithValidId_ShouldDeleteUser() {
        // Arrange
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository).findById(userId);
        verify(userRepository).delete(user);
    }

    @Test
    void deleteUser_WithInvalidId_ShouldThrowException() {
        // Arrange
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.deleteUser(userId));

        assertEquals("Usuário não encontrado no ID específicado", exception.getMessage());
        verify(userRepository).findById(userId);
        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    void getOrSaveGoogleAccount_WithExistingUser_ShouldReturnExistingUser() {
        // Arrange
        String email = "google@example.com";
        when(googlePayload.getEmail()).thenReturn(email);
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getOrSaveGoogleAccount(googlePayload);

        // Assert
        assertEquals(user, result);
        verify(userRepository).findUserByEmail(email);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getOrSaveGoogleAccount_WithNewUser_ShouldCreateAndSaveNewUser() {
        // Arrange
        String email = "google@example.com";
        String name = "Google User";
        User newUser = new User(name, email, "MasterPassword");

        when(googlePayload.getEmail()).thenReturn(email);
        when(googlePayload.get("name")).thenReturn(name);
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        // Act
        User result = userService.getOrSaveGoogleAccount(googlePayload);

        // Assert
        assertEquals(newUser, result);
        verify(userRepository).findUserByEmail(email);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void getPublicProfileById_WithValidId_ShouldReturnPublicProfile() {
        // Arrange
        user.setAvatarUrl("avatar.jpg");
        user.setBio("Test bio");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        PublicProfileResponseDTO result = userService.getPublicProfileById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getAvatarUrl(), result.getAvatarUrl());
        assertEquals(user.getBio(), result.getBio());
        verify(userRepository).findById(userId);
    }

    @Test
    void getPublicProfileById_WithInvalidId_ShouldThrowException() {
        // Arrange
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.getPublicProfileById(userId));

        assertEquals("Usuário não encontrado no ID específicado", exception.getMessage());
        verify(userRepository).findById(userId);
    }
}
