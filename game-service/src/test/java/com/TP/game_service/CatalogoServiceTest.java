package com.TP.game_service;

import com.TP.game_service.facade.RawgFacade;
import com.TP.game_service.models.*;
import com.TP.game_service.models.DTOs.BaseResponseDTO;
import com.TP.game_service.models.DTOs.GameSearchRequestDTO;
import com.TP.game_service.models.DTOs.BaseSearchRequestDTO;
import com.TP.game_service.services.CatalogoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatalogoServiceTest {

    @Mock
    private RawgFacade facade;

    @InjectMocks
    private CatalogoService catalogoService;

    private Long gameId;
    private GameExtraInfoAdapted gameExtraInfo;
    private GameSearchRequestDTO gameSearchRequest;
    private BaseSearchRequestDTO baseSearchRequest;
    private BaseResponseDTO<GameAdapted> gameResponse;
    private BaseResponseDTO<PlatformAdapted> platformResponse;
    private BaseResponseDTO<GenreAdapted> genreResponse;
    private BaseResponseDTO<StoreAdapted> storeResponse;

    @BeforeEach
    void setUp() {
        gameId = 1L;
        gameExtraInfo = new GameExtraInfoAdapted();
        gameSearchRequest = new GameSearchRequestDTO();
        baseSearchRequest = new BaseSearchRequestDTO();

        // Configurar responses mock
        gameResponse = new BaseResponseDTO<>();
        gameResponse.setResults(Arrays.asList(new GameAdapted()));

        platformResponse = new BaseResponseDTO<>();
        platformResponse.setResults(Arrays.asList(new PlatformAdapted()));

        genreResponse = new BaseResponseDTO<>();
        genreResponse.setResults(Arrays.asList(new GenreAdapted()));

        storeResponse = new BaseResponseDTO<>();
        storeResponse.setResults(Arrays.asList(new StoreAdapted()));
    }

    @Test
    void getGameById_Success() {
        // Arrange
        when(facade.getGameById(gameId)).thenReturn(gameExtraInfo);

        // Act
        GameExtraInfoAdapted result = catalogoService.getGameById(gameId);

        // Assert
        assertNotNull(result);
        assertEquals(gameExtraInfo, result);
        verify(facade).getGameById(gameId);
    }

    @Test
    void getGameById_FacadeThrowsException_PropagatesException() {
        // Arrange
        RuntimeException facadeException = new RuntimeException("Erro na facade");
        when(facade.getGameById(gameId)).thenThrow(facadeException);

        // Act & Assert
        Exception exception = assertThrows(
                Exception.class,
                () -> catalogoService.getGameById(gameId)
        );

        assertEquals(facadeException, exception);
        verify(facade).getGameById(gameId);
    }

    @Test
    void getGameById_ReturnsNull() {
        // Arrange
        when(facade.getGameById(gameId)).thenReturn(null);

        // Act
        GameExtraInfoAdapted result = catalogoService.getGameById(gameId);

        // Assert
        assertNull(result);
        verify(facade).getGameById(gameId);
    }

    @Test
    void searchGames_Success() {
        // Arrange
        when(facade.searchGames(gameSearchRequest)).thenReturn(gameResponse);

        // Act
        BaseResponseDTO<GameAdapted> result = catalogoService.searchGames(gameSearchRequest);

        // Assert
        assertNotNull(result);
        assertEquals(gameResponse, result);
        assertNotNull(result.getResults());
        assertFalse(result.getResults().isEmpty());
        verify(facade).searchGames(gameSearchRequest);
    }

    @Test
    void searchGames_FacadeThrowsException_PropagatesException() {
        // Arrange
        RuntimeException facadeException = new RuntimeException("Erro na busca de jogos");
        when(facade.searchGames(gameSearchRequest)).thenThrow(facadeException);

        // Act & Assert
        Exception exception = assertThrows(
                Exception.class,
                () -> catalogoService.searchGames(gameSearchRequest)
        );

        assertEquals(facadeException, exception);
        verify(facade).searchGames(gameSearchRequest);
    }

    @Test
    void searchGames_EmptyResults() {
        // Arrange
        BaseResponseDTO<GameAdapted> emptyResponse = new BaseResponseDTO<>();
        emptyResponse.setResults(Arrays.asList());
        when(facade.searchGames(gameSearchRequest)).thenReturn(emptyResponse);

        // Act
        BaseResponseDTO<GameAdapted> result = catalogoService.searchGames(gameSearchRequest);

        // Assert
        assertNotNull(result);
        assertEquals(emptyResponse, result);
        assertNotNull(result.getResults());
        assertTrue(result.getResults().isEmpty());
        verify(facade).searchGames(gameSearchRequest);
    }

    @Test
    void searchPlatforms_Success() {
        // Arrange
        when(facade.searchPlatforms(baseSearchRequest)).thenReturn(platformResponse);

        // Act
        BaseResponseDTO<PlatformAdapted> result = catalogoService.searchPlatforms(baseSearchRequest);

        // Assert
        assertNotNull(result);
        assertEquals(platformResponse, result);
        assertNotNull(result.getResults());
        assertFalse(result.getResults().isEmpty());
        verify(facade).searchPlatforms(baseSearchRequest);
    }

    @Test
    void searchPlatforms_FacadeThrowsException_PropagatesException() {
        // Arrange
        RuntimeException facadeException = new RuntimeException("Erro na busca de plataformas");
        when(facade.searchPlatforms(baseSearchRequest)).thenThrow(facadeException);

        // Act & Assert
        Exception exception = assertThrows(
                Exception.class,
                () -> catalogoService.searchPlatforms(baseSearchRequest)
        );

        assertEquals(facadeException, exception);
        verify(facade).searchPlatforms(baseSearchRequest);
    }

    @Test
    void searchPlatforms_EmptyResults() {
        // Arrange
        BaseResponseDTO<PlatformAdapted> emptyResponse = new BaseResponseDTO<>();
        emptyResponse.setResults(Arrays.asList());
        when(facade.searchPlatforms(baseSearchRequest)).thenReturn(emptyResponse);

        // Act
        BaseResponseDTO<PlatformAdapted> result = catalogoService.searchPlatforms(baseSearchRequest);

        // Assert
        assertNotNull(result);
        assertEquals(emptyResponse, result);
        assertNotNull(result.getResults());
        assertTrue(result.getResults().isEmpty());
        verify(facade).searchPlatforms(baseSearchRequest);
    }

    @Test
    void searchGenres_Success() {
        // Arrange
        when(facade.searchGenres(baseSearchRequest)).thenReturn(genreResponse);

        // Act
        BaseResponseDTO<GenreAdapted> result = catalogoService.searchGenres(baseSearchRequest);

        // Assert
        assertNotNull(result);
        assertEquals(genreResponse, result);
        assertNotNull(result.getResults());
        assertFalse(result.getResults().isEmpty());
        verify(facade).searchGenres(baseSearchRequest);
    }

    @Test
    void searchGenres_FacadeThrowsException_PropagatesException() {
        // Arrange
        RuntimeException facadeException = new RuntimeException("Erro na busca de gêneros");
        when(facade.searchGenres(baseSearchRequest)).thenThrow(facadeException);

        // Act & Assert
        Exception exception = assertThrows(
                Exception.class,
                () -> catalogoService.searchGenres(baseSearchRequest)
        );

        assertEquals(facadeException, exception);
        verify(facade).searchGenres(baseSearchRequest);
    }

    @Test
    void searchGenres_EmptyResults() {
        // Arrange
        BaseResponseDTO<GenreAdapted> emptyResponse = new BaseResponseDTO<>();
        emptyResponse.setResults(Arrays.asList());
        when(facade.searchGenres(baseSearchRequest)).thenReturn(emptyResponse);

        // Act
        BaseResponseDTO<GenreAdapted> result = catalogoService.searchGenres(baseSearchRequest);

        // Assert
        assertNotNull(result);
        assertEquals(emptyResponse, result);
        assertNotNull(result.getResults());
        assertTrue(result.getResults().isEmpty());
        verify(facade).searchGenres(baseSearchRequest);
    }

    @Test
    void searchStores_Success() {
        // Arrange
        when(facade.searchStores(baseSearchRequest)).thenReturn(storeResponse);

        // Act
        BaseResponseDTO<StoreAdapted> result = catalogoService.searchStores(baseSearchRequest);

        // Assert
        assertNotNull(result);
        assertEquals(storeResponse, result);
        assertNotNull(result.getResults());
        assertFalse(result.getResults().isEmpty());
        verify(facade).searchStores(baseSearchRequest);
    }

    @Test
    void searchStores_FacadeThrowsException_PropagatesException() {
        // Arrange
        RuntimeException facadeException = new RuntimeException("Erro na busca de lojas");
        when(facade.searchStores(baseSearchRequest)).thenThrow(facadeException);

        // Act & Assert
        Exception exception = assertThrows(
                Exception.class,
                () -> catalogoService.searchStores(baseSearchRequest)
        );

        assertEquals(facadeException, exception);
        verify(facade).searchStores(baseSearchRequest);
    }

    @Test
    void searchStores_EmptyResults() {
        // Arrange
        BaseResponseDTO<StoreAdapted> emptyResponse = new BaseResponseDTO<>();
        emptyResponse.setResults(Arrays.asList());
        when(facade.searchStores(baseSearchRequest)).thenReturn(emptyResponse);

        // Act
        BaseResponseDTO<StoreAdapted> result = catalogoService.searchStores(baseSearchRequest);

        // Assert
        assertNotNull(result);
        assertEquals(emptyResponse, result);
        assertNotNull(result.getResults());
        assertTrue(result.getResults().isEmpty());
        verify(facade).searchStores(baseSearchRequest);
    }

    @Test
    void searchGames_WithNullRequest() {
        // Arrange
        when(facade.searchGames(null)).thenReturn(gameResponse);

        // Act
        BaseResponseDTO<GameAdapted> result = catalogoService.searchGames(null);

        // Assert
        assertNotNull(result);
        assertEquals(gameResponse, result);
        verify(facade).searchGames(null);
    }

    @Test
    void searchPlatforms_WithNullRequest() {
        // Arrange
        when(facade.searchPlatforms(null)).thenReturn(platformResponse);

        // Act
        BaseResponseDTO<PlatformAdapted> result = catalogoService.searchPlatforms(null);

        // Assert
        assertNotNull(result);
        assertEquals(platformResponse, result);
        verify(facade).searchPlatforms(null);
    }

    @Test
    void searchGenres_WithNullRequest() {
        // Arrange
        when(facade.searchGenres(null)).thenReturn(genreResponse);

        // Act
        BaseResponseDTO<GenreAdapted> result = catalogoService.searchGenres(null);

        // Assert
        assertNotNull(result);
        assertEquals(genreResponse, result);
        verify(facade).searchGenres(null);
    }

    @Test
    void searchStores_WithNullRequest() {
        // Arrange
        when(facade.searchStores(null)).thenReturn(storeResponse);

        // Act
        BaseResponseDTO<StoreAdapted> result = catalogoService.searchStores(null);

        // Assert
        assertNotNull(result);
        assertEquals(storeResponse, result);
        verify(facade).searchStores(null);
    }
}
