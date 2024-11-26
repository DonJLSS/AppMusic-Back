package com.aunnait.appmusic.model.filters;


import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Lombok autogenerate getters/setters/toString/hash
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object representing an Album dynamic search request")
public class AlbumSearchRequest {
    @Schema(description = "Title of the Album", example = "Thriller")
    private String title;

    @Schema(description = "Year the album was launched", example = "1986")
    private Integer launchYear;

    @Schema(description = "Brief description of the album", example = "A collection of top chart songs from the 80s.")
    private String description;

    @Schema(description = "Url of the Album cover", example = "example/example.png")
    private String coverUrl;

    @Schema(description = "Name of the artist who created the album", example = "Talking Heads")
    private String artistName;

    @Schema(description = "Number of songs the Album has", example = "12")
    private Integer songsCount;

    @Schema(description = "Minimum year for the filter", example = "1960")
    private Integer minYear;
    @Schema(description = "Maximum year for the filter", example = "2024")
    private Integer maxYear;
    @Schema(description = "Minimum song count for the filter", example = "1")
    private Integer minSongCount;
    @Schema(description = "Maximum song count for the filter", example = "25")
    private Integer maxSongCount;

    @Schema(description = "Specifies the attribute to sort by", example = "title")
    private String sortBy;

    @Schema(description = "Specifies the direction of the sorting", example = "true")
    private boolean ascending;

}
