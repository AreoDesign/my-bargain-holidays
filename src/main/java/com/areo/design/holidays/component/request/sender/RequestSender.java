package com.areo.design.holidays.component.request.sender;

import com.areo.design.holidays.component.request.valueobject.Request;
import com.areo.design.holidays.component.response.Response;

public interface RequestSender<REQ extends Request, RES extends Response> {
    RES send(REQ request);
}
