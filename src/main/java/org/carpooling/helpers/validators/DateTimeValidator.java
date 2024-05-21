package org.carpooling.helpers.validators;

import org.carpooling.exceptions.BadRequestException;
import org.carpooling.helpers.errors.TravelDtoErrors;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeValidator {
    private static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern (DEFAULT_TIME_FORMAT);

    public static boolean isTimeFormatValid(String pattern) {
        try {
            formatter.parse(pattern);
            return true;
        } catch (DateTimeParseException e) {
            throw new BadRequestException(TravelDtoErrors.DATE_TIME);
        }
    }
}
