package com.TP.review_service.services;

import com.TP.review_service.models.Comment;
import com.TP.review_service.models.DTO.CreateCommentDTO;
import com.TP.review_service.repositories.CommentRepository;
import com.TP.review_service.security.AuthValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.module.ResolutionException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    private UUID userId;
    private UUID postId;
    private UUID commentId;
    private CreateCommentDTO createCommentDTO;
    private Comment comment;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        postId = UUID.randomUUID();
        commentId = UUID.randomUUID();

        createCommentDTO = new CreateCommentDTO(userId, postId, "Test comment content");

        comment = new Comment();
        comment.setId(commentId);
        comment.setUserId(userId);
        comment.setPostId(postId);
        comment.setContent("Test comment content");
        comment.setCreatedAt(Instant.now());
    }

    @Test
    void postComment_ShouldCreateAndSaveComment() {
        // Arrange
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // Act
        Comment result = commentService.postComment(createCommentDTO);

        // Assert
        assertNotNull(result);
        assertEquals(comment.getId(), result.getId());
        assertEquals(comment.getUserId(), result.getUserId());
        assertEquals(comment.getPostId(), result.getPostId());
        assertEquals(comment.getContent(), result.getContent());

        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void postComment_ShouldCreateCommentWithCorrectData() {
        // Arrange
        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> {
            Comment savedComment = invocation.getArgument(0);
            savedComment.setId(commentId);
            savedComment.setCreatedAt(Instant.now());
            return savedComment;
        });

        // Act
        Comment result = commentService.postComment(createCommentDTO);

        // Assert
        assertEquals(createCommentDTO.userId(), result.getUserId());
        assertEquals(createCommentDTO.postId(), result.getPostId());
        assertEquals(createCommentDTO.content(), result.getContent());
        assertNotNull(result.getId());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    void removeComment_ShouldDeleteComment_WhenCommentExistsAndUserIsAuthorized() {
        // Arrange
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        try (MockedStatic<AuthValidator> authValidatorMock = mockStatic(AuthValidator.class)) {
            authValidatorMock.when(() -> AuthValidator.checkIfUserIsAuthorized(userId))
                    .thenAnswer(invocation -> null);

            // Act
            commentService.removeComment(commentId);

            // Assert
            verify(commentRepository, times(1)).findById(commentId);
            verify(commentRepository, times(1)).delete(comment);
            authValidatorMock.verify(() -> AuthValidator.checkIfUserIsAuthorized(userId));
        }
    }

    @Test
    void removeComment_ShouldThrowException_WhenCommentNotFound() {
        // Arrange
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        // Act & Assert
        ResolutionException exception = assertThrows(ResolutionException.class,
                () -> commentService.removeComment(commentId));

        assertEquals("Comment not found in id: " + commentId, exception.getMessage());
        verify(commentRepository, times(1)).findById(commentId);
        verify(commentRepository, never()).delete(any(Comment.class));
    }

    @Test
    void removeComment_ShouldThrowException_WhenUserNotAuthorized() {
        // Arrange
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        try (MockedStatic<AuthValidator> authValidatorMock = mockStatic(AuthValidator.class)) {
            authValidatorMock.when(() -> AuthValidator.checkIfUserIsAuthorized(userId))
                    .thenThrow(new RuntimeException("User not authorized"));

            // Act & Assert
            assertThrows(RuntimeException.class, () -> commentService.removeComment(commentId));

            verify(commentRepository, times(1)).findById(commentId);
            verify(commentRepository, never()).delete(any(Comment.class));
        }
    }

    @Test
    void getAllCommentsFromPost_ShouldReturnCommentsList() {
        // Arrange
        Comment comment1 = new Comment();
        comment1.setId(UUID.randomUUID());
        comment1.setPostId(postId);
        comment1.setUserId(UUID.randomUUID());
        comment1.setContent("Comment 1");

        Comment comment2 = new Comment();
        comment2.setId(UUID.randomUUID());
        comment2.setPostId(postId);
        comment2.setUserId(UUID.randomUUID());
        comment2.setContent("Comment 2");

        List<Comment> expectedComments = Arrays.asList(comment1, comment2);
        when(commentRepository.findAllByPostId(postId)).thenReturn(expectedComments);

        // Act
        List<Comment> result = commentService.getAllCommentsFromPost(postId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedComments, result);
        verify(commentRepository, times(1)).findAllByPostId(postId);
    }

    @Test
    void getAllCommentsFromPost_ShouldReturnEmptyList_WhenNoComments() {
        // Arrange
        when(commentRepository.findAllByPostId(postId)).thenReturn(Arrays.asList());

        // Act
        List<Comment> result = commentService.getAllCommentsFromPost(postId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(commentRepository, times(1)).findAllByPostId(postId);
    }
}