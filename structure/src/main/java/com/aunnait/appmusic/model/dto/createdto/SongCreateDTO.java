package com.aunnait.appmusic.model.dto.createdto;

import com.aunnait.appmusic.model.dto.ArtistRequestDTO;
import com.aunnait.appmusic.model.dto.GenreDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongCreateDTO {
    private String title;
    private Long duration;
    private String songUrl;
    private Integer artistId;
    private ArtistRequestDTO newArtist;
    private Integer albumId;
    private Set<Integer> genreIds;
    private Set<GenreDTO> newGenres;

}