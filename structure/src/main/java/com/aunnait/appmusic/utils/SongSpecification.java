package com.aunnait.appmusic.utils;

import com.aunnait.appmusic.model.Album;
import com.aunnait.appmusic.model.Artist;
import com.aunnait.appmusic.model.Song;
import com.aunnait.appmusic.model.filters.DynamicSearchRequest;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SongSpecification {

    public static Specification<Song> getSongByAttributes(String title, String artistName, String albumName, Long duration) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (title != null && !title.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }
            if (artistName != null && !artistName.isEmpty()) {
                Join<Song, Artist> artistJoin = root.join("artist");
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(artistJoin.get("name")), "%" + artistName.toLowerCase() + "%"));
            }
            if (albumName != null && !albumName.isEmpty()) {
                Join<Song, Album> albumJoin = root.join("album");
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(albumJoin.get("title")), "%" + albumName.toLowerCase() + "%"));
            }
            if (duration != null) {
                predicates.add(criteriaBuilder.equal(root.get("duration"), duration));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    @SuppressWarnings("DuplicatedCode")
    public static Specification<Song> getSongSpecification(String key, DynamicSearchRequest.CriteriaOperation operation, String value) {
        return (root, query, criteriaBuilder) -> {
            Path<?> path;

            if (key.contains(".")) {
                String[] parts = key.split("\\.");
                path = root.join(parts[0]).get(parts[1]);
            } else {
                path = root.get(key);
            }
            switch (operation) {
                case EQUALS:
                    return criteriaBuilder.equal(path, value);
                case CONTAINS:
                    return criteriaBuilder.like(criteriaBuilder.lower(path.as(String.class)), "%" + value.toLowerCase() + "%");
                case GREATER_THAN:
                    return criteriaBuilder.greaterThan(path.as(String.class), value);
                case LESS_THAN:
                    return criteriaBuilder.lessThan(path.as(String.class), value);
                default:
                    throw new IllegalArgumentException("Invalid operation: " + operation);
            }
        };
    }

}
