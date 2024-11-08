package com.aunnait.appmusic.service;

import com.aunnait.appmusic.exceptions.EntityNotFoundException;
import com.aunnait.appmusic.model.Song;
import com.aunnait.appmusic.model.dto.SongDTO;
import com.aunnait.appmusic.model.mapper.SongMapper;
import com.aunnait.appmusic.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongService implements ISongService {

    @Autowired
    SongRepository songRepository;

    @Autowired
    SongMapper songMapper;

    @Override
    public List<SongDTO> findAll() {
        List<Song> songs = songRepository.findAll();
        return songs.stream()
                .map(songMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SongDTO findSongById(Integer id) {
        Song song = songRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Song: "+id+" not found"));
        return songMapper.convertToDTO(song);
    }

    @Override
    public SongDTO updateSong(Integer id, SongDTO songDTO) {
        Song song = songRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Song: "+id+" not found"));
        ErrorHandler(songDTO);
        Song updated = songRepository.save(song);
        return songMapper.convertToDTO(updated);
    }

    @Override
    public SongDTO addSong(SongDTO songDTO) {
        ErrorHandler(songDTO);
        Song newSong = songMapper.convertToEntity(songDTO);
        Song saved = songRepository.save(newSong);
        return songMapper.convertToDTO(saved);
    }

    @Override
    public void deleteSong(Integer id) {
        Song song = songRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Song: "+id+" not found"));
        songRepository.delete(song);
    }

    private void ErrorHandler(SongDTO songDTO) {
        if(songDTO.getTitle() == null || songDTO.getTitle().isEmpty())
            throw new IllegalArgumentException("Song title can not be empty");
        if(songDTO.getSongUrl() == null || songDTO.getSongUrl().isEmpty())
            throw new IllegalArgumentException("Song url can not be empty");
        if(songDTO.getDuration().isNegative())
            throw new IllegalArgumentException("Song duration can not be negative");
        if(songDTO.getDuration().isZero())
            throw new IllegalArgumentException("Song duration can not be zero");
    }
}
