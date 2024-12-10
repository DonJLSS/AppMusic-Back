package com.aunnait.appmusic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "songs")
@Data   //Lombok autogenerate getters/setters/toString/hash
@ToString(exclude = "artist") //Avoids StackOverflow errors
@AllArgsConstructor
@NoArgsConstructor
public class Song {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;
    private String title;
    private Long duration; //Changed to long bc persistence
    //Foreign key
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "albumId", nullable = true)
    private Album album;
    private String songUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "artistId", nullable = false)
    private Artist artist;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "song_genre",
            joinColumns = @JoinColumn(name = "songId"),
            inverseJoinColumns = @JoinColumn(name = "genreId")
    )
    private Set<Genre> genres = new HashSet<>();
}
