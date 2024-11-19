package com.aunnait.appmusic.model.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Lombok autogenerate getters/setters/toString/hash
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object representing a Genre")
public class GenreDTO {
    @Schema(description = "Unique identifier for the genre", example = "0")
    private Integer id;
    @NotNull(message = "Genre name cannot be null")
    @NotEmpty(message = "Genre name cannot be empty")
    @Schema(description = "Name of the genre", example = "R&B")
    private String name;
    @Schema(description = "Brief description of the genre", example = "A great mix known as Rhythm and Blues")
    private String description;
    @NotNull(message = "Year cannot be null")
    @NotEmpty(message = "Year cannot be empty")
    @Schema(description = "Year the genre was created", example = "1940")
    private Integer yearOfOrigin;
}
