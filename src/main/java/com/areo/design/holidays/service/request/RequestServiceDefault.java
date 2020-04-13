package com.areo.design.holidays.service.request;

import com.areo.design.holidays.service.request.strategy.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RequestServiceDefault implements RequestService {

    private final RestTemplate restTemplate;

    @Autowired
    public RequestServiceDefault(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity getResponse(Request request) {
        return restTemplate.exchange(
                request.getUri(),
                request.getHttpMethod(),
                request.getHttpEntity(),
                request.getResponseType()
        );
    }
}
