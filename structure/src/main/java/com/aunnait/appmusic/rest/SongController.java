package com.aunnait.appmusic.rest;

import com.aunnait.appmusic.model.dto.SongDTO;
import com.aunnait.appmusic.model.dto.SongResponseDTO;
import com.aunnait.appmusic.service.ISongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Duration;
import java.util.List;

/**
 * @author jlserrano
 */
@Tag(name = "Songs API", description = "This API serves functionality for song management")
@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final ISongService songService;

    @Autowired
    public SongController(ISongService songService) {
        this.songService = songService;
    }

    @Operation(description = "Returns all Song bundled into Response")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping
    public ResponseEntity<List<SongResponseDTO>> findAll(){
        List<SongResponseDTO> songs = songService.findAll();
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }

    @Operation(description = "Returns the Song given its id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404",description = "Not found")})
    @GetMapping("/{id}")
    public ResponseEntity<SongResponseDTO> getSongById(@PathVariable Integer id){
        SongResponseDTO songDTO = songService.findSongById(id);
        return ResponseEntity.ok(songDTO);
    }

    @Operation(description = "Updates the Song given its id and DTO")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404",description = "Not found")})
    @PutMapping("/{id}")
    public ResponseEntity<SongDTO> updateSong(@PathVariable Integer id,
                                              @Valid @RequestBody SongDTO songDTO){
        SongDTO songUpdated = songService.updateSong(id,songDTO);
        return ResponseEntity.ok(songUpdated);
    }

    @Operation(description = "Creates a Song")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PostMapping("/add")
    public ResponseEntity<SongDTO> addSong(@RequestBody SongDTO songDTO){
        SongDTO song = songService.addSong(songDTO);
        return ResponseEntity.ok(song);
    }

    @Operation(description = "Deletes a Song given its id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404",description = "Not found")})
    @DeleteMapping("/{id}")
    public ResponseEntity<SongDTO> deleteSong(@PathVariable Integer id){
        songService.deleteSong(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(description = "Returns all matching Song bundled into Response")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping("/search")
    public ResponseEntity<List<SongDTO>> findAllSongsByAttributes(
            @RequestParam(required = false) String title,
            @RequestParam(required = false)Duration duration,
            @RequestParam(required = false) String songUrl){
        return new ResponseEntity<>(songService.findAllSongByAttributes(title,duration,songUrl), HttpStatus.OK);
    }

    @Operation(description = "Returns all Song as paginated resources")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping("/all")
    public ResponseEntity<Page<SongDTO>> findAllSongsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<SongDTO> songsPage = songService.findAllPaginated(pageable);
        return ResponseEntity.ok(songsPage);
    }

    //--------------------------------------------------Genre-related operations--------------------------------------------------------
    @Operation(description = "Adds a list of Genre to a Song given its id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404",description = "Not found")})
    @PutMapping("/{songId}/genres")
    public ResponseEntity<SongResponseDTO> addGenresToSong(@PathVariable Integer songId,
                                                   @RequestBody List<String> genres){
        SongResponseDTO updatedSong = songService.addGenresToSong(songId,genres);
        return ResponseEntity.ok(updatedSong);
    }

    @Operation(description = "Removes a list of Genre from a Song given its id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404",description = "Not found")})
    @PutMapping("/{songId}/genres/remove")
    public ResponseEntity<SongResponseDTO> removeSongGenres(@PathVariable Integer songId,
                                                    @RequestBody List<String> genres){
        SongResponseDTO updatedSong = songService.removeGenresFromSong(songId,genres);
        return ResponseEntity.ok(updatedSong);
    }

}
