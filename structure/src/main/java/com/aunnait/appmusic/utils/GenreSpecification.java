package com.aunnait.appmusic.utils;

import com.aunnait.appmusic.model.Genre;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class GenreSpecification {

    public static Specification<Genre> getGenreByAttributes(String name, Integer yearOfOrigin,
                                                            String description) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (yearOfOrigin != null) {
                predicates.add(criteriaBuilder.equal(root.get("yearOfOrigin"), yearOfOrigin));
            }
            if (description != null && !description.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + description.toLowerCase() + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
