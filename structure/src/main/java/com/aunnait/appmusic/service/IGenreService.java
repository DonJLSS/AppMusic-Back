package com.aunnait.appmusic.service;


import com.aunnait.appmusic.model.dto.GenreDTO;

import java.util.List;

public interface IGenreService {
    List<GenreDTO> findAll();
    GenreDTO findGenreById(Integer id);
    GenreDTO updateGenre(Integer id, GenreDTO genreDTO);
    GenreDTO addGenre(GenreDTO genreDTO);
    void deleteGenre(Integer id);
}
