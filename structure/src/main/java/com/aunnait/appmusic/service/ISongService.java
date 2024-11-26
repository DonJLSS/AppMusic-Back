package com.aunnait.appmusic.service;


import com.aunnait.appmusic.model.dto.SongDTO;
import com.aunnait.appmusic.model.dto.SongResponseDTO;
import com.aunnait.appmusic.utils.GenreOperations;
import com.aunnait.appmusic.utils.SongOperations;
import java.util.List;

public interface ISongService extends SongOperations, GenreOperations {
    List<SongResponseDTO> findAll();
    SongResponseDTO findSongById(Integer id);
    SongDTO updateSong(Integer id, SongDTO songDTO);
    SongDTO addSong(SongDTO songDTO);
    SongResponseDTO partialUpdateSong(Integer id, SongDTO songDTO);
    List<SongResponseDTO> searchSongs(String title, Long duration, Long minDuration, Long maxDuration,
                                      String songUrl, String artistName, String albumName,
                                      String sortBy, boolean isAscending);
    void deleteSong(Integer id);
}
