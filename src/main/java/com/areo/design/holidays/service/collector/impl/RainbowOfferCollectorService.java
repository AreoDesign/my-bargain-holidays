package com.areo.design.holidays.service.collector.impl;

import com.areo.design.holidays.acl.impl.rainbow.RainbowPayloadTemplateACL;
import com.areo.design.holidays.component.parser.ResponseParser;
import com.areo.design.holidays.component.request.httpentity.RequestEntityCreator;
import com.areo.design.holidays.component.request.sender.RequestSender;
import com.areo.design.holidays.component.request.valueobject.impl.RainbowRequest;
import com.areo.design.holidays.component.response.impl.RainbowResponse;
import com.areo.design.holidays.dto.offer.HotelDto;
import com.areo.design.holidays.dto.requestor.SearchCriterionDto;
import com.areo.design.holidays.service.collector.OfferCollectorService;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
public class RainbowOfferCollectorService implements OfferCollectorService {

    private final RequestEntityCreator<RainbowPayloadTemplateACL> rainbowRequestEntityCreator;
    private final RequestSender<RainbowRequest, RainbowResponse> rainbowRequestSender;
    private final ResponseParser<RainbowResponse> rainbowResponseParser;

    @Override
    public Collection<HotelDto> collect(SearchCriterionDto criterion) {
        long startTime = System.currentTimeMillis();
        log.info("offers collection started");
        Collection<HotelDto> result = Lists.newLinkedList();
        RainbowRequest request = new RainbowRequest(rainbowRequestEntityCreator.create(criterion));
        RainbowResponse response = rainbowRequestSender.send(request);
        while (result.addAll(rainbowResponseParser.parse(response)) && response.getStatusCode().is2xxSuccessful()) {
            response = rainbowRequestSender.send(request.incrementDownloadCounter());
            result.addAll(rainbowResponseParser.parse(response));
        }
        log.info("collection completed in {} ms", System.currentTimeMillis() - startTime);
        return result;
    }
}
