package com.areo.design.holidays.component.response;

import com.areo.design.holidays.acl.ResponseACL;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public interface Response {
    ResponseACL getBody();

    HttpStatus getStatusCode();

    LocalDateTime getTimestamp();
}
