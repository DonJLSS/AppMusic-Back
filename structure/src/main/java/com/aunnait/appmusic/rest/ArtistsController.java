package com.aunnait.appmusic.rest;

import com.aunnait.appmusic.model.dto.AlbumDTO;
import com.aunnait.appmusic.model.dto.ArtistDTO;
import com.aunnait.appmusic.service.IArtistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @PutMapping("/{artistId}/albums")
    public ResponseEntity<ArtistDTO> addAlbumToArtist(
            @PathVariable Integer artistId,
            @RequestBody AlbumDTO albumDTO) {

        ArtistDTO updatedArtist = artistService.addAlbum(artistId, albumDTO);
        return ResponseEntity.ok(updatedArtist);
    }

    @PutMapping("/{artistId}/albums/remove")
    public ResponseEntity<ArtistDTO> removeAlbumFromArtist(
            @PathVariable Integer artistId,
            @RequestBody AlbumDTO albumDTO) {

        ArtistDTO updatedArtist = artistService.removeAlbum(artistId, albumDTO);
        return ResponseEntity.ok(updatedArtist);
    }

    //Custom filter get
    @GetMapping("/search")
    public ResponseEntity<List<ArtistDTO>> findArtistsByAttributes(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) LocalDate dateOfBirth,
            @RequestParam(required = false) String nationality){
        return new ResponseEntity<>(artistService.
                findAllArtistByAttributes(name,dateOfBirth,nationality), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ArtistDTO>> getAllArtistsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ArtistDTO> artistsPage = artistService.findAllPaginated(pageable);
        return ResponseEntity.ok(artistsPage);
    }



}
