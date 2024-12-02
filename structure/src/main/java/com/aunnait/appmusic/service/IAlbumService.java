package com.aunnait.appmusic.service;

import com.aunnait.appmusic.model.dto.AlbumDTO;
import com.aunnait.appmusic.model.dto.AlbumRequestDTO;
import com.aunnait.appmusic.utils.AlbumOperations;


import java.util.List;
import java.util.Map;

public interface IAlbumService extends AlbumOperations {
    List<AlbumDTO> findAll();
    AlbumDTO findAlbumById(Integer id);
    AlbumDTO updateAlbum(Integer id, AlbumRequestDTO albumDTO);
    AlbumDTO addAlbum(AlbumRequestDTO albumDTO);
    AlbumDTO patchAlbum(Integer id, Map<Object,Object> fields);
    void deleteAlbum(Integer id);
    List<AlbumDTO> findAllAlbumByAttributes(String title, Integer launchYear,
                                            Integer songsCount, String coverUrl, String artistName);
    List<AlbumDTO> searchAlbum(String title,Integer launchYear, Integer songsCount,
                               String coverUrl, String artistName,
                               Integer minYear, Integer maxYear,
                               Integer minSongs, Integer maxSongs,
                               String sortBy, boolean isAscending);

}
