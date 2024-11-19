package com.aunnait.appmusic.service;


import com.aunnait.appmusic.model.dto.AlbumDTO;
import com.aunnait.appmusic.model.dto.GenreDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IGenreService {
    List<GenreDTO> findAll();
    GenreDTO findGenreById(Integer id);
    GenreDTO updateGenre(Integer id, GenreDTO genreDTO);
    GenreDTO addGenre(GenreDTO genreDTO);
    void deleteGenre(Integer id);
    List<GenreDTO> findAllGenreByAttributes(String name,Integer yearOfOrigin, String description);
    Page<GenreDTO> findAllPaginated(Pageable pageable);

}
