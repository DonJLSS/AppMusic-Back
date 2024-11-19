package com.aunnait.appmusic.model.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data //Lombok autogenerate getters/setters/toString/hash
@AllArgsConstructor
@NoArgsConstructor@Schema(description = "Data Transfer Object representing an Artist")
public class ArtistDTO {
    @Schema(description = "Unique identifier for the artist", example = "0")
    private Integer id;
    @NotNull(message = "Artist name cannot be null")
    @NotEmpty(message = "Artist name cannot be empty")
    @Schema(description = "Name of the artist", example = "Fleetwood Mac")
    private String name;
    @NotNull(message = "Artist nationality cannot be null")
    @NotEmpty(message = "Artist nationality cannot be empty")
    @Schema(description = "Nationality of the artist", example = "British")
    private String nationality;
    @Schema(description = "Date the artist was born", example = "1999/05/18")
    private LocalDate dateOfBirth;
    @Schema(description = "Number of albums the artist has", example = "15",  defaultValue = "0")
    private Integer albumsCount=0;
}
