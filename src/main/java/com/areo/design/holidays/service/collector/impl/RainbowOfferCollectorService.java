package com.areo.design.holidays.service.collector.impl;

import com.areo.design.holidays.acl.impl.rainbow.RainbowResponseACL;
import com.areo.design.holidays.component.parser.ResponseParser;
import com.areo.design.holidays.component.request.Request;
import com.areo.design.holidays.component.request.impl.RainbowRequest;
import com.areo.design.holidays.component.request.sender.RequestSender;
import com.areo.design.holidays.dto.HotelDto;
import com.areo.design.holidays.dto.SearchCriterionDto;
import com.areo.design.holidays.service.collector.OfferCollectorService;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
public class RainbowOfferCollectorService implements OfferCollectorService {

    private final Request rainbowRequest;
    private final RequestSender<RainbowRequest> rainbowRequestSender;
    private final ResponseParser<RainbowResponseACL> rainbowResponseParser;

    @Override
    public Collection<HotelDto> collect(SearchCriterionDto criterion) {
        long startTime = System.currentTimeMillis();
        log.info("offers collection started");
        Collection<HotelDto> result = Sets.newLinkedHashSet();
        Request request = rainbowRequest.initialize(criterion);
        ResponseEntity response = rainbowRequestSender.send(request);
        boolean responseContainsNewResults = true;
        while (response.getStatusCode().is2xxSuccessful() && responseContainsNewResults) {
            responseContainsNewResults = result.addAll(rainbowResponseParser.parse(response));
            rainbowRequest.incrementPagination();
            response = rainbowRequestSender.send(request);
        }
        log.info("collection completed in {} ms", System.currentTimeMillis() - startTime);
        return result;
    }
}
