package com.aunnait.appmusic.repository;

import com.aunnait.appmusic.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface IArtistRepository extends PagingAndSortingRepository<Artist, Integer> {
}
