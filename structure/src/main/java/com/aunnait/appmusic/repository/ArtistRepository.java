package com.aunnait.appmusic.repository;

import com.aunnait.appmusic.model.Artist;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {
}
