package com.areo.design.holidays.valueobjects.atomic;

import com.areo.design.holidays.dictionary.State;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@Builder
@RequiredArgsConstructor(staticName = "of")
public class CriterionState {

    private final State value;

    public State getValue() {
        return value;
    }

    public String getAsLowerCaseString() {
        return value.name().toLowerCase();
    }

    @Override
    public String toString() {
        return "CriterionState(" + getAsLowerCaseString() + ')';
    }

}
