package com.areo.design.holidays.service.request.payload;

import com.areo.design.holidays.dto.SearchCriterionDto;

public interface PayloadPreparator<V extends Payload> {
    V prepare(SearchCriterionDto searchCriterionDto);

    V prepareNext(V payload);
}
