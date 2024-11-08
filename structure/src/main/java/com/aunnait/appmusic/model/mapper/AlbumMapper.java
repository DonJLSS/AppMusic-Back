package com.aunnait.appmusic.model.mapper;

import com.aunnait.appmusic.model.Album;
import com.aunnait.appmusic.model.dto.AlbumDTO;
import org.springframework.stereotype.Component;

@Component
public class AlbumMapper {
   public AlbumDTO toAlbumDTO(Album album) {
       AlbumDTO albumDTO = new AlbumDTO();
       albumDTO.setId(album.getId());
       albumDTO.setDescription(album.getDescription());
       albumDTO.setTitle(album.getTitle());
       albumDTO.setSongsCount(album.getSongsCount());
       albumDTO.setLaunchYear(album.getLaunchYear());
       return albumDTO;
   }
    public Album convertToEntity(AlbumDTO albumDTO) {
        Album album = new Album();
        album.setId(albumDTO.getId());
        album.setTitle(albumDTO.getTitle());
        album.setLaunchYear(albumDTO.getLaunchYear());
        album.setDescription(albumDTO.getDescription());
        album.setSongsCount(albumDTO.getSongsCount());
        return album;
    }
}
