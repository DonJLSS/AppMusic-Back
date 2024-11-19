package com.aunnait.appmusic.model.mapper;

import com.aunnait.appmusic.model.Album;
import com.aunnait.appmusic.model.Song;
import com.aunnait.appmusic.model.Genre;

import com.aunnait.appmusic.model.dto.SongDTO;

import com.aunnait.appmusic.model.dto.SongResponseDTO;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;


@Component
public class SongMapper {

    public SongDTO convertToDTO(Song song) {
        SongDTO songDTO = new SongDTO();
        songDTO.setId(song.getId());
        songDTO.setTitle(song.getTitle());
        songDTO.setSongUrl(song.getSongUrl());
        songDTO.setDuration(song.getDuration());
        songDTO.setAlbumName(song.getAlbum().getTitle());
        return songDTO;
    }

    public Song convertToEntity(SongDTO songDTO, Album album) {
        Song song = new Song();
        song.setId(songDTO.getId());
        song.setTitle(songDTO.getTitle());
        song.setSongUrl(songDTO.getSongUrl());
        song.setDuration(songDTO.getDuration());
        song.setAlbum(album);
        return song;
    }

    public SongResponseDTO convertToResponseDTO(Song song) {
        SongResponseDTO responseDTO = new SongResponseDTO();
        responseDTO.setId(song.getId());
        responseDTO.setTitle(song.getTitle());
        responseDTO.setSongUrl(song.getSongUrl());
        responseDTO.setDuration(song.getDuration());
        responseDTO.setAlbumName(song.getAlbum().getTitle());

        Set<String> genreNames = song.getGenres().stream()
                .map(Genre::getName)
                .collect(Collectors.toSet());
        responseDTO.setGenreNames(genreNames);

        return responseDTO;
    }
}
