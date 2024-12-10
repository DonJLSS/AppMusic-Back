package com.aunnait.appmusic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "albums")
@Data //Lombok autogenerate getters/setters/toString/hash
@AllArgsConstructor
@NoArgsConstructor
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    private Integer launchYear;
    private String description;
    private Integer songsCount;
    private String coverUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artistId", nullable = true)
    private Artist artist;
    @OneToMany(mappedBy = "album",fetch = FetchType.LAZY , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Song> songs = new ArrayList<>();



}

