package com.areo.design.holidays.service.collector;

import com.areo.design.holidays.dto.HotelDto;
import com.areo.design.holidays.dto.SearchCriterionDto;

import java.util.Collection;

public interface OfferCollectorService {
    Collection<HotelDto> collect(SearchCriterionDto criterion);
}
