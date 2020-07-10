package com.areo.design.holidays.component.request.entity.impl;

import com.areo.design.holidays.acl.PayloadPreparatorACL;
import com.areo.design.holidays.acl.impl.rainbow.RainbowPayloadTemplateACL;
import com.areo.design.holidays.component.request.creator.Request;
import com.areo.design.holidays.component.request.entity.HttpEntityCreator;
import com.areo.design.holidays.dto.SearchCriterionDto;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static com.google.common.collect.Lists.newArrayList;

@Slf4j
@RequiredArgsConstructor
public class RainbowHttpEntityCreator implements HttpEntityCreator {

    private final Gson gson;
    private final PayloadPreparatorACL<RainbowPayloadTemplateACL> payloadPreparatorACL;

    @Override
    public HttpEntity<String> create(SearchCriterionDto searchCriterionDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(newArrayList(MediaType.APPLICATION_JSON));
        RainbowPayloadTemplateACL payload = payloadPreparatorACL.prepare(searchCriterionDto);
        return new HttpEntity<>(gson.toJson(payload), headers);
    }

    @Override
    public HttpEntity<String> create(Request request) {
        HttpEntity<String> httpEntity = request.getHttpEntity();
        RainbowPayloadTemplateACL body = gson.fromJson(httpEntity.getBody(), RainbowPayloadTemplateACL.class);
        log.info("current read counter has {} offers", body.getPaginacja().getPrzeczytane());
        body.zwiekszPaginacje();
        log.info("read counter value after increase {}", body.getPaginacja().getPrzeczytane());
        return new HttpEntity<>(gson.toJson(body), httpEntity.getHeaders());
    }

}
