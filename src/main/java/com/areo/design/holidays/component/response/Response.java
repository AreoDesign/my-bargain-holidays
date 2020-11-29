package com.areo.design.holidays.component.response;

import com.areo.design.holidays.acl.ResponseBodyACL;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public interface Response {
    ResponseBodyACL getBody();

    HttpStatus getStatusCode();

    LocalDateTime getTimestamp();
}
