package com.aunnait.appmusic.rest;

import com.aunnait.appmusic.model.dto.GenreDTO;
import com.aunnait.appmusic.service.IGenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jlserrano
 */
@Tag(name = "Genres API", description = "This API serves functionality for genre management")
@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private final IGenreService genreService;
    @Autowired
    public GenreController(IGenreService genreService) {
        this.genreService = genreService;
    }

    @Operation(description = "Returns all Genre bundled into Response")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping
    public ResponseEntity<List<GenreDTO>> getAllGenres(){
        List<GenreDTO> genres = genreService.findAll();
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }

    @Operation(description = "Returns the Genre given its id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404",description = "Not found")})
    @GetMapping("/{id}")
    public ResponseEntity<GenreDTO> getGenreById(@PathVariable Integer id){
        GenreDTO genreDTO = genreService.findGenreById(id);
        return ResponseEntity.ok(genreDTO);
    }

    @Operation(description = "Updates the Genre given its id and DTO")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404",description = "Not found")})
    @PutMapping("/{id}")
    public ResponseEntity<GenreDTO> updateGenre(@PathVariable Integer id,
                                                @Valid @RequestBody GenreDTO genreDTO){
        GenreDTO genreDTOUpdated = genreService.updateGenre(id, genreDTO);
        return ResponseEntity.ok(genreDTOUpdated);
    }

    @Operation(description = "Creates a Genre")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PostMapping()
    public ResponseEntity<GenreDTO> addGenre(@Valid @RequestBody GenreDTO genreDTO){
        GenreDTO genreDTOAdded = genreService.addGenre(genreDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(genreDTOAdded);
    }

    @Operation(description = "Updates any attribute of the Genre given")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404",description = "Not found")})
    @PatchMapping("/{id}")
    public ResponseEntity<GenreDTO> patchGenre(@PathVariable Integer id,
                                               @Valid @RequestBody GenreDTO genreDTO){
        GenreDTO genreDTOUpdated = genreService.partialUpdateGenre(id, genreDTO);
        return ResponseEntity.ok(genreDTOUpdated);
    }

    @Operation(description = "Deletes a Genre given its id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404",description = "Not found")})
    @DeleteMapping("/{id}")
    public ResponseEntity<GenreDTO> deleteGenre(@PathVariable Integer id){
        genreService.deleteGenre(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(description = "Returns all matching Genre bundled into Response")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping("/search")
    public ResponseEntity<List<GenreDTO>> findGenresByAttribute(
            @RequestParam(required = true) String name,
            @RequestParam(required = false) Integer yearOfOrigin,
            @RequestParam(required = false) String description){
        return new ResponseEntity<>(genreService.findAllGenreByAttributes(name, yearOfOrigin, description),
                HttpStatus.OK);
    }

}
