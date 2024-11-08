package com.aunnait.appmusic.repository;

import com.aunnait.appmusic.model.Album;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
@NoRepositoryBean
public interface IAlbumRepository extends PagingAndSortingRepository<Album, Integer> {
}
