package com.areo.design.holidays.component.request;

import com.areo.design.holidays.acl.PayloadTemplateACL;
import com.areo.design.holidays.acl.ResponseACL;
import com.areo.design.holidays.dto.requestor.SearchCriterionDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;

public interface Request {
    Request initialize(SearchCriterionDto criterion);

    void incrementPagination();

    <PAYLOAD extends PayloadTemplateACL> RequestEntity<PAYLOAD> getRequestEntity();

    <RES extends ResponseACL> ParameterizedTypeReference<RES> getResponseType();
}
