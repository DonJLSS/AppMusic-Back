package com.aunnait.appmusic.service;

import com.aunnait.appmusic.model.dto.GenreDTO;
import com.aunnait.appmusic.model.dto.SongDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ISongService {
    List<SongDTO> findAll();
    SongDTO findSongById(Integer id);
    SongDTO updateSong(Integer id, SongDTO songDTO);
    SongDTO addSong(SongDTO songDTO);
    void deleteSong(Integer id);
}
