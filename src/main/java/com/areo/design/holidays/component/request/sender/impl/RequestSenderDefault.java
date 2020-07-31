package com.areo.design.holidays.component.request.sender.impl;

import com.areo.design.holidays.acl.ResponseACL;
import com.areo.design.holidays.component.request.Request;
import com.areo.design.holidays.component.request.sender.RequestSender;
import com.areo.design.holidays.component.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
@RequiredArgsConstructor
@Slf4j
public class RequestSenderDefault implements RequestSender {

    private final RestTemplate restTemplate;

    @Override
    public Response send(Request request) {
        long startTime = System.currentTimeMillis();
        log.info("sending request to travel agency");
        ResponseEntity<ResponseACL> exchange = restTemplate.exchange(request.getRequestEntity(), request.getResponseType());
        log.info("response status: {} received in {} ms", exchange.getStatusCode(), System.currentTimeMillis() - startTime);
        return new Response() {
            @Override
            public ResponseACL getBody() {
                return exchange.getBody();
            }

            @Override
            public HttpStatus getStatusCode() {
                return exchange.getStatusCode();
            }

            @Override
            public LocalDateTime getTimestamp() {
                return Instant.ofEpochMilli(exchange.getHeaders().getDate())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
            }
        };
    }
}
