package com.TP.game_service;
import com.TP.game_service.models.DTOs.BaseResponseDTO;
import com.TP.game_service.models.Rawg.*;
import com.TP.game_service.services.RawgApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RawgApiClientTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RawgApiClient rawgApiClient;

    private String testUrl;
    private GameExtraInfo gameExtraInfo;
    private BaseResponseDTO<Game> gameResponse;
    private BaseResponseDTO<PlatformComplete> platformResponse;
    private BaseResponseDTO<GenreComplete> genreResponse;
    private BaseResponseDTO<StoreComplete> storeResponse;

    @BeforeEach
    void setUp() {
        testUrl = "https://api.rawg.io/api/games/1";

        gameExtraInfo = new GameExtraInfo();
        // Configurar propriedades do gameExtraInfo se necessário

        gameResponse = new BaseResponseDTO<>();
        gameResponse.setResults(Arrays.asList(new Game()));

        platformResponse = new BaseResponseDTO<>();
        platformResponse.setResults(Arrays.asList(new PlatformComplete()));

        genreResponse = new BaseResponseDTO<>();
        genreResponse.setResults(Arrays.asList(new GenreComplete()));

        storeResponse = new BaseResponseDTO<>();
        storeResponse.setResults(Arrays.asList(new StoreComplete()));
    }

    @Test
    void getGameById_Success() {
        // Arrange
        when(restTemplate.getForObject(testUrl, GameExtraInfo.class)).thenReturn(gameExtraInfo);

        // Act
        GameExtraInfo result = rawgApiClient.getGameById(testUrl);

        // Assert
        assertNotNull(result);
        assertEquals(gameExtraInfo, result);
        verify(restTemplate).getForObject(testUrl, GameExtraInfo.class);
    }

    @Test
    void getGameById_RestTemplateThrowsException_ReturnsNull() {
        // Arrange
        when(restTemplate.getForObject(testUrl, GameExtraInfo.class))
                .thenThrow(new RestClientException("API Error"));

        // Act
        GameExtraInfo result = rawgApiClient.getGameById(testUrl);

        // Assert
        assertNull(result);
        verify(restTemplate).getForObject(testUrl, GameExtraInfo.class);
    }

    @Test
    void getGameById_WithNullUrl_ShouldReturnNull() {
        // Act
        GameExtraInfo result = rawgApiClient.getGameById(null);

        // Assert
        assertNull(result);
        verifyNoInteractions(restTemplate); // Não deve chamar restTemplate com URL null
    }

    @Test
    void searchGames_Success() {
        // Arrange
        ResponseEntity<BaseResponseDTO<Game>> responseEntity = ResponseEntity.ok(gameResponse);
        when(restTemplate.exchange(
                eq(testUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        )).thenReturn(responseEntity);

        // Act
        BaseResponseDTO<Game> result = rawgApiClient.searchGames(testUrl);

        // Assert
        assertNotNull(result);
        assertEquals(gameResponse, result);
        assertNotNull(result.getResults());
        assertFalse(result.getResults().isEmpty());
        verify(restTemplate).exchange(
                eq(testUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        );
    }

    @Test
    void searchGames_RestTemplateThrowsException_ReturnsNull() {
        // Arrange
        when(restTemplate.exchange(
                eq(testUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        )).thenThrow(new RestClientException("API Error"));

        // Act
        BaseResponseDTO<Game> result = rawgApiClient.searchGames(testUrl);

        // Assert
        assertNull(result);
        verify(restTemplate).exchange(
                eq(testUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        );
    }

    @Test
    void searchGames_EmptyResponseBody() {
        // Arrange
        ResponseEntity<BaseResponseDTO<Game>> responseEntity = ResponseEntity.ok(null);
        when(restTemplate.exchange(
                eq(testUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        )).thenReturn(responseEntity);

        // Act
        BaseResponseDTO<Game> result = rawgApiClient.searchGames(testUrl);

        // Assert
        assertNull(result);
        verify(restTemplate).exchange(
                eq(testUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        );
    }

    @Test
    void searchPlatforms_Success() {
        // Arrange
        ResponseEntity<BaseResponseDTO<PlatformComplete>> responseEntity = ResponseEntity.ok(platformResponse);
        when(restTemplate.exchange(
                eq(testUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        )).thenReturn(responseEntity);

        // Act
        BaseResponseDTO<PlatformComplete> result = rawgApiClient.searchPlatforms(testUrl);

        // Assert
        assertNotNull(result);
        assertEquals(platformResponse, result);
        assertNotNull(result.getResults());
        assertFalse(result.getResults().isEmpty());
        verify(restTemplate).exchange(
                eq(testUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        );
    }

    @Test
    void searchPlatforms_RestTemplateThrowsException_ReturnsNull() {
        // Arrange
        when(restTemplate.exchange(
                eq(testUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        )).thenThrow(new RestClientException("API Error"));

        // Act
        BaseResponseDTO<PlatformComplete> result = rawgApiClient.searchPlatforms(testUrl);

        // Assert
        assertNull(result);
        verify(restTemplate).exchange(
                eq(testUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        );
    }

    @Test
    void searchGenres_Success() {
        // Arrange
        ResponseEntity<BaseResponseDTO<GenreComplete>> responseEntity = ResponseEntity.ok(genreResponse);
        when(restTemplate.exchange(
                eq(testUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        )).thenReturn(responseEntity);

        // Act
        BaseResponseDTO<GenreComplete> result = rawgApiClient.searchGenres(testUrl);

        // Assert
        assertNotNull(result);
        assertEquals(genreResponse, result);
        assertNotNull(result.getResults());
        assertFalse(result.getResults().isEmpty());
        verify(restTemplate).exchange(
                eq(testUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        );
    }

    @Test
    void searchGenres_RestTemplateThrowsException_ReturnsNull() {
        // Arrange
        when(restTemplate.exchange(
                eq(testUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        )).thenThrow(new RestClientException("API Error"));

        // Act
        BaseResponseDTO<GenreComplete> result = rawgApiClient.searchGenres(testUrl);

        // Assert
        assertNull(result);
        verify(restTemplate).exchange(
                eq(testUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        );
    }

    @Test
    void searchStores_Success() {
        // Arrange
        ResponseEntity<BaseResponseDTO<StoreComplete>> responseEntity = ResponseEntity.ok(storeResponse);
        when(restTemplate.exchange(
                eq(testUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        )).thenReturn(responseEntity);

        // Act
        BaseResponseDTO<StoreComplete> result = rawgApiClient.searchStores(testUrl);

        // Assert
        assertNotNull(result);
        assertEquals(storeResponse, result);
        assertNotNull(result.getResults());
        assertFalse(result.getResults().isEmpty());
        verify(restTemplate).exchange(
                eq(testUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        );
    }

    @Test
    void searchStores_RestTemplateThrowsException_ReturnsNull() {
        // Arrange
        when(restTemplate.exchange(
                eq(testUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        )).thenThrow(new RestClientException("API Error"));

        // Act
        BaseResponseDTO<StoreComplete> result = rawgApiClient.searchStores(testUrl);

        // Assert
        assertNull(result);
        verify(restTemplate).exchange(
                eq(testUrl),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        );
    }

    @Test
    void searchGames_WithNullUrl_ShouldReturnNull() {
        // Act
        BaseResponseDTO<Game> result = rawgApiClient.searchGames(null);

        // Assert
        assertNull(result);

        verifyNoInteractions(restTemplate);
    }

    @Test
    void searchPlatforms_WithNullUrl_ShouldReturnNull() {
        // Act
        BaseResponseDTO<PlatformComplete> result = rawgApiClient.searchPlatforms(null);

        // Assert
        assertNull(result);

        verifyNoInteractions(restTemplate);
    }

    @Test
    void searchGenres_WithNullUrl_ShouldReturnNull() {
        // Act
        BaseResponseDTO<GenreComplete> result = rawgApiClient.searchGenres(null);

        // Assert
        assertNull(result);

        verifyNoInteractions(restTemplate);
    }

    @Test
    void searchStores_WithNullUrl_ShouldReturnNull() {
        // Act
        BaseResponseDTO<StoreComplete> result = rawgApiClient.searchStores(null);

        // Assert
        assertNull(result);

        verifyNoInteractions(restTemplate);
    }
}
