package com.aunnait.appmusic.model.mapper;

import com.aunnait.appmusic.model.Artist;
import com.aunnait.appmusic.model.dto.ArtistDTO;
import org.springframework.stereotype.Component;

@Component
public class ArtistMapper {
    public ArtistDTO convertToDTO(Artist artist) {
        return new ArtistDTO(artist.getId(), artist.getName(), artist.getNationality(), artist.getDateOfBirth(),
                artist.getAlbums().size());
    }

    public Artist convertToEntity(ArtistDTO artistDTO) {
        Artist artist = new Artist();
        artist.setId(artistDTO.getId());
        artist.setName(artistDTO.getName());
        artist.setNationality(artistDTO.getNationality());
        artist.setDateOfBirth(artistDTO.getDateOfBirth());
        return artist;
    }
}
