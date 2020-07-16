package com.areo.design.holidays.component.request.impl;

import com.areo.design.holidays.acl.impl.rainbow.RainbowPayloadTemplateACL;
import com.areo.design.holidays.component.request.Request;
import com.areo.design.holidays.component.request.httpEntity.HttpEntityCreator;
import com.areo.design.holidays.dto.SearchCriterionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.net.URI;

import static com.areo.design.holidays.dictionary.TravelAgency.RAINBOW_TOURS;
import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpMethod.POST;

@RequiredArgsConstructor
public class RainbowRequest implements Request {

    private final HttpEntityCreator rainbowHttpEntityCreator;

    private HttpEntity<RainbowPayloadTemplateACL> httpEntity;

    @Override
    public Request initialize(SearchCriterionDto criterion) {
        httpEntity = rainbowHttpEntityCreator.create(criterion);
        return this;
    }

    @Override
    public Request incrementPagination() {
        requireNonNull(httpEntity.getBody()).zwiekszPaginacje();
        return this;
    }

    @Override
    public URI getUri() {
        return RAINBOW_TOURS.getUri();
    }

    @Override
    public HttpMethod getHttpMethod() {
        return POST;
    }

    @Override
    public HttpEntity<RainbowPayloadTemplateACL> getHttpEntity() {
        return this.httpEntity;
    }

    @Override
    public <T> ParameterizedTypeReference<T> getResponseType() {
        return new ParameterizedTypeReference<>() {
        };
    }
}
