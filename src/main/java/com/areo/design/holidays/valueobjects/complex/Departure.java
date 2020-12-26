package com.areo.design.holidays.valueobjects.complex;

import com.areo.design.holidays.dictionary.City;
import com.areo.design.holidays.valueobjects.atomic.DateAndTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

@EqualsAndHashCode
@Builder
@RequiredArgsConstructor(staticName = "of")
public class Departure {
    private final DateAndTime departureTime;
    private final City departureCity;

    public DateAndTime getDepartureTime() {
        return departureTime;
    }

    public City getDepartureCity() {
        return departureCity;
    }

    public LocalDateTime getDepartureTimeAsLocalDateTime() {
        return departureTime.getAsLocalDateTime();
    }

    public String getDepartureCityAsString() {
        return departureCity.name();
    }

    @Override
    public String toString() {
        return "Departure[" +
                " " + departureCity +
                " " + departureTime.getAsLocalDateTime().format(getCustomFormatter()) +
                ']';
    }

    private DateTimeFormatter getCustomFormatter() {
        return new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(ISO_LOCAL_DATE)
                .appendLiteral(' ')
                .append(ISO_LOCAL_TIME)
                .toFormatter();
    }
}
