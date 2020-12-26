package com.areo.design.holidays.valueobjects.atomic;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@Builder
@RequiredArgsConstructor(staticName = "of")
public class HotelOpinionScore {

    private final Double value;

    public String getAsString() {
        return String.valueOf(value);
    }

    public Double getAsDouble() {
        return value;
    }

    @Override
    public String toString() {
        return "HotelOpinionScore(" + value + ')';
    }
}
