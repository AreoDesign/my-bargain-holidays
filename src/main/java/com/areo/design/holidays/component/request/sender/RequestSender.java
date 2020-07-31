package com.areo.design.holidays.component.request.sender;

import com.areo.design.holidays.component.request.Request;
import com.areo.design.holidays.component.response.Response;

public interface RequestSender {
    Response send(Request request);
}
