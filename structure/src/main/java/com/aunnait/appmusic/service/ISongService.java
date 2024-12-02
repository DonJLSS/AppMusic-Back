package com.aunnait.appmusic.service;


import com.aunnait.appmusic.model.Song;
import com.aunnait.appmusic.model.dto.SongDTO;
import com.aunnait.appmusic.model.dto.SongResponseDTO;
import com.aunnait.appmusic.utils.GenreOperations;
import com.aunnait.appmusic.utils.SongOperations;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import io.swagger.v3.core.util.Json;

import java.util.List;
import java.util.Map;

public interface ISongService extends SongOperations, GenreOperations {
    List<SongResponseDTO> findAll();
    SongResponseDTO findSongById(Integer id);
    SongDTO updateSong(Integer id, SongDTO songDTO);
    SongDTO addSong(SongDTO songDTO);
    List<SongResponseDTO> searchSongs(String title, Long duration, Long minDuration, Long maxDuration,
                                      String songUrl, String artistName, String albumName,
                                      String sortBy, boolean isAscending);
    SongResponseDTO patchSong(Integer id, Map<Object, Object> fields);
    void deleteSong(Integer id);
}
