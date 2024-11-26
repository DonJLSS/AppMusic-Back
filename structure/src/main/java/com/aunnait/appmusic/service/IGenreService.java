package com.aunnait.appmusic.service;


import com.aunnait.appmusic.model.dto.GenreDTO;
import com.aunnait.appmusic.model.filters.GenreSearchRequest;


import java.util.List;

public interface IGenreService {
    List<GenreDTO> findAll();
    GenreDTO findGenreById(Integer id);
    GenreDTO updateGenre(Integer id, GenreDTO genreDTO);
    GenreDTO addGenre(GenreDTO genreDTO);
    GenreDTO partialUpdateGenre(Integer id, GenreDTO genreDTO);
    void deleteGenre(Integer id);
    List<GenreDTO> searchGenre(String name, Integer yearOfOrigin, String description,
                               Integer minYear, Integer maxYear,
                               String sortBy, boolean isAscending);
    List<GenreDTO> findAllGenreByAttributes(String name,Integer yearOfOrigin, String description);

}
