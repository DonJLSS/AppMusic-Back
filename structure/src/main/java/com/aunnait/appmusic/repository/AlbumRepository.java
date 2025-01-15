package com.aunnait.appmusic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.aunnait.appmusic.model.Album;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

public interface AlbumRepository extends JpaRepository<Album, Integer>,
        JpaSpecificationExecutor<Album> {
}
