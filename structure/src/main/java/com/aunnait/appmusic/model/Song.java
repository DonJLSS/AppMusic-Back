package com.aunnait.appmusic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "songs")
@Data   //Lombok autogenerate getters/setters/toString/hash
@AllArgsConstructor
@NoArgsConstructor
public class Song {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;
    private String title;
    private Long duration; //Changed to long bc persistence
    //Foreign key
    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;
    private String songUrl;

    @ManyToMany
    @JoinTable(
            name = "song_genre",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();
}
