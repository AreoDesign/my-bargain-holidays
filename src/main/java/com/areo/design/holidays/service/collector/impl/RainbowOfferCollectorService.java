package com.areo.design.holidays.service.collector.impl;

import com.areo.design.holidays.acl.impl.rainbow.RainbowPayloadTemplateACL;
import com.areo.design.holidays.component.parser.ResponseParser;
import com.areo.design.holidays.component.request.httpentity.RequestEntityCreator;
import com.areo.design.holidays.component.request.sender.RequestSender;
import com.areo.design.holidays.component.request.valueobject.impl.RainbowRequest;
import com.areo.design.holidays.component.response.impl.RainbowResponse;
import com.areo.design.holidays.service.collector.OfferCollectorService;
import com.areo.design.holidays.valueobjects.offer.Answer;
import com.areo.design.holidays.valueobjects.requestor.SearchCriterion;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class RainbowOfferCollectorService implements OfferCollectorService {

    private final RequestEntityCreator<RainbowPayloadTemplateACL> rainbowRequestEntityCreator;
    private final RequestSender<RainbowRequest, RainbowResponse> rainbowRequestSender;
    private final ResponseParser<RainbowResponse> rainbowResponseParser;

    @Override
    public Answer collect(SearchCriterion criterion) {
        long startTime = System.currentTimeMillis();
        Collection<Answer> singleAnswers = Lists.newLinkedList();
        RainbowRequest request = RainbowRequest.of(rainbowRequestEntityCreator.create(criterion));
        RainbowResponse response = rainbowRequestSender.send(request);
        Answer answer = rainbowResponseParser.parse(response);
        while (answer.isNotEmpty() && response.getStatusCode().is2xxSuccessful()) {
            singleAnswers.add(answer);
            request = request.incrementDownloadCounter();
            response = rainbowRequestSender.send(request);
            answer = rainbowResponseParser.parse(response);
        }
        log.info("collection completed in {} ms", System.currentTimeMillis() - startTime);
        return Answer.of(singleAnswers.stream()
                .map(Answer::getHotelsWithTravelOffers)
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(LinkedList::new))
        );
    }
}
