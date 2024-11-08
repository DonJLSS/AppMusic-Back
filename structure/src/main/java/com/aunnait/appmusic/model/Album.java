package com.aunnait.appmusic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;


import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data //Lombok autogenerate getters/setters/toString/hash
@AllArgsConstructor
@NoArgsConstructor
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    private Integer launchYear;
    //Foreign key
    private Integer artistId;
    private String description;
    private Integer songsCount;
    private String coverUrl;


}

