package com.aunnait.appmusic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artists")
@Data //Lombok autogenerate getters/setters/toString/hash
@ToString(exclude = "songs") //Avoids StackOverflow errors
@AllArgsConstructor
@NoArgsConstructor
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private LocalDate dateOfBirth;
    private String nationality;

    //Artist may have 0-N albums
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Album> albums = new ArrayList<>();

    //Artist may have 0-N songs
    @OneToMany(mappedBy = "artist",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Song> songs = new ArrayList<>();



}
