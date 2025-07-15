package com.TP.notifications.services;

import com.TP.notifications.exceptions.Custom.NotificationCreationException;
import com.TP.notifications.models.DTOs.CreateNotificationDTO;
import com.TP.notifications.models.Notification;
import com.TP.notifications.repositories.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    private UUID receiverId;
    private UUID senderId;
    private UUID notificationId;
    private Notification notification;
    private CreateNotificationDTO createNotificationDTO;

    @BeforeEach
    void setUp() {
        receiverId = UUID.randomUUID();
        senderId = UUID.randomUUID();
        notificationId = UUID.randomUUID();

        notification = new Notification(receiverId, senderId, "LIKE", "/post/123");
        notification.setId(notificationId);

        createNotificationDTO = new CreateNotificationDTO(
                receiverId,
                senderId,
                "LIKE",
                "/post/123"
        );
    }

    @Test
    void getNonReadNotificationsToUser_WithValidReceiverId_ShouldReturnUnreadNotifications() {
        // Arrange
        List<Notification> expectedNotifications = Arrays.asList(
                new Notification(receiverId, senderId, "LIKE", "/post/123"),
                new Notification(receiverId, senderId, "COMMENT", "/post/456")
        );

        when(notificationRepository.findByReceiverIdAndIsReadFalseOrderByCreatedAtDesc(receiverId))
                .thenReturn(expectedNotifications);

        // Act
        List<Notification> result = notificationService.getNonReadNotificationsToUser(receiverId);

        // Assert
        assertEquals(expectedNotifications, result);
        assertEquals(2, result.size());
        verify(notificationRepository).findByReceiverIdAndIsReadFalseOrderByCreatedAtDesc(receiverId);
    }

    @Test
    void getNonReadNotificationsToUser_WithNoUnreadNotifications_ShouldReturnEmptyList() {
        // Arrange
        when(notificationRepository.findByReceiverIdAndIsReadFalseOrderByCreatedAtDesc(receiverId))
                .thenReturn(Collections.emptyList());

        // Act
        List<Notification> result = notificationService.getNonReadNotificationsToUser(receiverId);

        // Assert
        assertTrue(result.isEmpty());
        verify(notificationRepository).findByReceiverIdAndIsReadFalseOrderByCreatedAtDesc(receiverId);
    }

    @Test
    void getAllNotificationsToUser_WithValidReceiverId_ShouldReturnAllNotifications() {
        // Arrange
        List<Notification> expectedNotifications = Arrays.asList(
                new Notification(receiverId, senderId, "LIKE", "/post/123"),
                new Notification(receiverId, senderId, "COMMENT", "/post/456"),
                new Notification(receiverId, senderId, "FOLLOW", "/profile/789")
        );

        when(notificationRepository.findByReceiverIdOrderByCreatedAtDesc(receiverId))
                .thenReturn(expectedNotifications);

        // Act
        List<Notification> result = notificationService.getAllNotificationsToUser(receiverId);

        // Assert
        assertEquals(expectedNotifications, result);
        assertEquals(3, result.size());
        verify(notificationRepository).findByReceiverIdOrderByCreatedAtDesc(receiverId);
    }

    @Test
    void getAllNotificationsToUser_WithNoNotifications_ShouldReturnEmptyList() {
        // Arrange
        when(notificationRepository.findByReceiverIdOrderByCreatedAtDesc(receiverId))
                .thenReturn(Collections.emptyList());

        // Act
        List<Notification> result = notificationService.getAllNotificationsToUser(receiverId);

        // Assert
        assertTrue(result.isEmpty());
        verify(notificationRepository).findByReceiverIdOrderByCreatedAtDesc(receiverId);
    }

    @Test
    void createNotification_WithValidDTO_ShouldCreateNotification() {
        // Arrange
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        // Act
        assertDoesNotThrow(() -> notificationService.createNotification(createNotificationDTO));

        // Assert
        verify(notificationRepository).save(any(Notification.class));
    }

    @Test
    void createNotification_WithRepositoryException_ShouldThrowNotificationCreationException() {
        // Arrange
        when(notificationRepository.save(any(Notification.class)))
                .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        NotificationCreationException exception = assertThrows(NotificationCreationException.class,
                () -> notificationService.createNotification(createNotificationDTO));

        assertEquals("Erro ao criar notificação", exception.getMessage());
        verify(notificationRepository).save(any(Notification.class));
    }

    @Test
    void createNotification_ShouldCreateNotificationWithCorrectData() {
        // Arrange
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        // Act
        notificationService.createNotification(createNotificationDTO);

        // Assert
        verify(notificationRepository).save(argThat(savedNotification ->
                savedNotification.getReceiverId().equals(receiverId) &&
                        savedNotification.getSenderId().equals(senderId) &&
                        savedNotification.getType().equals("LIKE") &&
                        savedNotification.getRedirect().equals("/post/123")
        ));
    }

    @Test
    void markAsRead_WithValidNotificationId_ShouldMarkNotificationAsRead() {
        // Arrange
        notification.setRead(false);
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        // Act
        notificationService.markAsRead(notificationId);

        // Assert
        assertTrue(notification.isRead());
        verify(notificationRepository).findById(notificationId);
        verify(notificationRepository).save(notification);
    }

    @Test
    void markAsRead_WithInvalidNotificationId_ShouldThrowEntityNotFoundException() {
        // Arrange
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> notificationService.markAsRead(notificationId));

        assertEquals("Notificação não encontrada", exception.getMessage());
        verify(notificationRepository).findById(notificationId);
        verify(notificationRepository, never()).save(any(Notification.class));
    }

    @Test
    void markAsRead_ShouldOnlyUpdateReadStatusAndSave() {
        // Arrange
        notification.setRead(false);
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        // Act
        notificationService.markAsRead(notificationId);

        // Assert
        verify(notificationRepository, times(1)).findById(notificationId);
        verify(notificationRepository, times(1)).save(notification);
        assertTrue(notification.isRead());
    }

    @Test
    void createNotification_WithNullDTO_ShouldThrowException() {
        // Act & Assert
        assertThrows(NullPointerException.class,
                () -> notificationService.createNotification(null));

        verify(notificationRepository, never()).save(any(Notification.class));
    }

    @Test
    void getNonReadNotificationsToUser_WithNullReceiverId_ShouldCallRepository() {
        // Arrange
        when(notificationRepository.findByReceiverIdAndIsReadFalseOrderByCreatedAtDesc(null))
                .thenReturn(Collections.emptyList());

        // Act
        List<Notification> result = notificationService.getNonReadNotificationsToUser(null);

        // Assert
        assertTrue(result.isEmpty());
        verify(notificationRepository).findByReceiverIdAndIsReadFalseOrderByCreatedAtDesc(null);
    }

    @Test
    void getAllNotificationsToUser_WithNullReceiverId_ShouldCallRepository() {
        // Arrange
        when(notificationRepository.findByReceiverIdOrderByCreatedAtDesc(null))
                .thenReturn(Collections.emptyList());

        // Act
        List<Notification> result = notificationService.getAllNotificationsToUser(null);

        // Assert
        assertTrue(result.isEmpty());
        verify(notificationRepository).findByReceiverIdOrderByCreatedAtDesc(null);
    }
}