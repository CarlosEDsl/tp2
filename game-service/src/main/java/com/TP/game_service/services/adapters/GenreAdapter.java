package com.TP.game_service.services.adapters;

import com.TP.game_service.models.GenreAdapted;
import com.TP.game_service.models.Rawg.Genre;
import com.TP.game_service.models.Rawg.GenreComplete;
import org.springframework.stereotype.Service;

@Service
public class GenreAdapter {

    public GenreAdapted adapt(Genre genre) {
        return new GenreAdapted(genre.getId(), genre.getName());
    }

    public GenreAdapted adapt(GenreComplete genre) {
        return new GenreAdapted(genre.getId(), genre.getName());
    }
}
