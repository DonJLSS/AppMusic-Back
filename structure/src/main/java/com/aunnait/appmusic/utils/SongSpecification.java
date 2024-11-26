package com.aunnait.appmusic.utils;

import com.aunnait.appmusic.model.Song;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SongSpecification {

    public static Specification<Song> getSongByAttributes(String title, Long duration, String songUrl,
                                                          Long minDuration, Long maxDuration, String artistName, String albumName){
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (title != null && !title.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"));
            }

            if (duration != null) {
                predicates.add(criteriaBuilder.equal(root.get("duration"), duration));
            }

            if (minDuration != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("duration"), minDuration));
            }

            if (maxDuration != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("duration"), maxDuration));
            }

            if (songUrl != null && !songUrl.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("songUrl")),
                        "%" + songUrl.toLowerCase() + "%"));
            }
            if (artistName != null && !artistName.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.join("artist").get("name")),
                        "%" + artistName.toLowerCase() + "%"));
            }

            if (albumName != null && !albumName.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.join("album").get("title")),
                        "%" + albumName.toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
