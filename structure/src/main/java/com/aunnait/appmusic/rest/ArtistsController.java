package com.aunnait.appmusic.rest;

import com.aunnait.appmusic.model.dto.ArtistDTO;
import com.aunnait.appmusic.service.IArtistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class ArtistsController {

    private final IArtistService artistService;

    @Autowired
    public ArtistsController(IArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public ResponseEntity<List<ArtistDTO>> getAllArtists() {
        List<ArtistDTO> artists = artistService.findAll();
        return new ResponseEntity<>(artists, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDTO> getArtistById(@PathVariable Integer id) {
        ArtistDTO artistDTO = artistService.findArtistById(id);
        return ResponseEntity.ok(artistDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistDTO> updateArtist(@PathVariable Integer id,
                                                  @Valid @RequestBody ArtistDTO artistDTO) {
        ArtistDTO updatedArtistDTO = artistService.updateArtist(id, artistDTO);
        return ResponseEntity.ok(updatedArtistDTO);
    }

    @PostMapping("/add")
    public ResponseEntity<ArtistDTO> addArtist(@Valid @RequestBody ArtistDTO artistDTO) {
        ArtistDTO addedArtistDTO = artistService.addArtist(artistDTO);
        return ResponseEntity.ok(addedArtistDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ArtistDTO> deleteArtist(@PathVariable Integer id) {
        artistService.deleteArtist(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
