package com.aunnait.appmusic.utils;

import com.aunnait.appmusic.model.Artist;
import com.aunnait.appmusic.model.filters.DynamicSearchRequest;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArtistSpecification {

    public static Specification<Artist> getArtistByAttributes(String name, LocalDate dateOfBirth, String nationality,
                                                              Integer albumsCount,LocalDate minDate,LocalDate maxDate,
                                                              Integer minAlbumCount,Integer maxAlbumCount) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (dateOfBirth != null) {
                predicates.add(criteriaBuilder.equal(root.get("dateOfBirth"), dateOfBirth));
            }
            if (nationality != null && !nationality.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nationality")), "%" + nationality.toLowerCase() + "%"));
            }
            if (albumsCount != null && albumsCount != 0) {
                predicates.add(criteriaBuilder.equal(root.get("albumsCount"), albumsCount));
            }
            if (minAlbumCount != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("minAlbumCount"), minAlbumCount));
            }
            if (maxAlbumCount != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("maxAlbumCount"), maxAlbumCount));
            }
            if (minDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dateOfBirth"), minDate));
            }
            if (maxDate != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dateOfBirth"), maxDate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
    public static Specification<Artist> getArtistSpecification(String key, DynamicSearchRequest.CriteriaOperation operation, String value) {
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
