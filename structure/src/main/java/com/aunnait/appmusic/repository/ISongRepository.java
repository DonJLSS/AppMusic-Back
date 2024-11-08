package com.aunnait.appmusic.repository;

import com.aunnait.appmusic.model.Song;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface ISongRepository extends PagingAndSortingRepository<Song, Integer> {

}
