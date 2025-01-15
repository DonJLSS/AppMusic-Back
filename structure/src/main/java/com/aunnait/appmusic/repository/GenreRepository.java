package com.aunnait.appmusic.repository;

import com.aunnait.appmusic.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Integer>,
        JpaSpecificationExecutor<Genre> {
    Optional<Genre> findByName(String name);
}
