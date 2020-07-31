package com.areo.design.holidays.component.request.httpentity.impl;

import com.areo.design.holidays.acl.PayloadPreparatorACL;
import com.areo.design.holidays.acl.impl.rainbow.RainbowPayloadTemplateACL;
import com.areo.design.holidays.component.request.httpentity.RequestEntityCreator;
import com.areo.design.holidays.dto.requestor.SearchCriterionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;

import static com.areo.design.holidays.dictionary.TravelAgency.RAINBOW_TOURS;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@RequiredArgsConstructor
public class RainbowRequestEntityCreator implements RequestEntityCreator<RainbowPayloadTemplateACL> {

    private final PayloadPreparatorACL<RainbowPayloadTemplateACL> payloadPreparatorACL;

    @Override
    public RequestEntity<RainbowPayloadTemplateACL> create(SearchCriterionDto searchCriterionDto) {
        RainbowPayloadTemplateACL payload = payloadPreparatorACL.prepare(searchCriterionDto);
        return RequestEntity
                .post(RAINBOW_TOURS.getUri())
                .accept(APPLICATION_JSON)
                .body(payload);
    }

}
