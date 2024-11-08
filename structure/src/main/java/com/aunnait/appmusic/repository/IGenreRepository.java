package com.aunnait.appmusic.repository;

import com.aunnait.appmusic.model.Genre;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface IGenreRepository extends PagingAndSortingRepository<Genre, Integer> {
}
