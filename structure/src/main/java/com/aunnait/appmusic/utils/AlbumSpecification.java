package com.aunnait.appmusic.utils;

import com.aunnait.appmusic.model.Album;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("DuplicatedCode")
public class AlbumSpecification {
    public static Specification<Album> getAlbumByAttributes(String title, Integer launchYear,
                                                            Integer songsCount, String coverUrl, String artistName,
                                                            Integer minYear, Integer maxYear, Integer minSongCount, Integer maxSongCount) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (title != null && !title.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }
            if (launchYear != null) {
                predicates.add(criteriaBuilder.equal(root.get("launchYear"), launchYear));
            }
            if (songsCount != null && songsCount != 0) {
                predicates.add(criteriaBuilder.equal(root.get("songsCount"), songsCount));
            }
            if (coverUrl != null && !coverUrl.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("coverUrl")), "%" + coverUrl.toLowerCase() + "%"));
            }
            if (artistName != null && !artistName.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("artist").get("name")), "%" + artistName.toLowerCase() + "%"));
            }
            if (minYear != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("launchYear"), minYear));
            }
            if (maxYear != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("launchYear"), maxYear));
            }
            if (minSongCount != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("songsCount"), minSongCount));
            }
            if (maxSongCount != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("songsCount"), maxSongCount));
            }


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}