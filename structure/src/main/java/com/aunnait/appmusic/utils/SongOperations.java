package com.aunnait.appmusic.utils;


import com.aunnait.appmusic.model.dto.SongDTO;

//This resolves circle dependency
public interface SongOperations {
    SongDTO addSong(SongDTO song);
}
