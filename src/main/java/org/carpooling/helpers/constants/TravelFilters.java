package org.carpooling.helpers.constants;

import java.time.LocalDateTime;

public class TravelFilters {
    public static final String EMPTY_STRING_FILTER = "";
    public static final LocalDateTime EMPTY_DATE_AFTER = LocalDateTime.of(
            1900, 1, 1, 0, 0);
    public static final LocalDateTime EMPTY_DATE_BEFORE = LocalDateTime.of(
            2200, 1, 1, 0, 0);
    public static final int EMPTY_FREE_SPOTS = 0;
}
