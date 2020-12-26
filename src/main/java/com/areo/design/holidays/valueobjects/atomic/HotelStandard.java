package com.areo.design.holidays.valueobjects.atomic;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@EqualsAndHashCode
@Builder
@RequiredArgsConstructor(staticName = "of")
public class HotelStandard {

    private static final String STAR = "*";

    @Min(value = 0, message = "Hotel standard cannot be less than no star")
    @Max(value = 7, message = "Hotel standard cannot exceed ******* (7 stars)")
    private final Double value;

    public String getAsString() {
        return String.valueOf(value);
    }

    public String getAsStars() {
        int starCount = value.intValue();
        boolean plus = (value / starCount) > 1;
        return STAR.repeat(starCount).concat(isEqualOrMoreThanHalfStar(plus));
    }

    public Double getAsDouble() {
        return value;
    }

    private String isEqualOrMoreThanHalfStar(boolean plus) {
        return plus ? "+" : "";
    }

    @Override
    public String toString() {
        return "HotelStandard(" + getAsStars() + ')';
    }
}
