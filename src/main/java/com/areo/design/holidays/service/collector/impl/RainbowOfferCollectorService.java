package com.areo.design.holidays.service.collector.impl;

import com.areo.design.holidays.component.parser.ResponseParser;
import com.areo.design.holidays.component.request.RequestSender;
import com.areo.design.holidays.component.request.creator.Request;
import com.areo.design.holidays.component.request.creator.RequestCreator;
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

    private final RequestCreator rainbowRequestCreator;
    private final ResponseParser rainbowResponseParser;
    private final RequestSender rainbowRequestSender;

    @Override
    public Collection<HotelDto> collect(SearchCriterionDto criterion) {
        long startTime = System.currentTimeMillis();
        log.info("offers collection started");
        Collection<HotelDto> result = Sets.newLinkedHashSet();
        Request request = rainbowRequestCreator.create(criterion);
        ResponseEntity response = rainbowRequestSender.send(request);
        boolean responseContainsNewResults = true;
        while (response.getStatusCode().is2xxSuccessful() && responseContainsNewResults) {
            responseContainsNewResults = result.addAll(rainbowResponseParser.parse(response));
            request = rainbowRequestCreator.create(request);
            response = rainbowRequestSender.send(request);
        }
        log.info("collection completed in {} ms", System.currentTimeMillis() - startTime);
        return result;
    }
}
