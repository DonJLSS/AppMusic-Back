package com.aunnait.appmusic.repository;

import com.aunnait.appmusic.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Integer> {
}
