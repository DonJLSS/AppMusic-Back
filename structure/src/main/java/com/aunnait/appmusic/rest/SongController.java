package com.aunnait.appmusic.rest;

import com.aunnait.appmusic.model.dto.SongDTO;
import com.aunnait.appmusic.model.dto.SongResponseDTO;
import com.aunnait.appmusic.model.dto.createdto.SongCreateDTO;
import com.aunnait.appmusic.model.mapper.SongMapper;
import com.aunnait.appmusic.service.interfaces.ISongService;
import com.aunnait.appmusic.model.filters.DynamicSearchRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * @author jlserrano
 */
@Tag(name = "Songs API", description = "This API serves functionality for song management")
@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final ISongService songService;
    private final SongMapper songMapper;

    @Autowired
    public SongController(ISongService songService, SongMapper songMapper) {
        this.songService = songService;
        this.songMapper = songMapper;
    }

    @Operation(description = "Returns all Song bundled into Response")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "204",description = "No Content")})
    @GetMapping
    public ResponseEntity<List<SongResponseDTO>> findAll(){
        List<SongResponseDTO> songs = songService.findAll();
        if (songs != null && !songs.isEmpty())
            return new ResponseEntity<>(songs, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(description = "Returns the Song given its id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404",description = "Not found")})
    @GetMapping("/{id}")
    public ResponseEntity<SongResponseDTO> getSongById(@PathVariable Integer id){
        SongResponseDTO songDTO = songService.findSongById(id);
        if (songDTO!=null) return ResponseEntity.ok(songDTO);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(description = "Updates the Song given its id and DTO")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404",description = "Not found")})
    @PutMapping("/{id}")
    public ResponseEntity<SongDTO> updateSong(@PathVariable Integer id,
                                              @RequestBody SongDTO songDTO){
        SongDTO songUpdated = songService.updateSong(id,songDTO);
        return ResponseEntity.ok(songUpdated);
    }

    @Operation(description = "Creates a Song")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PostMapping()
    public ResponseEntity<SongDTO> addSong(@RequestBody SongDTO songDTO){
        SongDTO song = songService.addSong(songDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(song);
    }

    @Operation(description = "Updates any attribute of the Song given")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404",description = "Not found")})
    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<SongResponseDTO> patchSong(@PathVariable Integer id,
                                             @RequestBody Map<Object,Object> fields) {
        SongResponseDTO updatedSong = songService.patchSong(id,fields);
        return ResponseEntity.ok(updatedSong);

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

    //--------------------------------------------------Dynamic Search Filter-----------------------------------------------------------
    @Operation(description = "Finds songs given criteria and bundles into a List")
    @PostMapping("/search")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "204",description = "No Content")})
    public ResponseEntity<List<SongResponseDTO>> searchSongs(@RequestBody DynamicSearchRequest searchRequest) {

        List<SongResponseDTO> songs = songService.searchSongs(searchRequest);

        if (songs != null && !songs.isEmpty()) {
            return ResponseEntity.ok(songs);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(description = "Finds songs given criteria and bundles into a List using GET")
    @GetMapping("/search")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "204",description = "No Content")})
    public ResponseEntity<Page<SongResponseDTO>> searchSongsQuery(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "duration", required = false) Long duration,
            @RequestParam(name = "artistName", required = false) String artistName,
            @RequestParam(name = "albumName", required = false) String albumName,
            @RequestParam(name = "sortBy", defaultValue = "title") String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = "asc") String sortDirection) {

        Page<SongResponseDTO> result = songService.searchSongsQuery(page, size, title, duration, artistName, albumName, sortBy, sortDirection);

        if (result.hasContent()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(description = "Creates a Song using Cascade method")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404",description = "Not found")})
    @PostMapping("/create")
    public ResponseEntity<SongResponseDTO> createSong(@RequestBody SongCreateDTO songCreateDTO) {
        SongResponseDTO response = songService.createSong(songCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
