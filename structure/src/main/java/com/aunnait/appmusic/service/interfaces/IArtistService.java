package com.aunnait.appmusic.service.interfaces;

import com.aunnait.appmusic.model.dto.*;
import com.aunnait.appmusic.model.dto.createdto.ArtistCreateDTO;
import com.aunnait.appmusic.model.filters.DynamicSearchRequest;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IArtistService {
    List<ArtistDTO> findAll();
    List<ArtistResponseDTO> getArtistsComplete();
    ArtistDTO findArtistById(Integer id);
    ArtistDTO updateArtist(Integer id, ArtistRequestDTO artistDTO);
    ArtistDTO addArtist(ArtistRequestDTO artistDTO);
    ArtistResponseDTO createArtistComplete(ArtistCreateDTO artistCreateDTO);
    void deleteArtist(Integer id);
    ArtistDTO patchArtist(Integer id, Map<Object,Object> fields);
    ArtistDTO addAlbum(Integer id, AlbumRequestDTO albumDTO);
    ArtistDTO removeAlbum(Integer id, AlbumDTO albumDTO);
    List<ArtistDTO> findAllArtistByAttributes(String name, LocalDate dateOfBirth, String nationality);
    List<ArtistDTO> searchArtist(DynamicSearchRequest searchRequest);

}
