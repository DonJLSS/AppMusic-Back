package com.aunnait.appmusic.model.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data //Lombok autogenerate getters/setters/toString/hash
@AllArgsConstructor
@NoArgsConstructor
public class ArtistDTO {
    private Integer id;
    @NotNull(message = "Artist name cannot be null")
    @NotEmpty(message = "Artist name cannot be empty")
    private String name;
    @NotNull(message = "Artist nationality cannot be null")
    @NotEmpty(message = "Artist nationality cannot be empty")
    private String nationality;
    private LocalDate dateOfBirth;
    private Integer albumsCount=0;
}
