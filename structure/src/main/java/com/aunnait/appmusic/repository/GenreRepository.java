package com.aunnait.appmusic.repository;

import com.aunnait.appmusic.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GenreRepository extends JpaRepository<Genre, Integer>,
        JpaSpecificationExecutor<Genre> {
}
