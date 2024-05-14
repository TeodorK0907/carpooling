package org.carpooling.helpers.validators;

import org.carpooling.helpers.model_filters.UserFilterOptions;

import java.util.Optional;

public class UserFilterValidator {

    public static String isFilterEmpty(Optional<String> filter) {
        return filter.orElse(null);
    }

    public static Boolean isFilterEmpty(UserFilterOptions filter) {
        return filter.getEmail().isEmpty()
                && filter.getUsername().isEmpty()
                && filter.getPhoneNumber().isEmpty();
    }
}
