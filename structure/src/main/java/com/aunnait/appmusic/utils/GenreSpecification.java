package com.aunnait.appmusic.utils;

import com.aunnait.appmusic.model.Genre;
import com.aunnait.appmusic.model.filters.DynamicSearchRequest;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class GenreSpecification {

    public static Specification<Genre> getGenreByAttributes(String name, Integer yearOfOrigin,
                                                            String description, Integer minYear, Integer maxYear) {
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
            if (minYear != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("yearOfOrigin"), minYear));
            }
            if (maxYear != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("yearOfOrigin"), maxYear));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Genre> getGenreSpecification(String key, DynamicSearchRequest.CriteriaOperation operation, String value) {
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
