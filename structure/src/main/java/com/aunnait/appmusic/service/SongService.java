package com.aunnait.appmusic.service;

import com.aunnait.appmusic.exceptions.EntityNotFoundException;
import com.aunnait.appmusic.model.Album;
import com.aunnait.appmusic.model.Song;
import com.aunnait.appmusic.model.dto.AlbumDTO;
import com.aunnait.appmusic.model.dto.SongDTO;
import com.aunnait.appmusic.model.mapper.AlbumMapper;
import com.aunnait.appmusic.model.mapper.SongMapper;
import com.aunnait.appmusic.repository.SongRepository;
import com.aunnait.appmusic.utils.SongSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SongService implements ISongService {

    @Autowired
    SongRepository songRepository;

    @Autowired
    SongMapper songMapper;

    @Lazy
    @Autowired
    AlbumService albumService;
    @Autowired
    private AlbumMapper albumMapper;

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
        Optional<AlbumDTO> albumDTOOptional = albumService.findAllAlbumByAttributes(songDTO.getAlbumName(),null, null,
                        null, null)
                .stream()
                .findFirst();
        if(albumDTOOptional.isPresent()){
            Album album = albumMapper.convertToEntity(albumDTOOptional.get());

            Song newSong = songMapper.convertToEntity(songDTO, album);
            Song saved = songRepository.save(newSong);
            return songMapper.convertToDTO(saved);
        }
        else throw new EntityNotFoundException("Album: "+songDTO.getAlbumName()+" not found");
    }

    @Override
    public void deleteSong(Integer id) {
        Song song = songRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Song: "+id+" not found"));
        songRepository.delete(song);
    }

    @Override
    public List<SongDTO> findAllAlbumByAttributes(String title, Duration duration, String songUrl) {
        Specification<Song> spec = SongSpecification.getArtistByAttributes(title, duration, songUrl);
        return songRepository.findAll(spec).stream()
                .map(songMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<SongDTO> findAllPaginated(Pageable pageable) {
        Page<Song> songsPage = songRepository.findAll(pageable);
        return songsPage.map(songMapper::convertToDTO);
    }

    private void ErrorHandler(SongDTO songDTO) {
        if(songDTO.getTitle() == null || songDTO.getTitle().isEmpty())
            throw new IllegalArgumentException("Song title can not be empty");
        if(songDTO.getSongUrl() == null || songDTO.getSongUrl().isEmpty())
            throw new IllegalArgumentException("Song url can not be empty");
        if(songDTO.getDuration() == null)
            throw new IllegalArgumentException("Song duration can not be negative");
        if(songDTO.getDuration() <= 0)
            throw new IllegalArgumentException("Song duration can not be zero");
    }
}
