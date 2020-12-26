package com.areo.design.holidays.valueobjects.atomic;

import com.areo.design.holidays.dictionary.BoardType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@Builder
@RequiredArgsConstructor(staticName = "of")
public class Board {

    private final BoardType value;

    public String getAsString() {
        return value.name();
    }

    public String getAsPrettyString() {
        return value.name().toLowerCase().replaceAll("_", "-");
    }

    public BoardType getAsEnum() {
        return value;
    }

    @Override
    public String toString() {
        return "Board(" + getAsPrettyString() + ')';
    }
}
