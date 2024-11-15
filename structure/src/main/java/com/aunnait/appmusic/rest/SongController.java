package com.aunnait.appmusic.rest;

import com.aunnait.appmusic.model.dto.AlbumDTO;
import com.aunnait.appmusic.model.dto.SongDTO;
import com.aunnait.appmusic.repository.SongRepository;
import com.aunnait.appmusic.service.ISongService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final ISongService songService;

    @Autowired
    public SongController(ISongService songService) {
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

    @GetMapping("/search")
    public ResponseEntity<List<SongDTO>> findAllSongsByAttributes(
            @RequestParam(required = false) String title,
            @RequestParam(required = false)Duration duration,
            @RequestParam(required = false) String songUrl){
        return new ResponseEntity<>(songService.findAllAlbumByAttributes(title,duration,songUrl), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<SongDTO>> findAllSongsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<SongDTO> songsPage = songService.findAllPaginated(pageable);
        return ResponseEntity.ok(songsPage);
    }

}
