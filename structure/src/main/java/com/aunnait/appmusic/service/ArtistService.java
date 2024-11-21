package com.aunnait.appmusic.service;

import com.aunnait.appmusic.model.Album;
import com.aunnait.appmusic.model.Artist;
import com.aunnait.appmusic.model.dto.AlbumDTO;
import com.aunnait.appmusic.model.dto.AlbumRequestDTO;
import com.aunnait.appmusic.model.dto.ArtistDTO;
import com.aunnait.appmusic.model.dto.ArtistRequestDTO;
import com.aunnait.appmusic.model.mapper.AlbumMapper;
import com.aunnait.appmusic.model.mapper.ArtistMapper;
import com.aunnait.appmusic.repository.ArtistRepository;
import com.aunnait.appmusic.exceptions.EntityNotFoundException;
import com.aunnait.appmusic.utils.AlbumOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.aunnait.appmusic.utils.ArtistSpecification;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistService implements IArtistService{

    @Autowired
    ArtistRepository repository;
    @Autowired
    ArtistMapper mapper;
    @Autowired
    AlbumMapper albumMapper;
    @Autowired
    AlbumOperations albumOperations;


    @Override
    public List<ArtistDTO> findAll() {
        List<Artist> artists = repository.findAll();
        return artists.stream()
                .map(a->mapper.convertToDTO(a))
                .collect(Collectors.toList());

    }

    @Override
    public ArtistDTO findArtistById(Integer id) {
        Artist artist = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Artist: "+ id +" not found"));
        return mapper.convertToDTO(artist);
    }

    @Override
    public ArtistDTO updateArtist(Integer id, ArtistRequestDTO artistDTO) {
        Artist artistToUpdate = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Artist: "+ id +" not found"));
        ErrorHandler(artistDTO);
        //Unique name on artists
        if(findAllArtistByAttributes(artistDTO.getName(),null,null).stream()
                        .anyMatch(a->a.getName().equals(artistDTO.getName())))
            throw new IllegalArgumentException("Name: "+ artistDTO.getName() +" already in use");

        artistToUpdate.setName(artistDTO.getName());
        artistToUpdate.setNationality(artistDTO.getNationality());

        Artist updated = repository.save(artistToUpdate);
        return mapper.convertToDTO(updated);
    }


    @Override
    public ArtistDTO addArtist(ArtistRequestDTO artistDTO) {
        ErrorHandler(artistDTO);
        //Unique name on artists
        if(findAllArtistByAttributes(artistDTO.getName(),null,null).stream()
                .anyMatch(a->a.getName().equals(artistDTO.getName())))
            throw new IllegalArgumentException("Name: "+ artistDTO.getName() +" already in use");
        Artist newArtist = mapper.fromRequestToArtist(artistDTO);
        Artist savedArtist = repository.save(newArtist);
        return mapper.convertToDTO(savedArtist);
    }

    @Override
    public void deleteArtist(Integer id) {
        Artist artistToDelete = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Artist: "+ id +" not found"));
        repository.delete(artistToDelete);
    }

    //Add an album to an existing artist
    public ArtistDTO addAlbum(Integer artistId, AlbumRequestDTO albumDTO){
        Artist artist = repository.findById(artistId)
                .orElseThrow(() -> new EntityNotFoundException("Artist: "+ artistId +" not found"));
        if(!albumDTO.getArtistName().equals(artist.getName()))
            throw new IllegalArgumentException("Artist name: "+ albumDTO.getArtistName()+
                    " not matching id: "+ artistId);
        AlbumDTO existing = albumOperations.addAlbum(albumDTO);
        Album savedAlbum = albumMapper.convertToEntity(existing);
        artist.getAlbums().add(savedAlbum);
        Artist updatedArtist = repository.save(artist);
        return mapper.convertToDTO(updatedArtist);

    }

    //Album removal from list (doesn't delete the album from database)
    public ArtistDTO removeAlbum(Integer artistId, AlbumDTO albumDTO){
        Artist artist = repository.findById(artistId)
                .orElseThrow(() -> new EntityNotFoundException("Artist: "+ artistId +" not found"));
        Album album = albumMapper.convertToEntity(albumDTO);
        artist.getAlbums().remove(album);
        Artist updatedArtist = repository.save(artist);
        return mapper.convertToDTO(updatedArtist);
    }

    @Override
    public List<ArtistDTO> findAllArtistByAttributes(String name, LocalDate dateOfBirth, String nationality) {
        Specification<Artist> spec = ArtistSpecification.getArtistByAttributes(name, dateOfBirth, nationality);
        return repository.findAll(spec).stream()
                .map(a->mapper.convertToDTO(a))
                .collect(Collectors.toList());
    }


    @Override
    public Page<ArtistDTO> findAllPaginated(Pageable pageable) {
        Page<Artist> artistsPage = repository.findAll(pageable);
        return artistsPage.map(mapper::convertToDTO);
    }


    //Argument error handling
    private void ErrorHandler(ArtistRequestDTO artistDTO) {
        if(artistDTO.getName() == null || artistDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if(artistDTO.getNationality() == null || artistDTO.getNationality().isEmpty()) {
            throw new IllegalArgumentException("Nationality cannot be empty");
        }
    }
}
