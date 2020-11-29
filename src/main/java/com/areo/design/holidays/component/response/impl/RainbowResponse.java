package com.areo.design.holidays.component.response.impl;

import com.areo.design.holidays.acl.ResponseBodyACL;
import com.areo.design.holidays.component.response.Response;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Builder
public class RainbowResponse implements Response {
    private final ResponseBodyACL bodyACL;
    private final HttpStatus statusCode;
    private final LocalDateTime timestamp;

    @Override
    public ResponseBodyACL getBody() {
        return bodyACL;
    }

    @Override
    public HttpStatus getStatusCode() {
        return statusCode;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
