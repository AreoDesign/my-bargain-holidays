package com.areo.design.holidays.service.request.strategy;

import com.areo.design.holidays.dto.SearchCriterionDto;

public interface RequestCreator {
    Request create(SearchCriterionDto criterion);

    Request createNext(Request request);
}
