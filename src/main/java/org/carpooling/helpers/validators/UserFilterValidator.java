package org.carpooling.helpers.validators;

import org.carpooling.helpers.constants.attribute_constants.UserAttribute;
import org.carpooling.helpers.model_filters.UserFilterOptions;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public class UserFilterValidator {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final String USERNAME = "username";
    private static final String EMAIL = "email";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String DESC = "desc";

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

    public static String getSort(Optional<String> sortBy) {
        String sort;
        if (sortBy.isEmpty()) {
            return UserAttribute.ID.toString();
        }

        switch (sortBy.get()) {
            case USERNAME:
                sort = USERNAME;
                break;
            case EMAIL:
                sort = EMAIL;
                break;
            case PHONE_NUMBER:
                sort = PHONE_NUMBER;
                break;
            default:
                sort = UserAttribute.ID.toString();
        }
        return sort;
    }

    public static Sort.Direction getOrder(Optional<String> orderBy) {
        if (orderBy.isEmpty()) {
            return Sort.Direction.ASC;
        }
        if (orderBy.get().equalsIgnoreCase(DESC)) {
            return Sort.Direction.DESC;
        } else {
            return Sort.Direction.ASC;
        }
    }

    public static Boolean isFilterEmpty(UserFilterOptions filter) {
        return filter.getEmail().isEmpty()
                && filter.getUsername().isEmpty()
                && filter.getPhoneNumber().isEmpty();
    }
}
