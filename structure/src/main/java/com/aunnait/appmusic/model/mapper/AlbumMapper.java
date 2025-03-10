package com.aunnait.appmusic.model.mapper;

import com.aunnait.appmusic.exceptions.EntityNotFoundException;
import com.aunnait.appmusic.model.Album;
import com.aunnait.appmusic.model.Artist;
import com.aunnait.appmusic.model.dto.AlbumDTO;
import com.aunnait.appmusic.model.dto.AlbumRequestDTO;
import com.aunnait.appmusic.model.dto.ArtistDTO;
import com.aunnait.appmusic.service.ArtistService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("DuplicatedCode")
@Component
public class AlbumMapper {

    private final ArtistService artistService;
    private final ArtistMapper artistMapper;

    public AlbumMapper(@Lazy ArtistService artistService, ArtistMapper artistMapper) {
        this.artistService = artistService;
        this.artistMapper = artistMapper;
    }

    public AlbumDTO toAlbumDTO(Album album) {
        AlbumDTO albumDTO = new AlbumDTO();
        albumDTO.setId(album.getId());
        albumDTO.setDescription(album.getDescription());
        albumDTO.setTitle(album.getTitle());
        albumDTO.setSongsCount(album.getSongs().size());
        albumDTO.setLaunchYear(album.getLaunchYear());
        albumDTO.setArtistName(album.getArtist().getName());
        return albumDTO;
    }

    public Album fromRequestToEntity(AlbumRequestDTO albumRequestDTO) {
        Album album = new Album();
        album.setId(albumRequestDTO.getId());
        album.setDescription(albumRequestDTO.getDescription());
        album.setTitle(albumRequestDTO.getTitle());
        album.setLaunchYear(albumRequestDTO.getLaunchYear());

        if (albumRequestDTO.getArtistName() != null && !albumRequestDTO.getArtistName().isEmpty()) {
            List<ArtistDTO> artistsDTO = artistService.findAllArtistByAttributes(albumRequestDTO.getArtistName(), null, null);

            Artist artist = artistsDTO.stream()
                    .map(artistMapper::convertToEntity)
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("Artist with name " + albumRequestDTO.getArtistName() + " not found"));

            album.setArtist(artist);
        }

        return album;

    }

    public Album convertToEntity(AlbumDTO albumDTO) {
        Album album = new Album();
        album.setId(albumDTO.getId());
        album.setTitle(albumDTO.getTitle());
        album.setLaunchYear(albumDTO.getLaunchYear());
        album.setDescription(albumDTO.getDescription());
        album.setSongsCount(album.getSongs().size());

        if (albumDTO.getArtistName() != null && !albumDTO.getArtistName().isEmpty()) {
            List<ArtistDTO> artistsDTO = artistService.findAllArtistByAttributes(albumDTO.getArtistName(), null, null);

            Artist artist = artistsDTO.stream()
                    .map(artistMapper::convertToEntity)
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("Artist with name " + albumDTO.getArtistName() + " not found"));

            album.setArtist(artist);
        }

        return album;
    }
}
