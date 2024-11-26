package com.aunnait.appmusic.model.filters;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data //Lombok autogenerate getters/setters/toString/hash
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object representing an Artist dynamic search request")
public class ArtistSearchRequest {
    @Schema(description = "Name of the artist", example = "Fleetwood Mac")
    private String name;
    @Schema(description = "Nationality of the artist", example = "British")
    private String nationality;
    @Schema(description = "Date the artist was born", example = "1999/05/18")
    private LocalDate dateOfBirth;
    @Schema(description = "Number of albums the artist has", example = "15")
    private Integer albumsCount;
    @Schema(description = "Minimum date to filter", example = "1969/05/18")
    private LocalDate minDate;
    @Schema(description = "Maximum date to filter", example = "2007/06/10")
    private LocalDate maxDate;
    @Schema(description = "Minimum album number to filter", example = "1")
    private Integer minAlbumCount;
    @Schema(description = "Maximum album number to filter", example = "40")
    private Integer maxAlbumCount;
}
