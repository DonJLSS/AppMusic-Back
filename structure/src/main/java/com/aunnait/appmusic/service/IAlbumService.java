package com.aunnait.appmusic.service;

import com.aunnait.appmusic.model.dto.AlbumDTO;
import com.aunnait.appmusic.model.dto.AlbumRequestDTO;
import com.aunnait.appmusic.utils.AlbumOperations;


import java.util.List;

public interface IAlbumService extends AlbumOperations {
    List<AlbumDTO> findAll();
    AlbumDTO findAlbumById(Integer id);
    AlbumDTO updateAlbum(Integer id, AlbumRequestDTO albumDTO);
    AlbumDTO addAlbum(AlbumRequestDTO albumDTO);
    AlbumDTO partialUpdateAlbum(Integer id, AlbumRequestDTO albumRequestDTO);
    void deleteAlbum(Integer id);
    List<AlbumDTO> findAllAlbumByAttributes(String title, Integer launchYear,
                                            Integer songsCount, String coverUrl, String artistName);

}
