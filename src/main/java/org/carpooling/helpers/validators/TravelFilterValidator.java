package org.carpooling.helpers.validators;

import org.carpooling.helpers.constants.attribute_constants.TravelAttributes;
import org.carpooling.helpers.model_filters.TravelFilterOptions;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public class TravelFilterValidator {

    private static final String DRIVER_NAME = "driver";
    private static final String USERNAME = "creator.username";
    private static final String TIME = "departureTime";
    private static final String FREE_SPOTS = "freeSpots";
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final String DESC = "desc";

    public static Boolean isFilterEmpty(TravelFilterOptions filter) {
        return filter.getStartLocation().isEmpty()
                && filter.getEndLocation().isEmpty()
                && filter.getDriver().isEmpty()
                && filter.getDepartAfter().isEmpty()
                && filter.getDepartBefore().isEmpty()
                && filter.getFreeSpots().isEmpty();
    }



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
            return TravelAttributes.ID.toString();
        }

        switch (sortBy.get()) {
            case DRIVER_NAME:
                sort = USERNAME;
                break;
            case TIME:
                sort = TIME;
                break;
            case FREE_SPOTS:
                sort = FREE_SPOTS;
                break;
            default:
                sort = TravelAttributes.ID.toString();
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
}