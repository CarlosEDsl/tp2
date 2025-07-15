package com.TP.review_service.services;

import com.TP.review_service.builders.PostDirector;
import com.TP.review_service.commands.UpdateAverageCommand;
import com.TP.review_service.commands.dispatcher.CommandDispatcher;
import com.TP.review_service.exceptions.custom.ResourceNotFoundException;
import com.TP.review_service.models.DTO.CreatePostDTO;
import com.TP.review_service.models.DTO.UpdatePostDTO;
import com.TP.review_service.models.Post;
import com.TP.review_service.models.enums.Rate;
import com.TP.review_service.rabbitmq.NotificationSender;
import com.TP.review_service.repositories.PostRepository;
import com.TP.review_service.security.AuthValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private NotificationSender notificationSender;

    @Mock
    private CommandDispatcher commandDispatcher;

    @Mock
    private PostDirector postDirector;

    @InjectMocks
    private PostService postService;

    private UUID postId;
    private UUID authorId;
    private long gameId;
    private Post post;
    private CreatePostDTO createPostDTO;
    private UpdatePostDTO updatePostDTO;

    @BeforeEach
    void setUp() {
        postId = UUID.randomUUID();
        authorId = UUID.randomUUID();
        gameId = 1L;

        post = new Post();
        post.setId(postId);
        post.setAuthorId(authorId);
        post.setGameId(gameId);
        post.setTitle("Test Post");
        post.setContent("Test Content");
        post.setRate(Rate.GOOD);
        post.setCreatedAt(Instant.now());
        post.setUpdatedAt(Instant.now());

        createPostDTO = new CreatePostDTO(
                authorId,
                gameId,
                "Test Post",
                "Test Content",
                "http://example.com/image.jpg",
                4.0
        );

        updatePostDTO = new UpdatePostDTO(
                gameId,
                "Updated Title",
                "Updated Content",
                "http://example.com/updated-image.jpg",
                5L
        );
    }

    @Test
    void getPostById_ShouldReturnPost_WhenPostExists() {
        // Arrange
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // Act
        Post result = postService.getPostById(postId);

        // Assert
        assertNotNull(result);
        assertEquals(postId, result.getId());
        assertEquals(authorId, result.getAuthorId());
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    void getPostById_ShouldThrowException_WhenPostNotFound() {
        // Arrange
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> postService.getPostById(postId));

        assertEquals("Post com ID " + postId + " não encontrado", exception.getMessage());
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    void getPostsFromUser_ShouldReturnUserPosts() {
        // Arrange
        Post post1 = new Post();
        post1.setId(UUID.randomUUID());
        post1.setAuthorId(authorId);

        Post post2 = new Post();
        post2.setId(UUID.randomUUID());
        post2.setAuthorId(authorId);

        List<Post> expectedPosts = Arrays.asList(post1, post2);
        when(postRepository.findAllByAuthorId(authorId)).thenReturn(expectedPosts);

        // Act
        List<Post> result = postService.getPostsFromUser(authorId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedPosts, result);
        verify(postRepository, times(1)).findAllByAuthorId(authorId);
    }

    @Test
    void getNewestPosts_ShouldReturnPagedPosts() {
        // Arrange
        List<Post> posts = Arrays.asList(post);
        Page<Post> postPage = new PageImpl<>(posts);
        when(postRepository.findAll(any(Pageable.class))).thenReturn(postPage);

        // Act
        List<Post> result = postService.getNewestPosts();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(post, result.get(0));
        verify(postRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void createPost_ShouldCreateAndSavePost_WhenUserIsAuthorized() {
        // Arrange
        when(postDirector.constructFromDTO(createPostDTO)).thenReturn(post);
        when(postRepository.save(post)).thenReturn(post);

        try (MockedStatic<AuthValidator> authValidatorMock = mockStatic(AuthValidator.class)) {
            authValidatorMock.when(() -> AuthValidator.checkIfUserIsAuthorized(authorId))
                    .thenAnswer(invocation -> null);

            // Act
            Post result = postService.createPost(createPostDTO);

            // Assert
            assertNotNull(result);
            assertEquals(post.getId(), result.getId());
            assertEquals(post.getAuthorId(), result.getAuthorId());

            verify(postDirector, times(1)).constructFromDTO(createPostDTO);
            verify(postRepository, times(1)).save(post);
            verify(commandDispatcher, times(1)).dispatch(any(UpdateAverageCommand.class));
            authValidatorMock.verify(() -> AuthValidator.checkIfUserIsAuthorized(authorId));
        }
    }

    @Test
    void createPost_ShouldThrowException_WhenUserNotAuthorized() {
        // Arrange
        try (MockedStatic<AuthValidator> authValidatorMock = mockStatic(AuthValidator.class)) {
            authValidatorMock.when(() -> AuthValidator.checkIfUserIsAuthorized(authorId))
                    .thenThrow(new RuntimeException("User not authorized"));

            // Act & Assert
            assertThrows(RuntimeException.class, () -> postService.createPost(createPostDTO));

            verify(postDirector, never()).constructFromDTO(any(CreatePostDTO.class));
            verify(postRepository, never()).save(any(Post.class));
            verify(commandDispatcher, never()).dispatch(any(UpdateAverageCommand.class));
        }
    }

    @Test
    void updatePost_ShouldUpdatePost_WhenUserIsAuthorizedAndPostExists() {
        // Arrange
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        try (MockedStatic<AuthValidator> authValidatorMock = mockStatic(AuthValidator.class)) {
            authValidatorMock.when(() -> AuthValidator.checkIfUserIsAuthorized(authorId))
                    .thenAnswer(invocation -> null);

            // Act
            Post result = postService.updatePost(postId, updatePostDTO);

            // Assert
            assertNotNull(result);
            assertEquals(updatePostDTO.title(), result.getTitle());
            assertEquals(updatePostDTO.content(), result.getContent());
            assertEquals(updatePostDTO.gameId(), result.getGameId());

            verify(postRepository, times(1)).findById(postId);
            verify(postRepository, times(1)).save(any(Post.class));
            verify(commandDispatcher, times(1)).dispatch(any(UpdateAverageCommand.class));
            authValidatorMock.verify(() -> AuthValidator.checkIfUserIsAuthorized(authorId));
        }
    }

    @Test
    void updatePost_ShouldThrowException_WhenPostNotFound() {
        // Arrange
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> postService.updatePost(postId, updatePostDTO));

        assertEquals("Post não encontrado", exception.getMessage());
        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    void updatePost_ShouldThrowException_WhenUserNotAuthorized() {
        // Arrange
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        try (MockedStatic<AuthValidator> authValidatorMock = mockStatic(AuthValidator.class)) {
            authValidatorMock.when(() -> AuthValidator.checkIfUserIsAuthorized(authorId))
                    .thenThrow(new RuntimeException("User not authorized"));

            // Act & Assert
            assertThrows(RuntimeException.class, () -> postService.updatePost(postId, updatePostDTO));

            verify(postRepository, times(1)).findById(postId);
            verify(postRepository, never()).save(any(Post.class));
            verify(commandDispatcher, never()).dispatch(any(UpdateAverageCommand.class));
        }
    }

    @Test
    void updatePost_ShouldUpdateGameAverage_WhenRateChanged() {
        // Arrange
        post.setRate(Rate.AVERAGE); // Rate diferente do updatePostDTO
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        try (MockedStatic<AuthValidator> authValidatorMock = mockStatic(AuthValidator.class)) {
            authValidatorMock.when(() -> AuthValidator.checkIfUserIsAuthorized(authorId))
                    .thenAnswer(invocation -> null);

            // Act
            postService.updatePost(postId, updatePostDTO);

            // Assert
            verify(commandDispatcher, times(1)).dispatch(any(UpdateAverageCommand.class));
        }
    }

    @Test
    void deletePost_ShouldDeletePost_WhenUserIsAuthorizedAndPostExists() {
        // Arrange
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        try (MockedStatic<AuthValidator> authValidatorMock = mockStatic(AuthValidator.class)) {
            authValidatorMock.when(() -> AuthValidator.checkIfUserIsAuthorized(authorId))
                    .thenAnswer(invocation -> null);

            // Act
            postService.deletePost(postId);

            // Assert
            verify(postRepository, times(1)).findById(postId);
            verify(postRepository, times(1)).delete(post);
            verify(commandDispatcher, times(1)).dispatch(any(UpdateAverageCommand.class));
            authValidatorMock.verify(() -> AuthValidator.checkIfUserIsAuthorized(authorId));
        }
    }

    @Test
    void deletePost_ShouldThrowException_WhenPostNotFound() {
        // Arrange
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> postService.deletePost(postId));

        assertEquals("Post com ID " + postId + " não encontrado", exception.getMessage());
        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, never()).delete(any(Post.class));
    }

    @Test
    void deletePost_ShouldThrowException_WhenUserNotAuthorized() {
        // Arrange
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        try (MockedStatic<AuthValidator> authValidatorMock = mockStatic(AuthValidator.class)) {
            authValidatorMock.when(() -> AuthValidator.checkIfUserIsAuthorized(authorId))
                    .thenThrow(new RuntimeException("User not authorized"));

            // Act & Assert
            assertThrows(RuntimeException.class, () -> postService.deletePost(postId));

            verify(postRepository, times(1)).findById(postId);
            verify(postRepository, never()).delete(any(Post.class));
            verify(commandDispatcher, never()).dispatch(any(UpdateAverageCommand.class));
        }
    }

    @Test
    void updateGameAverage_ShouldDispatchUpdateAverageCommand() {
        // Act
        postService.updateGameAverage(postId);

        // Assert
        verify(commandDispatcher, times(1)).dispatch(any(UpdateAverageCommand.class));
    }
}