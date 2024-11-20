package com.aunnait.appmusic.service;

import com.aunnait.appmusic.exceptions.EntityNotFoundException;
import com.aunnait.appmusic.model.Album;
import com.aunnait.appmusic.model.Genre;
import com.aunnait.appmusic.model.Song;
import com.aunnait.appmusic.model.dto.AlbumDTO;
import com.aunnait.appmusic.model.dto.GenreDTO;
import com.aunnait.appmusic.model.dto.SongDTO;
import com.aunnait.appmusic.model.dto.SongResponseDTO;
import com.aunnait.appmusic.model.mapper.AlbumMapper;
import com.aunnait.appmusic.model.mapper.GenreMapper;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SuppressWarnings("DuplicatedCode")
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
    @Autowired
    private GenreMapper genreMapper;
    @Autowired
    private GenreService genreService;

    @Override
    public List<SongResponseDTO> findAll() {
        List<Song> songs = songRepository.findAll();
        return songs.stream()
                .map(songMapper::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SongResponseDTO findSongById(Integer id) {
        Song song = EntityErrorHandler(id);
        return songMapper.convertToResponseDTO(song);
    }

    @Override
    public SongDTO updateSong(Integer id, SongDTO songDTO) {
        Song song = EntityErrorHandler(id);
        ErrorHandler(songDTO);
        Song updated = songRepository.save(song);
        return songMapper.convertToDTO(updated);
    }

    @Override
    public SongDTO addSong(SongDTO songDTO) {
        ErrorHandler(songDTO);
        Optional<AlbumDTO> albumDTOOptional = albumService.findAllAlbumByAttributes(songDTO.getAlbumName(),
                        null, null,
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
        Song song = EntityErrorHandler(id);
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



    @Override
    public SongResponseDTO addGenresToSong(Integer songId, List<String> genresNames) {
        Song song = EntityErrorHandler(songId);
        if (genresNames == null || genresNames.isEmpty())
            throw new IllegalArgumentException("Please list genres to add");

        List<Genre> genresToAdd = new ArrayList<>();
        List<String> notFoundGenres = new ArrayList<>();

        for (String name : genresNames) {
            List<GenreDTO> foundGenres = genreService.findAllGenreByAttributes(name, null, null);

            if (foundGenres.isEmpty()) {
                notFoundGenres.add(name);
            } else {
                Genre genreEntity = genreMapper.convertToEntity(foundGenres.get(0));
                genresToAdd.add(genreEntity);
            }
        }
        song.getGenres().addAll(genresToAdd);
        Song savedSong = songRepository.save(song);

        if (!notFoundGenres.isEmpty()) {
            throw new EntityNotFoundException("Genres not found: " + String.join(", ", notFoundGenres));
        }

        return songMapper.convertToResponseDTO(savedSong);
    }

    @Override
    public SongResponseDTO removeGenresFromSong(Integer songId, List<String> genreNames) {
        Song song = EntityErrorHandler(songId);

        if (genreNames == null || genreNames.isEmpty()) {
            throw new IllegalArgumentException("Please list genres to remove");
        }

        List<Genre> genresToRemove = genreNames.stream()
                .map(name -> genreService.findAllGenreByAttributes(name, null, null))
                .flatMap(List::stream)
                .map(genreMapper::convertToEntity)
                .toList();

        genresToRemove.forEach(genreToRemove ->
                song.getGenres().removeIf(existingGenre ->
                        existingGenre.getId().equals(genreToRemove.getId())
                )
        );

        Song updatedSong = songRepository.save(song);
        return songMapper.convertToResponseDTO(updatedSong);
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

    private Song EntityErrorHandler(Integer id){
        return songRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Song: "+id+" not found"));
    }


}
