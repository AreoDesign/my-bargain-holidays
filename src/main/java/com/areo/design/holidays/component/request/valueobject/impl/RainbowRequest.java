package com.areo.design.holidays.component.request.valueobject.impl;

import com.areo.design.holidays.acl.impl.rainbow.RainbowPayloadTemplateACL;
import com.areo.design.holidays.acl.impl.rainbow.RainbowResponseBodyACL;
import com.areo.design.holidays.component.request.valueobject.Request;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Value
@Builder
@RequiredArgsConstructor(staticName = "of")
public class RainbowRequest implements Request<RainbowPayloadTemplateACL, RainbowResponseBodyACL> {

    @NotNull
    RequestEntity<RainbowPayloadTemplateACL> requestEntity;

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
        RainbowRequest rainbowRequestWithIncreasedPagination = new RainbowRequest(this.requestEntity);
        Objects.requireNonNull(rainbowRequestWithIncreasedPagination.getRequestEntity().getBody()).zwiekszPaginacje();
        return rainbowRequestWithIncreasedPagination;
    }
}
