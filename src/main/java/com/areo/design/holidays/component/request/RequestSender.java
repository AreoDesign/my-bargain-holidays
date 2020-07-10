package com.areo.design.holidays.component.request;

import com.areo.design.holidays.component.request.creator.Request;
import org.springframework.http.ResponseEntity;

public interface RequestSender<T> {
    ResponseEntity<T> send(Request request);
}
