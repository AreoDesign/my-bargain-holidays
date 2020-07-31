package com.areo.design.holidays.service.collector.impl;

import com.areo.design.holidays.component.parser.ResponseParser;
import com.areo.design.holidays.component.request.Request;
import com.areo.design.holidays.component.request.sender.RequestSender;
import com.areo.design.holidays.component.response.Response;
import com.areo.design.holidays.dto.offer.HotelDto;
import com.areo.design.holidays.dto.requestor.SearchCriterionDto;
import com.areo.design.holidays.service.collector.OfferCollectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

import static com.google.common.collect.Sets.newHashSet;

@Slf4j
@RequiredArgsConstructor
public class RainbowOfferCollectorService implements OfferCollectorService {

    private final Request rainbowRequest;
    private final RequestSender rainbowRequestSender;
    private final ResponseParser rainbowResponseParser;

    @Override
    public Collection<HotelDto> collect(SearchCriterionDto criterion) {
        long startTime = System.currentTimeMillis();
        log.info("offers collection started");
        Collection<HotelDto> result = newHashSet();
        boolean containsNewResults;
        Response response;
        Request request = rainbowRequest.initialize(criterion);
        do {
            response = rainbowRequestSender.send(request);
            Collection<HotelDto> parsedResponse = rainbowResponseParser.parse(response);
            containsNewResults = result.addAll(parsedResponse);
            rainbowRequest.incrementPagination();
        } while (response.getStatusCode().is2xxSuccessful() && containsNewResults);
        log.info("collection completed in {} ms", System.currentTimeMillis() - startTime);
        return result;
    }
}
