package com.aunnait.appmusic.rest;

import com.aunnait.appmusic.model.dto.AlbumDTO;
import com.aunnait.appmusic.service.IAlbumService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
