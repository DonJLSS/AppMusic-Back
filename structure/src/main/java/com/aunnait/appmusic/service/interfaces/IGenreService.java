package com.aunnait.appmusic.service.interfaces;


import com.aunnait.appmusic.model.dto.GenreDTO;
import com.aunnait.appmusic.model.filters.DynamicSearchRequest;


import java.util.List;
import java.util.Map;

public interface IGenreService {
    List<GenreDTO> findAll();
    GenreDTO findGenreById(Integer id);
    GenreDTO updateGenre(Integer id, GenreDTO genreDTO);
    GenreDTO addGenre(GenreDTO genreDTO);
    void deleteGenre(Integer id);
    List<GenreDTO> searchGenre(DynamicSearchRequest searchRequest);
    GenreDTO patchGenre(Integer id, Map<Object, Object> fields);

    List<GenreDTO> findAllGenreByAttributes(String name,Integer yearOfOrigin, String description);

}
