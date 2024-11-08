package com.aunnait.appmusic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.aunnait.appmusic.model.Album;

public interface AlbumRepository extends JpaRepository<Album, Integer> {
}
