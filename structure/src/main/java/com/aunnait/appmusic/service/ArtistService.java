package com.aunnait.appmusic.service;

import com.aunnait.appmusic.model.Artist;
import com.aunnait.appmusic.model.dto.ArtistDTO;
import com.aunnait.appmusic.model.mapper.ArtistMapper;
import com.aunnait.appmusic.repository.ArtistRepository;
import com.aunnait.appmusic.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.aunnait.appmusic.utils.ArtistSpecification;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistService implements IArtistService{

    @Autowired
    ArtistRepository repository;
    @Autowired
    ArtistMapper mapper;

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
    public ArtistDTO updateArtist(Integer id, ArtistDTO artistDTO) {
        Artist artistToUpdate = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Artist: "+ id +" not found"));
        ErrorHandler(artistDTO);
        artistToUpdate.setName(artistDTO.getName());
        artistToUpdate.setNationality(artistDTO.getNationality());

        Artist updated = repository.save(artistToUpdate);
        return mapper.convertToDTO(updated);
    }


    @Override
    public ArtistDTO addArtist(ArtistDTO artistDTO) {
        ErrorHandler(artistDTO);
        Artist newArtist = mapper.convertToEntity(artistDTO);
        Artist savedArtist = repository.save(newArtist);
        return mapper.convertToDTO(savedArtist);
    }

    @Override
    public void deleteArtist(Integer id) {
        Artist artistToDelete = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Artist: "+ id +" not found"));
        repository.delete(artistToDelete);
    }

    @Override
    public List<ArtistDTO> findAllArtistByAttributes(String name, LocalDate dateOfBirth, String nationality) {
        Specification<Artist> spec = ArtistSpecification.getArtistByAttributes(name, dateOfBirth, nationality);
        return repository.findAll(spec).stream()
                .map(a->mapper.convertToDTO(a))
                .collect(Collectors.toList());
    }

    //Argument error handling
    private void ErrorHandler(ArtistDTO artistDTO) {
        if(artistDTO.getName() == null || artistDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if(artistDTO.getNationality() == null || artistDTO.getNationality().isEmpty()) {
            throw new IllegalArgumentException("Nationality cannot be empty");
        }
    }
}
