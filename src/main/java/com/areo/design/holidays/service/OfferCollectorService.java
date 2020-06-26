package com.areo.design.holidays.service;

import com.areo.design.holidays.dictionary.TravelAgency;
import com.areo.design.holidays.dto.HotelDto;
import com.areo.design.holidays.dto.SearchCriterionDto;

import java.util.Collection;

public interface OfferCollectorService {
    Collection<HotelDto> collect(TravelAgency agency, SearchCriterionDto criterion);
}
