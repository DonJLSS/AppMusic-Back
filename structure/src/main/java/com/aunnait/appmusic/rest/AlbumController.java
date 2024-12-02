package com.aunnait.appmusic.rest;

import com.aunnait.appmusic.model.dto.AlbumDTO;
import com.aunnait.appmusic.model.dto.AlbumRequestDTO;
import com.aunnait.appmusic.model.filters.AlbumSearchRequest;
import com.aunnait.appmusic.service.IAlbumService;
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
@Tag(name = "Albums API", description = "This API serves functionality for album management")
@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final IAlbumService albumService;

    @Autowired
    public AlbumController(IAlbumService albumService) {
        this.albumService = albumService;
    }

    @Operation(description = "Return all Album bundled into Response")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "204",description = "No Content")})
    @GetMapping
    public ResponseEntity<List<AlbumDTO>> getAllAlbums() {
        List<AlbumDTO> albums = albumService.findAll();
        if (albums != null && !albums.isEmpty())
            return new ResponseEntity<>(albums, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(description = "Returns the Album given its id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404",description = "Not found")})
    @GetMapping("/{id}")
    public ResponseEntity<AlbumDTO> getAlbumById(@PathVariable Integer id) {
        AlbumDTO albumDTO = albumService.findAlbumById(id);
        if (albumDTO != null)
            return new ResponseEntity<>(albumDTO, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(description = "Updates the Album given its id and DTO")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404",description = "Not found")})
    @PutMapping("/{id}")
    public ResponseEntity<AlbumDTO> updateAlbum(@PathVariable Integer id,
                                                @Valid @RequestBody AlbumRequestDTO albumDTO) {
        AlbumDTO updatedAlbum = albumService.updateAlbum(id, albumDTO);
        return ResponseEntity.ok(updatedAlbum);
    }

    @Operation(description = "Creates an Album")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PostMapping()
    public ResponseEntity<AlbumDTO> addAlbum(@Valid @RequestBody AlbumRequestDTO albumRequestDTO) {
        AlbumDTO addedAlbum = albumService.addAlbum(albumRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedAlbum);
    }

    @Operation(description = "Updates any attribute of the Album given")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404",description = "Not found")})
    @PatchMapping("/{id}")
    public ResponseEntity<AlbumDTO> patchAlbum(@PathVariable Integer id,
                                               @RequestBody Map<Object,Object> fields){
        AlbumDTO updatedAlbum = albumService.patchAlbum(id,fields);
        return ResponseEntity.ok(updatedAlbum);
    }

    @Operation(description = "Deletes an Album given its id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404",description = "Not found")})
    @DeleteMapping("/{id}")
    public ResponseEntity<AlbumDTO> deleteAlbum(@PathVariable Integer id) {
        albumService.deleteAlbum(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(description = "Finds albums given criteria and bundles into a List")
    @PostMapping("/search")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "204",description = "No Content")})
    public ResponseEntity<List<AlbumDTO>> searchAlbum(AlbumSearchRequest searchRequest){
        String sortBy = searchRequest.getSortBy() != null ? searchRequest.getSortBy() : "title"; //Default sorting by title
        boolean isAscending = searchRequest.isAscending();

        List<AlbumDTO> albums = albumService.searchAlbum(
                searchRequest.getTitle(),
                searchRequest.getLaunchYear(),
                searchRequest.getSongsCount(),
                searchRequest.getCoverUrl(),
                searchRequest.getArtistName(),
                searchRequest.getMinYear(),
                searchRequest.getMaxYear(),
                searchRequest.getMinSongCount(),
                searchRequest.getMaxSongCount(),
                sortBy, isAscending);
        if (albums != null && !albums.isEmpty())
            return new ResponseEntity<>(albums, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
