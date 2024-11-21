package com.aunnait.appmusic.utils;

import com.aunnait.appmusic.model.dto.AlbumDTO;
import com.aunnait.appmusic.model.dto.AlbumRequestDTO;

//This resolves circle dependency
public interface AlbumOperations {
    AlbumDTO addAlbum(AlbumRequestDTO albumDTO);
}