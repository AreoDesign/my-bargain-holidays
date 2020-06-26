package com.areo.design.holidays.service;

import com.areo.design.holidays.dictionary.TravelAgency;
import com.areo.design.holidays.dto.HotelDto;
import com.areo.design.holidays.dto.SearchCriterionDto;
import com.areo.design.holidays.service.request.RequestService;
import com.areo.design.holidays.service.request.strategy.Request;
import com.areo.design.holidays.service.request.strategy.RequestCreator;
import com.areo.design.holidays.service.request.strategy.RequestCreatorProvider;
import com.areo.design.holidays.service.response.ParserService;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class OfferCollectorServiceDefault implements OfferCollectorService {

    private final RequestCreatorProvider requestCreatorProvider;
    private final RequestService requestService;
    private final ParserService parserService;

    @Override
    public Collection<HotelDto> collect(@NotNull TravelAgency agency, @NotNull SearchCriterionDto criterion) {
        Collection<HotelDto> result = Sets.newLinkedHashSet();
        RequestCreator requestCreator = requestCreatorProvider.provide(agency);
        Request request = requestCreator.create(criterion);
        ResponseEntity<String> response = requestService.getResponse(request);
        while (response.getStatusCode().is2xxSuccessful()) {
            result.addAll(parserService.parse(response));
            requestCreator.createNext(request);
            requestService.getResponse(request);
        }
        return result;
    }

}
