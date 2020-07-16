package com.areo.design.holidays.component.request.sender.impl;

import com.areo.design.holidays.component.request.Request;
import com.areo.design.holidays.component.request.sender.RequestSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class RequestSenderDefault<T> implements RequestSender<T> {

    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<T> send(Request request) {
        long startTime = System.currentTimeMillis();
        log.info("sending request to travel agency");
        ResponseEntity<T> exchange = restTemplate.exchange(
                request.getUri(),
                request.getHttpMethod(),
                request.getHttpEntity(),
                request.getResponseType()
        );
        log.info("response status: {} received in {} ms", exchange.getStatusCode(), System.currentTimeMillis() - startTime);
        return exchange;
    }
}
