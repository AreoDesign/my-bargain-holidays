package com.areo.design.holidays.component.request.httpEntity.impl;

import com.areo.design.holidays.acl.PayloadPreparatorACL;
import com.areo.design.holidays.acl.impl.rainbow.RainbowPayloadTemplateACL;
import com.areo.design.holidays.component.request.httpEntity.HttpEntityCreator;
import com.areo.design.holidays.dto.SearchCriterionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static com.google.common.collect.Lists.newArrayList;

@Slf4j
@RequiredArgsConstructor
public class RainbowHttpEntityCreator implements HttpEntityCreator {

    private final PayloadPreparatorACL<RainbowPayloadTemplateACL> payloadPreparatorACL;

    @Override
    public HttpEntity<RainbowPayloadTemplateACL> create(SearchCriterionDto searchCriterionDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(newArrayList(MediaType.APPLICATION_JSON));
        RainbowPayloadTemplateACL payload = payloadPreparatorACL.prepare(searchCriterionDto);
        return new HttpEntity<>(payload, headers);
    }

}
