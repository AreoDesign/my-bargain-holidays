package com.areo.design.holidays.component.request.sender.impl;

import com.areo.design.holidays.acl.ResponseACL;
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
public class RequestSenderDefault implements RequestSender {

    private final RestTemplate restTemplate;

    @Override
    public <RES extends ResponseACL> ResponseEntity<RES> send(Request request) {
        long startTime = System.currentTimeMillis();
        log.info("sending request to travel agency");
        ResponseEntity<RES> exchange = restTemplate.exchange(request.getRequestEntity(), request.getResponseType());
        log.info("response status: {} received in {} ms", exchange.getStatusCode(), System.currentTimeMillis() - startTime);
        return exchange;
    }
}
