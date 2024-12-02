package com.aunnait.appmusic.service;

import com.aunnait.appmusic.model.dto.AlbumDTO;
import com.aunnait.appmusic.model.dto.AlbumRequestDTO;
import com.aunnait.appmusic.model.dto.ArtistDTO;
import com.aunnait.appmusic.model.dto.ArtistRequestDTO;



import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IArtistService {
    List<ArtistDTO> findAll();
    ArtistDTO findArtistById(Integer id);
    ArtistDTO updateArtist(Integer id, ArtistRequestDTO artistDTO);
    ArtistDTO addArtist(ArtistRequestDTO artistDTO);
    void deleteArtist(Integer id);
    ArtistDTO patchArtist(Integer id, Map<Object,Object> fields);
    ArtistDTO addAlbum(Integer id, AlbumRequestDTO albumDTO);
    ArtistDTO removeAlbum(Integer id, AlbumDTO albumDTO);
    List<ArtistDTO> findAllArtistByAttributes(String name, LocalDate dateOfBirth, String nationality);
    List<ArtistDTO> searchArtist(String name, String nationality, LocalDate dateOfBirth,
                                 Integer albumsCount, LocalDate minDate, LocalDate maxDate,
                                 Integer minAlbumCount, Integer maxAlbumCount,
                                 String sortBy, boolean isAscending);

}
