package com.areo.design.holidays.service.request.payload;

import com.areo.design.holidays.dto.SearchCriterionDto;
import com.areo.design.holidays.service.translator.Translable;

public interface PayloadPreparator<V extends Payload> {
    V prepare(SearchCriterionDto searchCriterionDto);

    V prepareNext(V payload);

    Translable getTranslator();
}
