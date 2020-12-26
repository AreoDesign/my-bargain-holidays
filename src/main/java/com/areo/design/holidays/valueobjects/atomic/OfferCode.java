package com.areo.design.holidays.valueobjects.atomic;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@Builder
@RequiredArgsConstructor(staticName = "of")
public class OfferCode {

    private final String value;

    public String getAsString() {
        return value;
    }

    @Override
    public String toString() {
        return "OfferCode(" + value + ')';
    }
}
