package com.aunnait.appmusic.utils;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DynamicSearchRequest {

    private List<SortCriteria> listOrderCriteria;
    private List<SearchCriteria> listSearchCriteria;
    private Page page;

    @Data
    public static class SortCriteria {
        private String sortBy;
        @Enumerated(value = EnumType.STRING)
        private SortValue valueSortOrder;
    }
    public enum SortValue {
        ASC,
        DESC
    }


    @Data
    public static class SearchCriteria {
        private String key;
        private CriteriaOperation operation;
        private String value;
    }

    public enum CriteriaOperation {
        EQUALS,
        CONTAINS,
        GREATER_THAN,
        LESS_THAN
    }

    @Data
    public static class Page {
        private int pageIndex;
        private int pageSize;

    }
}

