package com.areo.design.holidays.component.request.valueobject.impl;

import com.areo.design.holidays.acl.impl.rainbow.RainbowPayloadTemplateACL;
import com.areo.design.holidays.acl.impl.rainbow.RainbowResponseBodyACL;
import com.areo.design.holidays.component.request.valueobject.Request;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;

import java.util.Objects;

@RequiredArgsConstructor
@Builder
public class RainbowRequest implements Request<RainbowPayloadTemplateACL, RainbowResponseBodyACL> {

    private final RequestEntity<RainbowPayloadTemplateACL> requestEntity;

    @Override
    public RequestEntity<RainbowPayloadTemplateACL> getRequestEntity() {
        return requestEntity;
    }

    @Override
    public ParameterizedTypeReference<RainbowResponseBodyACL> getResponseType() {
        return new ParameterizedTypeReference<>() {
        };
    }

    public RainbowRequest incrementDownloadCounter() {
        Objects.requireNonNull(this.requestEntity.getBody()).zwiekszPaginacje();
        return this;
    }
}
