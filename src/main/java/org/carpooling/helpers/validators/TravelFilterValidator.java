package org.carpooling.helpers.validators;

import org.carpooling.helpers.model_filters.TravelFilterOptions;

public class TravelFilterValidator {


    public static Boolean isFilterEmpty(TravelFilterOptions filter) {
        return filter.getStartLocation().isEmpty()
                && filter.getEndLocation().isEmpty()
                && filter.getDriver().isEmpty()
                && filter.getDepartAfter().isEmpty()
                && filter.getDepartBefore().isEmpty()
                && filter.getFreeSpots().isEmpty();
    }
}
