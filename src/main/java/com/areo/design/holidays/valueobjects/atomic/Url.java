package com.areo.design.holidays.valueobjects.atomic;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;

@EqualsAndHashCode
@Builder
@RequiredArgsConstructor(staticName = "of")
public class Url {

    @URL
    private final String address;

    public String getAsString() {
        return address;
    }

    @Override
    public String toString() {
        return "Url(" + address + ')';
    }
}
