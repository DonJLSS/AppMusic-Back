package com.aunnait.appmusic.service;

import com.aunnait.appmusic.model.Album;
import com.aunnait.appmusic.model.Artist;
import com.aunnait.appmusic.model.dto.AlbumDTO;
import com.aunnait.appmusic.model.dto.AlbumRequestDTO;
import com.aunnait.appmusic.model.dto.ArtistDTO;
import com.aunnait.appmusic.model.dto.ArtistRequestDTO;
import com.aunnait.appmusic.model.filters.ArtistSearchRequest;
import com.aunnait.appmusic.model.mapper.AlbumMapper;
import com.aunnait.appmusic.model.mapper.ArtistMapper;
import com.aunnait.appmusic.repository.ArtistRepository;
import com.aunnait.appmusic.exceptions.EntityNotFoundException;
import com.aunnait.appmusic.utils.AlbumOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.aunnait.appmusic.utils.ArtistSpecification;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistService implements IArtistService {

    @Autowired
    ArtistRepository repository;
    @Autowired
    ArtistMapper mapper;
    @Autowired
    AlbumMapper albumMapper;
    @Autowired
    AlbumOperations albumOperations;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private ArtistMapper artistMapper;


    @Override
    public List<ArtistDTO> findAll() {
        List<Artist> artists = repository.findAll();
        return artists.stream()
                .map(a -> mapper.convertToDTO(a))
                .collect(Collectors.toList());

    }

    @Override
    public ArtistDTO findArtistById(Integer id) {
        Artist artist = ErrorHandlerEntity(id);
        return mapper.convertToDTO(artist);
    }

    @Override
    public ArtistDTO updateArtist(Integer id, ArtistRequestDTO artistDTO) {
        Artist artistToUpdate = ErrorHandlerEntity(id);
        ErrorHandler(artistDTO);
        //Unique name on artists
        if (findAllArtistByAttributes(artistDTO.getName(), null, null).stream()
                .anyMatch(a -> a.getName().equals(artistDTO.getName())))
            throw new IllegalArgumentException("Name: " + artistDTO.getName() + " already in use");

        artistToUpdate.setName(artistDTO.getName());
        artistToUpdate.setNationality(artistDTO.getNationality());

        Artist updated = repository.save(artistToUpdate);
        return mapper.convertToDTO(updated);
    }


    @Override
    public ArtistDTO addArtist(ArtistRequestDTO artistDTO) {
        ErrorHandler(artistDTO);
        //Unique name on artists
        if (findAllArtistByAttributes(artistDTO.getName(), null, null).stream()
                .anyMatch(a -> a.getName().equals(artistDTO.getName())))
            throw new IllegalArgumentException("Name: " + artistDTO.getName() + " already in use");
        Artist newArtist = mapper.fromRequestToArtist(artistDTO);
        Artist savedArtist = repository.save(newArtist);
        return mapper.convertToDTO(savedArtist);
    }


    @Override
    public void deleteArtist(Integer id) {
        Artist artistToDelete = ErrorHandlerEntity(id);
        repository.delete(artistToDelete);
    }

    @Override
    public ArtistDTO partialUpdateArtist(Integer id, ArtistRequestDTO artistRequestDTO) {
        Artist artist = ErrorHandlerEntity(id);
        if (artistRequestDTO.getName()!=null && !artistRequestDTO.getName().isEmpty())
            artist.setName(artistRequestDTO.getName());
        if (artistRequestDTO.getNationality()!=null && !artistRequestDTO.getNationality().isEmpty())
            artist.setNationality(artistRequestDTO.getNationality());
        if (!artistRequestDTO.getDateOfBirth().isBefore(LocalDate.of(1900, 1, 1))
                && !artistRequestDTO.getDateOfBirth().isAfter(LocalDate.now()))
            artist.setDateOfBirth(artistRequestDTO.getDateOfBirth());
        Artist savedArtist = artistRepository.save(artist);
        return artistMapper.convertToDTO(savedArtist);

    }

    //Add an album to an existing artist
    public ArtistDTO addAlbum(Integer artistId, AlbumRequestDTO albumDTO) {
        Artist artist = ErrorHandlerEntity(artistId);
        if (!albumDTO.getArtistName().equals(artist.getName()))
            throw new IllegalArgumentException("Artist name: " + albumDTO.getArtistName() +
                    " not matching id: " + artistId);
        AlbumDTO existing = albumOperations.addAlbum(albumDTO);
        Album savedAlbum = albumMapper.convertToEntity(existing);
        artist.getAlbums().add(savedAlbum);
        Artist updatedArtist = repository.save(artist);
        return mapper.convertToDTO(updatedArtist);

    }


    //Album removal from list (doesn't delete the album from database)
    public ArtistDTO removeAlbum(Integer artistId, AlbumDTO albumDTO) {
        Artist artist = ErrorHandlerEntity(artistId);
        Album album = albumMapper.convertToEntity(albumDTO);
        artist.getAlbums().remove(album);
        Artist updatedArtist = repository.save(artist);
        return mapper.convertToDTO(updatedArtist);
    }

    @Override
    public List<ArtistDTO> findAllArtistByAttributes(String name, LocalDate dateOfBirth, String nationality) {
        Specification<Artist> spec = ArtistSpecification.getArtistByAttributes(name, dateOfBirth, nationality,null,
                null,null,null,null);
        return repository.findAll(spec).stream()
                .map(a -> mapper.convertToDTO(a))
                .collect(Collectors.toList());
    }

    @Override
    public List<ArtistDTO> searchArtist(ArtistSearchRequest artistSearchRequest) {
        Specification<Artist> spec = ArtistSpecification.getArtistByAttributes(
                artistSearchRequest.getName(),
                artistSearchRequest.getDateOfBirth(),
                artistSearchRequest.getNationality(),
                artistSearchRequest.getAlbumsCount(),
                artistSearchRequest.getMinDate(),
                artistSearchRequest.getMaxDate(),
                artistSearchRequest.getMinAlbumCount(), artistSearchRequest.getMaxAlbumCount()
        );
        List<Artist> artists = repository.findAll(spec);
        return artists.stream()
                .map(mapper::convertToDTO)
                .toList();
    }


    //Argument error handling
    private void ErrorHandler(ArtistRequestDTO artistDTO) {
        if (artistDTO.getName() == null || artistDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (artistDTO.getNationality() == null || artistDTO.getNationality().isEmpty()) {
            throw new IllegalArgumentException("Nationality cannot be empty");
        }
    }

    private Artist ErrorHandlerEntity(Integer id) {
        Artist artist = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Artist: " + id + " not found"));
        return artist;
    }
}