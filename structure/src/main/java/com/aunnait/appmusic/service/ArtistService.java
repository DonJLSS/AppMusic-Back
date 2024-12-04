package com.aunnait.appmusic.service;

import com.aunnait.appmusic.model.Album;
import com.aunnait.appmusic.model.Artist;
import com.aunnait.appmusic.model.dto.*;
import com.aunnait.appmusic.model.mapper.AlbumMapper;
import com.aunnait.appmusic.model.mapper.ArtistMapper;
import com.aunnait.appmusic.repository.ArtistRepository;
import com.aunnait.appmusic.exceptions.EntityNotFoundException;
import com.aunnait.appmusic.service.interfaces.IArtistService;
import com.aunnait.appmusic.utils.AlbumOperations;
import com.aunnait.appmusic.model.filters.DynamicSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.aunnait.appmusic.utils.ArtistSpecification;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
    public ArtistDTO patchArtist(Integer id, Map<Object,Object> fields) {
        Artist artist = ErrorHandlerEntity(id);
        fields.forEach((key,value)->{
            Field field = ReflectionUtils.findField(Artist.class, (String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field,artist,value);
        });
        Artist updated = repository.save(artist);
        return mapper.convertToDTO(updated);

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
    public List<ArtistDTO> searchArtist(DynamicSearchRequest searchRequest) {
        Specification<Artist> spec = Specification.where(null);
        for (DynamicSearchRequest.SearchCriteria criteria : searchRequest.getListSearchCriteria()) {
            spec = spec.and(ArtistSpecification.getArtistSpecification(criteria.getKey(), criteria.getOperation(), criteria.getValue()));
        }

        Sort sort = Sort.unsorted();
        for (DynamicSearchRequest.SortCriteria order : searchRequest.getListOrderCriteria()) {
            Sort orderSort = order.getValueSortOrder() == DynamicSearchRequest.SortValue.ASC
                    ? Sort.by(order.getSortBy()).ascending()
                    : Sort.by(order.getSortBy()).descending();
            sort = sort.and(orderSort);
        }

        Pageable pageable = PageRequest.of(
                searchRequest.getPage().getPageIndex(),
                searchRequest.getPage().getPageSize(),
                sort
        );
        Page<Artist> filtered = artistRepository.findAll(spec,pageable);

        List<ArtistDTO> artistDTOs = filtered.stream()
                .map(mapper::convertToDTO)
                .toList();

        return artistDTOs;
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