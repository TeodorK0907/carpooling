package org.carpooling.helpers.validators;

import org.carpooling.helpers.model_filters.UserFilterOptions;

import java.util.Optional;


public class UserFilterValidator {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;

    public static int getPageNum(Optional<Integer> pageNum) {
        if (pageNum.isEmpty()) {
            return DEFAULT_PAGE;
        }
        return pageNum.get() - 1;
    }

    public static int getPageSize(Optional<Integer> pageSize) {
        if (pageSize.isEmpty()) {
            return DEFAULT_PAGE_SIZE;
        }
        return pageSize.get();
    }

    public static Boolean isFilterEmpty(UserFilterOptions filter) {
        return filter.getEmail().isEmpty()
                && filter.getUsername().isEmpty()
                && filter.getPhoneNumber().isEmpty();
    }
}
