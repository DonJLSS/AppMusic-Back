package com.aunnait.appmusic.model.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Lombok autogenerate getters/setters/toString/hash
@AllArgsConstructor
@NoArgsConstructor
public class GenreDTO {
    private Integer id;
    @NotNull(message = "Genre name cannot be null")
    @NotEmpty(message = "Genre name cannot be empty")
    private String name;
    private String description;
    @NotNull(message = "Year cannot be null")
    @NotEmpty(message = "Year cannot be empty")
    private Integer yearOfOrigin;
}
