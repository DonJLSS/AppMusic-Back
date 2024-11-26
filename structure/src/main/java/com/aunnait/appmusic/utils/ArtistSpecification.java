package com.aunnait.appmusic.utils;

import com.aunnait.appmusic.model.Artist;
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
}
