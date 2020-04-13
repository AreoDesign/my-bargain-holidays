package com.areo.design.holidays.service.request.strategy.impl.rainbow;

import com.areo.design.holidays.dto.SearchCriterionDto;
import com.areo.design.holidays.service.request.creator.impl.rainbow.RainbowHttpEntityCreator;
import com.areo.design.holidays.service.request.strategy.Request;
import com.areo.design.holidays.service.request.strategy.RequestCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.net.URI;

import static com.areo.design.holidays.dictionary.TravelAgency.RAINBOW_TOURS;

@Component
@RequiredArgsConstructor
public class RainbowRequestCreator implements RequestCreator {

    private final RainbowHttpEntityCreator entityCreator;

    @Override
    public Request create(SearchCriterionDto criterion) {
        return new Request() {
            @Override
            public URI getUri() {
                return RAINBOW_TOURS.getUri();
            }

            @Override
            public HttpMethod getHttpMethod() {
                return HttpMethod.POST;
            }

            @Override
            public HttpEntity<String> getHttpEntity() {
                return entityCreator.create(criterion);
            }

            @Override
            public ParameterizedTypeReference<String> getResponseType() {
                return new ParameterizedTypeReference<>() {
                };
            }
        };
    }
}
