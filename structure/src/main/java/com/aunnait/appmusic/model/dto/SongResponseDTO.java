package com.aunnait.appmusic.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;

@Data //Lombok autogenerate getters/setters/toString/hash
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Data Transfer Object representing a response for Song related updates")
public class SongResponseDTO {
    @Schema(description = "Unique identifier for the response", example = "0")
    private Integer id;

    @NotNull(message = "Song title cannot be null")
    @NotEmpty(message = "Song title cannot be empty")
    @Schema(description = "Title of the song", example = "This must be the place")
    private String title;

    @NotNull(message = "Song duration cannot be null")
    @NotEmpty(message = "Song duration cannot be empty")
    @Schema(description = "Duration (seconds) of the song", example = "240")
    private Long duration;  //Changed from java.time.Duration to Long bc persistence

    @NotNull(message = "Url cannot be null")
    @NotEmpty(message = "Url cannot be empty")
    @Schema(description = "URL of the song", example = "https://youtu.be/fsccjsW8bSY")
    private String songUrl;

    @Schema(description = "Name of the album the song belongs to", example = "Speaking Tongues")
    private String albumName;

    @NotNull(message = "Artist name cannot be null")
    @NotEmpty(message = "Artist name cannot be empty")
    private String artistName;

    @Schema(description = "Genres of the song", example = "Pop, Alternative Rock")
    private Set<String> genreNames;
}
