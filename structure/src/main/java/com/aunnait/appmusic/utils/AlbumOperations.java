package com.aunnait.appmusic.utils;

import com.aunnait.appmusic.model.dto.AlbumDTO;

//This resolves circle dependency
public interface AlbumOperations {
    AlbumDTO addAlbum(AlbumDTO albumDTO);
}