package com.areo.design.holidays.component.request.sender;

import com.areo.design.holidays.component.request.Request;
import org.springframework.http.ResponseEntity;

public interface RequestSender<T> {
    ResponseEntity<T> send(Request request);
}
