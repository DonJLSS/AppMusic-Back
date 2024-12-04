package com.aunnait.appmusic.rest;

import com.aunnait.appmusic.model.dto.*;
import com.aunnait.appmusic.service.interfaces.IArtistService;
import com.aunnait.appmusic.model.filters.DynamicSearchRequest;
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
@Tag(name = "Artists API", description = "This API serves functionality for artist management")
@RestController
@RequestMapping("/api/artists")
public class ArtistsController {

    private final IArtistService artistService;

    @Autowired
    public ArtistsController(IArtistService artistService) {
        this.artistService = artistService;
    }

    @Operation(description = "Returns all Artist bundled into Response")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "204",description = "No Content")})
    @GetMapping
    public ResponseEntity<List<ArtistDTO>> getAllArtists() {
        List<ArtistDTO> artists = artistService.findAll();
        if (artists != null && !artists.isEmpty())
            return new ResponseEntity<>(artists, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(description = "Returns the Artist given its id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404",description = "Not found")})
    @GetMapping("/{id}")
    public ResponseEntity<ArtistDTO> getArtistById(@PathVariable Integer id) {
        ArtistDTO artistDTO = artistService.findArtistById(id);
        if (artistDTO != null)
            return ResponseEntity.ok(artistDTO);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(description = "Updates the Artist given its id and DTO")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404",description = "Not found")})
    @PutMapping("/{id}")
    public ResponseEntity<ArtistDTO> updateArtist(@PathVariable Integer id,
                                                  @Valid @RequestBody ArtistRequestDTO artistDTO) {
        ArtistDTO updatedArtistDTO = artistService.updateArtist(id, artistDTO);
        return ResponseEntity.ok(updatedArtistDTO);
    }

    @Operation(description = "Creates an Artist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Artist created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping()
    public ResponseEntity<ArtistDTO> addArtist(@Valid @RequestBody ArtistRequestDTO artistDTO) {
        ArtistDTO addedArtistDTO = artistService.addArtist(artistDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedArtistDTO);
    }

    @Operation(description = "Updates any attribute of the Artist given")
    @PatchMapping("/{id}")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404",description = "Not found")})
    public ResponseEntity<ArtistDTO> patchArtist(@PathVariable Integer id,
                                                 @RequestBody Map<Object,Object> fields){
        ArtistDTO artistPatched = artistService.patchArtist(id,fields);
        return ResponseEntity.ok(artistPatched);
    }

    @Operation(description = "Deletes an Artist given its id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404",description = "Not found")})
    @DeleteMapping("/{id}")
    public ResponseEntity<ArtistDTO> deleteArtist(@PathVariable Integer id) {
        artistService.deleteArtist(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(description = "Adds an Album to Artist collection")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404",description = "Not found")})
    @PutMapping("/{artistId}/albums")
    public ResponseEntity<ArtistDTO> addAlbumToArtist(
            @PathVariable Integer artistId,
            @RequestBody AlbumRequestDTO albumDTO) {

        ArtistDTO updatedArtist = artistService.addAlbum(artistId, albumDTO);
        return ResponseEntity.ok(updatedArtist);
    }

    @Operation(description = "Removes an Album from Artist collection")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404",description = "Not found")})
    @PutMapping("/{artistId}/albums/remove")
    public ResponseEntity<ArtistDTO> removeAlbumFromArtist(
            @PathVariable Integer artistId,
            @RequestBody AlbumDTO albumDTO) {

        ArtistDTO updatedArtist = artistService.removeAlbum(artistId, albumDTO);
        return ResponseEntity.ok(updatedArtist);
    }

    @Operation(description = "Finds artists given criteria and bundles into a List")
    @PostMapping("/search")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "204",description = "No Content")})
    public ResponseEntity<List<ArtistDTO>> searchGenre(@RequestBody DynamicSearchRequest searchRequest) {
        List<ArtistDTO> artists = artistService.searchArtist(searchRequest);

        if (artists != null && !artists.isEmpty()) {
            return ResponseEntity.ok(artists);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

}
