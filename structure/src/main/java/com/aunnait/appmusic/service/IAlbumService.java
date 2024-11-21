package com.aunnait.appmusic.service;

import com.aunnait.appmusic.model.dto.AlbumDTO;
import com.aunnait.appmusic.model.dto.AlbumRequestDTO;
import com.aunnait.appmusic.utils.AlbumOperations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAlbumService extends AlbumOperations {
    List<AlbumDTO> findAll();
    AlbumDTO findAlbumById(Integer id);
    AlbumDTO updateAlbum(Integer id, AlbumRequestDTO albumDTO);
    AlbumDTO addAlbum(AlbumRequestDTO albumDTO);
    void deleteAlbum(Integer id);
    List<AlbumDTO> findAllAlbumByAttributes(String title, Integer launchYear,
                                            Integer songsCount, String coverUrl, String artistName);
    Page<AlbumDTO> findAllPaginated(Pageable pageable);

}
