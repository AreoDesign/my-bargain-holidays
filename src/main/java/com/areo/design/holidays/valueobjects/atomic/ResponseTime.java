package com.areo.design.holidays.valueobjects.atomic;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

@EqualsAndHashCode
@Builder
@RequiredArgsConstructor(staticName = "of")
public class ResponseTime {

    @PastOrPresent
    private final LocalDateTime responseHeaderTime;

    public LocalDateTime toLocalDateTime() {
        return responseHeaderTime;
    }

    @Override
    public String toString() {
        return "ResponseTime(" + responseHeaderTime.format(getCustomFormatter()) + ')';
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
