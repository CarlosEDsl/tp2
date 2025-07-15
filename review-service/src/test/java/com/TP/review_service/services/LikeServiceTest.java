package com.TP.review_service.services;

import com.TP.review_service.exceptions.custom.BusinessRuleException;
import com.TP.review_service.exceptions.custom.ResourceNotFoundException;
import com.TP.review_service.models.DTO.CreateNotificationDTO;
import com.TP.review_service.models.Like;
import com.TP.review_service.models.Post;
import com.TP.review_service.rabbitmq.NotificationSender;
import com.TP.review_service.repositories.LikeRepository;
import com.TP.review_service.repositories.PostRepository;
import com.TP.review_service.security.AuthValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private NotificationSender notificationSender;

    @InjectMocks
    private LikeService likeService;

    private UUID userId;
    private UUID postId;
    private UUID authorId;
    private Like like;
    private Like.LikeId likeId;
    private Post post;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        postId = UUID.randomUUID();
        authorId = UUID.randomUUID();

        like = new Like();
        like.setUserId(userId);
        like.setPostId(postId);

        likeId = new Like.LikeId(userId, postId);

        post = new Post();
        post.setId(postId);
        post.setAuthorId(authorId);
        post.setTitle("Test Post");
    }

    @Test
    void postLike_ShouldCreateLike_WhenUserHasNotLikedPost() {
        // Arrange
        when(likeRepository.findById(any(Like.LikeId.class))).thenReturn(Optional.empty());
        when(likeRepository.save(like)).thenReturn(like);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // Act
        Like result = likeService.postLike(like);

        // Assert
        assertNotNull(result);
        assertEquals(like.getUserId(), result.getUserId());
        assertEquals(like.getPostId(), result.getPostId());

        verify(likeRepository, times(1)).findById(any(Like.LikeId.class));
        verify(likeRepository, times(1)).save(like);
        verify(notificationSender, times(1)).sendNotification(any(CreateNotificationDTO.class));
    }

    @Test
    void postLike_ShouldThrowException_WhenUserAlreadyLikedPost() {
        // Arrange
        when(likeRepository.findById(any(Like.LikeId.class))).thenReturn(Optional.of(like));

        // Act & Assert
        BusinessRuleException exception = assertThrows(BusinessRuleException.class,
                () -> likeService.postLike(like));

        assertEquals("User already liked this post.", exception.getMessage());
        verify(likeRepository, times(1)).findById(any(Like.LikeId.class));
        verify(likeRepository, never()).save(any(Like.class));
        verify(notificationSender, never()).sendNotification(any(CreateNotificationDTO.class));
    }

    @Test
    void postLike_ShouldSendNotification_WhenLikeIsCreated() {
        // Arrange
        when(likeRepository.findById(any(Like.LikeId.class))).thenReturn(Optional.empty());
        when(likeRepository.save(like)).thenReturn(like);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // Act
        likeService.postLike(like);

        // Assert
        verify(notificationSender, times(1)).sendNotification(argThat(notification ->
                notification.receiverId().equals(authorId) &&
                        notification.senderId().equals(userId) &&
                        notification.type().equals("Like") &&
                        notification.redirect().equals("/" + postId)
        ));
    }

    @Test
    void postLike_ShouldThrowException_WhenPostNotFoundForNotification() {
        // Arrange
        when(likeRepository.findById(any(Like.LikeId.class))).thenReturn(Optional.empty());
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> likeService.postLike(like));

        assertEquals("Post not found", exception.getMessage());
        verify(likeRepository, times(1)).findById(any(Like.LikeId.class));
        verify(postRepository, times(1)).findById(postId);
        verify(likeRepository, never()).save(any(Like.class));
    }

    @Test
    void removeLike_ShouldDeleteLike_WhenUserIsAuthorized() {
        // Arrange
        try (MockedStatic<AuthValidator> authValidatorMock = mockStatic(AuthValidator.class)) {
            authValidatorMock.when(() -> AuthValidator.checkIfUserIsAuthorized(userId))
                    .thenAnswer(invocation -> null);

            // Act
            likeService.removeLike(like);

            // Assert
            verify(likeRepository, times(1)).delete(like);
            authValidatorMock.verify(() -> AuthValidator.checkIfUserIsAuthorized(userId));
        }
    }

    @Test
    void removeLike_ShouldThrowException_WhenUserNotAuthorized() {
        // Arrange
        try (MockedStatic<AuthValidator> authValidatorMock = mockStatic(AuthValidator.class)) {
            authValidatorMock.when(() -> AuthValidator.checkIfUserIsAuthorized(userId))
                    .thenThrow(new RuntimeException("User not authorized"));

            // Act & Assert
            assertThrows(RuntimeException.class, () -> likeService.removeLike(like));

            verify(likeRepository, never()).delete(any(Like.class));
        }
    }

    @Test
    void countPostLikes_ShouldReturnLikeCount() {
        // Arrange
        Double expectedCount = 5.0;
        when(likeRepository.countByPostId(postId)).thenReturn(expectedCount);

        // Act
        Double result = likeService.countPostLikes(postId);

        // Assert
        assertEquals(expectedCount, result);
        verify(likeRepository, times(1)).countByPostId(postId);
    }

    @Test
    void countPostLikes_ShouldReturnZero_WhenNoLikes() {
        // Arrange
        when(likeRepository.countByPostId(postId)).thenReturn(0.0);

        // Act
        Double result = likeService.countPostLikes(postId);

        // Assert
        assertEquals(0.0, result);
        verify(likeRepository, times(1)).countByPostId(postId);
    }

    @Test
    void isPostLikedByUser_ShouldReturnTrue_WhenUserLikedPost() {
        // Arrange
        when(likeRepository.findLikeByUserIdAndPostId(userId, postId)).thenReturn(Optional.of(like));

        // Act
        boolean result = likeService.isPostLikedByUser(userId, postId);

        // Assert
        assertTrue(result);
        verify(likeRepository, times(1)).findLikeByUserIdAndPostId(userId, postId);
    }

    @Test
    void isPostLikedByUser_ShouldReturnFalse_WhenUserHasNotLikedPost() {
        // Arrange
        when(likeRepository.findLikeByUserIdAndPostId(userId, postId)).thenReturn(Optional.empty());

        // Act
        boolean result = likeService.isPostLikedByUser(userId, postId);

        // Assert
        assertFalse(result);
        verify(likeRepository, times(1)).findLikeByUserIdAndPostId(userId, postId);
    }
}