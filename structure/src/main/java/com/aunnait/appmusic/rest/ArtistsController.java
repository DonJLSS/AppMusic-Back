package com.aunnait.appmusic.rest;

import com.aunnait.appmusic.model.dto.*;
import com.aunnait.appmusic.model.filters.ArtistSearchRequest;
import com.aunnait.appmusic.service.IArtistService;
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
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping
    public ResponseEntity<List<ArtistDTO>> getAllArtists() {
        List<ArtistDTO> artists = artistService.findAll();
        return new ResponseEntity<>(artists, HttpStatus.OK);
    }

    @Operation(description = "Returns the Artist given its id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404",description = "Not found")})
    @GetMapping("/{id}")
    public ResponseEntity<ArtistDTO> getArtistById(@PathVariable Integer id) {
        ArtistDTO artistDTO = artistService.findArtistById(id);
        return ResponseEntity.ok(artistDTO);
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
                                                 @Valid @RequestBody ArtistRequestDTO artistDTO){
        ArtistDTO artistPatched = artistService.partialUpdateArtist(id, artistDTO);
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
    public ResponseEntity<List<ArtistDTO>> searchGenre(@RequestBody ArtistSearchRequest searchRequest){
        String sortBy = searchRequest.getSortBy() != null ? searchRequest.getSortBy() : "name"; //Default sorting by name
        boolean isAscending = searchRequest.isAscending();

        List<ArtistDTO> artists = artistService.searchArtist(
                searchRequest.getName(),
                searchRequest.getNationality(),
                searchRequest.getDateOfBirth(),
                searchRequest.getAlbumsCount(),
                searchRequest.getMinDate(),
                searchRequest.getMaxDate(),
                searchRequest.getMinAlbumCount(),
                searchRequest.getMaxAlbumCount(),
                sortBy, isAscending
        );
        if (artists != null && !artists.isEmpty())
            return new ResponseEntity<>(artists, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
