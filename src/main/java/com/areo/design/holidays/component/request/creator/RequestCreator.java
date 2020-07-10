package com.areo.design.holidays.component.request.creator;

import com.areo.design.holidays.dto.SearchCriterionDto;

public interface RequestCreator {
    Request create(SearchCriterionDto criterion);

    Request create(Request request);
}
