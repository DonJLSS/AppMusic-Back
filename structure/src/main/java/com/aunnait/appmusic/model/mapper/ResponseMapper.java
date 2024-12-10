package com.aunnait.appmusic.model.mapper;

import com.aunnait.appmusic.model.Album;
import com.aunnait.appmusic.model.Artist;
import com.aunnait.appmusic.model.Genre;
import com.aunnait.appmusic.model.Song;
import com.aunnait.appmusic.model.dto.AlbumResponseDTO;
import com.aunnait.appmusic.model.dto.ArtistResponseDTO;
import com.aunnait.appmusic.model.dto.SongResponseDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ResponseMapper {

    public ArtistResponseDTO mapToResponseDTO(Artist artist) {
        return ArtistResponseDTO.builder()
                .id(artist.getId())
                .name(artist.getName())
                .nationality(artist.getNationality())
                .dateOfBirth(artist.getDateOfBirth())
                .albums(artist.getAlbums().stream()
                        .map(this::mapAlbumToResponseDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public AlbumResponseDTO mapAlbumToResponseDTO(Album album) {
        return AlbumResponseDTO.builder()
                .id(album.getId())
                .title(album.getTitle())
                .launchYear(album.getLaunchYear())
                .description(album.getDescription())
                .songs(album.getSongs().stream()
                        .map(this::mapSongToResponseDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public SongResponseDTO mapSongToResponseDTO(Song song) {
        return SongResponseDTO.builder()
                .id(song.getId())
                .title(song.getTitle())
                .duration(song.getDuration())
                .songUrl(song.getSongUrl())
                .albumName(song.getAlbum().getTitle())
                .artistName(song.getArtist().getName())
                .genreNames(song.getGenres().stream()
                        .map(Genre::getName)
                        .collect(Collectors.toSet()))
                .build();
    }
}
