package com.TP.review_service.repositories;

import com.TP.review_service.builders.PostBuilder;
import com.TP.review_service.builders.PostDirector;
import com.TP.review_service.models.DTO.CreatePostDTO;
import com.TP.review_service.models.Post;
import com.TP.review_service.models.enums.Rate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("PostRepository Tests")
class PostRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    private PostDirector postDirector;
    private UUID testAuthorId;
    private UUID anotherAuthorId;
    private long testGameId;
    private long anotherGameId;

    @BeforeEach
    void setUp() {
        postDirector = new PostDirector();
        testAuthorId = UUID.randomUUID();
        anotherAuthorId = UUID.randomUUID();
        testGameId = 1L;
        anotherGameId = 2L;
    }

    @Test
    @DisplayName("Should save and find post by ID")
    void shouldSaveAndFindPostById() {
        // Given
        Post post = createTestPost(testAuthorId, testGameId, "Test Title", Rate.GOOD);

        // When
        Post savedPost = postRepository.save(post);
        Optional<Post> foundPost = postRepository.findById(savedPost.getId());

        // Then
        assertThat(foundPost).isPresent();
        assertThat(foundPost.get().getTitle()).isEqualTo("Test Title");
        assertThat(foundPost.get().getAuthorId()).isEqualTo(testAuthorId);
        assertThat(foundPost.get().getGameId()).isEqualTo(testGameId);
        assertThat(foundPost.get().getRate()).isEqualTo(Rate.GOOD);
    }

    @Test
    @DisplayName("Should return empty when post not found")
    void shouldReturnEmptyWhenPostNotFound() {
        // Given
        UUID nonExistentId = UUID.randomUUID();

        // When
        Optional<Post> foundPost = postRepository.findById(nonExistentId);

        // Then
        assertThat(foundPost).isEmpty();
    }

    @Test
    @DisplayName("Should find all posts by author ID")
    void shouldFindAllPostsByAuthorId() {
        // Given
        Post post1 = createTestPost(testAuthorId, testGameId, "First Post", Rate.GOOD);
        Post post2 = createTestPost(testAuthorId, anotherGameId, "Second Post", Rate.EXCELLENT);
        Post post3 = createTestPost(anotherAuthorId, testGameId, "Other Author Post", Rate.AVERAGE);

        entityManager.persistAndFlush(post1);
        entityManager.persistAndFlush(post2);
        entityManager.persistAndFlush(post3);

        // When
        List<Post> authorPosts = postRepository.findAllByAuthorId(testAuthorId);

        // Then
        assertThat(authorPosts).hasSize(2);
        assertThat(authorPosts).extracting(Post::getTitle)
                .containsExactlyInAnyOrder("First Post", "Second Post");
        assertThat(authorPosts).allMatch(post -> post.getAuthorId().equals(testAuthorId));
    }

    @Test
    @DisplayName("Should return empty list when no posts found for author")
    void shouldReturnEmptyListWhenNoPostsFoundForAuthor() {
        // Given
        UUID nonExistentAuthorId = UUID.randomUUID();

        // When
        List<Post> authorPosts = postRepository.findAllByAuthorId(nonExistentAuthorId);

        // Then
        assertThat(authorPosts).isEmpty();
    }

    @Test
    @DisplayName("Should find ratings by game ID")
    void shouldFindRatingsByGameId() {
        // Given
        Post post1 = createTestPost(testAuthorId, testGameId, "Review 1", Rate.GOOD);
        Post post2 = createTestPost(anotherAuthorId, testGameId, "Review 2", Rate.EXCELLENT);
        Post post3 = createTestPost(testAuthorId, testGameId, "Review 3", Rate.AVERAGE);
        Post post4 = createTestPost(testAuthorId, anotherGameId, "Different Game", Rate.POOR);

        entityManager.persistAndFlush(post1);
        entityManager.persistAndFlush(post2);
        entityManager.persistAndFlush(post3);
        entityManager.persistAndFlush(post4);

        // When
        List<Rate> gameRatings = postRepository.findRatingByGameId(testGameId);

        // Then
        assertThat(gameRatings).hasSize(3);
        assertThat(gameRatings).containsExactlyInAnyOrder(Rate.GOOD, Rate.EXCELLENT, Rate.AVERAGE);
    }

    @Test
    @DisplayName("Should return empty list when no ratings found for game")
    void shouldReturnEmptyListWhenNoRatingsFoundForGame() {
        // Given
        long nonExistentGameId = 999L;

        // When
        List<Rate> gameRatings = postRepository.findRatingByGameId(nonExistentGameId);

        // Then
        assertThat(gameRatings).isEmpty();
    }

    @Test
    @DisplayName("Should handle posts with null ratings")
    void shouldHandlePostsWithNullRatings() {
        // Given
        Post postWithoutRating = createTestPost(testAuthorId, testGameId, "No Rating Post", null);
        Post postWithRating = createTestPost(anotherAuthorId, testGameId, "With Rating Post", Rate.GOOD);

        entityManager.persistAndFlush(postWithoutRating);
        entityManager.persistAndFlush(postWithRating);

        // When
        List<Rate> gameRatings = postRepository.findRatingByGameId(testGameId);

        // Then
        assertThat(gameRatings).hasSize(2);
        assertThat(gameRatings).containsExactlyInAnyOrder(null, Rate.GOOD);
    }

    @Test
    @DisplayName("Should save post with all fields populated")
    void shouldSavePostWithAllFieldsPopulated() {
        // Given
        CreatePostDTO dto = new CreatePostDTO(
                testAuthorId,
                testGameId,
                "Complete Post",
                "This is a complete post with all fields",
                "https://example.com/image.jpg",
                4.5
        );
        Post post = postDirector.constructFromDTO(dto);

        // When
        Post savedPost = postRepository.save(post);

        // Then
        assertThat(savedPost.getId()).isNotNull();
        assertThat(savedPost.getAuthorId()).isEqualTo(testAuthorId);
        assertThat(savedPost.getGameId()).isEqualTo(testGameId);
        assertThat(savedPost.getTitle()).isEqualTo("Complete Post");
        assertThat(savedPost.getContent()).isEqualTo("This is a complete post with all fields");
        assertThat(savedPost.getImageURL()).isEqualTo("https://example.com/image.jpg");
        assertThat(savedPost.getRate()).isEqualTo(Rate.VERY_GOOD);
        assertThat(savedPost.getCreatedAt()).isNotNull();
        assertThat(savedPost.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("Should save post with minimal fields")
    void shouldSavePostWithMinimalFields() {
        // Given
        CreatePostDTO dto = new CreatePostDTO(
                testAuthorId,
                testGameId,
                "Minimal Post",
                null,
                null,
                3.0
        );
        Post post = postDirector.constructFromDTO(dto);

        // When
        Post savedPost = postRepository.save(post);

        // Then
        assertThat(savedPost.getId()).isNotNull();
        assertThat(savedPost.getAuthorId()).isEqualTo(testAuthorId);
        assertThat(savedPost.getGameId()).isEqualTo(testGameId);
        assertThat(savedPost.getTitle()).isEqualTo("Minimal Post");
        assertThat(savedPost.getContent()).isNull();
        assertThat(savedPost.getImageURL()).isNull();
        assertThat(savedPost.getRate()).isEqualTo(Rate.AVERAGE);
    }

    @Test
    @DisplayName("Should update post successfully")
    void shouldUpdatePostSuccessfully() throws InterruptedException {
        // Given
        Post originalPost = createTestPost(testAuthorId, testGameId, "Original Title", Rate.GOOD);
        Post savedPost = entityManager.persistAndFlush(originalPost);

        // When
        savedPost.setTitle("Updated Title");
        savedPost.setContent("Updated content");
        savedPost.setRate(Rate.EXCELLENT);
        Post updatedPost = postRepository.saveAndFlush(savedPost);

        // Then
        assertThat(updatedPost.getTitle()).isEqualTo("Updated Title");
        assertThat(updatedPost.getContent()).isEqualTo("Updated content");
        assertThat(updatedPost.getRate()).isEqualTo(Rate.EXCELLENT);
        assertThat(updatedPost.getUpdatedAt()).isAfter(updatedPost.getCreatedAt());
    }

    @Test
    @DisplayName("Should delete post successfully")
    void shouldDeletePostSuccessfully() {
        // Given
        Post post = createTestPost(testAuthorId, testGameId, "To Delete", Rate.GOOD);
        Post savedPost = entityManager.persistAndFlush(post);
        UUID postId = savedPost.getId();

        // When
        postRepository.deleteById(postId);

        // Then
        Optional<Post> deletedPost = postRepository.findById(postId);
        assertThat(deletedPost).isEmpty();
    }

    @Test
    @DisplayName("Should count posts correctly")
    void shouldCountPostsCorrectly() {
        // Given
        Post post1 = createTestPost(testAuthorId, testGameId, "Post 1", Rate.GOOD);
        Post post2 = createTestPost(anotherAuthorId, testGameId, "Post 2", Rate.EXCELLENT);

        entityManager.persistAndFlush(post1);
        entityManager.persistAndFlush(post2);

        // When
        long count = postRepository.count();

        // Then
        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("Should find all posts")
    void shouldFindAllPosts() {
        // Given
        Post post1 = createTestPost(testAuthorId, testGameId, "Post 1", Rate.GOOD);
        Post post2 = createTestPost(anotherAuthorId, testGameId, "Post 2", Rate.EXCELLENT);

        entityManager.persistAndFlush(post1);
        entityManager.persistAndFlush(post2);

        // When
        List<Post> allPosts = postRepository.findAll();

        // Then
        assertThat(allPosts).hasSize(2);
        assertThat(allPosts).extracting(Post::getTitle)
                .containsExactlyInAnyOrder("Post 1", "Post 2");
    }

    // Helper method to create test posts
    private Post createTestPost(UUID authorId, long gameId, String title, Rate rate) {
        return new PostBuilder()
                .authorId(authorId)
                .gameId(gameId)
                .title(title)
                .content("Test content for " + title)
                .imageURL("https://example.com/test.jpg")
                .rate(rate)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }
}