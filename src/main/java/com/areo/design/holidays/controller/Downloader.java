package com.areo.design.holidays.controller;

import com.areo.design.holidays.converter.impl.HotelConverter;
import com.areo.design.holidays.converter.impl.SearchCriterionConverter;
import com.areo.design.holidays.dictionary.TravelAgency;
import com.areo.design.holidays.dto.HotelDto;
import com.areo.design.holidays.dto.SearchCriterionDto;
import com.areo.design.holidays.entity.SearchCriterionEntity;
import com.areo.design.holidays.repository.HotelRepository;
import com.areo.design.holidays.repository.SearchCriterionRepository;
import com.areo.design.holidays.service.OfferCollectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class Downloader {

    private final OfferCollectorService offerCollectorService;
    private final SearchCriterionRepository searchCriterionRepository;
    private final SearchCriterionConverter searchCriterionConverter;
    private final HotelConverter hotelConverter;
    private final HotelRepository hotelRepository;

    @GetMapping("/offers/{travelAgency}")
    public HttpStatus downloadOffers(@RequestParam TravelAgency travelAgency) {
        SearchCriterionEntity searchCriterionEntity = searchCriterionRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("no search criterion to run offer download!"));
        SearchCriterionDto criterionDto = searchCriterionConverter.convertToDto(searchCriterionEntity);
        Collection<HotelDto> collectedHotels = offerCollectorService.collect(travelAgency, criterionDto);
        hotelRepository.saveAll(hotelConverter.convertToEntities(collectedHotels));
        return HttpStatus.OK;
    }
}
