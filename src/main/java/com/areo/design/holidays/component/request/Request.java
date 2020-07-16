package com.areo.design.holidays.component.request;

import com.areo.design.holidays.acl.PayloadTemplateACL;
import com.areo.design.holidays.acl.impl.rainbow.RainbowPayloadTemplateACL;
import com.areo.design.holidays.dto.SearchCriterionDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.net.URI;

public interface Request {
    Request initialize(SearchCriterionDto criterion);

    Request incrementPagination();

    URI getUri();

    HttpMethod getHttpMethod();

    <T extends PayloadTemplateACL> HttpEntity<RainbowPayloadTemplateACL> getHttpEntity();

    <T> ParameterizedTypeReference<T> getResponseType();
}
