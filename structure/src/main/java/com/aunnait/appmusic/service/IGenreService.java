package com.aunnait.appmusic.service;


import com.aunnait.appmusic.model.dto.GenreDTO;


import java.util.List;
import java.util.Map;

public interface IGenreService {
    List<GenreDTO> findAll();
    GenreDTO findGenreById(Integer id);
    GenreDTO updateGenre(Integer id, GenreDTO genreDTO);
    GenreDTO addGenre(GenreDTO genreDTO);
    void deleteGenre(Integer id);
    List<GenreDTO> searchGenre(String name, Integer yearOfOrigin, String description,
                               Integer minYear, Integer maxYear,
                               String sortBy, boolean isAscending);
    GenreDTO patchGenre(Integer id, Map<Object, Object> fields);

    List<GenreDTO> findAllGenreByAttributes(String name,Integer yearOfOrigin, String description);

}
