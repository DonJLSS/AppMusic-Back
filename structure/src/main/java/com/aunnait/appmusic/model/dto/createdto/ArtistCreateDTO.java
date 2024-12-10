package com.aunnait.appmusic.model.dto.createdto;


import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class ArtistCreateDTO {

    private String name;
    private String nationality;
    private LocalDate dateOfBirth;
    private List<AlbumCreateDTO> albums;

    @Data
    public static class AlbumCreateDTO {
        private String title;
        private Integer launchYear;
        private String description;
        private List<SongRequestDTO> songs;

        @Data
        public static class SongRequestDTO {
            private String title;
            private Long duration;
            private String songUrl;
            private Set<String> genreNames;
        }
    }
}
