package com.aunnait.appmusic.service;

import com.aunnait.appmusic.model.dto.AlbumDTO;

import java.time.LocalDate;
import java.util.List;

public interface IAlbumService {
    List<AlbumDTO> findAll();
    AlbumDTO findAlbumById(Integer id);
    AlbumDTO updateAlbum(Integer id, AlbumDTO albumDTO);
    AlbumDTO addAlbum(AlbumDTO albumDTO);
    void deleteAlbum(Integer id);
    List<AlbumDTO> findAllAlbumByAttributes(String title, Integer launchYear,
                                            Integer songsCount, String coverUrl, String artistName);

}
