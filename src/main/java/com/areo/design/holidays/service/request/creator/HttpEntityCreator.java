package com.areo.design.holidays.service.request.creator;

import com.areo.design.holidays.dto.SearchCriterionDto;
import org.springframework.http.HttpEntity;

public interface HttpEntityCreator {
    HttpEntity<String> create(SearchCriterionDto searchCriterionDto);

    HttpEntity<String> createNext(HttpEntity<String> httpEntity);
}
