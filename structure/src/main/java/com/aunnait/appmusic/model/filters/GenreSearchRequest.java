package com.aunnait.appmusic.model.filters;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Lombok autogenerate getters/setters/toString/hash
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object representing a Genre dynamic search request")
public class GenreSearchRequest {

    @Schema(description = "Name of the genre", example = "R&B")
    private String name;
    @Schema(description = "Brief description of the genre", example = "A great mix known as Rhythm and Blues")
    private String description;
    @Schema(description = "Year the genre was created", example = "1940")
    private Integer yearOfOrigin;
    @Schema(description = "Bottom year filter", example = "1920")
    private Integer minYear;
    @Schema(description = "Top year filter", example = "2020")
    private Integer maxYear;

    @Schema(description = "Specifies the attribute to sort by", example = "name")
    private String sortBy;

    @Schema(description = "Specifies the direction of the sorting", example = "true")
    private boolean ascending;
}
