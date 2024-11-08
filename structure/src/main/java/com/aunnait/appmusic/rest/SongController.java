package com.aunnait.appmusic.rest;

import com.aunnait.appmusic.model.Song;
import com.aunnait.appmusic.model.dto.SongDTO;
import com.aunnait.appmusic.repository.SongRepository;
import com.aunnait.appmusic.service.ISongService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final ISongService songService;

    @Autowired
    public SongController(ISongService songService, SongRepository songRepository) {
        this.songService = songService;
    }

    @GetMapping
    public ResponseEntity<List<SongDTO>> findAll(){
        List<SongDTO> songs = songService.findAll();
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<SongDTO> getSongById(@PathVariable Integer id){
        SongDTO songDTO = songService.findSongById(id);
        return ResponseEntity.ok(songDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SongDTO> updateSong(@PathVariable Integer id,
                                              @Valid @RequestBody SongDTO songDTO){
        SongDTO songUpdated = songService.updateSong(id,songDTO);
        return ResponseEntity.ok(songUpdated);
    }

    @PostMapping("/add")
    public ResponseEntity<SongDTO> addSong(@RequestBody SongDTO songDTO){
        SongDTO song = songService.addSong(songDTO);
        return ResponseEntity.ok(song);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SongDTO> deleteSong(@PathVariable Integer id){
        songService.deleteSong(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
