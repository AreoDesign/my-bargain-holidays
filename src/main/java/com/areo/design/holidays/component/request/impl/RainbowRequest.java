package com.areo.design.holidays.component.request.impl;

import com.areo.design.holidays.acl.impl.rainbow.RainbowPayloadTemplateACL;
import com.areo.design.holidays.acl.impl.rainbow.RainbowResponseACL;
import com.areo.design.holidays.component.request.Request;
import com.areo.design.holidays.component.request.httpentity.RequestEntityCreator;
import com.areo.design.holidays.dto.requestor.SearchCriterionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;

import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
public class RainbowRequest implements Request {

    private final RequestEntityCreator rainbowRequestEntityCreator;

    private RequestEntity<RainbowPayloadTemplateACL> requestEntity;

    @Override
    public Request initialize(SearchCriterionDto criterion) {
        requestEntity = rainbowRequestEntityCreator.create(criterion);
        return this;
    }

    @Override
    public void incrementPagination() {
        requireNonNull(requestEntity.getBody()).zwiekszPaginacje();
    }

    @Override
    public RequestEntity<RainbowPayloadTemplateACL> getRequestEntity() {
        return this.requestEntity;
    }

    @Override
    public ParameterizedTypeReference<RainbowResponseACL> getResponseType() {
        return new ParameterizedTypeReference<>() {
        };
    }
}
