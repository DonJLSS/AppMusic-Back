package com.aunnait.appmusic.model.mapper;

import com.aunnait.appmusic.model.Genre;
import com.aunnait.appmusic.model.dto.GenreDTO;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {
    public GenreDTO convertToDTO(Genre genre) {
        GenreDTO dto = new GenreDTO();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        dto.setYearOfOrigin(genre.getYearOfOrigin());
        dto.setDescription(genre.getDescription());
        return dto;
    }
    public Genre convertToEntity(GenreDTO dto) {
        Genre genre = new Genre();
        genre.setId(dto.getId());
        genre.setName(dto.getName());
        genre.setYearOfOrigin(dto.getYearOfOrigin());
        genre.setDescription(dto.getDescription());
        return genre;
    }
}
