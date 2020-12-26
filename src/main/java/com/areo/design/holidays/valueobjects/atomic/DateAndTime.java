package com.areo.design.holidays.valueobjects.atomic;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode
@Builder
@RequiredArgsConstructor(staticName = "of")
public class DateAndTime {
    private final LocalDateTime value;

    public LocalDateTime getAsLocalDateTime() {
        return value;
    }

    public LocalDate getAsLocalDate() {
        return value.toLocalDate();
    }

    @Override
    public String toString() {
        return "DateAndTime(" + value + ')';
    }
}
