package com.aunnait.appmusic.rest;

import com.aunnait.appmusic.model.Song;
import com.aunnait.appmusic.model.dto.SongDTO;
import com.aunnait.appmusic.model.dto.SongResponseDTO;
import com.aunnait.appmusic.model.filters.SongSearchRequest;
import com.aunnait.appmusic.model.mapper.SongMapper;
import com.aunnait.appmusic.service.ISongService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
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
                                              @Valid @RequestBody SongDTO songDTO){
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
    public ResponseEntity<List<SongResponseDTO>> searchSongs(@RequestBody SongSearchRequest searchRequest) {

        String sortBy = searchRequest.getSortBy() != null ? searchRequest.getSortBy() : "title"; //Default sorting by title
        boolean isAscending = searchRequest.isAscending();

        List<SongResponseDTO> songs = songService.searchSongs(
                searchRequest.getTitle(),
                searchRequest.getDuration(),
                searchRequest.getMinDuration(),
                searchRequest.getMaxDuration(),
                searchRequest.getSongUrl(),
                searchRequest.getArtistName(),
                searchRequest.getAlbumName(),
                sortBy,
                isAscending
        );
        if (songs != null && !songs.isEmpty())
            return new ResponseEntity<>(songs, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
