package com.aunnait.appmusic.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data //Lombok autogenerate getters/setters/toString/hash
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlbumResponseDTO {
    @Schema(description = "Unique identifier for the album", example = "0")
    private Integer id;

    @NotNull(message = "Album title cannot be null")
    @NotEmpty(message = "Album title cannot be empty")
    @Schema(description = "Title of the album", example = "Greatest Hits")
    private String title;

    @NotNull(message = "Launch year cannot be null")
    @Min(value = 1900, message = "Launch year must be a valid year")
    @Schema(description = "Year the album was launched", example = "1986")
    private Integer launchYear;

    @NotNull(message = "Description cannot be null")
    @NotEmpty(message = "Description cannot be empty")
    @Schema(description = "Brief description of the album", example = "A collection of top chart songs from the 80s.")
    private String description;

    private List<SongResponseDTO> songs;

}
