package com.aunnait.appmusic.model.mapper;

import com.aunnait.appmusic.model.Song;
import com.aunnait.appmusic.model.dto.SongDTO;
import org.springframework.stereotype.Component;

@Component
public class SongMapper {
    public SongDTO convertToDTO(Song song) {
        SongDTO songDTO = new SongDTO();
        songDTO.setId(song.getId());
        songDTO.setTitle(song.getTitle());
        songDTO.setSongUrl(song.getSongUrl());
        songDTO.setDuration(song.getDuration());
        return songDTO;
    }
    public Song convertToEntity(SongDTO songDTO) {
        Song song = new Song();
        song.setId(songDTO.getId());
        song.setTitle(songDTO.getTitle());
        song.setSongUrl(songDTO.getSongUrl());
        song.setDuration(songDTO.getDuration());
        return song;
    }
}
