package com.areo.design.holidays.component.request.httpentity;

import com.areo.design.holidays.acl.PayloadTemplateACL;
import com.areo.design.holidays.valueobjects.requestor.SearchCriterion;
import org.springframework.http.RequestEntity;

public interface RequestEntityCreator<T extends PayloadTemplateACL> {
    RequestEntity<T> create(SearchCriterion searchCriterion);
}
