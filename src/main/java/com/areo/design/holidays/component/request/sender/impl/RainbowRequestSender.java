package com.areo.design.holidays.component.request.sender.impl;

import com.areo.design.holidays.acl.impl.rainbow.RainbowResponseBodyACL;
import com.areo.design.holidays.component.request.sender.RequestSender;
import com.areo.design.holidays.component.request.valueobject.impl.RainbowRequest;
import com.areo.design.holidays.component.response.impl.RainbowResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.ZoneId;

@Slf4j
@RequiredArgsConstructor
public class RainbowRequestSender implements RequestSender<RainbowRequest, RainbowResponse> {

    private final RestTemplate restTemplate;

    @Override
    public RainbowResponse send(RainbowRequest request) {
        long startTime = System.currentTimeMillis();
        ResponseEntity<RainbowResponseBodyACL> exchange = restTemplate.exchange(request.getRequestEntity(), request.getResponseType());
        log.info("sending request to travel agency ended with response status: {} received in {} ms", exchange.getStatusCode(), System.currentTimeMillis() - startTime);
        return RainbowResponse.builder()
                .bodyACL(exchange.getBody())
                .statusCode(exchange.getStatusCode())
                .timestamp(Instant.ofEpochMilli(exchange.getHeaders().getDate()).atZone(ZoneId.systemDefault()).toLocalDateTime())
                .build();
    }
}
