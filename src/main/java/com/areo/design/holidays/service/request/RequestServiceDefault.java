package com.areo.design.holidays.service.request;

import com.areo.design.holidays.service.request.strategy.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RequestServiceDefault implements RequestService {

    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<String> getResponse(Request request) throws RestClientException {
        return restTemplate.exchange(
                request.getUri(),
                request.getHttpMethod(),
                request.getHttpEntity(),
                request.getResponseType()
        );
    }
}
