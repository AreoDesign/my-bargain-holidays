package com.areo.design.holidays.component.request.valueobject;

import com.areo.design.holidays.acl.PayloadTemplateACL;
import com.areo.design.holidays.acl.ResponseBodyACL;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;

public interface Request<PAYLOAD extends PayloadTemplateACL, RES extends ResponseBodyACL> {
    RequestEntity<PAYLOAD> getRequestEntity();

    ParameterizedTypeReference<RES> getResponseType();
}
