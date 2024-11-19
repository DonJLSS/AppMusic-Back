package com.aunnait.appmusic.service;

import com.aunnait.appmusic.exceptions.EntityNotFoundException;
import com.aunnait.appmusic.model.dto.GenreDTO;
import com.aunnait.appmusic.model.Genre;
import com.aunnait.appmusic.model.mapper.GenreMapper;
import com.aunnait.appmusic.repository.GenreRepository;
import com.aunnait.appmusic.utils.GenreSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService implements IGenreService {

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    GenreMapper genreMapper;

    @Override
    public List<GenreDTO> findAll() {
        List<Genre> genres = genreRepository.findAll();
        return genres.stream()
                .map(genreMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GenreDTO findGenreById(Integer id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genre: "+ id +" not found"));
        return genreMapper.convertToDTO(genre);
    }

    @Override
    public GenreDTO updateGenre(Integer id, GenreDTO genreDTO) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genre: "+ id +" not found"));
        ErrorHandler(genreDTO);
        genre.setName(genreDTO.getName());
        Genre updated = genreRepository.save(genre);
        return genreMapper.convertToDTO(updated);
    }

    @Override
    public GenreDTO addGenre(GenreDTO genreDTO) {
        ErrorHandler(genreDTO);
        if(findAllGenreByAttributes(genreDTO.getName(),null,null).stream()
                .anyMatch(genre -> genre.getName().equals(genreDTO.getName())))
            throw new IllegalArgumentException("Genre: "+genreDTO.getName()+" already exists");
        Genre newGenre = genreMapper.convertToEntity(genreDTO);
        Genre added = genreRepository.save(newGenre);
        return genreMapper.convertToDTO(added);
    }

    @Override
    public void deleteGenre(Integer id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genre: "+ id +" not found"));
        genreRepository.delete(genre);
    }

    @Override
    public List<GenreDTO> findAllGenreByAttributes(String name, Integer yearOfOrigin, String description) {
        Specification<Genre> spec = GenreSpecification.getGenreByAttributes(name, yearOfOrigin, description);
        return genreRepository.findAll(spec).stream()
                .map(g->genreMapper.convertToDTO(g))
                .collect(Collectors.toList());
    }

    @Override
    public Page<GenreDTO> findAllPaginated(Pageable pageable) {
        Page<Genre> genresPage = genreRepository.findAll(pageable);
        return genresPage.map(genreMapper::convertToDTO);
    }

    private void ErrorHandler(GenreDTO genreDTO) {
        if(genreDTO.getName()==null || genreDTO.getName().isEmpty())
            throw new IllegalArgumentException("Genre name cannot be empty");
    }
}
