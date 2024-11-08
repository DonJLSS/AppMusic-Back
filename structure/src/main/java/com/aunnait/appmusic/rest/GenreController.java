package com.aunnait.appmusic.rest;

import com.aunnait.appmusic.model.dto.GenreDTO;
import com.aunnait.appmusic.service.IGenreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private final IGenreService genreService;
    @Autowired
    public GenreController(IGenreService genreService) {
        this.genreService = genreService;
    }
    @GetMapping
    public ResponseEntity<List<GenreDTO>> getAllGenres(){
        List<GenreDTO> genres = genreService.findAll();
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDTO> getGenreById(@PathVariable Integer id){
        GenreDTO genreDTO = genreService.findGenreById(id);
        return ResponseEntity.ok(genreDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenreDTO> updateGenre(@PathVariable Integer id,
                                                @Valid @RequestBody GenreDTO genreDTO){
        GenreDTO genreDTOUpdated = genreService.updateGenre(id, genreDTO);
        return ResponseEntity.ok(genreDTOUpdated);
    }

    @PostMapping("/add")
    public ResponseEntity<GenreDTO> addGenre(@Valid @RequestBody GenreDTO genreDTO){
        GenreDTO genreDTOAdded = genreService.addGenre(genreDTO);
        return ResponseEntity.ok(genreDTOAdded);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenreDTO> deleteGenre(@PathVariable Integer id){
        genreService.deleteGenre(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
