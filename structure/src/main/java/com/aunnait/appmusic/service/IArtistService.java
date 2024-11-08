package com.aunnait.appmusic.service;

import com.aunnait.appmusic.model.Artist;
import com.aunnait.appmusic.model.dto.ArtistDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IArtistService {
    List<ArtistDTO> findAll();
    ArtistDTO findArtistById(Integer id);
    ArtistDTO updateArtist(Integer id, ArtistDTO artistDTO);
    ArtistDTO addArtist(ArtistDTO artistDTO);
    void deleteArtist(Integer id);
}
