package com.aunnait.appmusic.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data //Lombok autogenerate getters/setters/toString/hash
@AllArgsConstructor
@NoArgsConstructor
public class SongDTO {
    private Integer id;

    @NotNull(message = "Song title cannot be null")
    @NotEmpty(message = "Song title cannot be empty")
    private String title;

    @NotNull(message = "Song duration cannot be null")
    @NotEmpty(message = "Song duration cannot be empty")
    private Long duration;  //Changed from java.time.Duration to Long bc persistence

    @NotNull(message = "Url cannot be null")
    @NotEmpty(message = "Url cannot be empty")
    private String songUrl;

    @NotNull(message = "Album name cannot be null")
    @NotEmpty(message = "Album name cannot be empty")
    private String albumName;


}
