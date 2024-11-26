package com.aunnait.appmusic.model.filters;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Lombok autogenerate getters/setters/toString/hash
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object representing a Song dynamic search request")
public class SongSearchRequest {


    @Schema(description = "Title of the Song", example = "Rosanna")
    private String title;

    @Schema(description = "Name of the artist", example = "Toto")
    private String artistName;

    @Schema(description = "Name of the album", example = "Toto IV")
    private String albumName;

    @Schema(description = "Duration of the Song in seconds", example = "240")
    private Long duration;

    @Schema(description = "URL fo the Song", example = "https://youtu.be/4G0uhL6W2_k")
    private String songUrl;

    @Schema(description = "Minimum duration filter", example = "45")
    private Long minDuration; //In seconds

    @Schema(description = "Maximum duration filter", example = "300")
    private Long maxDuration; //In seconds

    @Schema(description = "Specifies the attribute to sort by", example = "title")
    private String sortBy;

    @Schema(description = "Specifies the direction of the sorting", example = "true")
    private boolean ascending;
}
