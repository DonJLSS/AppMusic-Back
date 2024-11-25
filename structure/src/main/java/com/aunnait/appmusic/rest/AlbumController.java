package com.aunnait.appmusic.rest;

import com.aunnait.appmusic.model.dto.AlbumDTO;
import com.aunnait.appmusic.model.dto.AlbumRequestDTO;
import com.aunnait.appmusic.service.IAlbumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import java.util.List;

/**
 * @author jlserrano
 */
@Tag(name = "Albums API", description = "This API serves functionality for album management")
@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final IAlbumService albumService;
    private final HandlerMapping resourceHandlerMapping;

    @Autowired
    public AlbumController(IAlbumService albumService, @Qualifier("resourceHandlerMapping") HandlerMapping resourceHandlerMapping) {
        this.albumService = albumService;
        this.resourceHandlerMapping = resourceHandlerMapping;
    }

    @Operation(description = "Return all Album bundled into Response")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping
    public ResponseEntity<List<AlbumDTO>> getAllAlbums() {
        List<AlbumDTO> albums = albumService.findAll();
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    @Operation(description = "Returns the Album given its id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "404",description = "Not found")})
    @GetMapping("/{id}")
    public ResponseEntity<AlbumDTO> getAlbumById(@PathVariable Integer id) {
        AlbumDTO albumDTO = albumService.findAlbumById(id);
        return ResponseEntity.ok(albumDTO);
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
        return ResponseEntity.ok(addedAlbum);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AlbumDTO> patchAlbum(@PathVariable Integer id,
                                               @Valid @RequestBody AlbumRequestDTO albumDTO){
        AlbumDTO updatedAlbum = albumService.partialUpdateAlbum(id,albumDTO);
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

    //Custom filter get
    @Operation(description = "Returns all matching Album bundled into Response")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping("/search")
    public ResponseEntity<List<AlbumDTO>> findAlbumsByAttributes(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer launchYear,
            @RequestParam(required = false) Integer songsCount,
            @RequestParam(required = false) String coverUrl,
            @RequestParam(required = false) String artistName){
        return new ResponseEntity<>(albumService.findAllAlbumByAttributes(title, launchYear, songsCount, coverUrl, artistName),
                HttpStatus.OK);
    }


}
