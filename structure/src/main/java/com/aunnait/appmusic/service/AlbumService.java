package com.aunnait.appmusic.service;

import com.aunnait.appmusic.model.Album;
import com.aunnait.appmusic.model.dto.AlbumDTO;
import com.aunnait.appmusic.model.mapper.AlbumMapper;
import com.aunnait.appmusic.model.mapper.SongMapper;
import com.aunnait.appmusic.repository.AlbumRepository;
import com.aunnait.appmusic.utils.AlbumSpecification;
import com.aunnait.appmusic.utils.SongOperations;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<AlbumDTO> findAll() {
        List<Album> albums = albumRepository.findAll();
        return albums.stream()
                .map(albumMapper::toAlbumDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AlbumDTO findAlbumById(Integer id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Album: "+ id +" not found"));
        return albumMapper.toAlbumDTO(album);
    }

    @Override
    public AlbumDTO updateAlbum(Integer id, AlbumDTO albumDTO) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Album: "+ id +" not found"));
        ErrorHandler(albumDTO);
        album.setSongsCount(albumDTO.getSongsCount());
        album.setTitle(albumDTO.getTitle());
        album.setDescription(albumDTO.getDescription());
        album.setLaunchYear(albumDTO.getLaunchYear());
        Album saved = albumRepository.save(album);
        return albumMapper.toAlbumDTO(saved);
    }

    @Override
    public AlbumDTO addAlbum(AlbumDTO albumDTO) {
        ErrorHandler(albumDTO);
        //Unique album title
        if(findAllAlbumByAttributes(albumDTO.getTitle(),null,null,null,
                null).stream()
                .anyMatch(a->a.getTitle().equals(albumDTO.getTitle())))
            throw new IllegalArgumentException("Album: "+ albumDTO.getTitle() +" already exists");
        Album newAlbum = albumMapper.convertToEntity(albumDTO);
        Album saved = albumRepository.save(newAlbum);
        return albumMapper.toAlbumDTO(saved);
    }

    @Override
    public void deleteAlbum(Integer id) {
        Album albumToDelete= albumRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Album: "+ id +" not found"));
        albumRepository.delete(albumToDelete);
    }



    @Override
    public List<AlbumDTO> findAllAlbumByAttributes(String title, Integer launchYear,
                                                   Integer songsCount, String coverUrl, String artistName) {
        Specification<Album> spec = AlbumSpecification.getAlbumByAttributes(title, launchYear, songsCount, coverUrl, artistName);
        return albumRepository.findAll(spec).stream()
                .map(a->albumMapper.toAlbumDTO(a))
                .collect(Collectors.toList());
    }

    @Override
    public Page<AlbumDTO> findAllPaginated(Pageable pageable) {
        Page<Album> albumsPage = albumRepository.findAll(pageable);
        return albumsPage.map(albumMapper::toAlbumDTO);
    }


    //Argument error handling
    private void ErrorHandler(AlbumDTO albumDTO) {
        if(albumDTO.getTitle() == null || albumDTO.getTitle().isEmpty())
            throw new IllegalArgumentException("Title can not be empty");
        if (albumDTO.getLaunchYear() == null)
            throw new IllegalArgumentException("Launch year can not be empty");
        if(artistService.findAllArtistByAttributes(
                albumDTO.getArtistName(),null,null).isEmpty())
            throw new IllegalArgumentException("Artist: "+albumDTO.getArtistName()+
                    " not registered");
    }
}
