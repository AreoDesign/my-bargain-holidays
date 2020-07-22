package com.areo.design.holidays.component.request.sender;

import com.areo.design.holidays.acl.ResponseACL;
import com.areo.design.holidays.component.request.Request;
import org.springframework.http.ResponseEntity;

public interface RequestSender {
    <RES extends ResponseACL> ResponseEntity<RES> send(Request request);
}
