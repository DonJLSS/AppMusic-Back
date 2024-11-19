package com.aunnait.appmusic.utils;

import com.aunnait.appmusic.model.dto.SongResponseDTO;

import java.util.List;

public interface GenreOperations {
    SongResponseDTO addGenresToSong(Integer songId, List<String> genresNames);
    SongResponseDTO removeGenresFromSong(Integer songId, List<String> genresNames);
}
