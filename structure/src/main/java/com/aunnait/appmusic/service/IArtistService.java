package com.aunnait.appmusic.service;

import com.aunnait.appmusic.model.dto.AlbumDTO;
import com.aunnait.appmusic.model.dto.AlbumRequestDTO;
import com.aunnait.appmusic.model.dto.ArtistDTO;
import com.aunnait.appmusic.model.dto.ArtistRequestDTO;
import com.aunnait.appmusic.model.filters.ArtistSearchRequest;


import java.time.LocalDate;
import java.util.List;

public interface IArtistService {
    List<ArtistDTO> findAll();
    ArtistDTO findArtistById(Integer id);
    ArtistDTO updateArtist(Integer id, ArtistRequestDTO artistDTO);
    ArtistDTO addArtist(ArtistRequestDTO artistDTO);
    void deleteArtist(Integer id);
    ArtistDTO partialUpdateArtist(Integer id, ArtistRequestDTO artistRequestDTO);
    ArtistDTO addAlbum(Integer id, AlbumRequestDTO albumDTO);
    ArtistDTO removeAlbum(Integer id, AlbumDTO albumDTO);
    List<ArtistDTO> findAllArtistByAttributes(String name, LocalDate dateOfBirth, String nationality);
    List<ArtistDTO> searchArtist(String name, String nationality, LocalDate dateOfBirth,
                                 Integer albumsCount, LocalDate minDate, LocalDate maxDate,
                                 Integer minAlbumCount, Integer maxAlbumCount,
                                 String sortBy, boolean isAscending);

}
