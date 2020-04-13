package com.areo.design.holidays.service;

import com.areo.design.holidays.dictionary.TravelAgency;
import com.areo.design.holidays.dto.SearchCriterionDto;
import com.areo.design.holidays.service.request.RequestService;
import com.areo.design.holidays.service.request.strategy.Request;
import com.areo.design.holidays.service.request.strategy.RequestCreatorProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Service
@RequiredArgsConstructor
public class OfferCollectorServiceDefault implements OfferCollectorService {

    private final RequestCreatorProvider requestCreatorProvider;
    private final RequestService requestService;
//    private final ParserService parserService;

    @Override
    public HttpStatus collect(@NotBlank TravelAgency agency, @NotNull SearchCriterionDto criterion) {
        Request request = requestCreatorProvider.provide(agency).create(criterion);
        ResponseEntity response = requestService.getResponse(request);
//        parserService.parse(response);

        return response.getStatusCode();
    }
}
