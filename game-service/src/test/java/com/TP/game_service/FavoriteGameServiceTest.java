package com.TP.game_service;

import com.TP.game_service.models.FavoriteGame;
import com.TP.game_service.models.GameExtraInfoAdapted;
import com.TP.game_service.repositories.FavoriteGameRepository;
import com.TP.game_service.services.CatalogoService;
import com.TP.game_service.services.FavoriteGameService;
import com.TP.game_service.util.customExceptions.GameAlreadyFavoritedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FavoriteGameServiceTest {
    @Mock
    private FavoriteGameRepository favoriteGameRepository;

    @Mock
    private CatalogoService catalogoService;

    @InjectMocks
    private FavoriteGameService favoriteGameService;

    private UUID userId;
    private Long gameId;
    private FavoriteGame favoriteGame;
    private GameExtraInfoAdapted gameInfo;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        gameId = 1L;
        favoriteGame = new FavoriteGame(gameId, userId);
        gameInfo = new GameExtraInfoAdapted();
    }

    @Test
    void saveNewFavoriteGame_Success() throws Exception {
        // Arrange
        when(catalogoService.getGameById(gameId)).thenReturn(gameInfo);
        when(favoriteGameRepository.findByUserIdAndGameId(userId, gameId)).thenReturn(Optional.empty());
        when(favoriteGameRepository.save(any(FavoriteGame.class))).thenReturn(favoriteGame);

        // Act
        FavoriteGame result = favoriteGameService.saveNewFavoriteGame(gameId, userId);

        // Assert
        assertNotNull(result);
        assertEquals(gameId, result.getGameId());
        assertEquals(userId, result.getUserId());
        verify(catalogoService).getGameById(gameId);
        verify(favoriteGameRepository).findByUserIdAndGameId(userId, gameId);
        verify(favoriteGameRepository).save(any(FavoriteGame.class));
    }

    @Test
    void saveNewFavoriteGame_GameNotFound_ReturnsNull() throws Exception {
        // Arrange
        when(catalogoService.getGameById(gameId)).thenReturn(null);

        // Act
        FavoriteGame result = favoriteGameService.saveNewFavoriteGame(gameId, userId);

        // Assert
        assertNull(result);
        verify(catalogoService).getGameById(gameId);
        verify(favoriteGameRepository, never()).findByUserIdAndGameId(any(), any());
        verify(favoriteGameRepository, never()).save(any());
    }

    @Test
    void saveNewFavoriteGame_GameAlreadyFavorited_ThrowsException() throws Exception {
        // Arrange
        when(catalogoService.getGameById(gameId)).thenReturn(gameInfo);
        when(favoriteGameRepository.findByUserIdAndGameId(userId, gameId)).thenReturn(Optional.of(favoriteGame));

        // Act & Assert
        GameAlreadyFavoritedException exception = assertThrows(
                GameAlreadyFavoritedException.class,
                () -> favoriteGameService.saveNewFavoriteGame(gameId, userId)
        );

        assertEquals("Jogo já está nos seus favoritos", exception.getMessage());
        verify(catalogoService).getGameById(gameId);
        verify(favoriteGameRepository).findByUserIdAndGameId(userId, gameId);
        verify(favoriteGameRepository, never()).save(any());
    }

    @Test
    void saveNewFavoriteGame_CatalogoServiceThrowsException_PropagatesException() throws Exception {
        // Arrange
        RuntimeException catalogoException = new RuntimeException("Erro no catálogo");
        when(catalogoService.getGameById(gameId)).thenThrow(catalogoException);

        // Act & Assert
        Exception exception = assertThrows(
                Exception.class,
                () -> favoriteGameService.saveNewFavoriteGame(gameId, userId)
        );

        assertEquals(catalogoException, exception);
        verify(catalogoService).getGameById(gameId);
        verify(favoriteGameRepository, never()).findByUserIdAndGameId(any(), any());
        verify(favoriteGameRepository, never()).save(any());
    }

    @Test
    void getFavoriteGameByGameIdAndUserId_Found() {
        // Arrange
        when(favoriteGameRepository.findByUserIdAndGameId(userId, gameId)).thenReturn(Optional.of(favoriteGame));

        // Act
        Optional<FavoriteGame> result = favoriteGameService.getFavoriteGameByGameIdAndUserId(gameId, userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(favoriteGame, result.get());
        verify(favoriteGameRepository).findByUserIdAndGameId(userId, gameId);
    }

    @Test
    void getFavoriteGameByGameIdAndUserId_NotFound() {
        // Arrange
        when(favoriteGameRepository.findByUserIdAndGameId(userId, gameId)).thenReturn(Optional.empty());

        // Act
        Optional<FavoriteGame> result = favoriteGameService.getFavoriteGameByGameIdAndUserId(gameId, userId);

        // Assert
        assertFalse(result.isPresent());
        verify(favoriteGameRepository).findByUserIdAndGameId(userId, gameId);
    }

    @Test
    void getAllFavoriteGamesByUserId_ReturnsListOfFavoriteGames() {
        // Arrange
        FavoriteGame favoriteGame1 = new FavoriteGame(1L, userId);
        FavoriteGame favoriteGame2 = new FavoriteGame(2L, userId);
        List<FavoriteGame> favoriteGames = Arrays.asList(favoriteGame1, favoriteGame2);

        when(favoriteGameRepository.findFavoriteGamesByUserId(userId)).thenReturn(favoriteGames);

        // Act
        List<FavoriteGame> result = favoriteGameService.getAllFavoriteGamesByUserId(userId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(favoriteGames, result);
        verify(favoriteGameRepository).findFavoriteGamesByUserId(userId);
    }

    @Test
    void getAllFavoriteGamesByUserId_EmptyList() {
        // Arrange
        when(favoriteGameRepository.findFavoriteGamesByUserId(userId)).thenReturn(Arrays.asList());

        // Act
        List<FavoriteGame> result = favoriteGameService.getAllFavoriteGamesByUserId(userId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(favoriteGameRepository).findFavoriteGamesByUserId(userId);
    }

    @Test
    void deleteFavoriteGame_Success() {
        // Arrange
        when(favoriteGameRepository.findByUserIdAndGameId(userId, gameId)).thenReturn(Optional.of(favoriteGame));

        // Act
        boolean result = favoriteGameService.deleteFavoriteGame(gameId, userId);

        // Assert
        assertTrue(result);
        verify(favoriteGameRepository).findByUserIdAndGameId(userId, gameId);
        verify(favoriteGameRepository).delete(favoriteGame);
    }

    @Test
    void deleteFavoriteGame_NotFound() {
        // Arrange
        when(favoriteGameRepository.findByUserIdAndGameId(userId, gameId)).thenReturn(Optional.empty());

        // Act
        boolean result = favoriteGameService.deleteFavoriteGame(gameId, userId);

        // Assert
        assertFalse(result);
        verify(favoriteGameRepository).findByUserIdAndGameId(userId, gameId);
        verify(favoriteGameRepository, never()).delete(any());
    }
}
