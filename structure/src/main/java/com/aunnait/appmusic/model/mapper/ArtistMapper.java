package com.aunnait.appmusic.model.mapper;

import com.aunnait.appmusic.model.Artist;
import com.aunnait.appmusic.model.dto.ArtistDTO;
import com.aunnait.appmusic.model.dto.ArtistRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class ArtistMapper {
    public ArtistDTO convertToDTO(Artist artist) {
        return new ArtistDTO(artist.getId(), artist.getName(), artist.getNationality(), artist.getDateOfBirth(),
                artist.getAlbums().size());
    }

    public Artist fromRequestToArtist(ArtistRequestDTO artistRequestDTO) {
        if (artistRequestDTO == null) {
            throw new IllegalArgumentException("ArtistRequestDTO cannot be null");
        }
        Artist artist = new Artist();
        artist.setId(artistRequestDTO.getId());
        artist.setName(artistRequestDTO.getName());
        artist.setNationality(artistRequestDTO.getNationality());
        artist.setDateOfBirth(artistRequestDTO.getDateOfBirth());
        return artist;
    }

    public Artist convertToEntity(ArtistDTO artistDTO) {
        if (artistDTO == null) {
            throw new IllegalArgumentException("ArtistDTO cannot be null");
        }
        Artist artist = new Artist();
        artist.setId(artistDTO.getId());
        artist.setName(artistDTO.getName());
        artist.setNationality(artistDTO.getNationality());
        artist.setDateOfBirth(artistDTO.getDateOfBirth());
        return artist;
    }
}
