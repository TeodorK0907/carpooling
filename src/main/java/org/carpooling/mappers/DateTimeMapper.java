package org.carpooling.mappers;

import org.carpooling.helpers.validators.DateTimeValidator;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateTimeMapper {
    private static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT);
    public DateTimeMapper() {

    }

    public LocalDateTime toLocalDateTime(String pattern) {
        DateTimeValidator.isTimeFormatValid(pattern);
        return LocalDateTime.parse(pattern, formatter);
    }
}
