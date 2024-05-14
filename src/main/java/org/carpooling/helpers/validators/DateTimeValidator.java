package org.carpooling.helpers.validators;

import org.carpooling.exceptions.BadRequestException;
import org.carpooling.helpers.errors.TravelDtoErrors;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTimeValidator {
    private static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    private static final SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_TIME_FORMAT);

    public static boolean isTimeFormatValid(String pattern) {
        try {
            formatter.parse(pattern);
            return true;
        } catch (ParseException e) {
            throw new BadRequestException(TravelDtoErrors.TIME);
        }
    }
}
