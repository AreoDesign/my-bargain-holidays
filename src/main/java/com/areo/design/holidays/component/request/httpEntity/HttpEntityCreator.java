package com.areo.design.holidays.component.request.httpEntity;

import com.areo.design.holidays.acl.PayloadTemplateACL;
import com.areo.design.holidays.dto.SearchCriterionDto;
import org.springframework.http.HttpEntity;

public interface HttpEntityCreator {
    <T extends PayloadTemplateACL> HttpEntity<T> create(SearchCriterionDto searchCriterionDto);
}
