package com.areo.design.holidays.service.request;

import com.areo.design.holidays.service.request.strategy.Request;
import org.springframework.http.ResponseEntity;

public interface RequestService {
    ResponseEntity getResponse(Request request);
}
