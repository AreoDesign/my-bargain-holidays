package com.areo.design.holidays.component.request.httpEntity;

import com.areo.design.holidays.acl.PayloadTemplateACL;
import com.areo.design.holidays.dto.SearchCriterionDto;
import org.springframework.http.RequestEntity;

public interface RequestEntityCreator<T extends PayloadTemplateACL> {
    RequestEntity<T> create(SearchCriterionDto searchCriterionDto);
}
