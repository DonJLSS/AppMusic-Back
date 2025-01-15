package com.aunnait.appmusic.repository;

import com.aunnait.appmusic.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

public interface SongRepository extends JpaRepository<Song, Integer>,
        JpaSpecificationExecutor<Song> {
}
