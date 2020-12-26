package com.areo.design.holidays.valueobjects.atomic;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static java.lang.Math.toIntExact;

@EqualsAndHashCode
@RequiredArgsConstructor(staticName = "of")
public class Duration {

    private final java.time.Duration duration;

    @Builder
    public Duration(@NotNull @Min(value = 1, message = "Duration cannot be shorter than 1 day.") Integer days) {
        this.duration = java.time.Duration.ofDays(days);
    }

    public Integer getAsInteger() {
        return toIntExact(duration.toDays());
    }

    @Override
    public String toString() {
        return "Duration(" + duration.toDays() + " days)";
    }
}
