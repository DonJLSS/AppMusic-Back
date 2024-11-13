package com.aunnait.appmusic.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Lombok autogenerate getters/setters/toString/hash
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDTO {
    private Integer id;

    @NotNull(message = "Album title cannot be null")
    @NotEmpty(message = "Album title cannot be empty")
    private String title;

    @NotNull(message = "Launch year cannot be null")
    @Min(value = 1900, message = "Launch year must be a valid year")
    private Integer launchYear;

    @NotNull(message = "Description cannot be null")
    @NotEmpty(message = "Description cannot be empty")
    private String description;

    @NotNull(message = "Songs count cannot be null")
    @Positive(message = "Songs count must be a positive number")
    private Integer songsCount;

    @NotNull(message = "Artist name cannot be null")
    @NotEmpty(message = "Artist name cannot be empty")
    private String artistName;

}
