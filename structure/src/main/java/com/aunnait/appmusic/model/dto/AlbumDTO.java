package com.aunnait.appmusic.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotEmpty(message = "Launch year cannot be empty")
    private Integer launchYear;

    private String description;

    @NotNull(message = "Songs count cannot be null")
    @NotEmpty(message = "Songs count cannot be empty")
    private Integer songsCount;
}
