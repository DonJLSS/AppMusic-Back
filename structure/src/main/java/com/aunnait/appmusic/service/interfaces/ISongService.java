package com.aunnait.appmusic.service.interfaces;


import com.aunnait.appmusic.model.dto.SongDTO;
import com.aunnait.appmusic.model.dto.SongResponseDTO;
import com.aunnait.appmusic.model.filters.DynamicSearchRequest;
import com.aunnait.appmusic.utils.GenreOperations;
import com.aunnait.appmusic.utils.SongOperations;

import java.util.List;
import java.util.Map;

public interface ISongService extends SongOperations, GenreOperations {
    List<SongResponseDTO> findAll();
    SongResponseDTO findSongById(Integer id);
    SongDTO updateSong(Integer id, SongDTO songDTO);
    SongDTO addSong(SongDTO songDTO);
    List<SongResponseDTO> searchSongs(DynamicSearchRequest searchRequest);
    SongResponseDTO patchSong(Integer id, Map<Object, Object> fields);
    void deleteSong(Integer id);
}
