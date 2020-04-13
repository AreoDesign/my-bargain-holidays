package com.areo.design.holidays.service.request.creator;

import com.areo.design.holidays.dto.SearchCriterionDto;
import org.springframework.http.HttpEntity;

public interface HttpEntityCreator {
    <T> HttpEntity<T> create(SearchCriterionDto searchCriterionDto);
}
