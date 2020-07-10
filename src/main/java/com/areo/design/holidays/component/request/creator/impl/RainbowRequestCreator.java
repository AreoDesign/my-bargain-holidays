package com.areo.design.holidays.component.request.creator.impl;

import com.areo.design.holidays.component.request.creator.Request;
import com.areo.design.holidays.component.request.creator.RequestCreator;
import com.areo.design.holidays.component.request.entity.impl.RainbowHttpEntityCreator;
import com.areo.design.holidays.dto.SearchCriterionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.net.URI;

import static com.areo.design.holidays.dictionary.TravelAgency.RAINBOW_TOURS;
import static org.springframework.http.HttpMethod.POST;

@Slf4j
@RequiredArgsConstructor
public class RainbowRequestCreator implements RequestCreator {

    private final RainbowHttpEntityCreator entityCreator;

    private static HttpMethod getHttpMethod() {
        return POST;
    }

    private static URI getUri() {
        return RAINBOW_TOURS.getUri();
    }

    @Override
    public Request create(SearchCriterionDto criterion) {
        long startTime = System.currentTimeMillis();
        log.info("starting request creation for criterion: {}", criterion);
        Request request = new Request() {
            @Override
            public URI getUri() {
                return RainbowRequestCreator.getUri();
            }

            @Override
            public HttpMethod getHttpMethod() {
                return RainbowRequestCreator.getHttpMethod();
            }

            @Override
            public HttpEntity<String> getHttpEntity() {
                return entityCreator.create(criterion);
            }

            @Override
            public <V> ParameterizedTypeReference<V> getResponseType() {
                return new ParameterizedTypeReference<>() {
                };
            }
        };
        log.info("request created successfully in {} ms", System.currentTimeMillis() - startTime);
        return request;
    }

    @Override
    public Request create(Request request) {
        long startTime = System.currentTimeMillis();
        log.info("starting next request creation");
        Request nextRequest = new Request() {
            @Override
            public URI getUri() {
                return RainbowRequestCreator.getUri();
            }

            @Override
            public HttpMethod getHttpMethod() {
                return RainbowRequestCreator.getHttpMethod();
            }

            @Override
            public HttpEntity<String> getHttpEntity() {
                return entityCreator.create(request);
            }

            @Override
            public <T> ParameterizedTypeReference<T> getResponseType() {
                return new ParameterizedTypeReference<>() {
                };
            }
        };
        log.info("next request created successfully in {} ms (request body: {})", System.currentTimeMillis() - startTime, request.getHttpEntity().getBody());
        return nextRequest;
    }

}
