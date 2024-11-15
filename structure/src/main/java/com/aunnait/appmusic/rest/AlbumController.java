package com.aunnait.appmusic.rest;

import com.aunnait.appmusic.model.Album;
import com.aunnait.appmusic.model.dto.AlbumDTO;
import com.aunnait.appmusic.model.dto.ArtistDTO;
import com.aunnait.appmusic.service.IAlbumService;
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
@RequestMapping("/api/albums")
public class AlbumController {

    private final IAlbumService albumService;

    @Autowired
    public AlbumController(IAlbumService albumService) {
        this.albumService = albumService;
    }
    @GetMapping
    public ResponseEntity<List<AlbumDTO>> getAllAlbums() {
        List<AlbumDTO> albums = albumService.findAll();
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumDTO> getAlbumById(@PathVariable Integer id) {
        AlbumDTO albumDTO = albumService.findAlbumById(id);
        return ResponseEntity.ok(albumDTO);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AlbumDTO> updateAlbum(@PathVariable Integer id,
                                                @Valid @RequestBody AlbumDTO albumDTO) {
        AlbumDTO updatedAlbum = albumService.updateAlbum(id, albumDTO);
        return ResponseEntity.ok(updatedAlbum);
    }
    @PostMapping("/add")
    public ResponseEntity<AlbumDTO> addAlbum(@Valid @RequestBody AlbumDTO albumDTO) {
        AlbumDTO addedAlbum = albumService.addAlbum(albumDTO);
        return ResponseEntity.ok(addedAlbum);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AlbumDTO> deleteAlbum(@PathVariable Integer id) {
        albumService.deleteAlbum(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Custom filter get
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

    @GetMapping("/all")
    public ResponseEntity<Page<AlbumDTO>> getAllAlbumsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AlbumDTO> albumsPage = albumService.findAllPaginated(pageable);
        return ResponseEntity.ok(albumsPage);
    }

}
