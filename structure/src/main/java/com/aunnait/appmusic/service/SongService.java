package com.aunnait.appmusic.service;

import com.aunnait.appmusic.exceptions.EntityNotFoundException;
import com.aunnait.appmusic.model.Album;
import com.aunnait.appmusic.model.Artist;
import com.aunnait.appmusic.model.Genre;
import com.aunnait.appmusic.model.Song;
import com.aunnait.appmusic.model.dto.*;
import com.aunnait.appmusic.model.filters.SongSearchRequest;
import com.aunnait.appmusic.model.mapper.AlbumMapper;
import com.aunnait.appmusic.model.mapper.ArtistMapper;
import com.aunnait.appmusic.model.mapper.GenreMapper;
import com.aunnait.appmusic.model.mapper.SongMapper;
import com.aunnait.appmusic.repository.AlbumRepository;
import com.aunnait.appmusic.repository.ArtistRepository;
import com.aunnait.appmusic.repository.SongRepository;
import com.aunnait.appmusic.utils.SongSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
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

    @Autowired
    ArtistMapper artistMapper;

    @Lazy
    @Autowired
    AlbumService albumService;
    @Autowired
    private AlbumMapper albumMapper;
    @Autowired
    private GenreMapper genreMapper;
    @Autowired
    private GenreService genreService;

    @Lazy
    @Autowired
    ArtistService artistService;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private AlbumRepository albumRepository;

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

        if (songDTO.getArtistName() == null || songDTO.getArtistName().isEmpty()) {
            throw new IllegalArgumentException("Artist name cannot be null or empty");
        }

        Optional<ArtistDTO> artist = artistService.findAllArtistByAttributes(songDTO.getArtistName(), null, null)
                .stream()
                .findFirst();

        if (!artist.isPresent()) {
            throw new EntityNotFoundException("Artist: " + songDTO.getArtistName() + " not found");
        }

        ArtistDTO foundArtist = artist.get();
        if (foundArtist.getName() == null || foundArtist.getNationality() == null) {
            throw new IllegalStateException("Artist data is incomplete: " + foundArtist);
        }

        Artist mapped = artistMapper.convertToEntity(foundArtist);

        Album album = null;
        if (songDTO.getAlbumName() != null && !songDTO.getAlbumName().isEmpty()) {
            Optional<AlbumDTO> albumDTOOptional = albumService.findAllAlbumByAttributes(
                    songDTO.getAlbumName(), null, null, null, null
            ).stream().findFirst();

            if (albumDTOOptional.isPresent()) {
                album = albumMapper.convertToEntity(albumDTOOptional.get());
            } else {
                throw new EntityNotFoundException("Album: " + songDTO.getAlbumName() + " not found");
            }
        }


        Song newSong = songMapper.convertToEntity(songDTO, album, mapped);
        Song saved = songRepository.save(newSong);

        return songMapper.convertToDTO(saved);
    }

    @Override
    public SongResponseDTO partialUpdateSong(Integer id, SongDTO songDTO) {
        Song song = EntityErrorHandler(id);

        if (songDTO.getArtistName() != null && !songDTO.getArtistName().isEmpty()) {
            Optional<ArtistDTO> artist = artistService.findAllArtistByAttributes(songDTO.getArtistName(), null, null)
                    .stream()
                    .findFirst();
            if (artist.isPresent()) {
                Artist artistEntity = artistMapper.convertToEntity(artist.get());
                song.setArtist(artistEntity);
            } else {
                throw new EntityNotFoundException("Artist: " + songDTO.getArtistName() + " not found");
            }
        }

        if (songDTO.getAlbumName() != null && !songDTO.getAlbumName().isEmpty()) {
            Optional<AlbumDTO> albumDTOOptional = albumService.findAllAlbumByAttributes(
                    songDTO.getAlbumName(), null, null, null, null
            ).stream().findFirst();
            if (albumDTOOptional.isPresent()) {
                Album albumEntity = albumMapper.convertToEntity(albumDTOOptional.get());
                song.setAlbum(albumEntity);
            } else {
                throw new EntityNotFoundException("Album: " + songDTO.getAlbumName() + " not found");
            }
        }

        if (songDTO.getTitle() != null && !songDTO.getTitle().isEmpty()) {
            song.setTitle(songDTO.getTitle());
        }
        if (songDTO.getDuration() != null) {
            song.setDuration(songDTO.getDuration());
        }
        if (songDTO.getSongUrl() != null && !songDTO.getSongUrl().isEmpty()) {
            song.setSongUrl(songDTO.getSongUrl());
        }

        Song savedSong = songRepository.save(song);

        return songMapper.convertToResponseDTO(savedSong);
    }

    @Override
    public List<SongResponseDTO> searchSongs(String title, Long duration, Long minDuration, Long maxDuration,
                                             String songUrl, String artistName, String albumName,
                                             String sortBy, boolean isAscending) {
        Specification<Song> spec = SongSpecification.getSongByAttributes(
                title, duration, songUrl, minDuration, maxDuration, artistName, albumName);

        Sort sort = isAscending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        return songRepository.findAll(spec, sort).stream()
                .map(songMapper::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSong(Integer id) {
        Song song = EntityErrorHandler(id);
        songRepository.delete(song);
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
