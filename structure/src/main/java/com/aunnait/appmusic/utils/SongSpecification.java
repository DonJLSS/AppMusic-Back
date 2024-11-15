package com.aunnait.appmusic.utils;

import com.aunnait.appmusic.model.Song;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SongSpecification {

    public static Specification<Song> getArtistByAttributes(String title, Duration duration, String songUrl){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (title != null && !title.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"));
            }
            if (duration != null) {
                predicates.add(criteriaBuilder.equal(root.get("duration"), duration));
            }
            if (songUrl != null && !songUrl.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("songUrl")),
                        "%" + songUrl.toLowerCase() + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
