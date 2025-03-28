package com.aunnait.appmusic.service;

import com.aunnait.appmusic.model.Album;
import com.aunnait.appmusic.model.dto.AlbumDTO;
import com.aunnait.appmusic.model.dto.AlbumRequestDTO;
import com.aunnait.appmusic.model.mapper.AlbumMapper;
import com.aunnait.appmusic.model.mapper.ArtistMapper;
import com.aunnait.appmusic.model.mapper.SongMapper;
import com.aunnait.appmusic.repository.AlbumRepository;
import com.aunnait.appmusic.service.interfaces.IAlbumService;
import com.aunnait.appmusic.utils.AlbumSpecification;
import com.aunnait.appmusic.model.filters.DynamicSearchRequest;
import com.aunnait.appmusic.utils.SongOperations;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("DuplicatedCode")
@Service
public class AlbumService implements IAlbumService {

    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    AlbumMapper albumMapper;
    @Autowired
    SongMapper songMapper;
    @Lazy
    @Autowired
    ArtistService artistService;
    @Autowired
    SongOperations songOperations;
    @Autowired
    private ArtistMapper artistMapper;

    @Override
    public List<AlbumDTO> findAll() {
        List<Album> albums = albumRepository.findAll();
        return albums.stream()
                .map(albumMapper::toAlbumDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AlbumDTO findAlbumById(Integer id) {
        Album album = ErrorHandlerEntity(id);
        return albumMapper.toAlbumDTO(album);
    }

    @Override
    public AlbumDTO updateAlbum(Integer id, AlbumRequestDTO albumDTO) {
        Album album = ErrorHandlerEntity(id);
        ErrorHandler(albumDTO);
        album.setTitle(albumDTO.getTitle());
        album.setDescription(albumDTO.getDescription());
        album.setLaunchYear(albumDTO.getLaunchYear());
        Album saved = albumRepository.save(album);
        return albumMapper.toAlbumDTO(saved);
    }

    @Override
    public AlbumDTO addAlbum(AlbumRequestDTO albumDTO) {
        ErrorHandler(albumDTO);
        //Unique album title
        if(findAllAlbumByAttributes(albumDTO.getTitle(),null,null,null,
                null).stream()
                .anyMatch(a->a.getTitle().equals(albumDTO.getTitle())))
            throw new IllegalArgumentException("Album: "+ albumDTO.getTitle() +" already exists");
        Album newAlbum = albumMapper.fromRequestToEntity(albumDTO);
        Album saved = albumRepository.save(newAlbum);
        return albumMapper.toAlbumDTO(saved);
    }

    @Override
    public AlbumDTO patchAlbum(Integer id, Map<Object,Object> fields) {
        Album album = ErrorHandlerEntity(id);
        fields.forEach((key,value)->{
            Field field = ReflectionUtils.findField(Album.class, (String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field,album,value);
        });
        Album updated = albumRepository.save(album);
        return albumMapper.toAlbumDTO(updated);
    }


    @Override
    public void deleteAlbum(Integer id) {
        Album albumToDelete = ErrorHandlerEntity(id);
        albumRepository.delete(albumToDelete);
    }


    @Override
    public List<AlbumDTO> findAllAlbumByAttributes(String title, Integer launchYear,
                                                   Integer songsCount, String coverUrl, String artistName) {
        Specification<Album> spec = AlbumSpecification.getAlbumByAttributes(title, launchYear, songsCount, coverUrl, artistName,
                null,null,null,null);
        return albumRepository.findAll(spec).stream()
                .map(a->albumMapper.toAlbumDTO(a))
                .collect(Collectors.toList());
    }

    @Override
    public List<AlbumDTO> searchAlbum(DynamicSearchRequest searchRequest) {
        Specification<Album> spec = Specification.where(null);
        for (DynamicSearchRequest.SearchCriteria criteria : searchRequest.getListSearchCriteria()) {
            spec = spec.and(AlbumSpecification.getAlbumSpecification(criteria.getKey(), criteria.getOperation(), criteria.getValue()));
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
        Page<Album> filtered = albumRepository.findAll(spec,pageable);

        List<AlbumDTO> albumsDTOs = filtered.stream()
                .map(albumMapper::toAlbumDTO)
                .toList();

        return albumsDTOs;
    }


    //Argument error handling
    private void ErrorHandler(AlbumRequestDTO albumDTO) {
        if(albumDTO.getTitle() == null || albumDTO.getTitle().isEmpty())
            throw new IllegalArgumentException("Title can not be empty");
        if (albumDTO.getLaunchYear() == null)
            throw new IllegalArgumentException("Launch year can not be empty");
        if(artistService.findAllArtistByAttributes(
                albumDTO.getArtistName(),null,null).isEmpty())
            throw new IllegalArgumentException("Artist: "+albumDTO.getArtistName()+
                    " not registered");
    }

    private Album ErrorHandlerEntity(Integer id){
        return albumRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Album: "+ id +" not found"));
    }
}
