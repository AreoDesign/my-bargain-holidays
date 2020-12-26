package com.areo.design.holidays.valueobjects.atomic;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@EqualsAndHashCode
@Builder
@RequiredArgsConstructor(staticName = "of")
public class Date {

    private final LocalDate value;

    public LocalDate getAsLocalDate() {
        return value;
    }

    public LocalDateTime getAsLocalDateTime() {
        return LocalDateTime.of(value, LocalTime.MIDNIGHT);
    }

    @Override
    public String toString() {
        return "Date(" + value + ')';
    }
}
