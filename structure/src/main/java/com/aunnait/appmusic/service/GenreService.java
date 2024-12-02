package com.aunnait.appmusic.service;

import com.aunnait.appmusic.exceptions.EntityNotFoundException;
import com.aunnait.appmusic.model.dto.GenreDTO;
import com.aunnait.appmusic.model.Genre;
import com.aunnait.appmusic.model.mapper.GenreMapper;
import com.aunnait.appmusic.repository.GenreRepository;
import com.aunnait.appmusic.utils.GenreSpecification;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
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
        Genre genre = EntityErrorHandler(id);
        return genreMapper.convertToDTO(genre);
    }

    @Override
    public GenreDTO updateGenre(Integer id, GenreDTO genreDTO) {
        Genre genre = EntityErrorHandler(id);
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
        Genre genre = EntityErrorHandler(id);
        genreRepository.delete(genre);
    }

    @Override
    public List<GenreDTO> findAllGenreByAttributes(String name, Integer yearOfOrigin, String description) {
        Specification<Genre> spec = GenreSpecification.getGenreByAttributes(name, yearOfOrigin, description, null, null);
        return genreRepository.findAll(spec).stream()
                .map(g->genreMapper.convertToDTO(g))
                .collect(Collectors.toList());
    }

    @Override
    public GenreDTO patchGenre(Integer id, Map<Object, Object> fields){
        Genre genre = EntityErrorHandler(id);
        fields.forEach((key,value)->{
            Field field = ReflectionUtils.findField(Genre.class, (String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field,genre,value);
        });
        Genre updated = genreRepository.save(genre);
        return genreMapper.convertToDTO(updated);
    }


    @Override
    public List<GenreDTO> searchGenre(String name, Integer yearOfOrigin, String description,
                                      Integer minYear, Integer maxYear,
                                      String sortBy, boolean isAscending){
        Specification<Genre> spec = GenreSpecification.getGenreByAttributes(
                name,yearOfOrigin,description,minYear,maxYear);
        Sort sort = isAscending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        return genreRepository.findAll(spec, sort).stream()
                .map(genreMapper::convertToDTO)
                .toList();
    }

    private Genre EntityErrorHandler(Integer id){
        return genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genre: "+ id +" not found"));
    }

    private void ErrorHandler(GenreDTO genreDTO) {
        if(genreDTO.getName()==null || genreDTO.getName().isEmpty())
            throw new IllegalArgumentException("Genre name cannot be empty");
    }
}
