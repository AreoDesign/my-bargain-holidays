package com.areo.design.holidays.component.request.entity;

import com.areo.design.holidays.component.request.creator.Request;
import com.areo.design.holidays.dto.SearchCriterionDto;
import org.springframework.http.HttpEntity;

public interface HttpEntityCreator {
    HttpEntity<String> create(SearchCriterionDto searchCriterionDto);

    HttpEntity<String> create(Request request);
}
